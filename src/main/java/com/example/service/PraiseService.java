package com.example.service;

import com.example.entity.Praise;
import com.example.mapper.PraiseMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PraiseService {

    @Resource
    private PraiseMapper praiseMapper;

    // 点赞 或者 取消点赞
    public void add(Praise praise) {
        Praise dbPraise = praiseMapper.selectByFidAndUserId(praise.getFid(), praise.getUserId());
        if (dbPraise != null) {  // 说明用户给当前这个帖子点过赞了
            praiseMapper.deleteByFidAndUserId(praise.getFid(), praise.getUserId()); // 取消点赞
        } else {
            praiseMapper.insert(praise);
        }
    }

}
