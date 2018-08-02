package com.flowernotes.back.service.impl;

import com.flowernotes.back.dao.UserRoleMapper;
import com.flowernotes.back.service.UserRoleService;
import com.flowernotes.core.base.BaseMapper;
import com.flowernotes.core.base.BaseServiceImpl;
import com.flowernotes.core.bean.UserRoleBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cyk
 * @date 2018/8/2/002 15:03
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Service
@Transactional
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleBean> implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    protected BaseMapper<UserRoleBean> getMainMapper() {
        return userRoleMapper;
    }
}
