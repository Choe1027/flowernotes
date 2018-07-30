package com.flowernotes.core.base;

/**
 * @author cyk
 * @date 2018/7/30/030 13:26
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class BaseDTO<T> {

    private Long startTime;
    private Long endTime;


    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }


}
