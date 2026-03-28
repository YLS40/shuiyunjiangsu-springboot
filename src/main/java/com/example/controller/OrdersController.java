package com.example.controller;

import com.example.common.Result;
import com.example.entity.Orders;
import com.example.exception.CustomException;
import com.example.service.OrdersService;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private OrdersService ordersService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Orders orders) {
        try {
            ordersService.add(orders);
            return Result.success("订单新增成功");
        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "订单新增失败：" + e.getMessage());
        }
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Orders orders) {
        try {
            ordersService.updateById(orders);
            return Result.success("订单修改成功");
        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "订单修改失败：" + e.getMessage());
        }
    }

    /**
     * 修改账户相关
     */
    @PutMapping("/updateAccount")
    public Result updateAccount(@RequestBody Orders orders) {
        try {
            ordersService.updateAccountById(orders);
            return Result.success("订单账户信息修改成功");
        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "订单账户信息修改失败：" + e.getMessage());
        }
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            ordersService.deleteById(id);
            return Result.success("订单删除成功");
        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "订单删除失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除（修正方法名，避免和单个删除重名）
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        try {
            ordersService.deleteBatch(ids);
            return Result.success("批量删除成功，共删除" + ids.size() + "条订单");
        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "批量删除失败：" + e.getMessage());
        }
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        try {
            Orders orders = ordersService.selectById(id);
            return Result.success(orders);
        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "订单查询失败：" + e.getMessage());
        }
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Orders orders) {
        try {
            List<Orders> list = ordersService.selectAll(orders);
            return Result.success(list);
        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "查询所有订单失败：" + e.getMessage());
        }
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Orders orders,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            if (pageNum < 1) pageNum = 1;
            if (pageSize < 1 || pageSize > 100) pageSize = 10;

            PageInfo<Orders> pageInfo = ordersService.selectPage(orders, pageNum, pageSize);
            return Result.success(pageInfo);
        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "订单分页查询失败：" + e.getMessage());
        }
    }

}