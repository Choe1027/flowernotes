package com.flowernotes.back.service;

import com.flowernotes.core.bean.BackUserBean;

/**
 * @author cyk
 * @date 2018/8/1/001 16:04
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface BackUserService {

    /**
     * 添加后台用户
     * @param backUserBean
     */
    void addUser(BackUserBean backUserBean);
}
