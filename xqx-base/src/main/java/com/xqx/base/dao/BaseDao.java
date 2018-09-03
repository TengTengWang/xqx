package com.xqx.base.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import com.xqx.base.exception.DaoException;

public interface BaseDao<T,ID extends Serializable> {

    /**
     * 保存数据对象
     * @param entity
     * @return
     */
    boolean save(T entity) throws DaoException;

    /**
     * 根据id查询
     * @param id
     * @param calzz
     * @return
     */
    T getOneById(Class<T> calzz, Long id) throws DaoException;

    List<T> listObjects(Class<T> calzz) throws DaoException;


    List<T> listObjecstByFeilds(Class<T> calzz, LinkedHashMap<String,Object> map) throws DaoException;


    /**
     * 根据表的id删除数据
     * @param  entity
     */
    boolean deleteOne(T entity) throws DaoException;
    /**
     * 更新对象
     * @param e
     * @return
     */
    boolean updateOne(T e);


    /**
     * 根据条件查询总条数返回object类型
     * @param calzz  表对应的实例类
     * @param map 传入参数放入map中
     * @return
     */
    Object count(Class<T> calzz, LinkedHashMap<String,Object> map) throws DaoException;


    /**
     * 查询总条数返回object类型
     * @param calzz  表对应的实例类
     * @return
     */
    Object count(Class<T> calzz) throws DaoException;

}