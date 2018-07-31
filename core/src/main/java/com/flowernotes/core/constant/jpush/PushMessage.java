package com.flowernotes.core.constant.jpush;

/**
 * @author cyk
 * @date 2018/7/31/031 15:21
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum PushMessage {

    ;
    private Integer code;
    private String codeString;

    PushMessage(Integer code, String codeString) {
        this.code = code;
        this.codeString = codeString;
    }

}
