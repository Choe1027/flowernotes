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
@Table(name = "grade")
public class GradeBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**等级名称*/
    private String name;
    /**所需积分*/
    private Integer required_score;
    /**等级描述*/
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRequired_score() {
        return required_score;
    }

    public void setRequired_score(Integer required_score) {
        this.required_score = required_score;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
