package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:19
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "category")
public class CategoryBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    /**级别*/
    private Integer level;
    /**名称*/
    private String name;
    /**父级id*/
    private Integer pid;
    /**描述*/
    private String desc;
    /**状态 0 启用，1 禁用*/
    private Integer state;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
