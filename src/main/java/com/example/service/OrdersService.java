package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Orders;
import com.example.entity.Tourism;
import com.example.exception.CustomException;
import com.example.mapper.OrdersMapper;
import com.example.mapper.TourismMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层方法（无需实现类，直接作为服务类使用）
 */
@Service
public class OrdersService {

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private TourismMapper tourismMapper;

    @Transactional
    public void add(Orders orders) {
        Account currentUser = TokenUtils.getCurrentUser();
        orders.setUserId(currentUser.getId());
        orders.setTime(DateUtil.now());
        orders.setStatus("待支付");
        orders.setOrderNo(IdUtil.getSnowflakeNextIdStr());  // 订单编号使用随机数，避免重复
        // 直接下单 之前要判断库存
        Integer tourismId = orders.getTourismId();
        Tourism tourism = tourismMapper.selectById(tourismId);
        if (tourism == null) {
            throw new CustomException("500", "商品不存在");
        }
        if (tourism.getStore() < orders.getNum()) {  // 商品数量不足
            throw new CustomException("500", "库存不足");
        }
        ordersMapper.insert(orders);
        tourism.setStore(tourism.getStore() - orders.getNum());
        tourismMapper.updateById(tourism);
    }

    public void updateAccountById(Orders orders) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            Integer tourismId = orders.getTourismId();
            Tourism tourism = tourismMapper.selectById(tourismId);
            if (tourism != null) {
                tourism.setStore(tourism.getStore() + orders.getNum());
                tourismMapper.updateById(tourism);
            }
        }
        ordersMapper.updateById(orders);
    }

    public void updateById(Orders orders) {
        ordersMapper.updateById(orders);
    }

    /**
     * 单个删除（核心优化：加校验+异常抛出，让Controller捕获）
     */
    public void deleteById(Integer id) {
        // 1. 校验ID
        if (id == null || id <= 0) {
            throw new CustomException("500", "订单ID不能为空且必须为正数");
        }
        // 2. 检查订单是否存在
        Orders existOrder = ordersMapper.selectById(id);
        if (existOrder == null) {
            throw new CustomException("500", "ID为" + id + "的订单不存在");
        }
        // 3. 执行删除
        ordersMapper.deleteById(id);
    }

    /**
     * 批量删除（核心优化：加校验，循环删除时捕获异常）
     */
    public void deleteBatch(List<Integer> ids) {
        // 1. 校验ID列表
        if (ids == null || ids.isEmpty()) {
            throw new CustomException("500", "请选择至少一条要删除的订单");
        }
        // 2. 循环删除（保持你原有逻辑，加异常校验）
        for (Integer id : ids) {
            if (id == null || id <= 0) {
                throw new CustomException("500", "订单ID" + id + "不合法，批量删除失败");
            }
            Orders existOrder = ordersMapper.selectById(id);
            if (existOrder == null) {
                throw new CustomException("500", "ID为" + id + "的订单不存在，批量删除失败");
            }
            ordersMapper.deleteById(id);
        }
    }

    public Orders selectById(Integer id) {
        return ordersMapper.selectById(id);
    }

    public List<Orders> selectAll(Orders orders) {
        return ordersMapper.selectAll(orders);
    }

    public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals(RoleEnum.USER.name())) {
            orders.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Orders> list = ordersMapper.selectAll(orders);
        return PageInfo.of(list);
    }

    public Orders selectByOrderNo(String orderNo) {
        return ordersMapper.selectByOrderNo(orderNo);
    }
}