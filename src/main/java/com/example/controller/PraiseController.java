package com.example.controller;

import com.example.common.Result;
import com.example.entity.Praise;
import com.example.service.PraiseService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/praise")
public class PraiseController {

    @Resource
    private PraiseService praiseService;

    /**
     * 点赞/取消点赞
     */
    @PostMapping("/add")
    public Result add(@RequestBody Praise praise) {
        praiseService.add(praise);
        return Result.success();
    }

}
