package com.flowernotes.back.service;

import com.flowernotes.back.dto.BackUserDTO;
import com.flowernotes.core.base.BaseService;
import com.flowernotes.core.bean.BackUserBean;

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
     * @param backUserBean
     * @return
     */
    BackUserDTO login(BackUserBean backUserBean);
}
