package com.flowernotes.core.jpush;

import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.bean.jpush.JMsgBean;
import com.flowernotes.core.bean.jpush.JpushBean;
import com.flowernotes.core.cache.CacheService;
import com.flowernotes.core.constant.Context;
import com.flowernotes.core.constant.UserType;
import com.flowernotes.core.constant.jpush.IosEnv;
import com.flowernotes.core.constant.jpush.JPushConstant;
import com.flowernotes.core.constant.jpush.PushMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.message.MessageClient;
import cn.jmessage.api.message.MessageType;
import cn.jmessage.api.message.SendMessageResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;
import cn.jpush.api.report.MessageStatus;
import cn.jpush.api.report.model.CheckMessagePayload;

/**
 * @author cyk
 * @date 2018/7/31/031 15:48
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Component
public class PushServiceImpl implements PushService {

    @Autowired
    private CacheService cacheService;

    @Override
    public void sendNotify(Long userId, UserType userType, PushMessage message) {

    }

    @Override
    public void sendNotify(Long userId, UserType userType, PushMessage message, String registrationId, Integer loginSource) {

    }

    @Override
    public SendMessageResult sendMsg(JMsgBean jMsgBean ,UserType userType) {
        MessageClient messageClient = getMessageClientByUserType(userType);
        MessageBody.Builder msgBodyBuilder = new MessageBody.Builder();
        if (jMsgBean.getExtras() != null && !jMsgBean.getExtras().isEmpty()) {
            msgBodyBuilder.addExtras(jMsgBean.getExtras());
        }
        msgBodyBuilder.addExtra(JPushConstant.MSG_CONTENT,jMsgBean.getContent());
        MessagePayload.Builder payloadBuilder = new MessagePayload.Builder();
        payloadBuilder.setVersion(jMsgBean.getVersion());
        payloadBuilder.setTargetType(jMsgBean.getTargetType());
        payloadBuilder.setTargetId(jMsgBean.getTargetId());
        payloadBuilder.setFromType(jMsgBean.getFromType());
        payloadBuilder.setFromId(jMsgBean.getFromId());
        payloadBuilder.setMessageType(MessageType.CUSTOM);
        payloadBuilder.setMessageBody(msgBodyBuilder.build());
        SendMessageResult result = null;
        try {
            result = messageClient.sendMessage(payloadBuilder.build());
        } catch (APIConnectionException e) {
            LoggerUtil.error(this.getClass(), "Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LoggerUtil.error(this.getClass(), "Error response from JPush server. Should review and fix it. ", e);
            LoggerUtil.error(this.getClass(), "HTTP Status: " + e.getStatus());
            LoggerUtil.error(this.getClass(), "Error Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PushResult sendNotify(PushPayload pushPayload, UserType userType) {
        JPushClient jpushClient = getJPushClientByUserType(userType);

        PushResult result = null;
        try {
            result = jpushClient.sendPush(pushPayload);
        } catch (APIConnectionException e) {
            LoggerUtil.error(this.getClass(), "Sendno: " + pushPayload.getSendno());
            LoggerUtil.error(this.getClass(), e.getMessage());
            e.printStackTrace();
        } catch (APIRequestException e) {
            printRequestError(e);
        }
        return result;
    }

    /**
     * 异常打印
     *
     * @param e
     */
    private void printRequestError(APIRequestException e) {
        LoggerUtil.error(this.getClass(), "Error response from JPush server. Should review and fix it. ", e);
        LoggerUtil.error(this.getClass(), "HTTP Status: " + e.getStatus());
        LoggerUtil.error(this.getClass(), "Error Code: " + e.getErrorCode());
        LoggerUtil.error(this.getClass(), "Error Message: " + e.getErrorMessage());
        LoggerUtil.error(this.getClass(), "Msg ID: " + e.getMsgId());
        e.printStackTrace();
    }

    @Override
    public PushResult sendNotifyByRegistrationIds(JpushBean jpushBean) {
        PushPayload pushPayload = buildNotifyMessageWithRegistrationId(jpushBean);
        return sendNotify(pushPayload,jpushBean.getUserType());
    }

    @Override
    public PushResult sendNotifyByAlias(JpushBean jpushBean) {
        PushPayload pushPayload = buildNotifyMessageWithAlias(jpushBean);
        return sendNotify(pushPayload,jpushBean.getUserType());
    }

    @Override
    public PushResult sendNotifyByTags(JpushBean jpushBean) {
        PushPayload pushPayload = buildNotifyMessageWithTags(jpushBean);
        return sendNotify(pushPayload,jpushBean.getUserType());
    }

    @Override
    public PushPayload buildNotifyMessageWithRegistrationId(JpushBean jpushBean) {
        PushPayload.Builder builder = buildCommonNotify(jpushBean);
        if (jpushBean.getRegistrationIds() != null && !jpushBean.getRegistrationIds().isEmpty()) {
            builder.setAudience(Audience.registrationId(jpushBean.getRegistrationIds()));
        }
        return builder.build();
    }

    @Override
    public PushPayload buildNotifyMessageWithAlias(JpushBean jpushBean) {
        PushPayload.Builder builder = buildCommonNotify(jpushBean);
        if (jpushBean.getAlias() != null && !jpushBean.getAlias().isEmpty()){
            builder.setAudience(Audience.alias(jpushBean.getAlias()));
        }
        return builder.build();
    }

    @Override
    public PushPayload buildNotifyMessageWithTags(JpushBean jpushBean) {
        PushPayload.Builder builder = buildCommonNotify(jpushBean);
        if (jpushBean.getTags() != null && !jpushBean.getTags().isEmpty()) {
            //取标签的并集，比如：永康A端用户
            builder.setAudience(Audience.tag_and(jpushBean.getTags()));
        }
        return builder.build();
    }


    @Override
    public PushPayload.Builder buildCommonNotify(JpushBean jpushBean) {
        PushPayload.Builder builder = PushPayload.newBuilder();
        //设置推送平台为所有
        builder.setPlatform(Platform.all());
        builder.setNotification(Notification.newBuilder()
                .setAlert("") // 如果Alert值为空，则不会显示在通知栏
                .addPlatformNotification(buildAndroidNotify(jpushBean))
                .addPlatformNotification(buildIosNotify(jpushBean))
                .build());
        //设置IOS端推送环境为开发环境，默认为生产环境
        Options.Builder optionBuilder = Options.newBuilder();
        optionBuilder.setTimeToLive(jpushBean.getTimeToLive());
        IosEnv currentEnv = getEnvByCurrentUserId(jpushBean.getUserIds().get(0), jpushBean.getUserType());
        if (currentEnv == IosEnv.dev) {
            optionBuilder.setApnsProduction(false);
        } else {
            optionBuilder.setApnsProduction(true);
        }
        builder.setOptions(optionBuilder.build());
        return builder;
    }

    @Override
    public PlatformNotification buildAndroidNotify(JpushBean jpushBean) {
        AndroidNotification android = AndroidNotification.newBuilder()
                .setAlert(StringUtil.isEmpty(jpushBean.getAlert()) ? "" : jpushBean.getAlert()) //如果alert为空，则不会显示在通知栏
                .addExtras((jpushBean.getExtras() != null && !jpushBean.getExtras().isEmpty()) ? jpushBean.getExtras() : null)
                .build();
        return android;
    }

    @Override
    public PlatformNotification buildIosNotify(JpushBean jpushBean) {
        IosNotification.Builder builder = IosNotification.newBuilder();
        builder.setAlert(StringUtil.isEmpty(jpushBean.getAlert()) ? "" : jpushBean.getAlert());//如果alert为空，则不会显示在通知栏
        builder.setBadge(1);
        if (jpushBean.getExtras() != null && !jpushBean.getExtras().isEmpty()) {
            builder.addExtras(jpushBean.getExtras());
            //可以添加声音，需要在app本地能够找到这个声音文件
//            if (PushEnum.oder_find_B_to_B.getCall().toString().equals(jPushBean.getExtras().get(Context.MSG_CALL))){
//                builder.setSound("bt_sounds.mp3");
//            }
        }
        return builder.build();
    }

    @Override
    public Integer getMsgStatus(Long msgId, UserType userType, List<String> regIds, String date) {
        Integer status = 0;
        JPushClient jPushClient = getJPushClientByUserType(userType);
        CheckMessagePayload payload = CheckMessagePayload.newBuilder()
                .setMsgId(msgId)
                .addRegistrationIds(regIds)
                .setDate(date)
                .build();
        try {
            Map<String, MessageStatus> map = jPushClient.getMessageStatus(payload);
            for (Map.Entry<String, MessageStatus> entry : map.entrySet()) {
                LoggerUtil.info(this.getClass(), "registrationId: " + entry.getKey() + " status: " + entry.getValue().getStatus());
                status = entry.getValue().getStatus();
            }
        } catch (APIConnectionException e) {
            LoggerUtil.error(this.getClass(), "Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LoggerUtil.error(this.getClass(), "Error response from JPush server. Should review and fix it. ", e);
            LoggerUtil.info(this.getClass(), "HTTP Status: " + e.getStatus());
            LoggerUtil.info(this.getClass(), "Error Code: " + e.getErrorCode());
            LoggerUtil.info(this.getClass(), "Error Message: " + e.getErrorMessage());
        }
        return status;
    }

    @Override
    public Map<String, Object> createWebImSignature() {
        Map<String, Object> retMap = new HashMap<>();
        String randomStr = StringUtil.getRandomString(32);
        Long timestamp = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append(JPushConstant.APP_KEY + JPushConstant.EQUAL)
                .append(JPushConstant.JPUSH_APP_KEY_FLOWERNOTES)
                .append(JPushConstant.AND)
                .append(JPushConstant.TIMESTAMP + JPushConstant.EQUAL)
                .append(timestamp)
                .append(JPushConstant.AND)
                .append(JPushConstant.RANDOM_STR + JPushConstant.EQUAL)
                .append(randomStr)
                .append(JPushConstant.AND)
                .append(JPushConstant.KEY + JPushConstant.EQUAL)
                .append(JPushConstant.JPUSH_MASTER_SCRET_FLOWERNOTES);
        String signature = StringUtil.toMD5(builder.toString());
        retMap.put(JPushConstant.APP_KEY, JPushConstant.JPUSH_APP_KEY_FLOWERNOTES);
        retMap.put(JPushConstant.RANDOM_STR, randomStr);
        retMap.put(JPushConstant.SIGNATURE, signature);
        retMap.put(JPushConstant.TIMESTAMP, timestamp);
        return retMap;
    }


    @Override
    public IosEnv getEnvByCurrentUserId(Long userId, UserType userType) {
        String mapValue = cacheService.getMapValue(Context.CACHE_PUSH_LOGIN_ENV, userType.getType() + Context.UNDER_LINE + userId);
        IosEnv env = IosEnv.getEnvByValue(mapValue);
        return env;
    }

    @Override
    public List<String> getRegistrationIdsByAlias(String alias ,UserType userType) {
        JPushClient jpushClient = getJPushClientByUserType(userType);
        List<String> registrationIds = null;
        AliasDeviceListResult result;
        try {
            result = jpushClient.getAliasDeviceList(alias, null);
            registrationIds = result.registration_ids;
        } catch (APIConnectionException e) {
            LoggerUtil.error(this.getClass(), e.getMessage());
            e.printStackTrace();
        } catch (APIRequestException e) {
            printRequestError(e);
        }
        return registrationIds;
    }

    @Override
    public JPushClient getJPushClientByUserType(UserType userType) {
        JPushClient jPushClient = null;
        if (userType.getType() == UserType.user.getType()) {
            jPushClient = new JPushClient(JPushConstant.JPUSH_MASTER_SCRET_FLOWERNOTES, JPushConstant.JPUSH_APP_KEY_FLOWERNOTES);
        } else {
            // TODO: 2018/8/1/001  其他的用户类型 暂时只使用用户
            jPushClient = new JPushClient(JPushConstant.JPUSH_MASTER_SCRET_FLOWERNOTES, JPushConstant.JPUSH_APP_KEY_FLOWERNOTES);
        }
        return jPushClient;
    }

    @Override
    public MessageClient getMessageClientByUserType(UserType userType) {
        MessageClient messageClient = null;
        if (userType != null && userType == UserType.user){
            messageClient = new MessageClient(JPushConstant.JPUSH_APP_KEY_FLOWERNOTES, JPushConstant.JPUSH_MASTER_SCRET_FLOWERNOTES);
        }else {
            // TODO: 2018/8/1/001  其他的用户类型 暂时只使用用户
            messageClient = new MessageClient(JPushConstant.JPUSH_APP_KEY_FLOWERNOTES, JPushConstant.JPUSH_MASTER_SCRET_FLOWERNOTES);
        }
        return messageClient;
    }
}
