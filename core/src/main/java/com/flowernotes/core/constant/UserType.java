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
    private Integer type;
    private String desc;

    UserType(Integer type, String desc) {
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
