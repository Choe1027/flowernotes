package com.flowernotes.core.base;

import com.flowernotes.common.bean.Page;

import java.util.List;

/** 顶级service类 */
public interface BaseService<T> {
	/**
	 * 分页查询
	 * @param t	使用T中不为null的字段查询
	 * @return	返回页码的对象，包含页码对应的数据
	 */
    Page<T> listPage(T t);

    /**
     * 分页查询
     * @param t	使用T中不为null的字段查询
     * @param start_time 开始时间
     * @param end_time 结束时间
     * @return	返回页码的对象，包含页码对应的数据
     */
	Page<T> listPage(T t, Long start_time, Long end_time);

    /**
     * 分页查询
     * @param t	使用T中不为null的字段并查询根据创建时间倒序排列
     * @return	返回页码的对象，包含页码对应的数据
     */
	Page<T> listPageDesc(T t);

    /**
     * 分页查询
     * @param t	使用T中不为null的字段查询
     * @param start_time 开始时间
     * @param end_time 结束时间
     * @return	返回页码的对象，包含页码对应的数据
     */
	Page<T> listPageDesc(T t, Long start_time, Long end_time);

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
	 * 依据不为空的参数查询一条数据
	 * @param t	依据T中不为空的字段作为条件
	 * @return	返回单条数据
	 */
	T get(T t);

	/**
	 * 依据id查询一条数据
	 * @param id 唯一标识
	 * @return	返回单条数据
	 */
	T getById(Long id);

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
	 * @return	数据集合
	 */
	List<T> select(T t);

    /**
     * 依据对象属性查询所有数据
     * (无分页，全量查询)
     * @param t	依据T中不为null的属性进行查询
     * @return	数据集合
     */
    List<T> selectDesc(T t);

    /**
     * 依据对象属性查询所有数据
     * @param t	使用T中不为null的字段查询
     * @param start_time 开始时间
     * @param end_time 结束时间
     * @return	返回页码对应的数据集合
     */
    List<T> select(T t, Long start_time, Long end_time);

    /**
     * 依据对象属性查询所有数据
     * @param t	使用T中不为null的字段查询
     * @param start_time 开始时间
     * @param end_time 结束时间
     * @return	返回页码对应的数据集合
     */
    List<T> selectDesc(T t, Long start_time, Long end_time);

}
