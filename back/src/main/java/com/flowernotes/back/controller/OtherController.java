package com.flowernotes.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cyk
 * @date 2018/8/1/001 16:31
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class OtherController implements BizController {
    @Override
    public boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json) {

        switch (call){
            // 发送验证码
            case 901:{
                break;
            }
        }
        return true;
    }
}
