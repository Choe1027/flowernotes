package com.flowernotes.core.constant;

/**
 * @author cyk
 * @date 2018/7/30/030 10:21
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum NoticeType {
    /**只带确认框*/
    confirm_type(1,"确认框"),
    /**提示框*/
    notice_type(2,"操作框")
    ;
    private Integer type;
    private String desc;

    private NoticeType(Integer type, String desc) {
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
