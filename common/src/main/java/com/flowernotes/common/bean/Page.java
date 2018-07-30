package com.flowernotes.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author cyk
 * @date 2018/7/30/030 11:59
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long total = 0L;
    private Long totalPage = 0L;
    private Long currentPage = 1L;
    private Integer showCount = 5;
    private Long currentIndex = 0L;
    private List<T> datas;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
        this.totalPage = total%showCount>0?total/showCount+1:total/showCount;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getShowCount() {
        return showCount;
    }

    public void setShowCount(Integer showCount) {
        this.showCount = showCount;
    }

    public Long getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Long currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
