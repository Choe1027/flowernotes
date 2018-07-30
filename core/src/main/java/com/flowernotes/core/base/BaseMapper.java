package com.flowernotes.core.base;

import java.util.List;

/**
 * @author cyk
 * @date 2018/7/30/030 13:51
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface BaseMapper<T> {

    /**
     * 分页查询
     * @param t	依据T中不为null的属性进行查询
     * @param start_time 开始时间
     * @param end_time 结束时间
     * @param is_desc 是否倒序
     * @return	返回页码对应的数据集合
     */
    List<T> listPage(T t, Long start_time, Long end_time, boolean is_desc);

    /**
     * 分页查询
     * @param t	依据T中不为null的属性进行查询
     * @return	返回页码对应的数据集合
     */
    List<T> listPage(T t);

    /**
     * 修改数据
     * (id为null,抛出异常)
     * @param t	依据T中id进行修改
     * @return	修改条数
     */
    Integer update(T t);

    /**
     * 新增数据
     * (无参数返回异常)
     * @param t	依据不为空的字段进新增
     * @return	返回条数
     */
    Integer add(T t);

    /**
     * 依据id查询一条数据
     * @param t	依据T中不为空的字段作为条件
     * @return	返回单条数据
     */
    T get(T t);

    /**
     * 依据对象属性值删除数据
     * (无参数返回异常)
     * @param t	依据T中不为空的字段进行删除
     * @return
     */
    Integer delete(T t);

    /**
     * 依据对象属性查询所有数据
     * (无分页，全量查询)
     * @param t	依据T中不为null的属性进行查询
     * @param start_time 开始时间
     * @param end_time 结束时间
     * @param is_desc 是否倒序
     * @return	数据集合
     */
    List<T> select(T t,Long start_time,Long end_time,boolean is_desc);

    /**
     * 依据对象属性查询所有数据
     * (无分页，全量查询)
     * @param t	依据T中不为null的属性进行查询
     * @return	数据集合
     */
    List<T> select(T t);
}
