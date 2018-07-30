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
    /** 系统异常*/
    system_error(9999, "系统异常, 服务异常,请与客服联系"),
    /**id不能为空*/
    id_must_not_be_null(101,"id参数不能为空"),
    /** 公共异常 */
    biz_error(201, "", "");

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
