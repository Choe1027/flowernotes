package com.flowernotes.back.service.impl;

import com.flowernotes.back.dao.RoleMapper;
import com.flowernotes.back.service.RoleService;
import com.flowernotes.core.base.BaseMapper;
import com.flowernotes.core.base.BaseServiceImpl;
import com.flowernotes.core.bean.RoleBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Override
    protected BaseMapper<RoleBean> getMainMapper() {
        return roleMapper;
    }
}
