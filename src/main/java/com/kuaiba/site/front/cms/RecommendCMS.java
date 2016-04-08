package com.kuaiba.site.front.cms;

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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.kuaiba.site.core.exception.SecurityException;
import com.kuaiba.site.db.entity.DataGrid;
import com.kuaiba.site.db.entity.Pagination;
import com.kuaiba.site.db.entity.Recommend;
import com.kuaiba.site.db.entity.RecommendExample;
import com.kuaiba.site.db.entity.SiteResponse;
import com.kuaiba.site.db.entity.StateAuditUtil;
import com.kuaiba.site.db.entity.TableIDs;
import com.kuaiba.site.front.controller.SiteUtil;
import com.kuaiba.site.front.vo.BookmarkVO;
import com.kuaiba.site.front.vo.RecommendVO;
import com.kuaiba.site.service.ActivityService;
import com.kuaiba.site.service.RecommendService;

@Controller
@RequestMapping("/cms/recmds")
public class RecommendCMS {

  private Logger logger = LoggerFactory.getLogger(RecommendCMS.class);

  @Resource
  private RecommendService rs;
  @Resource
  private ActivityService as;

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index() throws SecurityException {
    return "cms/recmds/index";
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String addPage() throws SecurityException {
    return "cms/recmds/new";
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
  public String editPage(@PathVariable Long id, Model model) throws SecurityException {
    model.addAttribute("record", rs.findOne(id));
    return "cms/recmds/edit";
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/ok", method = RequestMethod.GET)
  public String auditOKPage(@PathVariable Long id, Model model) throws SecurityException {
    model.addAttribute("record", rs.findOne(id));
    return "cms/recmds/ok";
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/refuse", method = RequestMethod.GET)
  public String auditRefusePage(@PathVariable Long id, Model model) throws SecurityException {
    model.addAttribute("record", rs.findOne(id));
    return "cms/recmds/refuse";
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String view(@PathVariable Long id, Model model) throws SecurityException {
    model.addAttribute("record", rs.findOne(id));
    return "cms/recmds/view";
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/list")
  public @ResponseBody DataGrid<Recommend> dataGrid(@RequestParam(required = false) String title,
      @RequestParam(required = false) Byte state, Pagination p) throws SecurityException {

    RecommendExample example = new RecommendExample();
    RecommendExample.Criteria criteria = example.createCriteria();

    if (StringUtils.isNotBlank(title)) {
      criteria.andTitleLike("%" + title + "%"); // 模糊查询
    }

    if (StateAuditUtil.validate(state)) {
      criteria.andStateEqualTo(state);
    }

    PageInfo<Recommend> pageInfo = rs.find(example, p);
    return new DataGrid<>(pageInfo);
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public @ResponseBody SiteResponse add(@RequestParam String url, HttpServletRequest request)
      throws SecurityException {
    as.addLogger("后台添加推荐", TableIDs.RECOMMEND, url, request);
    logger.info("后台添加推荐: {}", url);
    
    rs.add(url);
    return SiteUtil.ok();
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
  public @ResponseBody SiteResponse delete(@PathVariable Long id, HttpServletRequest request)
      throws SecurityException {
    as.addLogger("后台删除推荐", TableIDs.RECOMMEND, id.toString(), request);
    logger.info("后台删除推荐: {}", id);
    
    rs.delete(id);
    return SiteUtil.ok();
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
  public @ResponseBody SiteResponse edit(@PathVariable Long id, RecommendVO vo,
      HttpServletRequest request) throws SecurityException {
    as.addLogger("后台编辑推荐", TableIDs.RECOMMEND, id + ", " +JSON.toJSONString(vo), request);
    logger.info("后台编辑推荐: {}, {}", id, JSON.toJSONString(vo));
    
    rs.update(id, vo);
    return SiteUtil.ok();
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/ok", method = RequestMethod.POST)
  public @ResponseBody SiteResponse auditOk(@PathVariable Long id, BookmarkVO vo,
      HttpServletRequest request) throws SecurityException {
    as.addLogger("后台审核推荐通过", TableIDs.BOOKMARK, id + ", " + JSON.toJSONString(vo), request);
    logger.info("后台审核推荐通过: {}, {}", id, JSON.toJSONString(vo));
    
    rs.audit(id, vo);
    return SiteUtil.ok();
  }

  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/refuse", method = RequestMethod.POST)
  public @ResponseBody SiteResponse auditRefuse(@PathVariable Long id, @RequestParam String remark,
      HttpServletRequest request) throws SecurityException {
    as.addLogger("后台审核推荐拒绝", TableIDs.RECOMMEND, id + ", " + remark, request);
    logger.info("后台审核推荐拒绝: {}, {}", id, remark);
    
    rs.audit(id, remark);
    return SiteUtil.ok();
  }

}
