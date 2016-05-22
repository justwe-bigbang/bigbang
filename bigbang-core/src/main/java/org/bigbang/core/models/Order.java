package org.bigbang.core.models;


import java.io.Serializable;

/**
 * @Title Order.java
 * @Description 订单模型（天猫订单和淘宝订单 数据结构一致）
 * @Author yzh yingzh@getui.com
 * @Date 05.22.2016
 */
public class Order implements Serializable{
    //订单ID
    private Long orderId;
    //买家ID
    private String buyerId;
    //商品ID
    private String productId;
    //卖家ID
    private String salerId;
    //订单创建时间
    private Long createTime;
    //订单总价格
    private Double totalPrice;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSalerId() {
        return salerId;
    }

    public void setSalerId(String salerId) {
        this.salerId = salerId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
