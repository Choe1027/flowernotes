package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:42
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "recharge_record")
public class RechargeRecordBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**用户id*/
    private Integer user_id;
    /**支付方式*/
    private Integer payment_type;
    /**充值金额*/
    private Double rechare_money;
    /**返充金额*/
    private Double handsel_money;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(Integer payment_type) {
        this.payment_type = payment_type;
    }

    public Double getRechare_money() {
        return rechare_money;
    }

    public void setRechare_money(Double rechare_money) {
        this.rechare_money = rechare_money;
    }

    public Double getHandsel_money() {
        return handsel_money;
    }

    public void setHandsel_money(Double handsel_money) {
        this.handsel_money = handsel_money;
    }
}
