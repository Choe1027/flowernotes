package com.flowernotes.core.bean.jpush;

import com.flowernotes.common.base.BaseBean;
import com.flowernotes.core.constant.UserType;

import java.util.List;
import java.util.Map;

/**
 * @author cyk
 * @date 2018/7/31/031 17:00
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class JpushBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    //用户编号
    private List<Long> userIds;

    //用户标签
    private List<String> tags;

    //设备编号
    private List<String> registrationIds;

    // 用户类型
    private UserType userType;

    //消息类型  0-通知 1-透传消息
    private Integer msgType;

    //消息标题
    private String title;

    //通知内容
    private String alert;

    //消息内容
    private String content;

    //消息类型 1-text 2-image
    private ContentType contentType;

    //离线消息保存时间
    private Long timeToLive = 86400L;

    //需要覆盖的消息编号
    private Long overrideMsgId;

    //扩展信息
    private Map<String,String> extras;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getRegistrationIds() {
        return registrationIds;
    }

    public void setRegistrationIds(List<String> registrationIds) {
        this.registrationIds = registrationIds;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Long getOverrideMsgId() {
        return overrideMsgId;
    }

    public void setOverrideMsgId(Long overrideMsgId) {
        this.overrideMsgId = overrideMsgId;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public enum ContentType{
        notKnow(-1, "未知"),
        text(1,"text"),
        image(2,"image");

        private Integer type;
        private String remark;

        ContentType(Integer type, String remark){
            this.type = type;
            this.remark = remark;
        }

        /**
         * 依据类型获取枚举
         * @param type	最后登录源值
         * @return	枚举对象
         */
        public static ContentType forType(Integer type) {
            for(ContentType ls : ContentType.values()) {
                if(ls.getType().equals(type)) {
                    return ls;
                }
            }
            return notKnow;
        }


        public Integer getType() {
            return type;
        }
        public String getRemark() {
            return remark;
        }
    }
}
