package com.flowernotes.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cyk
 * @date 2018/8/1/001 14:39
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface BizController {

    /**
     * 业务处理方法
     * @param request	请求
     * @param response	返回
     * @param call	接口号(call<10000，则不会校验是否登陆，也无法通过RequestPool获取用户id和类型)
     * @param json	参数
     * @return 是否call处理的接口号已在本类中
     * @throws Exception
     */
    public boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json);
}
