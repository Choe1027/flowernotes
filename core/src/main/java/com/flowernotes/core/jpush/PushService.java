package com.flowernotes.core.jpush;

import com.flowernotes.core.bean.jpush.JMsgBean;
import com.flowernotes.core.bean.jpush.JpushBean;
import com.flowernotes.core.constant.UserType;
import com.flowernotes.core.constant.jpush.IosEnv;
import com.flowernotes.core.constant.jpush.PushMessage;

import java.util.List;
import java.util.Map;

import cn.jmessage.api.message.MessageClient;
import cn.jmessage.api.message.SendMessageResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.notification.PlatformNotification;

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
     * 为web IM 生成签名,web端根据这进行注册
     *
     * @return
     */
    Map<String, Object> createWebImSignature();

    /**
     * 发送通知消息
     *
     * @param pushPayload
     * @param userType
     */
    PushResult sendNotify(PushPayload pushPayload, UserType userType);

    /**
     * 发送IM消息
     *
     * @param jMsgBean
     */
    SendMessageResult sendMsg(JMsgBean jMsgBean, UserType userType);

    /**
     * 根据RegistrationId发送通知消息
     */
    PushResult sendNotifyByRegistrationIds(JpushBean jpushBean);

    /**
     * 根据别名推送消息
     *
     * @param jpushBean
     */
    PushResult sendNotifyByAlias(JpushBean jpushBean);

    /**
     * 根据标签推送消息
     *
     * @param jpushBean
     */
    PushResult sendNotifyByTags(JpushBean jpushBean);

    /**
     * 构建registrationId 推送对象
     *
     * @param jpushBean
     * @return
     */
    PushPayload buildNotifyMessageWithRegistrationId(JpushBean jpushBean);

    /**
     * 构建别名推送对象
     *
     * @param jpushBean
     * @return
     */
    PushPayload buildNotifyMessageWithAlias(JpushBean jpushBean);

    /**
     * 构建标签推送对象
     *
     * @param jpushBean
     * @return
     */
    PushPayload buildNotifyMessageWithTags(JpushBean jpushBean);

    /**
     * @param jpushBean
     * @return
     */
    PushPayload.Builder buildCommonNotify(JpushBean jpushBean);

    /**
     * 构建ios的推送通知
     *
     * @param jpushBean
     * @return
     */
    PlatformNotification buildIosNotify(JpushBean jpushBean);

    /**
     * 构建android平台的推送通知
     *
     * @param jpushBean
     * @return
     */
    PlatformNotification buildAndroidNotify(JpushBean jpushBean);

    /**
     * 根据当前用户获IOS的生产环境(目前用户的环境类型是保存在redis缓存中，如果环境不同，需要另外自己进行解决)
     * ios推送会经过Apns ,
     *
     * @param userId
     * @return
     */
    IosEnv getEnvByCurrentUserId(Long userId, UserType userType);

    /**
     * 根据用户类型获取 推送通知client
     *
     * @param userType
     * @return
     */
    JPushClient getJPushClientByUserType(UserType userType);

    /**
     * 根据用户类型获取 im推送client
     *
     * @param userType
     * @return
     */
    MessageClient getMessageClientByUserType(UserType userType);

    /**
     * 根据别名获取registrationId列表
     *
     * @param alias
     * @return
     */
    List<String> getRegistrationIdsByAlias(String alias, UserType userType);
}

