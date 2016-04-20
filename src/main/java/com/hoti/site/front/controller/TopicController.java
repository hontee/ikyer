package com.hoti.site.front.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.hoti.site.core.exception.SecurityException;
import com.hoti.site.core.security.AuthzUtil;
import com.hoti.site.db.entity.Filter;
import com.hoti.site.db.entity.Pagination;
import com.hoti.site.db.entity.Product;
import com.hoti.site.db.entity.Topic;
import com.hoti.site.db.entity.TopicExample;
import com.hoti.site.rest.BaseService;

@Controller
@Scope("prototype")
public class TopicController {

  @Resource
  private BaseService service;

  /**
   * @WebPage 主题 = 我 | 猜你喜欢 | 全部
   * @param model
   * @return
   * @throws SecurityException
   */
  @RequestMapping(value = "/groups", method = RequestMethod.GET)
  public String group(@RequestParam(required = false) String f, Pagination p, Model model,
      HttpServletRequest request) throws SecurityException {

    p.initFrontRows();
    Filter filter = Filter.parse(f);
    f = filter.toString().toLowerCase();
    PageInfo<Topic> pageInfo = new PageInfo<>();
    TopicExample example = new TopicExample();
    TopicExample.Criteria criteria = example.createCriteria();

    if (filter == Filter.MY) { // 我的产品
      pageInfo = service.findUserTopics(AuthzUtil.getUserId(), p);
    } else if (filter == Filter.NEW) { // 最新
      p.setOrderBy("created", "DESC");
      pageInfo = service.findTopics(example, p);
    } else if (filter == Filter.HOT) { // 最热
      p.setOrderBy("star", "DESC");
      pageInfo = service.findTopics(example, p);
    } else if (filter == Filter.PICK) { // 精选
      criteria.andPickEqualTo((byte) 1);
      pageInfo = service.findTopics(example, p);
    }

    ModelUtil.addHeader(model, "红提主题 | 更好的产品组织者");
    ModelUtil.addF(model, f);
    ModelUtil.addPager(model, pageInfo, "/groups?f=" + f);
    ModelUtil.addGroups(model, pageInfo.getList());
    return "group.ftl";
  }

  /**
   * @WebPage 主题下的所有产品（支持分页）
   * @param id
   * @param model
   * @param p
   * @return
   * @throws SecurityException
   */
  @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
  public String group(@PathVariable Long id, @RequestParam(defaultValue = "hot") String f,
      Model model, Pagination p, HttpServletRequest request) throws SecurityException {

    p.initFrontRows();
    Filter filter = Filter.parse(f);
    f = filter.toString().toLowerCase();
    Topic record = service.findTopic(id);
    
    PageInfo<Product> pageInfo = service.findTopicProducts(id, p);
    ModelUtil.addHeader(model, "红提主题 | 更好的产品组织者");
    ModelUtil.addF(model, f);
    ModelUtil.addPager(model, pageInfo, "/groups/" + id + "/?f=" + f);
    ModelUtil.addGroup(model, record);
    ModelUtil.addBookmarks(model, pageInfo.getList());
    return "group-bm.ftl";
  }

}
