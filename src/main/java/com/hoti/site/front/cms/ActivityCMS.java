package com.hoti.site.front.cms;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.hoti.site.core.exception.SecurityException;
import com.hoti.site.db.entity.Activity;
import com.hoti.site.db.entity.ActivityExample;
import com.hoti.site.db.entity.DataGrid;
import com.hoti.site.db.entity.Pagination;
import com.hoti.site.db.entity.SiteResponse;
import com.hoti.site.db.entity.TableIDs;
import com.hoti.site.front.controller.SiteUtil;
import com.hoti.site.service.ActivityService;

@Controller
@RequestMapping("/cms/activities")
public class ActivityCMS {

  private Logger logger = LoggerFactory.getLogger(ActivityCMS.class);

  @Resource
  private ActivityService as;

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index(Model model) throws SecurityException {
    model.addAttribute("records", TableIDs.getList());
    return "cms/activities/index";
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String view(@PathVariable Long id, Model model) throws SecurityException {
    model.addAttribute("record", as.findOne(id));
    return "cms/activities/view";
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/list")
  public @ResponseBody DataGrid<Activity> dataGrid(@RequestParam(required = false) String name,
      @RequestParam(required = false) String tbl, Pagination p) throws SecurityException {

    ActivityExample example = new ActivityExample();
    ActivityExample.Criteria criteria = example.createCriteria();

    if (StringUtils.isNotBlank(name)) {
      criteria.andNameLike("%" + name + "%"); // 模糊查询
    }

    if (TableIDs.getList().contains(tbl)) {
      criteria.andTblEqualTo(tbl);
    }

    PageInfo<Activity> pageInfo = as.find(example, p);
    return new DataGrid<>(pageInfo);
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
  public @ResponseBody SiteResponse delete(@PathVariable Long id, HttpServletRequest request)
      throws SecurityException {
    as.addLogger("后台删除记录", TableIDs.ACTIVITY, id.toString(), request);
    logger.info("后台删除记录: {}", id);

    as.delete(id);
    return SiteUtil.ok();
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public @ResponseBody SiteResponse delete(@RequestParam String ids, HttpServletRequest request)
      throws SecurityException {
    as.addLogger("后台批量删除记录", TableIDs.ACTIVITY, ids, request);
    logger.info("后台批量删除记录: {}", ids);

    as.delete(ids.split(","));
    return SiteUtil.ok();
  }

}