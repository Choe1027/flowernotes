package com.flowernotes.back.service;

import com.flowernotes.back.dto.RoleDTO;
import com.flowernotes.core.base.BaseService;
import com.flowernotes.core.bean.RoleBean;

import java.util.List;

/**
 * @author cyk
 * @date 2018/8/2/002 15:00
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface RoleService extends BaseService<RoleBean> {

    /**
     * 添加角色
     * @param roleBean
     * @return
     */
    RoleBean addRole(RoleBean roleBean);

    /**
     * 更新角色
     * @param roleBean
     * @return
     */
    RoleBean updateRole(RoleBean roleBean);

    /**
     * 删除角色
     * @param roleBean
     * @return
     */
    void deleteRole(RoleBean roleBean);

    /**
     * 给角色授权
     * @param roleDTO
     * @return
     */
    RoleDTO authorization(RoleDTO roleDTO);

    /**
     * 获取角色信息，带有模块列表
     * @param roleBean
     * @return
     */
    RoleDTO getRole(RoleBean roleBean);

    /**
     * 获取角色列表带有模块
     * @param roleDTO
     * @return
     */
    List<RoleDTO> getRoleList(RoleDTO roleDTO);
}
