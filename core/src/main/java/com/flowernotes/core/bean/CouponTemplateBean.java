package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:26
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "coupon_template")
public class CouponTemplateBean extends BaseBean {


    private static final long serialVersionUID = 1L;

    /**优惠卷名*/
    private String name;
    /**优惠券描述*/
    private String desc;
    /**优惠券类型*/
    private String type;
    /**需要满足金额*/
    private Double require_money;
    /**状态0 启用，1禁用*/
    private Integer state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getRequire_money() {
        return require_money;
    }

    public void setRequire_money(Double require_money) {
        this.require_money = require_money;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
