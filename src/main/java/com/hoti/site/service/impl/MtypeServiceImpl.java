package com.hoti.site.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.hoti.site.core.exception.CreateException;
import com.hoti.site.core.exception.DeleteException;
import com.hoti.site.core.exception.ReadException;
import com.hoti.site.core.exception.SecurityException;
import com.hoti.site.core.exception.UpdateException;
import com.hoti.site.core.exception.ValidationException;
import com.hoti.site.core.security.AuthzUtil;
import com.hoti.site.core.security.MemcachedUtil;
import com.hoti.site.db.dao.MtypeMapper;
import com.hoti.site.db.entity.Attribute;
import com.hoti.site.db.entity.Mtype;
import com.hoti.site.db.entity.MtypeExample;
import com.hoti.site.db.entity.PagerUtil;
import com.hoti.site.db.entity.Pagination;
import com.hoti.site.db.entity.VUtil;
import com.hoti.site.front.vo.MtypeVO;
import com.hoti.site.service.CachePolicy;
import com.hoti.site.service.MtypeService;

@Service
public class MtypeServiceImpl implements MtypeService {

  @Resource
  private MtypeMapper mapper;
  @Resource
  private CachePolicy cacheMgr;

  @Override
  public PageInfo<Mtype> find(MtypeExample example, Pagination p) throws SecurityException {
    try {
      VUtil.assertNotNull(example, p);
      PagerUtil.startPage(p);
      List<Mtype> list = findAll(example);
      return new PageInfo<>(list);
    } catch (Exception e) {
      throw new ReadException("分页读取类型失败", e);
    }
  }

  @Override
  public int count(MtypeExample example) throws SecurityException {
    try {
      VUtil.assertNotNull(example);
      return mapper.countByExample(example);
    } catch (Exception e) {
      throw new ReadException("统计类型失败", e);
    }
  }

  @Override
  public void delete(MtypeExample example) throws SecurityException {
    try {
      MemcachedUtil.delete("mtypes");
      VUtil.assertNotNull(example);
      mapper.deleteByExample(example);
    } catch (Exception e) {
      throw new DeleteException("删除类型失败", e);
    }
  }

  @Override
  public void delete(Long id) throws SecurityException {
    try {
      MemcachedUtil.delete("mtypes");
      VUtil.assertNotNull(id);
      mapper.deleteByPrimaryKey(id);
    } catch (Exception e) {
      throw new DeleteException("删除类型失败", e);
    }
  }

  @Override
  public void add(MtypeVO vo) throws SecurityException {
    try {
      MemcachedUtil.delete("mtypes");
      VUtil.assertNotNull(vo);
      Mtype record = new Mtype();
      record.setWeight(vo.getWeight());
      record.setCreator(AuthzUtil.getUsername());
      record.setDescription(vo.getDescription());
      record.setState(vo.getState());
      record.setName(vo.getName());
      record.setTitle(vo.getTitle());
      mapper.insert(record);
    } catch (Exception e) {
      throw new CreateException("添加类型失败", e);
    }
  }

  @Override
  public List<Mtype> findAll() throws SecurityException {
    return cacheMgr.readMtypes();
  }

  @Override
  public List<Mtype> findAll(MtypeExample example) throws SecurityException {
    try {
      VUtil.assertNotNull(example);
      return mapper.selectByExample(example);
    } catch (Exception e) {
      throw new ReadException("读取类型失败", e);
    }
  }

  @Override
  public Mtype findOne(Long id) throws SecurityException {
    try {
      VUtil.assertNotNull(id);
      return mapper.selectByPrimaryKey(id);
    } catch (Exception e) {
      throw new ReadException("读取类型失败", e);
    }
  }

  @Override
  public void update(Mtype record, MtypeExample example) throws SecurityException {
    try {
      MemcachedUtil.delete("mtypes");
      VUtil.assertNotNull(record, example);
      mapper.updateByExample(record, example);
    } catch (Exception e) {
      throw new UpdateException("更新类型失败", e);
    }
  }

  @Override
  public void update(Long id, MtypeVO vo) throws SecurityException {
    try {
      MemcachedUtil.delete("mtypes");
      VUtil.assertNotNull(vo, id);
      Mtype record = new Mtype();
      record.setId(id);
      record.setWeight(vo.getWeight());
      record.setDescription(vo.getDescription());
      record.setState(vo.getState());
      record.setName(vo.getName());
      record.setTitle(vo.getTitle());
      mapper.updateByPrimaryKey(record);
    } catch (Exception e) {
      throw new UpdateException("更新类型失败", e);
    }
  }

  @Override
  public boolean validate(Attribute attr, String value) throws SecurityException {
    try {
      VUtil.assertNotNull(value);
      MtypeExample example = new MtypeExample();

      if (attr == Attribute.TITLE) {
        example.createCriteria().andTitleEqualTo(value);
      } else { // Attribute.NAME
        example.createCriteria().andNameEqualTo(value);
      }

      return !mapper.selectByExample(example).isEmpty();
    } catch (Exception e) {
      throw new ValidationException("验证类型" + attr.name() + "失败", e);
    }
  }

}