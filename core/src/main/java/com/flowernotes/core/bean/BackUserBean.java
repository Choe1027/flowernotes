package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:10
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "back_user")
public class BackUserBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    /**昵称*/
    private String name;
    /**账号*/
    private String account;
    /**密码*/
    private String password;
    /**头像url*/
    private String head;
    /**状态 0 启用 1禁用*/
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
