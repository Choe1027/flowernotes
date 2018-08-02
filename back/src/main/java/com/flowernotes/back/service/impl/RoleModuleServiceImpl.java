package com.flowernotes.back.service.impl;


import com.flowernotes.back.dao.RoleModuleMapper;
import com.flowernotes.back.service.RoleModuleService;
import com.flowernotes.core.base.BaseMapper;
import com.flowernotes.core.base.BaseServiceImpl;
import com.flowernotes.core.bean.RoleModuleBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cyk
 * @date 2018/8/2/002 14:19
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Service
@Transactional
public class RoleModuleServiceImpl extends BaseServiceImpl<RoleModuleBean> implements RoleModuleService {

    @Autowired
    private RoleModuleMapper roleModuleMapper;

    @Override
    protected BaseMapper<RoleModuleBean> getMainMapper() {
        return roleModuleMapper;
    }
}
