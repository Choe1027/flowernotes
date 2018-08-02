package com.flowernotes.back.dto;

import com.flowernotes.core.bean.BackUserBean;
import com.flowernotes.core.bean.ModuleBean;

import java.util.List;

/**
 * @author cyk
 * @date 2018/8/2/002 15:08
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class BackUserDTO extends BackUserBean {

    private Long startTime;
    private Long endTime;
    /**所有有权限的模块*/
    private List<ModuleBean> modules ;
    /**所有的角色名称*/
    private List<String> roleNames;


    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<ModuleBean> getModules() {
        return modules;
    }

    public void setModules(List<ModuleBean> modules) {
        this.modules = modules;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
