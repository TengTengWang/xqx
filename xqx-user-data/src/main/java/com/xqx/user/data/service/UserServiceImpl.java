package com.xqx.user.data.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.xqx.base.exception.ErrorCode;
import com.xqx.base.exception.ServiceException;
import com.xqx.base.pojo.dto.UserDTO;
import com.xqx.user.data.dao.IUserRepository;
import com.xqx.user.data.pojo.entity.UserDO;
import com.xqx.user.data.util.POJOConverter;

@Service
//缓存设置。cacheNames：当前默认缓存名称；keyGenerator：默认的key生成策略
@CacheConfig(cacheNames="user",keyGenerator = "wiselyKeyGenerator")
public class UserServiceImpl implements IUserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	IUserRepository userRepostory;

    @Override
    @CachePut	// @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存  
    public UserDTO saveUser(UserDTO user) throws ServiceException {
        if(user == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"User对象不能为Null");
        }
        UserDO userDO = POJOConverter.toUserDO(user);
        userDO = userRepostory.saveAndFlush(userDO);
        return POJOConverter.toUserDTO(userDO);
    }

    /**
     * Cacheable(value="cacheName", key ="#id",sync = true, unless = "#user eq null")
     * 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * value：缓存key的前缀。
     * key：缓存key的后缀。
     * sync：设置如果缓存过期是不是只放一个请求去请求数据库，其他请求阻塞，默认是false。
     * unless 表示条件表达式成立的话不放入缓存。用于方法执行后校验
     */
    @Override
    @Cacheable(sync = true)
    public UserDTO getUserByID(Long id) throws ServiceException {
        if(id == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数ID不能为Null");
        }
        UserDO userDO = userRepostory.getOne(id);
        return POJOConverter.toUserDTO(userDO);
    }

    @Override
    @Cacheable(sync = true)
    public UserDTO getUser(String name, String password) throws ServiceException {
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
        UserDO userDO = userRepostory.findByNameAndPassword(name, password);
        if(userDO == null) {
        	throw new ServiceException(ErrorCode.DAO_NOTFOUND,"未找到用户");
        }
        return POJOConverter.toUserDTO(userDO);
    }

    //@Cacheable(sync = true)
    public List<UserDTO> listUser() throws ServiceException {
    	List<UserDO> listUserDO = userRepostory.findAll();
    	return POJOConverter.toUserDTOList(listUserDO);
    }

    @Override
    @Cacheable(sync = true)
    public Long countUser(String name) throws ServiceException {
    	if(name == null){
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数Name不能为Null");
        }
    	UserDO user = new UserDO();
        user.setName(name);
        Example<UserDO> example = Example.of(user);
        return userRepostory.count(example);
    }

    @Override
    @CacheEvict	// @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
    public void removeUser(Long id) throws ServiceException {
    	if(id == null) {
    		throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT,"参数ID："+id+"不能为Null");
    	}
		userRepostory.deleteById(id);
    }
    
    
    @Override
    public UserDTO doForbidden(Long id)throws ServiceException{
    	try {
    		UserDO userDO = userRepostory.getOne(id);
			userDO.setForbidden(true);
			userDO = userRepostory.saveAndFlush(userDO);
			return POJOConverter.toUserDTO(userDO);
		} catch(EntityNotFoundException e){
			throw new ServiceException(ErrorCode.DAO_NOTFOUND,"参数用户ID："+id+"未找到对应的有效记录");
		} catch (Exception e) {
			throw new ServiceException(ErrorCode.DAO_ERROR,"参数用户ID："+id+"设置黑名单失败");
		}
    }
    
    @Override
    public UserDTO doUnforbidden(Long id)throws ServiceException{
    	try {
    		UserDO userDO = userRepostory.getOne(id);
			if(userDO.getForbidden()) {
				userDO.setForbidden(false);
				userDO = userRepostory.saveAndFlush(userDO);
			}else {
				logger.info("用户ID {}无需解禁",id);
			}
			return POJOConverter.toUserDTO(userDO);
		
		} catch(EntityNotFoundException e){
			throw new ServiceException(ErrorCode.DAO_NOTFOUND,"参数用户ID："+id+"未找到对应的有效记录");
		} catch (Exception e) {
			throw new ServiceException(ErrorCode.DAO_ERROR,"参数用户ID："+id+"解禁失败");
		}
    }
}