package com.flowernotes.back.controller;

import com.flowernotes.core.constant.Context;
import com.flowernotes.core.utils.CodeGenerator;
import com.flowernotes.core.utils.RandomImgCodeUtil;
import com.flowernotes.core.utils.RequestPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
@Controller
public class OtherController implements BizController {

    @Autowired
    private RandomImgCodeUtil randomImgCodeUtil;


    @Override
    public boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json) {

        switch (call){
            // 获取图片验证码
            case 901:{
                response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
                response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Set-Cookie", "name=value; HttpOnly");// 设置HttpOnly属性,防止Xss攻击
                response.setDateHeader("Expire", -1);
                String randCode = CodeGenerator.getCode(4, CodeGenerator.CodeType.all);
                RequestPool.getSession().setAttribute(Context.session_img_code, randCode);
                randomImgCodeUtil.getRandcode(response, randCode);// 输出图片方法
                break;
            }
            default:{
                return false;
            }
        }
        return true;
    }
}
