package org.bigbang.core.models;


/**
 * @Title Pay.java
 * @Description 付款信息
 * @Author yzh yingzh@getui.com
 * @Date 05.22.2016
 */
public class Pay {
    //该条支付消息 所属的订单ID
    private Long orderId;
    //支付金额,<=订单的总价格
    private Double payAmount;
    //支付金额的来源:
    /*
    * 0:支付宝
    * 1:红包/代金卷
    * 2:银联
    * 3:其他
    * */
    private Short paySource;
    //支付平台
    /**
     * 0:pc
     * 1:无线终端
     */
    private Short payPlatform;
    //创建时间
    private Long createTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Short getPaySource() {
        return paySource;
    }

    public void setPaySource(Short paySource) {
        this.paySource = paySource;
    }

    public Short getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(Short payPlatform) {
        this.payPlatform = payPlatform;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
