package com.flowernotes.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cyk
 * @date 2018/8/8/008 10:42
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface BizController {

    boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json);
}
