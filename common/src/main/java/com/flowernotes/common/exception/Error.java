package com.flowernotes.common.exception;

/**
 * @author cyk
 * @date 2018/7/30/030 09:50
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum  Error {
    /** 未知异常*/
    notKnow_error(-1, "未知异常,未知异常，请与客服联系"),
    /** 找不到请求号*/
    system_call_notfind(104, "找不到请求号","未找到对应的请求号,服务异常，请与客服联系"),
    /** 加解密异常*/
    system_msg_AES_error(102,"加解密异常" ,"加解密异常"),
    /** 系统异常*/
    system_error(100, "系统异常, 服务异常,请与客服联系"),
    /** 空指针异常,服务器错误*/
    system_nullpoint_error(101, "空指针异常,服务器错误"),
    /**id不能为空*/
    id_must_not_be_null(203,"id参数不能为空"),
    /** 公共异常 */
    biz_error(201, "", ""),
    /** 请求参数不是一个json*/
    biz_param_is_not_a_json(202,"请求数据中不是一个json格式数据"),
    /**token为空*/
    biz_nottoken(204, "请求加密接口，未建立加密token", "请求超时，请重试"),
    /** 用户名 密码不能为空*/
    common_account_password_cannot_be_null(205, "用户名或者密码不能为空", "用户名或者密码不能为空"),
    /** 用户已经存在*/
    common_user_is_exist(206, "用户已经存在","用户已存在"),
    common_user_not_exist(207,"用户不存在" ,"用户不存在" ),
    account_password_is_wrong(208,"账号或密码错误" , "账号或密码错误"),
    common_lost_params(209,"缺少参数","缺少参数"),
    common_obj_not_exist(210,"对象不存在","对象不存在"),
    common_code_is_wrong(211, "验证码有误","验证码有误");




    /** 编码值 */
    private int code;
    /** 备注，提示 */
    private String remark;
    /** 异常显示问题 */
    private String[] viewMsg;
    /**
     * 异常枚举类
     * @param code	编码值
     * @param remark	备注，提示
     */
    private Error(int code, String remark, String... viewMsg) {
        this.code = code;
        this.remark = remark;
        this.viewMsg = viewMsg;
    }

    /** 依据异常编码值获取异常 */
    public static Error getErrorCode(int code) {
        for(Error ec : Error.values()) {
            if(ec.getCode() == code) {
                return ec;
            }
        }
        return notKnow_error;
    }

    public int getCode() {
        return code;
    }
    public String getRemark() {
        return remark;
    }
    public String getViewMsg() {
        return viewMsg!=null&&viewMsg.length>0?viewMsg[0]:remark;
    }
}
