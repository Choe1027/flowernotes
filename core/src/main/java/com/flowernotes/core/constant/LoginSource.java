package com.flowernotes.core.constant;

/**
 * @author cyk
 * @date 2018/7/31/031 15:23
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum  LoginSource {
    app(1,"APP"),
    wechat(2,"微信公众号"),
    small_program(3,"小程序"),
    unknown(-1,"未知登录源")
    ;
    private Integer code;
    private String desc;

    LoginSource(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
