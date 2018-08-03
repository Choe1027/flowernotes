package com.flowernotes.back.service.impl;

import com.flowernotes.back.dao.RoleMapper;
import com.flowernotes.back.dto.RoleDTO;
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
import com.flowernotes.core.bean.ModuleBean;
import com.flowernotes.core.bean.RoleBean;
import com.flowernotes.core.bean.RoleModuleBean;
import com.flowernotes.core.bean.UserRoleBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyk
 * @date 2018/8/2/002 15:02
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<RoleBean> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleModuleService roleModuleService;
    @Autowired
    private ModuleService moduleService;


    @Override
    protected BaseMapper<RoleBean> getMainMapper() {
        return roleMapper;
    }

    @Override
    public RoleBean addRole(RoleBean roleBean) {
        if (StringUtil.isEmpty(roleBean.getName())){
            throw new CommonException(Error.common_lost_params,"角色名称不能为空");
        }
        if (StringUtil.isEmpty(roleBean.getDesc())){
            roleBean.setDesc(roleBean.getName());
        }
        if (roleBean.getState() == null){
            roleBean.setState(0);
        }
        add(roleBean);
        return roleBean;
    }

    @Override
    public RoleBean updateRole(RoleBean roleBean) {
        if (roleBean.getId() == null || roleBean.getId().equals(0L)){
            throw new CommonException(Error.id_must_not_be_null,"更新角色时，id不能为空");
        }
        RoleBean byId = getById(roleBean.getId());
        if (byId == null){
            throw new CommonException(Error.common_obj_not_exist,"更新角色不存在");
        }
        update(roleBean);
        ObjectUtil.insertObj(byId,roleBean);
        return byId;
    }

    @Override
    public void deleteRole(RoleBean roleBean) {
        if (roleBean.getId() == null || roleBean.getId().equals(0L)){
            throw new CommonException(Error.id_must_not_be_null,"删除角色时，id不能为空");
        }
        if (getById(roleBean.getId())== null){
            throw new CommonException(Error.common_obj_not_exist,"删除角色不存在");
        }
        Integer delete = delete(roleBean);
        if (delete >1 ){
            // 删除用户角色中间表
            UserRoleBean userRoleBean = new UserRoleBean();
            userRoleBean.setRole_id(roleBean.getId());
            userRoleService.delete(userRoleBean);
            // 删除角色模块中间表
            RoleModuleBean roleModuleBean = new RoleModuleBean();
            roleModuleBean.setRole_id(roleBean.getId());
            roleModuleService.delete(roleModuleBean);
        }
    }

    @Override
    public RoleDTO authorization(RoleDTO roleDTO) {
        if (roleDTO.getId() == null || roleDTO.getId().equals(0)){
            throw new CommonException(Error.id_must_not_be_null,"角色授权时，id不能为空");
        }
        if (roleDTO.getModules() == null || roleDTO.getModules().isEmpty()){
            throw  new CommonException(Error.common_lost_params,"角色授权缺少授权内容");
        }
        RoleBean byId = getById(roleDTO.getId());
        if (byId == null){
            throw new CommonException(Error.common_obj_not_exist,"授权角色不存在");
        }

        RoleModuleBean roleModuleBean = new RoleModuleBean();
        roleModuleBean.setRole_id(roleDTO.getId());
        roleModuleService.delete(roleModuleBean);
        for (ModuleBean moduleBean: roleDTO.getModules()) {
            roleModuleBean.setModule_id(moduleBean.getId());
            roleModuleService.add(roleModuleBean);
        }
        List<ModuleBean> modules = moduleService.generateTree(roleDTO.getModules(), 1L);
        roleDTO.setModules(modules);
        ObjectUtil.insertObj(roleDTO,byId);
        return roleDTO;
    }

    @Override
    public RoleDTO getRole(RoleBean roleBean) {
        if (roleBean.getId() == null || roleBean.getId().equals(0)){
            throw new CommonException(Error.id_must_not_be_null,"获取角色时，id不能为空");
        }
        RoleBean byId = getById(roleBean.getId());
        if (byId == null){
            throw new CommonException(Error.common_obj_not_exist,"获取角色不存在");
        }
        RoleModuleBean roleModuleBean = new RoleModuleBean();
        roleModuleBean.setRole_id(roleBean.getId());
        List<RoleModuleBean> select = roleModuleService.select(roleModuleBean);
        RoleDTO roleDTO = new RoleDTO();
        if (select != null && !select.isEmpty()){
            List<ModuleBean> modules = new ArrayList<>();
            for (RoleModuleBean roleModule: select) {
                modules.add(moduleService.getById(roleModule.getModule_id()));
            }
           roleDTO.setModules(moduleService.generateTree(modules,1L));
        }
        ObjectUtil.insertObj(roleDTO,byId);
        return roleDTO;
    }

    @Override
    public List<RoleDTO> getRoleList(RoleDTO roleDTO) {
        List<RoleBean> select = select(roleDTO);
        List<RoleDTO> roles = new ArrayList<>();
        if (select != null && !select.isEmpty()){
            for (RoleBean role:select) {
                roles.add(getRole(role));
            }
        }
        return roles;
    }
}
