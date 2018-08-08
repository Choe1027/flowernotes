package com.flowernotes.api.controller;

import com.flowernotes.common.exception.BaseException;
import com.flowernotes.common.exception.BizException;
import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.exception.Error;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.common.utils.SpringBeanUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.constant.Context;
import com.flowernotes.core.utils.RequestPool;
import com.flowernotes.core.utils.ResponseUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author cyk
 * @date 2018/8/1/001 14:30
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Controller
@RequestMapping("/api")
public class ApiController {
    /**
     * 是否需要加密
     */
    private static boolean isEncrypt = false;

    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
    public void action(HttpServletRequest request, HttpServletResponse response,Integer call ,String json){
        String requestText = "";
        try {
            if (call == null){
                LoggerUtil.info(this.getClass(),"请求接口号为空");
                return;
            }
            RequestPool.set(request);
            LoggerUtil.info(this.getClass(),"请求参数：\ncall="+call +"\njson="+json);
            requestText = requestText = "call=" + call + ", json=" + json;
            Map<String, BizController> maps = SpringBeanUtil.getBeanOfType(BizController.class);
            maps.remove("bizController"); //移除自身
            // 解密
            json = execJson(json,call);
            LoggerUtil.info(this.getClass(),"请求参数：\ncall="+call +"\njson="+json);
            json = StringUtil.isEmpty(json) ? "{}" : json;
            // 判断是否是json格式
            if (!JsonUtil.isJson(json)){
                throw new BizException(Error.biz_param_is_not_a_json);
            }
            for (BizController controller: maps.values()) {
                if (controller.action(request,response,call,json)){
                    return;
                }
            }
            LoggerUtil.info(this.getClass(),"未找到对应的接口号");
            throw new CommonException(Error.system_call_notfind);
        }catch (Exception e){
            if (e instanceof BaseException) {
                BaseException be = (BaseException) e;
                be.setRequestText(requestText);
                ResponseUtil.outError(response, e);
                be.printStackTrace();
            } else {
                Error errorCode = Error.notKnow_error;
                if (e instanceof NullPointerException) {
                    errorCode = Error.system_nullpoint_error;
                }
                CommonException ce = new CommonException(errorCode, e);
                ce.setRequestText(requestText);
                ResponseUtil.outError(response, String.valueOf(Error.notKnow_error.getCode()));
                ce.printStackTrace();
            }
        }finally {
            RequestPool.remove();
        }
    }


    /** 处理和校验请求数据 */
    private String execJson(String oldJson, int call) {
        ResponseUtil.hasAES.set(true);
//		// 测试使用
		if(!isEncrypt) {
			ResponseUtil.hasAES.set(false);
			return oldJson;
		}
        HttpSession session = RequestPool.getSession();
        if(call < 10000) {
            ResponseUtil.hasAES.set(false);
            return oldJson;
        }
        if(StringUtil.isEmpty(oldJson)) {
            return oldJson;
        }
        if(oldJson.contains("%")) {
            oldJson = StringUtil.urlStrToString(oldJson);
        }
        String token = (String)session.getAttribute(Context.session_token);
        LoggerUtil.info(this.getClass(), "获取到后台token：" + token);
        if(StringUtil.isEmpty(token)) {
            throw new CommonException(Error.biz_nottoken);
        }
        try {
            LoggerUtil.info(this.getClass(), "\nuserId="+RequestPool.getUserId()+"_"+RequestPool.getUserType()+"\ntoken="+token+"\njson="+oldJson+"\nsessionId="+RequestPool.getSession().getId());
            return StringUtil.AESToString(oldJson, token);
        } catch (Exception e) {
            LoggerUtil.info(this.getClass(),"抛出异常接口号："+call);
            throw new CommonException(Error.system_msg_AES_error, e);
        }
    }

}
