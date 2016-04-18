package com.hoti.site.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hoti.site.core.exception.SecurityException;
import com.hoti.site.db.entity.Activity;
import com.hoti.site.db.entity.ActivityExample;
import com.hoti.site.db.entity.Pager;

public interface ActivityService extends Pager<Activity, ActivityExample> {

  /**
   * 按条件统计
   * 
   * @param example
   * @return
   * @throws SecurityException
   */
  int count(ActivityExample example) throws SecurityException;

  /**
   * 删除
   * 
   * @param id
   * @throws SecurityException
   */
  void delete(Long id) throws SecurityException;

  /**
   * 批量删除
   * 
   * @param ids
   * @throws SecurityException
   */
  void delete(String[] ids) throws SecurityException;

  /**
   * 添加
   * 
   * @param record
   * @throws SecurityException
   */
  void addLogger(String name, String tbl, String desc, HttpServletRequest request)
      throws SecurityException;

  /**
   * 读取数据
   * 
   * @param example
   * @return
   * @throws SecurityException
   */
  List<Activity> findAll(ActivityExample example) throws SecurityException;

  /**
   * 读取一条数据
   * 
   * @param id
   * @return
   * @throws SecurityException
   */
  Activity findOne(Long id) throws SecurityException;
}