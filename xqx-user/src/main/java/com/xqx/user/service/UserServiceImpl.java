package com.xqx.user.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqx.base.exception.ErrorCode;
import com.xqx.base.exception.ServiceException;
import com.xqx.user.dao.UserDaoImpl;
import com.xqx.user.entity.User;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserDaoImpl<User,Long> baseDaoImpl;

    @Override
    public boolean saveUser(User user) throws ServiceException {
        if(user == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"User对象不能为Null");
        }
        return baseDaoImpl.save(user);
    }

    @Override
    public User getUserByID(Long id) throws ServiceException {
        if(id == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数ID不能为Null");
        }
        return baseDaoImpl.getOneById(User.class,id);
    }

    @Override
    public User getUser(String name, String password) throws ServiceException {
        if(name == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数Name不能为Null");
        }
        if(name.isEmpty()){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数Name不能为空");
        }
        if(password == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数Password不能为Null");
        }
        if(password.isEmpty()){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数Password不能为空");
        }
        LinkedHashMap<String,Object> filter = new LinkedHashMap<>();
        filter.put("name",name);
        filter.put("password",password);
        List<User> users = baseDaoImpl.listObjecstByFeilds(User.class,filter);
        if(users != null && users.size() > 0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public List<User> listUserByName(String name) throws ServiceException {
        if(name == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数Name不能为Null");
        }
        if(name.isEmpty()){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数Name不能为空");
        }
        LinkedHashMap<String,Object> filter = new LinkedHashMap<String,Object>();
        filter.put("name",name);
        return baseDaoImpl.listObjecstByFeilds(User.class,filter);
    }

    public List<User> listUser() throws ServiceException {
        return baseDaoImpl.listObjects(User.class);
    }

    @Override
    public Long getCount(String name) throws ServiceException {
        LinkedHashMap<String,Object> filter = null;
        if(name != null && !name.isEmpty()){
            filter = new LinkedHashMap<String,Object>();
            filter.put("name",name);
        }
        Object result = baseDaoImpl.count(User.class,filter);
        if(result != null){
            return Long.valueOf(result.toString());
        }
        return 0L;
    }

    @Override
    public boolean deleteUser(Long id) throws ServiceException {
        User user = getUserByID(id);
        if(user == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数ID："+id+"为找到对应的有效记录");
        }
        return baseDaoImpl.deleteOne(user);
    }
}
