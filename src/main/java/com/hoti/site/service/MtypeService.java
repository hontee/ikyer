package com.hoti.site.service;

import java.util.List;

import com.hoti.site.core.exception.SecurityException;
import com.hoti.site.db.entity.Attribute;
import com.hoti.site.db.entity.Mtype;
import com.hoti.site.db.entity.MtypeExample;
import com.hoti.site.db.entity.Pager;
import com.hoti.site.front.vo.MtypeVO;

public interface MtypeService extends Pager<Mtype, MtypeExample> {
	
	/**
	 * 按条件统计
	 * @param example
	 * @return
	 * @throws SecurityException
	 */
    int count(MtypeExample example) throws SecurityException;

    /**
     * 按条件删除
     * @param example
     * @throws SecurityException
     */
    void delete(MtypeExample example) throws SecurityException;

    /**
     * 删除
     * @param id
     * @throws SecurityException
     */
    void delete(Long id) throws SecurityException;

    /**
     * 添加
     * @param vo
     * @throws SecurityException
     */
    void add(MtypeVO vo) throws SecurityException;
    
    /**
     * 获取数据列表
     * @return
     * @throws SecurityException
     */
    List<Mtype> findAll() throws SecurityException;

    /**
     * 获取数据列表
     * @param example
     * @return
     * @throws SecurityException
     */
    List<Mtype> findAll(MtypeExample example) throws SecurityException;

    /**
     * 获取数据
     * @param id
     * @return
     * @throws SecurityException
     */
    Mtype findOne(Long id) throws SecurityException;
    
    /**
     * 按条件更新
     * @param record
     * @param example
     * @throws SecurityException
     */
    void update(Mtype record, MtypeExample example) throws SecurityException;

    /**
     * 更新
     * @param id
     * @param vo
     * @throws SecurityException
     */
    void update(Long id, MtypeVO vo) throws SecurityException;
    
    /**
     * 验证属性值是否存在
     * @param attr
     * @param value
     * @return
     * @throws SecurityException
     */
	boolean validate(Attribute attr, String value) throws SecurityException;
	
}