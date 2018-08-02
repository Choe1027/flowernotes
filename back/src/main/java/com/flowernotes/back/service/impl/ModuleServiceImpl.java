package com.flowernotes.back.service.impl;

import com.flowernotes.back.dao.ModuleMapper;
import com.flowernotes.back.service.ModuleService;
import com.flowernotes.core.base.BaseMapper;
import com.flowernotes.core.base.BaseServiceImpl;
import com.flowernotes.core.bean.ModuleBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cyk
 * @date 2018/8/2/002 15:01
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Service
@Transactional
public class ModuleServiceImpl extends BaseServiceImpl<ModuleBean> implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    protected BaseMapper<ModuleBean> getMainMapper() {
        return moduleMapper;
    }
}
