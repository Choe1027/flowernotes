package com.flowernotes.core.constant;

/**
 * @author cyk
 * @date 2018/7/30/030 10:31
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum  PaymentType {

    offline(1,"线下支付"),
    wechat(2,"微信支付"),
    balance(3,"余额支付")
    ;

    private Integer type;
    private String desc;

    PaymentType(Integer type, String desc) {
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
