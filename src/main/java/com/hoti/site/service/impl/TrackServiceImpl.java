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
import com.hoti.site.db.dao.TrackMapper;
import com.hoti.site.db.entity.PagerUtil;
import com.hoti.site.db.entity.Pagination;
import com.hoti.site.db.entity.Track;
import com.hoti.site.db.entity.TrackExample;
import com.hoti.site.db.entity.VUtil;
import com.hoti.site.service.TrackService;

@Service
public class TrackServiceImpl implements TrackService {
	
	@Resource
	private TrackMapper mapper;

	@Override
	public PageInfo<Track> find(TrackExample example, Pagination p) throws SecurityException { 
		try {
			VUtil.assertNotNull(example, p);
			PagerUtil.startPage(p);
			List<Track> list = findAll(example);
			return new PageInfo<>(list);
		} catch (Exception e) {
			throw new ReadException("分页读取异常失败", e);
		}
	}

	@Override
	public int count(TrackExample example) throws SecurityException { 
		try {
			VUtil.assertNotNull(example);
			return mapper.countByExample(example);
		} catch (Exception e) {
			throw new ReadException("统计异常失败", e);
		}
	}

	@Override
	public void delete(TrackExample example) throws SecurityException { 
		try {
			VUtil.assertNotNull(example);
			mapper.deleteByExample(example);
		} catch (Exception e) {
			throw new DeleteException("删除异常失败", e);
		}
	}

	@Override
	public void delete(Long id) throws SecurityException { 
		try {
			VUtil.assertNotNull(id);
			mapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			throw new DeleteException("删除异常失败", e);
		}
	}
	
	@Override
	public void delete(String[] ids) throws SecurityException { 
		try {
			VUtil.assertNotNull(ids);
			mapper.batchDelete(ids);
		} catch (Exception e) {
			throw new DeleteException("删除异常失败", e);
		}
	}

	@Override
	public void add(Track record) throws SecurityException { 
		try {
			VUtil.assertNotNull(record);
			mapper.insert(record);
		} catch (Exception e) {
			throw new CreateException("添加异常失败", e);
		}
	}

	@Override
	public List<Track> findAll(TrackExample example) throws SecurityException { 
		try {
			VUtil.assertNotNull(example);
			return mapper.selectByExample(example);
		} catch (Exception e) {
			throw new ReadException("读取异常失败", e);
		}
	}

	@Override
	public Track findOne(Long id) throws SecurityException { 
		try {
			VUtil.assertNotNull(id);
			return mapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new ReadException("读取异常失败", e);
		}
	}

	@Override
	public void update(Track record, TrackExample example) throws SecurityException { 
		try {
			VUtil.assertNotNull(record, example);
			mapper.updateByExample(record, example);
		} catch (Exception e) {
			throw new UpdateException("更新异常失败", e);
		}
	}

	@Override
	public void update(Long id, Track record) throws SecurityException { 
		try {
			VUtil.assertNotNull(record, id);
			mapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			throw new UpdateException("更新异常失败", e);
		}
	}

}