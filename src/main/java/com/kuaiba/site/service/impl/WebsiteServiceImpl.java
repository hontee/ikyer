package com.kuaiba.site.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kuaiba.site.db.dao.SiteFollowMapper;
import com.kuaiba.site.db.dao.WebsiteMapper;
import com.kuaiba.site.db.entity.Website;
import com.kuaiba.site.db.entity.WebsiteExample;
import com.kuaiba.site.service.WebsiteService;
import com.kuaiba.site.support.Pagination;
import com.kuaiba.site.support.ValidUtils;

@Service
public class WebsiteServiceImpl implements WebsiteService {
	
	@Resource
	private WebsiteMapper mapper;
	
	@Resource
	private SiteFollowMapper sfMapper;

	@Override
	public PageInfo<Website> findByExample(WebsiteExample example, Pagination p) {
		ValidUtils.checkNotNull(example, p);
		PageHelper.startPage(p.getPage(), p.getRows(), p.getOrderByClause());
		List<Website> list = this.findByExample(example);
		return new PageInfo<>(list);
	}

	@Override
	public int countByExample(WebsiteExample example) {
		ValidUtils.checkNotNull(example);
		return mapper.countByExample(example);
	}

	@Override
	public void deleteByExample(WebsiteExample example) {
		ValidUtils.checkNotNull(example);
		mapper.deleteByExample(example);
	}

	@Override
	public void deleteByPrimaryKey(Long id) {
		ValidUtils.checkPrimaryKey(id);
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void add(Website record) {
		ValidUtils.checkNotNull(record);
		mapper.insert(record);
	}

	@Override
	public List<Website> findByExample(WebsiteExample example) {
		ValidUtils.checkNotNull(example);
		return mapper.selectByExample(example);
	}

	@Override
	public Website findByPrimaryKey(Long id) {
		ValidUtils.checkPrimaryKey(id);
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByExample(Website record, WebsiteExample example) {
		ValidUtils.checkNotNull(record, example);
		mapper.updateByExample(record, example);
	}

	@Override
	public void updateByPrimaryKey(Website record) {
		ValidUtils.checkNotNull(record);
		mapper.updateByPrimaryKey(record);
	}

	@Override
	public void unfollow(Long uid, Long fid) {
		ValidUtils.checkPrimaryKey(uid);
		ValidUtils.checkPrimaryKey(fid);
		sfMapper.deleteByPrimaryKey(uid, fid);
	}

	@Override
	public void follow(Long uid, Long fid) {
		ValidUtils.checkPrimaryKey(uid);
		ValidUtils.checkPrimaryKey(fid);
		sfMapper.insert(uid, fid);
	}
	
}