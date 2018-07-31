package com.flowernotes.core.jpush;

import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.bean.jpush.JMsgBean;
import com.flowernotes.core.bean.jpush.JpushBean;
import com.flowernotes.core.constant.UserType;
import com.flowernotes.core.constant.jpush.JPushConstant;
import com.flowernotes.core.constant.jpush.PushMessage;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;
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

    @Override
    public void sendNotify(Long userId, UserType userType, PushMessage message) {

    }

    @Override
    public void sendNotify(Long userId, UserType userType, PushMessage message, String registrationId, Integer loginSource) {

    }

    @Override
    public void sendMsg(JMsgBean jMsgBean) {

    }

    @Override
    public void sendNotify(PushPayload pushPayload, UserType userType) {

    }

    @Override
    public void sendNotifyByRegistrationIds(JpushBean jpushBean) {

    }

    @Override
    public void sendNotifyByAlias(JpushBean jpushBean) {

    }

    @Override
    public void sendNotifyByTags(JpushBean jpushBean) {

    }

    @Override
    public PushPayload buildNotifyMessageWithRegistrationId(JpushBean jpushBean) {
        return null;
    }

    @Override
    public PushPayload buildNotifyMessageWithAlias(JpushBean jpushBean) {
        return null;
    }

    @Override
    public PushPayload buildNotifyMessageWithTags(JpushBean jpushBean) {
        return null;
    }

    @Override
    public Integer getMsgStatus(Long msgId, UserType userType, List<String> regIds, String date) {
        Integer status = 0;
        JPushClient jPushClient = null;
        if (userType == UserType.user){
            jPushClient = new JPushClient(JPushConstant.JPUSH_MASTER_SCRET_FLOWERNOTES, JPushConstant.JPUSH_APP_KEY_FLOWERNOTES);
        }else {
            //TODO 其他类型
        }
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
        builder.append(JPushConstant.APP_KEY_STRING + JPushConstant.EQUAL_STRING)
                .append(JPushConstant.JPUSH_APP_KEY_FLOWERNOTES)
                .append(JPushConstant.AND_STRING)
                .append(JPushConstant.TIMESTAMP_STRING + JPushConstant.EQUAL_STRING)
                .append(timestamp)
                .append(JPushConstant.AND_STRING)
                .append(JPushConstant.RANDOM_STR_STRING + JPushConstant.EQUAL_STRING)
                .append(randomStr)
                .append(JPushConstant.AND_STRING)
                .append(JPushConstant.KEY_STRING + JPushConstant.EQUAL_STRING)
                .append(JPushConstant.JPUSH_MASTER_SCRET_FLOWERNOTES);
        String signature = StringUtil.toMD5(builder.toString());
        retMap.put(JPushConstant.APP_KEY_STRING, JPushConstant.JPUSH_APP_KEY_FLOWERNOTES);
        retMap.put(JPushConstant.RANDOM_STR_STRING, randomStr);
        retMap.put(JPushConstant.SIGNATURE_STRING, signature);
        retMap.put(JPushConstant.TIMESTAMP_STRING, timestamp);
        return retMap;
    }
}
