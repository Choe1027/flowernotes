package com.flowernotes.back.service.impl;

import com.flowernotes.back.dao.BackUserMapper;
import com.flowernotes.back.service.BackUserService;
import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.exception.Error;
import com.flowernotes.common.utils.StringUtil;
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

    @Override
    public void addUser(BackUserBean backUserBean) {
        String account = backUserBean.getAccount();
        String password = backUserBean.getPassword();
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)){
            throw new CommonException(Error.common_account_password_cannot_be_null);
        }
        BackUserBean user= new BackUserBean();
        user.setAccount(backUserBean.getAccount());
        BackUserBean backUser = get(user);
        if (backUser != null){
            throw new CommonException(Error.common_user_is_exist);
        }
        // 如果没有填写名称，则将账户名作为name
        if (StringUtil.isEmpty(backUserBean.getName())){
            backUserBean.setName(account);
        }
        backUserBean.setStatus(0);
        Integer add = add(backUserBean);
    }
}
