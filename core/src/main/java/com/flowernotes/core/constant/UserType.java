package com.flowernotes.core.constant;

/**
 * @author cyk
 * @date 2018/7/31/031 15:28
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum UserType {
    user(1,"用户"),
    delivery_man(2,"配送员")
    ;
    private Integer code;
    private String desc;

    UserType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
