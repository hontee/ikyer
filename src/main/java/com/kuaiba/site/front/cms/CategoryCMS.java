package com.kuaiba.site.front.cms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.kuaiba.site.aop.SiteLog;
import com.kuaiba.site.core.exception.SecurityException;
import com.kuaiba.site.db.entity.Category;
import com.kuaiba.site.db.entity.CategoryExample;
import com.kuaiba.site.db.entity.DataGrid;
import com.kuaiba.site.db.entity.Pagination;
import com.kuaiba.site.db.entity.SiteResponse;
import com.kuaiba.site.db.entity.TableIDs;
import com.kuaiba.site.front.co.BaseCO;
import com.kuaiba.site.front.vo.CategoryVO;

@Controller
@RequestMapping(CmsIDs.CMS_CATES)
public class CategoryCMS extends BaseCO {
	
	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.HOME, method = RequestMethod.GET)
	public String index() {
		return "cms/cates/index";
	}
	
	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.CREATE, method = RequestMethod.GET)
	public String addPage() {
		return "cms/cates/new";
	}

	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.EDIT, method = RequestMethod.GET)
	public String editPage(@PathVariable Long id, Model model) throws SecurityException {
		model.addAttribute("record", findByPrimaryKey(id));
		return "cms/cates/edit";
	}
	
	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.VIEW, method = RequestMethod.GET)
	public String view(@PathVariable Long id, Model model) throws SecurityException {
		model.addAttribute("record", findByPrimaryKey(id));
		return "cms/cates/view";
	}

	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.LIST)
	public @ResponseBody DataGrid<Category> dataGrid(@RequestParam(required = false) String title, Pagination p) throws Exception {
		CategoryExample example = new CategoryExample();
		if (StringUtils.isNotBlank(title)) {
			example.createCriteria().andTitleLike("%" + title + "%"); // 模糊查询
		}
		PageInfo<Category> pageInfo = categoryService.findByExample(example, p);
		return new DataGrid<>(pageInfo);
	}
	
	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.DATALIST)
	public @ResponseBody List<Category> datalist() throws Exception {
		CategoryExample example = new CategoryExample();
		example.createCriteria().andStateEqualTo((byte)1);
		return categoryService.findByExample(example);
	}
	
	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.CREATE, method = RequestMethod.POST)
	@SiteLog(action = "后台添加分类", table = TableIDs.CATEGORY, clazz = CategoryVO.class)
	public @ResponseBody SiteResponse add(CategoryVO vo, HttpServletRequest request) throws SecurityException {
		categoryService.add(vo);
		return ok();
	}

	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.DELETE, method = RequestMethod.POST)
	@SiteLog(action = "后台删除分类", table = TableIDs.CATEGORY)
	public @ResponseBody SiteResponse delete(@PathVariable Long id, HttpServletRequest request) throws SecurityException {
		categoryService.deleteByPrimaryKey(id);
		return ok();
	}
	
	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.EDIT, method = RequestMethod.POST)
	@SiteLog(action = "后台编辑分类", table = TableIDs.CATEGORY, clazz = CategoryVO.class)
	public @ResponseBody SiteResponse edit(@PathVariable Long id, CategoryVO vo, HttpServletRequest request) throws SecurityException {
		categoryService.updateByPrimaryKey(id, vo);
		return ok();
	}
	
	@RequiresRoles(value = "admin")
	@RequestMapping(value = CmsIDs.COUNT_TASK, method = RequestMethod.POST)
	@SiteLog(action = "后台统计分类数据", table = TableIDs.CATEGORY, clazz = String.class)
	public @ResponseBody SiteResponse countTask(
			@RequestParam(defaultValue = "后台统计分类数据") String desc, 
			HttpServletRequest request) throws SecurityException {
		countable.countCategoryTask();
		return ok();
	}
	
	private Category findByPrimaryKey(Long id) throws SecurityException {
		return categoryService.findByPrimaryKey(id);
	}
}
