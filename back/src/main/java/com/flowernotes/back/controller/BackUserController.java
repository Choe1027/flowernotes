package com.flowernotes.back.controller;

import com.flowernotes.back.service.BackUserService;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.core.bean.BackUserBean;
import com.flowernotes.core.utils.RequestPool;
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
                BackUserBean backUserBean = JsonUtil.jsonToBean(json, BackUserBean.class);
                BackUserBean backUser = backUserService.login(backUserBean);
                ResponseUtil.outSuccess(response,backUser);
                break;
            }

            // 保持连接
            case 102:{
                ResponseUtil.outSuccess(response, System.currentTimeMillis() + "");
                break;
            }
            //添加后台用户
            case 10001:{
                BackUserBean backUserBean = JsonUtil.jsonToBean(json, BackUserBean.class);
                backUserService.addUser(backUserBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            // 更新后台用户信息,自己操作自己
            case 10002:{
                BackUserBean backUserBean = JsonUtil.jsonToBean(json, BackUserBean.class);
                backUserBean.setId(RequestPool.getUserId());
                BackUserBean backUser = backUserService.updateUser(backUserBean);
                ResponseUtil.outSuccess(response,backUser);
                break;
            }
            // 重置后台用户密码
            case 10003:{
                BackUserBean backUserBean = JsonUtil.jsonToBean(json, BackUserBean.class);
                BackUserBean backUser = backUserService.resetPassword(backUserBean);
                ResponseUtil.outSuccess(response,backUser);
                break;
            }
            // 给用户添加角色
            case 10004:{
                break;
            }
            // 添加菜单模块
            case 10100:{

                break;
            }
            // 更新菜单模块
            case 10102:{
                break;
            }
            // 删除菜单模块
            case 10103:{

                break;
             }
             //添加角色
            case 10201:{
                break;
            }
            //更新角色
            case 10202:{
                break;
            }
            //删除角色
            case 10203:{
                break;
            }
            //为角色授权
            case 10204:{
                break;
            }
            default:
                return false;
        }
        return true;
    }
}
