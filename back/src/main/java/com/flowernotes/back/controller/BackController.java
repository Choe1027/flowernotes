package com.flowernotes.back.controller;

import com.flowernotes.common.exception.BaseException;
import com.flowernotes.common.exception.BizException;
import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.exception.Error;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.common.utils.LoggerUtil;
import com.flowernotes.common.utils.SpringBeanUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.utils.RequestPool;
import com.flowernotes.core.utils.ResponseUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/back")
public class BackController  {

    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
    public void action(HttpServletRequest request, HttpServletResponse response,Integer call ,String json){
        String requestText = "";
        try {
            if (call == null){
                LoggerUtil.info(this.getClass(),"请求接口号为空");
            }
            RequestPool.set(request);
            LoggerUtil.info(this.getClass(),"请求参数：\ncall="+call +"\njson="+json);
            requestText = requestText = "call=" + call + ", json=" + json;
            Map<String, BizController> maps = SpringBeanUtil.getBeanOfType(BizController.class);
            maps.remove("bizController"); //移除自身
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

}
