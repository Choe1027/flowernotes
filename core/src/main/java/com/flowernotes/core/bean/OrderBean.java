package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:40
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "order")
public class OrderBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**订单编号*/
    private String ordercode;
    /**用户ID*/
    private Integer user_id;
    /**订单种类 1普通订单 2秒杀订单 3预购订单 4拍卖订单*/
    private Integer type;
    /**地址详情*/
    private String address_deatil;
    /**收货人名称*/
    private String receiver_name;
    /**收货人电话*/
    private String receiver_mobile;
    /**用户优惠券id*/
    private Integer coupon_id;
    /**优惠券金额*/
    private Double coupon_money;
    /**总金额*/
    private Double total_money;
    /**实付金额*/
    private Double real_pay_money;
    /**省级编码*/
    private String province_code;
    /**区编码*/
    private String city_code;
    /**县编码*/
    private String county_code;
    /**城镇编码*/
    private String town_code;
    /**支付方式， 1、 线下支付 2、微信支付 3、余额支付*/
    private Integer payment_type;
    /**订单状态：0.待支付 1.待送货 2.送货中 3.未评价 4.已评价 -1取消*/
    private Integer status;
    /**备注*/
    private String remark;
    /**配送时间段开始*/
    private Long send_time_start;
    /**配送时间段结束*/
    private Long send_time_end;
    /**支付时间*/
    private Long pay_time;
    /**送达时间*/
    private Long arrive_time;


    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress_deatil() {
        return address_deatil;
    }

    public void setAddress_deatil(String address_deatil) {
        this.address_deatil = address_deatil;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Double getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(Double coupon_money) {
        this.coupon_money = coupon_money;
    }

    public Double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Double total_money) {
        this.total_money = total_money;
    }

    public Double getReal_pay_money() {
        return real_pay_money;
    }

    public void setReal_pay_money(Double real_pay_money) {
        this.real_pay_money = real_pay_money;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCounty_code() {
        return county_code;
    }

    public void setCounty_code(String county_code) {
        this.county_code = county_code;
    }

    public String getTown_code() {
        return town_code;
    }

    public void setTown_code(String town_code) {
        this.town_code = town_code;
    }

    public Integer getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(Integer payment_type) {
        this.payment_type = payment_type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSend_time_start() {
        return send_time_start;
    }

    public void setSend_time_start(Long send_time_start) {
        this.send_time_start = send_time_start;
    }

    public Long getSend_time_end() {
        return send_time_end;
    }

    public void setSend_time_end(Long send_time_end) {
        this.send_time_end = send_time_end;
    }

    public Long getPay_time() {
        return pay_time;
    }

    public void setPay_time(Long pay_time) {
        this.pay_time = pay_time;
    }

    public Long getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(Long arrive_time) {
        this.arrive_time = arrive_time;
    }
}
