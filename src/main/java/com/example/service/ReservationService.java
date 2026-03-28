package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.DateCountDTO;
import com.example.entity.Reservation;
import com.example.mapper.ReservationMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService extends ServiceImpl<ReservationMapper, Reservation> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public boolean save(Reservation reservation) {
        return super.save(reservation);
    }

    public List<DateCountDTO> getReservationCount(String startDate, String endDate, String dateField) {
        LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<>();
        LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);




        // 拼接日期筛选条件（适配LocalDate类型的字段）
        if ("create_time".equals(dateField)) {
            queryWrapper.between(Reservation::getCreateTime, start, end);
        } else if ("travel_date".equals(dateField)) {
            queryWrapper.between(Reservation::getTravelDate, start, end);
        }

        // 2. 查询符合条件的所有预约数据
        List<Reservation> reservationList = this.list(queryWrapper);

        // 3. 按日期分组统计数量（核心：修复LocalDate格式化）
        Map<String, Integer> dateCountMap = reservationList.stream()
                .collect(Collectors.groupingBy(
                        // 直接格式化LocalDate，避免类型不匹配
                        reservation -> {
                            if ("create_time".equals(dateField)) {
                                return reservation.getCreateTime().format(DATE_FORMATTER);
                            } else {
                                return reservation.getTravelDate().format(DATE_FORMATTER);
                            }
                        },
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        // 4. 生成日期范围内的所有日期，填充统计值（无数据则为0）
        List<DateCountDTO> resultList = new ArrayList<>();
        LocalDate currentDate = start;

        while (!currentDate.isAfter(end)) {
            String dateStr = currentDate.format(DATE_FORMATTER);
            DateCountDTO dto = new DateCountDTO();
            dto.setName(dateStr);
            // 有数据则取统计值，无则为0
            dto.setValue(dateCountMap.getOrDefault(dateStr, 0));
            resultList.add(dto);
            // 日期+1天（LocalDate的加法操作）
            currentDate = currentDate.plusDays(1);
        }

        return resultList;
    }
}