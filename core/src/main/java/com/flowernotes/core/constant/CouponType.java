package com.flowernotes.core.constant;

/**
 * @author cyk
 * @date 2018/7/30/030 10:04
 * @email choe0227@163.com
 * @desc 优惠券类型
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum  CouponType {
    full_reduce_coupon(1,"满减券"),
    new_user_coupon(2,"新人券")
    ;
    private Integer type;
    private String  desc;

    private CouponType(Integer type, String desc) {
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
