package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:25
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "coupon")
public class CouponBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**模板id*/
    private Integer coupon_template_id;
    /**优惠券名称*/
    private String name;
    /**优惠券描述*/
    private String desc;
    /**优惠券类型*/
    private String type;
    /**使用金额限制*/
    private Double require_money;
    /**数量*/
    private Integer num;
    /**生效时间*/
    private Long start_time;
    /**过期时间*/
    private Long end_time;
    /**创建时间*/
    private Long create_time;
}
