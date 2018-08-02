package com.flowernotes.common.base;

import com.flowernotes.common.bean.Page;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author cyk
 * @date 2018/7/30/030 09:44
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long create_time;
    private Page page;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseBean baseBean = (BaseBean) o;
        return Objects.equals(id, baseBean.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public void removeSensetive(){
        create_time = null;
    }
}
