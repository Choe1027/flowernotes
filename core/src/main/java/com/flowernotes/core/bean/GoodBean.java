package com.flowernotes.core.bean;

import com.flowernotes.common.annotation.Table;
import com.flowernotes.common.base.BaseBean;

/**
 * @author cyk
 * @date 2018/7/30/030 11:29
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
@Table(name = "goods")
public class GoodBean extends BaseBean {


    private static final long serialVersionUID = 1L;

    /**品类id*/
    private Integer category_id;
    /**商品名称*/
    private String name;
    /**主图url*/
    private String main_url;
    /**描述*/
    private String desc;
    /**原价*/
    private Double old_price;
    /**现价*/
    private Double now_price;
    /**库存*/
    private Integer stock_num;
    /**商品类型 1普通商品 2秒杀商品 3预购商品 4竞拍商品*/
    private Integer type;
    /**状态 0 启用 1禁用*/
    private Integer state;
    /**已售数量*/
    private Integer sold_num;
    /**排序号*/
    private Integer order_no;
    /**起拍金额*/
    private Double init_price;
    /**加价步调*/
    private Double price_step;
    /**当前价格*/
    private Double current_price;
    /**开始时间*/
    private Long start_time;
    /**结束时间*/
    private Long end_time;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMain_url() {
        return main_url;
    }

    public void setMain_url(String main_url) {
        this.main_url = main_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getOld_price() {
        return old_price;
    }

    public void setOld_price(Double old_price) {
        this.old_price = old_price;
    }

    public Double getNow_price() {
        return now_price;
    }

    public void setNow_price(Double now_price) {
        this.now_price = now_price;
    }

    public Integer getStock_num() {
        return stock_num;
    }

    public void setStock_num(Integer stock_num) {
        this.stock_num = stock_num;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSold_num() {
        return sold_num;
    }

    public void setSold_num(Integer sold_num) {
        this.sold_num = sold_num;
    }

    public Integer getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        this.order_no = order_no;
    }

    public Double getInit_price() {
        return init_price;
    }

    public void setInit_price(Double init_price) {
        this.init_price = init_price;
    }

    public Double getPrice_step() {
        return price_step;
    }

    public void setPrice_step(Double price_step) {
        this.price_step = price_step;
    }

    public Double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Double current_price) {
        this.current_price = current_price;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }
}
