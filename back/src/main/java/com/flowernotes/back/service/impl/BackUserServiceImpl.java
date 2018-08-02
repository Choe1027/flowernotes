package com.flowernotes.back.service.impl;

import com.flowernotes.back.dao.BackUserMapper;
import com.flowernotes.back.dto.BackUserDTO;
import com.flowernotes.back.service.BackUserService;
import com.flowernotes.back.service.ModuleService;
import com.flowernotes.back.service.RoleModuleService;
import com.flowernotes.back.service.RoleService;
import com.flowernotes.back.service.UserRoleService;
import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.exception.Error;
import com.flowernotes.common.utils.ObjectUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.base.BaseMapper;
import com.flowernotes.core.base.BaseServiceImpl;
import com.flowernotes.core.bean.BackUserBean;
import com.flowernotes.core.bean.ModuleBean;
import com.flowernotes.core.bean.RoleBean;
import com.flowernotes.core.bean.RoleModuleBean;
import com.flowernotes.core.bean.UserRoleBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cyk
 * @date 2018/8/1/001 16:07
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Service
@Transactional
public class BackUserServiceImpl extends BaseServiceImpl<BackUserBean> implements BackUserService {

    @Autowired
    private BackUserMapper backUserMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleModuleService roleModuleService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private RoleService roleSerivce;

    @Value("${default_password}")
    private String defaultPassword;

    @Override
    protected BaseMapper<BackUserBean> getMainMapper() {
        return backUserMapper;
    }

    @Override
    public BackUserDTO login(BackUserBean backUserBean) {

        if (StringUtil.isEmpty(backUserBean.getAccount()) || StringUtil.isEmpty(backUserBean.getPassword())) {
            throw new CommonException(Error.common_account_password_cannot_be_null);
        }
        BackUserBean backUser = get(backUserBean);
        if (backUser == null) {
            throw new CommonException(Error.account_password_is_wrong);
        }
        //
        BackUserDTO backUserDTO = new BackUserDTO();
        ObjectUtil.insertObj(backUserDTO, backUser);
        // 获取用户所拥有的模块
        UserRoleBean userRoleBean = new UserRoleBean();
        userRoleBean.setUser_id(backUser.getId());
        List<UserRoleBean> userRoles = userRoleService.select(userRoleBean);
        Set<ModuleBean> modules = new HashSet<>();
        List<String> roleNames = null;
        if (userRoles != null && !userRoles.isEmpty()) {
            roleNames = new ArrayList<>();
            for (UserRoleBean userRole : userRoles) {
                RoleBean role = roleSerivce.getById(userRole.getRole_id());
                if (role.getState().equals(1)){
                    continue;
                }
                roleNames.add(role.getName());
                RoleModuleBean roleModuleBean = new RoleModuleBean();
                roleModuleBean.setRole_id(userRole.getRole_id());
                List<RoleModuleBean> roleModules = roleModuleService.select(roleModuleBean);
                if (roleModules != null && !roleModules.isEmpty()) {
                    for (RoleModuleBean roleModule : roleModules) {
                        ModuleBean module = moduleService.getById(roleModule.getModule_id());
                        modules.add(module);
                    }
                }
            }
        }
        List<ModuleBean> list = new ArrayList<>();
        list.addAll(modules);
        backUserDTO.setModules(generateTree(list,1L));
        backUserDTO.setRoleNames(roleNames);
        return backUserDTO;
    }

    /**
     * 根据list 生成 权限树
     * @param modules 所有的权限
     * @param pid 根节点的父id, 在本项目中,根节点父id从1开始
     * @return
     */
    private List<ModuleBean>  generateTree(List<ModuleBean> modules,Long pid){
        List<ModuleBean> list = new ArrayList<>();
        if (modules == null || modules.isEmpty()){
            return list;
        }
        for (ModuleBean module: modules) {
            if (pid.equals(module.getPid())){
                list.add(module);
                List<ModuleBean> list1 = generateTree(modules, module.getId());
                module.setChildrenList(list1);
            }
        }
        return list;
    }



    @Override
    public void addUser(BackUserBean backUserBean) {
        String account = backUserBean.getAccount();
        String password = backUserBean.getPassword();
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            throw new CommonException(Error.common_account_password_cannot_be_null);
        }
        BackUserBean user = new BackUserBean();
        user.setAccount(backUserBean.getAccount());
        BackUserBean backUser = get(user);
        if (backUser != null) {
            throw new CommonException(Error.common_user_is_exist);
        }
        // 如果没有填写名称，则将账户名作为name
        if (StringUtil.isEmpty(backUserBean.getName())) {
            backUserBean.setName(account);
        }
        backUserBean.setStatus(0);
        Integer add = add(backUserBean);
    }

    @Override
    public BackUserBean updateUser(BackUserBean backUserBean) {
        Long id = backUserBean.getId();
        if (id == null || id.equals(0L)) {
            throw new CommonException(Error.id_must_not_be_null, "更新后台用户信息，id为空");
        }
        BackUserBean backUser = getById(id);
        if (backUser == null) {
            throw new CommonException(Error.common_user_not_exist);
        }
        update(backUserBean);
        ObjectUtil.insertObj(backUser, backUserBean);
        return backUser;
    }

    @Override
    public BackUserBean resetPassword(BackUserBean backUserBean) {
        Long id = backUserBean.getId();
        if (id == null || id.equals(0L)) {
            throw new CommonException(Error.id_must_not_be_null, "修改密码信息，id为空");
        }
        BackUserBean backUser = getById(id);
        if (backUser == null) {
            throw new CommonException(Error.common_user_not_exist);
        }
        // 设置密码
        String encryptPassword = StringUtil.toMD5(defaultPassword);
        ObjectUtil.cleanInitValue(backUserBean);
        backUserBean.setId(id);
        backUserBean.setPassword(encryptPassword);
        update(backUserBean);
        ObjectUtil.insertObj(backUser, backUserBean);
        return backUser;
    }


//    public static void main(String[] args) {
//        List<ModuleBean> datas = new ArrayList<>();
//        ModuleBean moduleBean = new ModuleBean();
//        moduleBean.setPid(0L);
//        moduleBean.setId(1L);
//        moduleBean.setName("1-1");
//        datas.add(moduleBean);
//
//        ModuleBean moduleBean2 = new ModuleBean();
//        moduleBean2.setPid(0L);
//        moduleBean2.setId(2L);
//        moduleBean2.setName("1-2");
//        datas.add(moduleBean2);
//
//        ModuleBean moduleBean3 = new ModuleBean();
//        moduleBean3.setPid(1L);
//        moduleBean3.setId(3L);
//        moduleBean3.setName("1-1-1");
//        datas.add(moduleBean3);
//
//        ModuleBean moduleBean4 = new ModuleBean();
//        moduleBean4.setPid(1L);
//        moduleBean4.setId(4L);
//        moduleBean4.setName("1-1-2");
//        datas.add(moduleBean4);
//
//        ModuleBean moduleBean5 = new ModuleBean();
//        moduleBean5.setPid(4L);
//        moduleBean5.setId(5L);
//        moduleBean5.setName("1-1-2-1");
//        datas.add(moduleBean5);
//
//        List<ModuleBean> list = generateTree(datas, 0L);
//        System.out.println("ddd");
//    }
}
