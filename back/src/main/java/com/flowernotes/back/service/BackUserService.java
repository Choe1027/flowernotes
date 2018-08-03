package com.flowernotes.back.service;

import com.flowernotes.back.dto.BackUserDTO;
import com.flowernotes.core.base.BaseService;
import com.flowernotes.core.bean.BackUserBean;

import java.util.List;

/**
 * @author cyk
 * @date 2018/8/2/002 14:19
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface BackUserService extends BaseService<BackUserBean> {

    /**
     * 添加后台用户
     * @param backUserBean
     */
    void addUser(BackUserBean backUserBean);

    /**
     * 更新用户信息(禁用/启用，更改用户密码，设置头像等等)
     * @param backUserBean
     * @return
     */
    BackUserBean updateUser(BackUserBean backUserBean);

    /**
     *  修改或者重置管理员密码，只能重置比自己级别低的用户密码
     * @param backUserBean
     * @return
     */
    BackUserBean resetPassword(BackUserBean backUserBean);

    /**
     * 登录
     * @param backUserDTO
     * @return
     */
    BackUserDTO login(BackUserDTO backUserDTO);

    /**
     * 给用户添加角色
     * @param backUserDTO
     * @return
     */
    BackUserDTO authorization(BackUserDTO backUserDTO);

    /**
     * 获取后台用户列表
     * @param backUserDTO
     * @return
     */
    List<BackUserDTO> getUserList(BackUserDTO backUserDTO);

    /**
     * 获取单个后台用户详情，返回结果带有角色列表
     * @param backUserBean
     * @return
     */
    BackUserDTO getBackUser(BackUserBean backUserBean);
}
