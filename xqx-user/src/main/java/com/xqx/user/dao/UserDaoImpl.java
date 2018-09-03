package com.xqx.user.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.xqx.base.dao.BaseDao;
import com.xqx.base.exception.DaoException;
import com.xqx.base.exception.ErrorCode;

@Repository
public class UserDaoImpl<T,ID extends Serializable> implements BaseDao<T,ID> {

    public static final String TABLE_ALIAS = "t";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean save(T entity) throws DaoException {
        boolean flag=false;
        try {
            entityManager.persist(entity);
            flag=true;
        }catch (Exception e){
            throw new DaoException(ErrorCode.DAO_ERROR,e.getMessage());
        }
        return flag;
    }

    @Transactional
    @Override
    public T getOneById(Class<T> calzz, Long id) throws DaoException {
        return entityManager.find(calzz,id);
    }

    @Transactional
    @Override
    public List<T> listObjecstByFeilds(Class<T> calzz, LinkedHashMap<String,Object> filter) throws DaoException {
        List<T> list = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("from " + calzz.getSimpleName() + " "+ TABLE_ALIAS);

            StringBuilder wherSql = new StringBuilder();
            Iterator<String> iterator = filter.keySet().iterator();
            String key = null;
            while (iterator.hasNext()) {
                key = iterator.next();
                if (!key.isEmpty()) {
                    if(wherSql.length() == 0){
                        wherSql.append(" where ");
                    }else {
                        wherSql.append(" and ");
                    }
                    wherSql.append(TABLE_ALIAS + "." + key + "=:" + key);

                }
            }

            String sqlStr = sql.append(wherSql).toString();
            System.out.println("SQL = "+sqlStr);
            Query query = entityManager.createQuery(sqlStr);

            filter.entrySet().stream().forEach(e -> query.setParameter(e.getKey(), e.getValue()));

            list = query.getResultList();
        }catch (Exception e){
            throw new DaoException(ErrorCode.DAO_ERROR,e.getMessage());
        }
        return list;
    }


    @Transactional
    @Override
    public List<T> listObjects(Class<T> calzz) throws DaoException {
        List<T> list = new ArrayList<>();
        try {
            String sql = "from " + calzz.getSimpleName() + " "+ TABLE_ALIAS;

            Query query = entityManager.createQuery(sql);

            list = query.getResultList();
        }catch (Exception e){
            throw new DaoException(ErrorCode.DAO_ERROR,e.getMessage());
        }
        return list;
    }

    @Transactional
    @Override
    public boolean updateOne(T entity) {
        boolean flag = false;
        try {
            entityManager.merge(entity);
            flag = true;
        } catch (Exception e) {
            throw new DaoException(ErrorCode.DAO_ERROR,e.getMessage());
        }
        return flag;
    }


    @Transactional
    @Override
    public boolean deleteOne(T entity) throws DaoException {
        boolean flag=false;
        try {
            entityManager.remove(entityManager.merge(entity));
            flag=true;
        }catch (Exception e){
            throw new DaoException(ErrorCode.DAO_ERROR,e.getMessage());
        }
        return flag;
    }

    @Override
    public Object count(Class<T> calzz, LinkedHashMap<String, Object> filter) throws DaoException {
        try {
            if (filter == null || filter.size() <= 0) {
                return count(calzz);
            }

            StringBuilder sql = new StringBuilder("select count(" + TABLE_ALIAS + ") from " + calzz.getSimpleName() + " " + TABLE_ALIAS + " where ");
            Iterator<String> iterator = filter.keySet().iterator();
            String key = null;
            while (iterator.hasNext()) {
                key = iterator.next();
                if (!key.isEmpty()) {
                    sql.append(TABLE_ALIAS + "." + key + "=:" + key + ",");
                }
            }
            Query query = entityManager.createQuery(sql.deleteCharAt(sql.lastIndexOf(",")).toString());
            filter.entrySet().stream().forEach(e -> query.setParameter(e.getKey(), e.getValue()));
            return query.getSingleResult();
        }catch (Exception e){
            throw new DaoException(ErrorCode.DAO_ERROR,e.getMessage());
        }
    }

    @Override
    public Object count(Class<T> calzz) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder("select count(");
            sql.append(TABLE_ALIAS);
            sql.append(") from ");
            sql.append(calzz.getSimpleName());
            sql.append(" ");
            sql.append(TABLE_ALIAS);
            Query query = entityManager.createQuery(sql.toString());
            return query.getSingleResult();
        }catch (Exception e){
            throw new DaoException(ErrorCode.DAO_ERROR,e.getMessage());
        }
    }

}
