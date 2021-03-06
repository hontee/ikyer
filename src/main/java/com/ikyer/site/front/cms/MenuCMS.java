package com.ikyer.site.front.cms;

import java.util.List;

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
import com.ikyer.site.core.exception.SecurityException;
import com.ikyer.site.db.entity.DataGrid;
import com.ikyer.site.db.entity.Menu;
import com.ikyer.site.db.entity.MenuExample;
import com.ikyer.site.db.entity.Pagination;
import com.ikyer.site.db.entity.VUtil;
import com.ikyer.site.front.BaseController;
import com.ikyer.site.front.vo.MenuVO;
import com.ikyer.site.front.vo.ResponseVO;
import com.ikyer.site.rest.BaseService;

@Controller
@RequestMapping("/cms/menus")
public class MenuCMS extends BaseController {

  private Logger logger = LoggerFactory.getLogger(MenuCMS.class);

  @Resource
  private BaseService service;

  /**
   * 菜单管理首页
   * 
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index() throws SecurityException {
    return "cms/menus/index";
  }

  /**
   * 新建菜单页
   * 
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String addPage() throws SecurityException {
    return "cms/menus/new";
  }

  /**
   * 编辑菜单页
   * 
   * @param id
   * @param model
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
  public String editPage(@PathVariable Long id, Model model) throws SecurityException {
    super.addRecord(model, service.findMenu(id));
    return "cms/menus/edit";
  }

  /**
   * 菜单详情页
   * 
   * @param id
   * @param model
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String view(@PathVariable Long id, Model model) throws SecurityException {
    super.addRecord(model, service.findMenu(id));
    return "cms/menus/view";
  }

  /**
   * 所有菜单数据, 用于Home.jsp
   * 
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/datalist")
  public @ResponseBody List<Menu> datalist() throws SecurityException {
    return service.findAllMenus();
  }

  /**
   * 菜单数据列表，支持分页和查询
   * 
   * @param title 菜单标题，模糊查询
   * @param state 状态 -1=全部状态 0=禁用 1=启用
   * @param p 默认的分页组件
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/list")
  public @ResponseBody DataGrid<Menu> dataGrid(@RequestParam(required = false) String title,
      @RequestParam(required = false) Byte state, Pagination p) throws SecurityException {

    MenuExample example = new MenuExample();
    MenuExample.Criteria criteria = example.createCriteria();

    /* 标题 模糊查询 */
    if (StringUtils.isNotBlank(title)) {
      criteria.andTitleLike("%" + title + "%");
    }

    /* 验证状态 0=禁用，1=启用 */
    if (VUtil.assertBaseState(state)) {
      criteria.andStateEqualTo(state);
    }

    PageInfo<Menu> pageInfo = service.findMenus(example, p);
    return new DataGrid<>(pageInfo);
  }

  /**
   * 新建菜单
   * 
   * @param vo 请求参数
   * @param request
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public @ResponseBody ResponseVO addMenu(MenuVO vo, HttpServletRequest request)
      throws SecurityException {
    logger.info("后台添加菜单: {}", JSON.toJSONString(vo));
    service.addMenu(vo);
    return buildResponse();
  }

  /**
   * 删除菜单
   * 
   * @param id 菜单ID
   * @param request
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
  public @ResponseBody ResponseVO deleteMenu(@PathVariable Long id, HttpServletRequest request)
      throws SecurityException {
    logger.info("后台删除菜单: {}", id);
    service.deleteMenu(id);
    return buildResponse();
  }

  /**
   * 编辑菜单
   * 
   * @param id 菜单ID
   * @param vo 请求参数
   * @param request
   * @return
   * @throws SecurityException
   */
  @RequiresRoles(value = "admin")
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
  public @ResponseBody ResponseVO editMenu(@PathVariable Long id, MenuVO vo,
      HttpServletRequest request) throws SecurityException {
    logger.info("后台编辑菜单: {}, {}", id, JSON.toJSONString(vo));
    service.updateMenu(id, vo);
    return buildResponse();
  }

}
