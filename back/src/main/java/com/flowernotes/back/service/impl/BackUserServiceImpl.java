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
import com.flowernotes.core.constant.Context;
import com.flowernotes.core.utils.RequestPool;

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
    public BackUserDTO login(BackUserDTO userDTO) {

        if (StringUtil.isEmpty(userDTO.getAccount()) || StringUtil.isEmpty(userDTO.getPassword())) {
            throw new CommonException(Error.common_account_password_cannot_be_null);
        }
        if (StringUtil.isEmpty(userDTO.getCode())){
            throw new CommonException(Error.common_lost_params,"验证码为空");
        }
        if (!userDTO.getCode().equals(RequestPool.getSession().getAttribute(Context.session_img_code))){
            throw new CommonException(Error.common_code_is_wrong);
        }
        BackUserBean backUser = get(userDTO);
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
        backUserDTO.setModules(moduleService.generateTree(list,1L));
        backUserDTO.setRoleNames(roleNames);
        return backUserDTO;
    }

    @Override
    public BackUserDTO authorization(BackUserDTO backUserDTO) {
        if (backUserDTO.getId() == null || backUserDTO.getId().equals(0)){
            throw  new CommonException(Error.id_must_not_be_null,"用户授权id为空");
        }
        BackUserBean byId = getById(backUserDTO.getId());
        if (byId == null){
            throw new CommonException(Error.common_obj_not_exist,"授权用户为空");
        }
        List<RoleBean> roles = backUserDTO.getRoles();
        if (roles == null || roles.isEmpty()){
            throw new CommonException(Error.common_lost_params,"缺少授权内容");
        }
        // 删除之前的授权
        UserRoleBean userRoleBean = new UserRoleBean();
        userRoleBean.setUser_id(backUserDTO.getId());
        userRoleService.delete(userRoleBean);
        // 添加新的授权信息
        for (RoleBean roleBean: roles) {
            userRoleBean.setRole_id(roleBean.getId());
            userRoleService.add(userRoleBean);
        }
        ObjectUtil.insertObj(backUserDTO,byId);
        return backUserDTO;
    }


    @Override
    public List<BackUserDTO> getUserList(BackUserDTO backUserDTO) {

        List<BackUserBean> select = select(backUserDTO);
        List<BackUserDTO> datas = new ArrayList<>();
        if (select != null && !select.isEmpty()){
            for (BackUserBean backUser: select
                 ) {
                datas.add(getBackUser(backUser));
            }
        }
        return datas;
    }

    @Override
    public BackUserDTO getBackUser(BackUserBean backUserBean) {

        if (backUserBean.getId() == null || backUserBean.getId().equals(0)){
            throw new CommonException(Error.id_must_not_be_null,"后台用户详情，id为空");
        }
        BackUserBean byId = getById(backUserBean.getId());
        if (byId == null){
            throw new CommonException(Error.common_user_not_exist);
        }
        UserRoleBean userRoleBean = new UserRoleBean();
        userRoleBean.setUser_id(backUserBean.getId());
        BackUserDTO backUserDTO = new BackUserDTO();
        List<UserRoleBean> userRoles = userRoleService.select(userRoleBean);
        if (userRoles != null && !userRoles.isEmpty()){
            List<RoleBean> roles = new ArrayList<>();
            for (UserRoleBean ur: userRoles) {
                roles.add(roleSerivce.getById(ur.getRole_id()));
            }
            backUserDTO.setRoles(roles);
        }
        ObjectUtil.insertObj(backUserDTO,byId);
        return backUserDTO;
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
