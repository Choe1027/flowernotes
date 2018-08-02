package com.flowernotes.back.service;

import com.flowernotes.core.base.BaseService;
import com.flowernotes.core.bean.UserRoleBean;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cyk
 * @date 2018/8/2/002 14:59
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Service
@Transactional
public interface UserRoleService extends BaseService<UserRoleBean> {
}
