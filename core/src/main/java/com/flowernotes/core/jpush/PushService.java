package com.flowernotes.core.jpush;

import com.flowernotes.core.bean.jpush.JMsgBean;
import com.flowernotes.core.bean.jpush.JpushBean;
import com.flowernotes.core.constant.UserType;
import com.flowernotes.core.constant.jpush.PushMessage;

import java.util.List;
import java.util.Map;

import cn.jpush.api.push.model.PushPayload;

/**
 * @author cyk
 * @date 2018/7/31/031 15:22
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface PushService {

    /**
     * @param userId   用户id
     * @param userType 用户类型
     * @param message  推送消息内容
     */
    void sendNotify(Long userId, UserType userType, PushMessage message);

    /**
     * @param userId         用户id
     * @param userType       用户类型
     * @param message        推送消息内容
     * @param registrationId 唯一的registrationId
     * @param loginSource    登录源
     */
    void sendNotify(Long userId, UserType userType, PushMessage message, String registrationId, Integer loginSource);


    /**
     * 获取消息发送状态
     *
     * @param msgId  消息编号
     * @param regIds 设备标识
     * @param date   消息发送日期
     * @return
     */
    Integer getMsgStatus(Long msgId, UserType userType, List<String> regIds, String date);

    /**
     * 为web IM 生成签名
     *
     * @return
     */
    Map<String, Object> createWebImSignature();

    /**
     * 发送通知消息
     * @param pushPayload
     * @param userType
     */
    void sendNotify(PushPayload pushPayload,UserType userType);

    /**
     * 发送IM消息
     * @param jMsgBean
     */
    void sendMsg(JMsgBean jMsgBean);

    /**
     * 根据RegistrationId发送通知消息
     */
    void sendNotifyByRegistrationIds(JpushBean jpushBean);

    /**
     * 根据别名推送消息
     * @param jpushBean
     */
    void sendNotifyByAlias(JpushBean jpushBean);

    /**
     * 根据标签推送消息
     * @param jpushBean
     */
    void sendNotifyByTags(JpushBean jpushBean);

    /**
     * 构建registrationId 推送对象
     * @param jpushBean
     * @return
     */
    PushPayload buildNotifyMessageWithRegistrationId(JpushBean jpushBean);

    /**
     * 构建别名推送对象
     * @param jpushBean
     * @return
     */
    PushPayload buildNotifyMessageWithAlias(JpushBean jpushBean);

    /**
     * 构建标签推送对象
     * @param jpushBean
     * @return
     */
    PushPayload buildNotifyMessageWithTags(JpushBean jpushBean);
}

