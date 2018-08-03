package com.flowernotes.back.dto;

import com.flowernotes.core.bean.ModuleBean;
import com.flowernotes.core.bean.RoleBean;

import java.util.List;

/**
 * @author cyk
 * @date 2018/8/3/003 10:03
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class RoleDTO extends RoleBean {
    private static final long serialVersionUID = 1L;

    private List<ModuleBean> modules;


    public List<ModuleBean> getModules() {
        return modules;
    }

    public void setModules(List<ModuleBean> modules) {
        this.modules = modules;
    }
}
