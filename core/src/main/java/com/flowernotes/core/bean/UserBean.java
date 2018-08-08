package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 10:52
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "user")
public class UserBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**昵称*/
    private String name;
    /**真实名称*/
    private String real_name;
    /**用户积分*/
    private Integer score;
    /**手机号码*/
    private String mobile;
    /**头像*/
    private String head;
    /**用户等级*/
    private Integer grade_name;
    /**用户等级id*/
    private Integer grade_id;
    /**是否vip 1 是 0不是*/
    private Integer is_vip;
    /**余额*/
    private Double balance;
    /**上次登录时间*/
    private Long last_login_time;
    /** 微信登录的openid*/
    private String openid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Integer getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(Integer grade_name) {
        this.grade_name = grade_name;
    }

    public Integer getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
    }

    public Integer getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(Integer is_vip) {
        this.is_vip = is_vip;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Long last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
