package com.xxl.job.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xxl.job.admin.core.model.XxlJobGroup;

/**
 * Created by xuxueli on 16/9/30.
 */
public interface XxlJobGroupDao {

	public List<XxlJobGroup> findAll();

	public List<XxlJobGroup> findByAddressType(@Param("addressType") int addressType);

	public List<XxlJobGroup> findByTitle(@Param("title") String title);

	public List<XxlJobGroup> findByTitleExceptSelf(@Param("title") String title, @Param("id") Integer id);

	public int save(XxlJobGroup xxlJobGroup);

	public int update(XxlJobGroup xxlJobGroup);

	public int remove(@Param("id") int id);

	public XxlJobGroup load(@Param("id") int id);
}
