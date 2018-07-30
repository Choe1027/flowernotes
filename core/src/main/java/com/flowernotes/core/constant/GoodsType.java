package com.flowernotes.core.constant;

/**
 * @author cyk
 * @date 2018/7/30/030 10:16
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public enum GoodsType {

    normal_good(1,"普通商品"),
    second_good(2,"秒杀商品"),
    prebuy_good(3,"预购商品"),
    auction_good(4,"竞拍商品")
    ;
    private Integer type;
    private String desc;

    GoodsType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
