package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("reservation")
public class Reservation {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("tourism_id")
    private Long tourismId;

    @TableField("tourism_name")
    private String tourismName;

    @TableField("tourism_price")
    private Double tourismPrice;

    private Integer num;

    private Double total;

    @TableField("travel_date")
    private LocalDate travelDate;

    private String phone;

    @TableField("create_time")
    private LocalDateTime createTime;

    private String status;

    // 兼容前端可能传的name字段（保留，防止遗漏）
    public void setName(String name) {
        this.tourismName = name;
    }

    // 兼容前端字符串日期转换（优化：增加空值判断）
    public void setTravelDate(String travelDateStr) {
        if (travelDateStr == null || travelDateStr.trim().isEmpty()) {
            this.travelDate = null;
            return;
        }
        String pureDate = travelDateStr.contains("T") ? travelDateStr.split("T")[0] : travelDateStr;
        this.travelDate = LocalDate.parse(pureDate);
    }

    // 新增：兼容前端传的数字类型tourismId（比如Integer）
    public void setTourismId(Number tourismId) {
        this.tourismId = tourismId.longValue();
    }
}