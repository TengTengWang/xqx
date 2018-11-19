package com.xqx.receive.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xqx.job.user.pojo.ReceiveDO;

@Repository
public interface IReceiveRepository extends JpaRepository<ReceiveDO, Long> {

	@Query("select t from ReceiveDO t where t.status = :status")
	List<ReceiveDO> findByStatus(@Param("status") String status);

}
