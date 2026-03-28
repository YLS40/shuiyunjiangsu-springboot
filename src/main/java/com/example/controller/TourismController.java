package com.example.controller;

import com.example.common.Result;
import com.example.entity.Tourism;
import com.example.service.TourismService;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/tourism")
public class TourismController {

    @Resource
    private TourismService tourismService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Tourism tourism) {
        tourismService.add(tourism);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Tourism tourism) {
        tourismService.updateById(tourism);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        tourismService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        tourismService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Tourism tourism = tourismService.selectById(id);
        return Result.success(tourism);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Tourism tourism) {
        List<Tourism> list = tourismService.selectAll(tourism);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Tourism tourism,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Tourism> pageInfo = tourismService.selectPage(tourism, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
