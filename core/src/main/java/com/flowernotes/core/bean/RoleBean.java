package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:42
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "role")
public class RoleBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**角色名称*/
    private String name;
    /**描述*/
    private String desc;
    /**权限级别*/
    private Integer level;
    /**状态 0 启用 1禁用*/
    private Integer state;

}
