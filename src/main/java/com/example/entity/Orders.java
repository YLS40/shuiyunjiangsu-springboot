package com.example.entity;

import java.math.BigDecimal;

/**
 * 订单信息
 */
public class Orders {
    /** ID */
    private Integer id;
    /** 订单编号 */
    private String orderNo;
    /** 商品名称 */
    private String name;
    /** 商品图片 */
    private String tourismImg;
    /** 商品价格 */
    private BigDecimal tourismPrice;
    /** 商品ID */
    private Integer tourismId;
    /** 购买数量 */
    private Integer num;
    /** 商品总价 */
    private BigDecimal total;
    /** 下单人ID */
    private Integer userId;
    /** 下单时间 */
    private String time;
    /** 支付单号 */
    private String payNo;
    /** 支付时间 */
    private String payTime;
    /** 订单状态 */
    private String status;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTourismImg() {
        return tourismImg;
    }

    public void setTourismImg(String tourismImg) {
        this.tourismImg = tourismImg;
    }

    public BigDecimal getTourismPrice() {
        return tourismPrice;
    }

    public void setTourismPrice(BigDecimal tourismPrice) {
        this.tourismPrice = tourismPrice;
    }

    public Integer getTourismId() {
        return tourismId;
    }

    public void setTourismId(Integer tourismId) {
        this.tourismId = tourismId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}