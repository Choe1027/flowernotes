package com.flowernotes.core.bean.jpush;

import com.flowernotes.common.base.BaseBean;

import java.util.Map;

import cn.jmessage.api.message.MessageType;

/**
 * @author cyk
 * @date 2018/7/31/031 14:49
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class JMsgBean extends BaseBean {

    public enum TargetType{


    }
    //版本号
    private Integer version = 1;

    //目标类型  single - 个人，group - 群组
    private String targetType;

    //目标人
    private String targetId;

    //发送人类型 admin
    private String fromType;

    //消息类型 text - 文本，image - 图片, custom - 自定义消息（msg_body为json对象即可，服务端不做校验）voice - 语音
    private MessageType msgType;

    //发送人
    private String fromId;

    //是否离线存储
    private boolean offline;

    //消息体
    private String content;

    //扩展字段
    private Map<String,String> extras;
}
