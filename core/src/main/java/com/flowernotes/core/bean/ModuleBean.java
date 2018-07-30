package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

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
    private Integer pid;
    /**0 菜单 1 按钮*/
    private Integer type;
    /**连接*/
    private String url;
}
