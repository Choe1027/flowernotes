package com.flowernotes.api.controller;

import org.springframework.stereotype.Controller;

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
    @Override
    public boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json) {
        return false;
    }
}
