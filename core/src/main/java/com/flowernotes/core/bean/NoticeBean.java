package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:38
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "notice")
public class NoticeBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**通知类型 1确认框 2操作框*/
    private Integer type;
    /**通知标题*/
    private String title;
    /**通知内容*/
    private String content;
    /**取消框按钮文案*/
    private String cancel_btn_msg;
    /**确认框按钮文案*/
    private String confirm_btn_msg;
    /**显示通知的页面*/
    private String page;
    /**状态 0启用 1停用*/
    private Integer state;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCancel_btn_msg() {
        return cancel_btn_msg;
    }

    public void setCancel_btn_msg(String cancel_btn_msg) {
        this.cancel_btn_msg = cancel_btn_msg;
    }

    public String getConfirm_btn_msg() {
        return confirm_btn_msg;
    }

    public void setConfirm_btn_msg(String confirm_btn_msg) {
        this.confirm_btn_msg = confirm_btn_msg;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
