package com.example.mapper;

import com.example.entity.Feedback;

import java.util.List;

public interface FeedbackMapper {

    int insert(Feedback feedback);

    void updateById(Feedback feedback);

    void deleteById(Integer id);

    Feedback selectById(Integer id);

    List<Feedback> selectAll(Feedback feedback);

}
