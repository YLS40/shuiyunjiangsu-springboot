package com.example.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.ArticleMapper;
import com.example.mapper.OrdersMapper;
import com.example.mapper.ReservationMapper;
import com.example.mapper.TourismMapper;
import com.example.mapper.TravelsMapper;
import com.example.service.AdminService;
import com.example.service.UserService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Resource
    private TravelsMapper travelsMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TourismMapper tourismMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private ReservationMapper reservationMapper;

    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account loginAccount = null;
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            loginAccount = adminService.login(account);
        } else if (RoleEnum.USER.name().equals(account.getRole())) {
            loginAccount = userService.login(account);
        }
        return Result.success(loginAccount);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        } else if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }

    @GetMapping("/count")
    public Result count() {
        long travelsCount = travelsMapper.selectAll(null).size();
        long articleCount = articleMapper.selectAll(null).size();
        long tourismCount = tourismMapper.selectAll(null).size();
        long ordersCount = ordersMapper.selectAll(null).size();
        Long reservationCount = reservationMapper.countAll();
        System.out.println("预约总数查询结果: " + reservationCount);
        Dict dict = Dict.create().set("travelsCount", travelsCount)
                .set("articleCount", articleCount)
                .set("tourismCount", tourismCount)
                .set("ordersCount", ordersCount)
                .set("reservationCount", reservationCount != null ? reservationCount : 0L);
        System.out.println("返回的统计数据: " + dict);
        return Result.success(dict);
    }

    @GetMapping("/ordersData")
    public Result selectOrdersData() {
        List<Dict> dictList = new ArrayList<>();
        Date date = new Date();
        DateTime start = DateUtil.offsetDay(date, -31);
        DateTime end = DateUtil.offsetDay(date, -1);
        List<String> dateList = DateUtil.rangeToList(start, end, DateField.DAY_OF_YEAR).stream()
                .map(DateUtil::formatDate)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        List<Orders> ordersList = ordersMapper.selectAll(null);
        for (String day : dateList) {
            Integer ordersNum = ordersList.stream().filter(orders -> orders.getTime().contains(day))
                    .map(Orders::getNum).reduce(Integer::sum).orElse(0);
            Dict dict = Dict.create().set("name", day).set("value", ordersNum);
            dictList.add(dict);
        }
        System.out.println(dictList);
        return Result.success(dictList);
    }

    @GetMapping("/travelsData")
    public Result selectTravelsData() {
        List<Dict> dictList = new ArrayList<>();
        Date date = new Date();
        DateTime start = DateUtil.offsetDay(date, -6);  // 从6天前开始
        DateTime end = DateUtil.offsetDay(date, 0);     // 到今天结束
        List<String> dateList = DateUtil.rangeToList(start, end, DateField.DAY_OF_YEAR).stream()
                .map(DateUtil::formatDate)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        List<Travels> travelsList = travelsMapper.selectAll(null);
        for (String day : dateList) {
            long count = travelsList.stream().filter(travels -> travels.getTime().contains(day)).count();
            Dict dict = Dict.create().set("name", day).set("value", count);
            dictList.add(dict);
        }
        System.out.println(dictList);
        return Result.success(dictList);
    }

}