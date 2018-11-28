package com.xqx.user.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xqx.user.data.pojo.entity.UserDO;

@Repository
public interface IUserRepository extends JpaRepository<UserDO, Long> {

	List<UserDO> findByName(@Param("name") String name);

	@Query("select t from UserDO t where t.name = :name")
	List<UserDO> findByName1(@Param("name") String name);

	@Query("select t from UserDO t where t.name = ?1")
	List<UserDO> findByName2(@Param("name") String name);

	UserDO findByNameAndPassword(String name, String password);

}
