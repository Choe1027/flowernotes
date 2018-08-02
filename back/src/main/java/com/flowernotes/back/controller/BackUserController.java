package com.flowernotes.back.controller;

import com.flowernotes.back.service.BackUserService;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.core.bean.BackUserBean;
import com.flowernotes.core.utils.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cyk
 * @date 2018/8/1/001 16:28
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Controller
public class BackUserController implements BizController {
    @Autowired
    private BackUserService backUserService;

    @Override
    public boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json) {
        switch (call){
            //用户登录
            case 101:{

                break;
            }
            //添加后台用户
            case 10001:{
                BackUserBean backUserBean = JsonUtil.jsonToBean(json, BackUserBean.class);
                backUserService.addUser(backUserBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            default:
                return false;
        }
        return true;
    }
}
