package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

import java.util.List;

/**
 * @author cyk
 * @date 2018/7/30/030 11:34
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "module")
public class ModuleBean extends BaseBean {


    private static final long serialVersionUID = 1L;

    /**模块名称*/
    private String name;
    /**父级id*/
    private Long pid;
    /**0 菜单 1 按钮*/
    private Integer type;
    /**连接*/
    private String url;

    /**子模块集合*/
    private List<ModuleBean> childrenList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ModuleBean> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<ModuleBean> childrenList) {
        this.childrenList = childrenList;
    }
}
