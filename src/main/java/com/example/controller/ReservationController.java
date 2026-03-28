package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Result;
import com.example.dto.DateCountDTO;
import com.example.entity.Reservation;
import com.example.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationMapper reservationMapper;

    // 全局日期格式化器
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 1. 查所有预约（修复：改用Mapper自定义的listAll，确保返回所有数据）
    @GetMapping("/list")
    public Result list() {
        // 直接调用Mapper中明确的SELECT * FROM reservation，避免MyBatis-Plus字段映射问题
        List<Map<String, Object>> list = reservationMapper.listAll();
        return Result.success(list);
    }

    // 2. 查预约总数（供Home.vue调用，实时返回数据库真实数量）
    @GetMapping("/countTotal")
    public Result countTotal() {
        // 调用Mapper中明确的COUNT(*)，保证和list接口数据一致
        Long total = reservationMapper.countAll();
        return Result.success(total);
    }

    // 3. 近7天预约趋势（供Home.vue的折线图调用）
    @GetMapping("/reservationData")
    public Result getReservationData() {
        List<DateCountDTO> resultList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 6; i >= 0; i--) {
            LocalDate currentDate = today.minusDays(i);
            String dateStr = currentDate.format(DATE_FORMATTER);

            // 精准匹配当天的创建时间
            LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
            wrapper.ge(Reservation::getCreateTime, LocalDateTime.of(currentDate, LocalDateTime.MIN.toLocalTime()))
                    .le(Reservation::getCreateTime, LocalDateTime.of(currentDate, LocalDateTime.MAX.toLocalTime()));

            Long count = reservationMapper.selectCount(wrapper);
            DateCountDTO dto = new DateCountDTO();
            dto.setName(dateStr);
            dto.setValue(count.intValue());
            resultList.add(dto);
        }
        return Result.success(resultList);
    }

    // 4. 自定义日期范围统计
    @GetMapping("/count")
    public Result getReservationCount(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "create_time") String dateField
    ) {
        List<DateCountDTO> resultList = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);

        LocalDate currentDate = start;
        while (!currentDate.isAfter(end)) {
            String dateStr = currentDate.format(DATE_FORMATTER);
            DateCountDTO dto = new DateCountDTO();
            dto.setName(dateStr);

            LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
            if ("create_time".equals(dateField)) {
                wrapper.ge(Reservation::getCreateTime, LocalDateTime.of(currentDate, LocalDateTime.MIN.toLocalTime()))
                        .le(Reservation::getCreateTime, LocalDateTime.of(currentDate, LocalDateTime.MAX.toLocalTime()));
            } else if ("travel_date".equals(dateField)) {
                wrapper.eq(Reservation::getTravelDate, dateStr);
            }

            Long count = reservationMapper.selectCount(wrapper);
            dto.setValue(count.intValue());
            resultList.add(dto);

            currentDate = currentDate.plusDays(1);
        }

        return Result.success(resultList);
    }

    // 5. 新增预约
    @PostMapping("/add")
    public Result addReservation(@RequestBody Reservation reservation) {
        try {
            reservation.setCreateTime(LocalDateTime.now());
            reservation.setStatus("待确认");

            int rows = reservationMapper.insert(reservation);
            return rows > 0 ? Result.success("预约新增成功") : Result.error("预约新增失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("新增失败：" + e.getMessage());
        }
    }

    // 6. 单个删除预约
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            int rows = reservationMapper.deleteById(id);
            if (rows > 0) {
                return Result.success("删除成功");
            } else {
                return Result.error("删除失败：预约记录不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败：" + e.getMessage());
        }
    }

    // 7. 批量删除预约
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return Result.error("请选择要删除的预约记录");
            }
            LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Reservation::getId, ids);
            int rows = reservationMapper.delete(wrapper);
            if (rows > 0) {
                return Result.success("批量删除成功，共删除" + rows + "条记录");
            } else {
                return Result.error("批量删除失败：未找到对应预约记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("批量删除失败：" + e.getMessage());
        }
    }
}