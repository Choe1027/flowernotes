package com.flowernotes.back.service.impl;

import com.flowernotes.back.dao.BackUserMapper;
import com.flowernotes.back.service.BackUserService;
import com.flowernotes.core.base.BaseMapper;
import com.flowernotes.core.base.BaseServiceImpl;
import com.flowernotes.core.bean.BackUserBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BackUserServiceImpl extends BaseServiceImpl<BackUserBean> implements BackUserService {

    @Autowired
    private BackUserMapper backUserMapper;

    @Override
    protected BaseMapper<BackUserBean> getMainMapper() {
        return backUserMapper;
    }
}
