package com.flowernotes.back.controller;

import com.flowernotes.back.dto.BackUserDTO;
import com.flowernotes.back.dto.RoleDTO;
import com.flowernotes.back.service.BackUserService;
import com.flowernotes.back.service.ModuleService;
import com.flowernotes.back.service.RoleService;
import com.flowernotes.common.utils.JsonUtil;
import com.flowernotes.core.bean.BackUserBean;
import com.flowernotes.core.bean.ModuleBean;
import com.flowernotes.core.bean.RoleBean;
import com.flowernotes.core.utils.RequestPool;
import com.flowernotes.core.utils.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;


    @Override
    public boolean action(HttpServletRequest request, HttpServletResponse response, Integer call, String json) {
        switch (call) {
            //用户登录
            case 101: {
                BackUserDTO backUserBean = JsonUtil.jsonToBean(json, BackUserDTO.class);
                BackUserBean backUser = backUserService.login(backUserBean);
                ResponseUtil.outSuccess(response, backUser);
                break;
            }

            // 保持连接
            case 102: {
                ResponseUtil.outSuccess(response, System.currentTimeMillis() + "");
                break;
            }
            //添加后台用户
            case 10001: {
                BackUserBean backUserBean = JsonUtil.jsonToBean(json, BackUserBean.class);
                backUserService.addUser(backUserBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            // 更新后台用户信息,自己操作自己
            case 10002: {
                BackUserBean backUserBean = JsonUtil.jsonToBean(json, BackUserBean.class);
                backUserBean.setId(RequestPool.getUserId());
                BackUserBean backUser = backUserService.updateUser(backUserBean);
                ResponseUtil.outSuccess(response, backUser);
                break;
            }
            // 重置后台用户密码
            case 10003: {
                BackUserBean backUserBean = JsonUtil.jsonToBean(json, BackUserBean.class);
                BackUserBean backUser = backUserService.resetPassword(backUserBean);
                ResponseUtil.outSuccess(response, backUser);
                break;
            }
            // 给用户添加角色
            case 10004: {
                BackUserDTO backUserDTO = JsonUtil.jsonToBean(json, BackUserDTO.class);
                backUserDTO = backUserService.authorization(backUserDTO);
                ResponseUtil.outSuccess(response, backUserDTO);
                break;
            }
            // 获取后台用户列表
            case 10005:{
                BackUserDTO backUserDTO = JsonUtil.jsonToBean(json,BackUserDTO.class);
                List<BackUserDTO> list = backUserService.getUserList(backUserDTO);
                ResponseUtil.outSuccess(response,list);
            }
            // 添加菜单模块
            case 10100: {
                ModuleBean moduleBean = JsonUtil.jsonToBean(json, ModuleBean.class);
                moduleBean = moduleService.addModule(moduleBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            // 更新菜单模块
            case 10102: {
                ModuleBean moduleBean = JsonUtil.jsonToBean(json, ModuleBean.class);
                moduleBean = moduleService.updateModule(moduleBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            // 删除菜单模块
            case 10103: {
                ModuleBean moduleBean = JsonUtil.jsonToBean(json, ModuleBean.class);
                moduleService.deleteModule(moduleBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            // 获取所有菜单模块，树形结构
            case 10104: {
                List<ModuleBean> modules = moduleService.getModuleList();
                ResponseUtil.outSuccess(response, modules);
                break;
            }
            //添加角色
            case 10201: {
                RoleBean roleBean = JsonUtil.jsonToBean(json, RoleBean.class);
                roleBean = roleService.addRole(roleBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            //更新角色
            case 10202: {
                RoleBean roleBean = JsonUtil.jsonToBean(json, RoleBean.class);
                roleBean = roleService.updateRole(roleBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            //删除角色
            case 10203: {
                RoleBean roleBean = JsonUtil.jsonToBean(json, RoleBean.class);
                roleService.deleteRole(roleBean);
                ResponseUtil.outSuccess(response);
                break;
            }
            //为角色授权
            case 10204: {
                RoleDTO roleDTO = JsonUtil.jsonToBean(json, RoleDTO.class);
                roleDTO = roleService.authorization(roleDTO);
                ResponseUtil.outSuccess(response, roleDTO);
                break;
            }
            // 获取单个role
            case 10205: {
                RoleDTO roleDTO = JsonUtil.jsonToBean(json, RoleDTO.class);
                roleDTO = roleService.getRole(roleDTO);
                ResponseUtil.outSuccess(response, roleDTO);
                break;
            }
            // 获取角色列表
            case 10206: {
                RoleDTO roleDTO = JsonUtil.jsonToBean(json, RoleDTO.class);
                List<RoleDTO> roles = roleService.getRoleList(roleDTO);
                ResponseUtil.outSuccess(response, roles);
                break;
            }
            default:
                return false;
        }
        return true;
    }
}
