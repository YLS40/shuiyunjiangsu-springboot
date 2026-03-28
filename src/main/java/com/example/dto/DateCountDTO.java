package com.example.dto;

import lombok.Data;

/**
 * 日期统计结果DTO
 */
@Data
public class DateCountDTO {
    // 日期（格式：yyyy-MM-dd）
    private String name;
    // 该日期的统计值（预约数）
    private Integer value;
}