package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;

/**
 * @author cyk
 * @date 2018/7/30/030 11:23
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "city_covered")
public class CityCoveredBean {


    private static final long serialVersionUID = 1L;
    /**城市编码*/
    private String city_code;
    /**城市名称*/
    private String city_name;
    /**父级编码*/
    private String parent_code;
    /**城市等级*/
    private Integer level;

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        this.parent_code = parent_code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
