package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:28
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "delivery_man")
public class DeliveryManBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**配送原名称*/
    private String name;
    /**配送员手机号*/
    private String mobile;
}
