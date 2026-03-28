package com.example.controller;

import com.example.common.Result;
import com.example.entity.Routes;
import com.example.service.RoutesService;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/routes")
public class RoutesController {

    @Resource
    private RoutesService routesService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Routes routes) {
        routesService.add(routes);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Routes routes) {
        routesService.updateById(routes);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        routesService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        routesService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Routes routes = routesService.selectById(id);
        return Result.success(routes);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Routes routes) {
        List<Routes> list = routesService.selectAll(routes);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Routes routes,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Routes> pageInfo = routesService.selectPage(routes, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
