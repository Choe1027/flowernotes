package com.flowernotes.core.constant;

/**
 * @author cyk
 * @date 2018/7/30/030 10:37
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum  OrderStatus {

    waiting_pay(0,"待支付"),
    waiting_send(1,"待配送"),
    sending(2,"待配送"),
    wating_evalute(3,"待评价"),
    finish(4,"已完成"),
    back_cancel(-1,"取消"),
    system_cancel(-2,"系统取消"),
    ;
    private Integer type;
    private String  desc;

    private OrderStatus(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
