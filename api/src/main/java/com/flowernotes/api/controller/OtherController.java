package com.flowernotes.api.controller;

import com.flowernotes.api.service.NavigationService;
import com.flowernotes.core.bean.NavigationBean;
import com.flowernotes.core.utils.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cyk
 * @date 2018/8/8/008 18:08
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Controller
public class OtherController implements BizController {

    @Autowired
    private NavigationService navigationService;
    @Override
    public boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json) {

        switch (call){
            case 700:{
                List<NavigationBean> list = navigationService.getListSortByOrderNo();
                ResponseUtil.outSuccess(response,list);
                break;
            }
            default:{
                return false;
            }
        }
        return true;
    }
}
