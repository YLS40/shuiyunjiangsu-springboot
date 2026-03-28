package com.example.mapper;

import com.example.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersMapper {

    int insert(Orders orders);

    void updateById(Orders orders);

    // 1. 删除@Delete注解，只保留方法定义（XML中已有该方法）
    void deleteById(Integer id);

    Orders selectById(Integer id);

    List<Orders> selectAll(Orders orders);

    @Select("select * from orders where order_no = #{orderNo}")
    Orders selectByOrderNo(String orderNo);

    // 2. 批量删除方法：只保留方法定义，SQL写在XML中
    void deleteBatchIds(@Param("ids") List<Integer> ids);
}