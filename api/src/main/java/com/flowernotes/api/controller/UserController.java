package com.flowernotes.api.controller;

import com.flowernotes.api.service.UserService;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.core.utils.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cyk
 * @date 2018/8/8/008 13:29
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Controller
public class UserController implements BizController {

    @Autowired
    private UserService userService;

    @Override
    public boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json) {
        switch (call){
            case 101:{
                Map<String, Object> param = JsonUtil.jsonToMap(json);
                String code = (String) param.get("code");
                Map<String, Object> resultMap = userService.wechatAuthorize(code);
                ResponseUtil.outSuccess(response,resultMap);
                break;
            }
            case 104:{
                ResponseUtil.outSuccess(response,System.currentTimeMillis());
                break;
            }
            default:{
                return false;
            }
        }
        return true;
    }
}
