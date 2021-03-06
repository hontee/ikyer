package com.ikyer.site.rest;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ikyer.site.core.exception.SecurityException;
import com.ikyer.site.db.entity.Menu;
import com.ikyer.site.db.entity.MenuExample;
import com.ikyer.site.db.entity.Pagination;
import com.ikyer.site.db.entity.Product;
import com.ikyer.site.db.entity.ProductExample;
import com.ikyer.site.db.entity.Recommend;
import com.ikyer.site.db.entity.RecommendExample;
import com.ikyer.site.db.entity.Topic;
import com.ikyer.site.db.entity.TopicExample;
import com.ikyer.site.db.entity.TopicProduct;
import com.ikyer.site.db.entity.User;
import com.ikyer.site.db.entity.UserExample;
import com.ikyer.site.front.vo.MenuVO;
import com.ikyer.site.front.vo.ProductVO;
import com.ikyer.site.front.vo.RecommendVO;
import com.ikyer.site.front.vo.TopicVO;
import com.ikyer.site.front.vo.UserVO;

public interface BaseService {

  /**
   * 统计Menu
   * 
   * @param example
   * @return
   */
  int countMenu(MenuExample example) throws SecurityException;

  /**
   * 统计产品
   * 
   * @param example
   * @return
   */
  int countProduct(ProductExample example) throws SecurityException;
  
  /**
   * 统计产品
   * 
   * @param title
   * @return
   */
  int countProduct(String title) throws SecurityException;

  /**
   * 统计推荐
   * 
   * @param example
   * @return
   */
  int countRecommend(RecommendExample example) throws SecurityException;

  /**
   * 统计主题
   * 
   * @param example
   * @return
   */
  int countTopic(TopicExample example) throws SecurityException;
  
  /**
   * 统计主题
   * 
   * @param title
   * @return
   */
  int countTopic(String title) throws SecurityException;

  /**
   * 统计用户
   * 
   * @param example
   * @return
   */
  int countUser(UserExample example) throws SecurityException;

  /**
   * 删除菜单
   * 
   * @param id
   * @return
   */
  void deleteMenu(Long id) throws SecurityException;

  /**
   * 删除产品
   * 
   * @param id
   * @return
   */
  void deleteProduct(Long id) throws SecurityException;

  /**
   * 删除推荐
   * 
   * @param id
   * @return
   */
  void deleteRecommend(Long id) throws SecurityException;

  /**
   * 删除主题
   * 
   * @param id
   * @return
   */
  void deleteTopic(Long id) throws SecurityException;

  /**
   * 删除主题和产品的关联
   * 
   * @param tid 主题ID
   * @param pid 产品ID
   * @return
   */
  void deleteTP(Long tid, Long pid) throws SecurityException;
  
  /**
   * 批量移除主题和产品的关联
   * @param tid
   * @param pids
   * @throws SecurityException
   */
  void deleteTP(Long tid, Long[] pids) throws SecurityException;

  /**
   * 删除用户
   * 
   * @param id
   * @return
   */
  void deleteUser(Long id) throws SecurityException;

  /**
   * 添加菜单
   * 
   * @param record
   * @return
   */
  int addMenu(Menu record) throws SecurityException;

  /**
   * 添加菜单
   * 
   * @param vo
   * @throws SecurityException
   */
  int addMenu(MenuVO vo) throws SecurityException;

  /**
   * 添加产品
   * 
   * @param record
   * @return
   */
  int addProduct(Product record) throws SecurityException;

  /**
   * 添加产品
   * 
   * @param record
   * @return
   */
  int addProduct(ProductVO vo) throws SecurityException;

  /**
   * 添加推荐
   * 
   * @param record
   * @return
   */
  int addRecommend(Recommend record) throws SecurityException;

  /**
   * 添加推荐
   * 
   * @param url
   * @return
   */
  void addRecommend(Long uid, String url) throws SecurityException;

  /**
   * 添加主题
   * 
   * @param record
   * @return
   */
  int addTopic(Topic record) throws SecurityException;
  
  /**
   * 添加主题
   * @param vo
   * @throws SecurityException
   */
  int addTopic(TopicVO vo) throws SecurityException;

  /**
   * 添加主题和产品的关联
   * 
   * @param tid 主题ID
   * @param pid 产品ID
   * @return
   */
  void addTP(Long tid, Long pid) throws SecurityException;
  
  /**
   * 批量添加主题和产品的关联
   * 
   * @param tid
   * @param pids
   * @throws SecurityException
   */
  void addTP(Long tid, Long[] pids) throws SecurityException;

  /**
   * 添加用户
   * 
   * @param record
   * @return
   */
  int addUser(User record) throws SecurityException;

  /**
   * 添加用户
   * 
   * @param record
   * @return
   */
  int addUser(UserVO record) throws SecurityException;

  /**
   * 查询菜单
   * 
   * @param id
   * @return
   */
  Menu findMenu(Long id) throws SecurityException;

  /**
   * 查询菜单
   * 
   * @param example
   * @return
   */
  PageInfo<Menu> findMenus(MenuExample example, Pagination p) throws SecurityException;

  /**
   * 查询所有菜单
   * 
   * @param example
   * @return
   */
  List<Menu> findAllMenus() throws SecurityException;

  /**
   * 查询产品
   * 
   * @param id
   * @return
   */
  Product findProduct(Long uid, Long id) throws SecurityException;

  /**
   * 查询产品
   * 
   * @param example
   * @return
   */
  PageInfo<Product> findProducts(Long uid, ProductExample example, Pagination p) throws SecurityException;

  /**
   * 主题关联的产品列表
   * 
   * @param tid 主题ID
   * @param title 产品标题
   * @param state 产品状态
   * @param pick 精选
   * @return
   */
  PageInfo<Product> findProducts(Long uid, TopicProduct tp, Pagination p)
      throws SecurityException;
  /**
   * 用户关注的产品列表
   * 
   * @param uid 用户ID
   * @return
   */
  PageInfo<Product> findProducts(Long uid, Pagination p) throws SecurityException;

  /**
   * 查询推荐
   * 
   * @param id
   * @return
   */
  Recommend findRecommend(Long id) throws SecurityException;

  /**
   * 查询推荐
   * 
   * @param example
   * @return
   */
  PageInfo<Recommend> findRecommends(RecommendExample example, Pagination p)
      throws SecurityException;

  /**
   * 查询主题
   * 
   * @param id
   * @return
   */
  Topic findTopic(Long uid, Long id) throws SecurityException;

  /**
   * 查询主题
   * 
   * @param example
   * @return
   */
  PageInfo<Topic> findTopics(Long uid, TopicExample example, Pagination p) throws SecurityException;

  /**
   * 用户关注的主题列表
   * 
   * @param uid 用户ID
   * @return
   */
  PageInfo<Topic> findTopics(Long uid, Pagination p) throws SecurityException;

  /**
   * 查询用户
   * 
   * @param id
   * @return
   */
  User findUser(Long id) throws SecurityException;

  /**
   * 查询用户
   * 
   * @param username
   * @return
   */
  User findUser(String username) throws SecurityException;

  /**
   * 查询用户
   * 
   * @param example
   * @return
   */
  PageInfo<User> findUsers(UserExample example, Pagination p) throws SecurityException;

  /**
   * 关注产品的用户列表
   * 
   * @param fid 产品ID
   * @return
   */
  PageInfo<User> findUsersByProduct(Long fid, Pagination p) throws SecurityException;
  
  /**
   * 关注产品的用户列表
   * @param fid 产品ID
   * @param user 用户对象
   * @return
   */
  PageInfo<User> findUsersByProduct(Long fid, User user, Pagination p) throws SecurityException;
  
  /**
   * 关注主题的用户列表
   * 
   * @param fid 主题ID
   * @return
   */
  PageInfo<User> findUsersByTopic(Long fid, Pagination p) throws SecurityException;
  
  /**
   * 关注主题的用户列表
   * @param fid 产品ID
   * @param user 用户对象
   * @return
   */
  PageInfo<User> findUsersByTopic(Long fid, User user, Pagination p) throws SecurityException;

  /**
   * 用户关注产品IDs
   * 
   * @param uid 用户ID
   * @return
   */
  List<Long> findProductIds(Long uid) throws SecurityException;

  /**
   * 用户关注主题IDs
   * 
   * @param uid 用户ID
   * @return
   */
  List<Long> findTopicIds(Long uid) throws SecurityException;

  /**
   * 判断用户是否关注产品
   * @param pid 产品ID
   * @return
   */
  boolean isFollowProduct(Long uid, Long pid);
  
  /**
   * 判断用户是否关注主题
   * @param tid 主题ID
   * @return
   */
  boolean isFollowTopic(Long uid, Long tid);

  /**
   * 检测产品的链接地址
   * 
   * @param url
   * @return
   * @throws SecurityException
   */
  boolean checkProductUrl(Long uid, String url) throws SecurityException;

  /**
   * 检测用户是否关注该产品
   * 
   * @param fid 产品ID
   * @return
   * @throws SecurityException
   */
  boolean checkProductFollow(Long uid, Long fid) throws SecurityException;
  
  /**
   * 检测用户是否关注该主题
   * 
   * @param fid 主题ID
   * @return
   * @throws SecurityException
   */
  boolean checkTopicFollow(Long uid, Long fid) throws SecurityException;

  /**
   * 检测用户名
   * 
   * @param username
   * @return
   * @throws SecurityException
   */
  boolean checkUser(String username) throws SecurityException;

  /**
   * 登录认证
   * 
   * @param username
   * @param password
   * @throws SecurityException
   */
  void authenticate(String username, String password) throws SecurityException;

  /**
   * 审核通过
   * 
   * @param id
   * @param remark
   */
  void auditRecommendOk(Long id, ProductVO vo) throws SecurityException;

  /**
   * 审核拒绝
   * 
   * @param id
   * @param remark
   */
  void auditRecommendRefuse(Long id, String remark) throws SecurityException;

  /**
   * 更新菜单
   * 
   * @param record
   * @return
   */
  void updateMenu(Menu record) throws SecurityException;

  /**
   * 更新菜单
   * 
   * @param id
   * @param vo
   * @throws SecurityException
   */
  void updateMenu(Long id, MenuVO vo) throws SecurityException;

  /**
   * 更新产品
   * 
   * @param record
   * @return
   */
  void updateProduct(Product record) throws SecurityException;

  /**
   * 更新产品
   * 
   * @param id
   * @param vo
   * @throws SecurityException
   */
  void updateProduct(Long id, ProductVO vo) throws SecurityException;

  /**
   * 更新产品点击，并获取返回URL
   * 
   * @param id
   * @return
   * @throws SecurityException
   */
  String updateProductHit(Long uid, Long id) throws SecurityException;

  /**
   * 更新推荐
   * 
   * @param record
   * @return
   */
  void updateRecommend(Recommend record) throws SecurityException;

  /**
   * 更新推荐
   * 
   * @param id
   * @param vo
   * @throws SecurityException
   */
  void updateRecommend(Long id, RecommendVO vo) throws SecurityException;

  /**
   * 更新主题
   * 
   * @param record
   * @return
   */
  void updateTopic(Topic record) throws SecurityException;
  
  /**
   * 更新主题
   * 
   * @param id
   * @param vo
   * @throws SecurityException
   */
  void updateTopic(Long id, TopicVO vo) throws SecurityException;
  
  /**
   * 更新用户
   * 
   * @param record
   * @return
   */
  void updateUser(User record) throws SecurityException;

  /**
   * 更新用户
   * 
   * @param record
   * @return
   */
  void updateUser(Long id, UserVO record) throws SecurityException;

  /**
   * 修改密码
   * 
   * @param record
   * @return
   */
  void updateUser(Long id, String password) throws SecurityException;

  /**
   * 关注产品
   * 
   * @param uid 用户ID
   * @param fid 产品ID
   * @return
   */
  void followProduct(Long uid, Long fid) throws SecurityException;

  /**
   * 取消关注产品
   * 
   * @param uid 用户ID
   * @param fid 产品ID
   * @return
   */
  void unfollowProduct(Long uid, Long fid) throws SecurityException;

  /**
   * 关注主题
   * 
   * @param uid 用户ID
   * @param fid 主题ID
   * @return
   */
  void followTopic(Long uid, Long fid) throws SecurityException;

  /**
   * 取消关注主题
   * 
   * @param uid 用户ID
   * @param fid 主题ID
   * @return
   */
  void unfollowTopic(Long uid, Long fid) throws SecurityException;

  /**
   * 精选产品
   * 
   * @param pick
   * @param array
   * @return
   */
  void pickProduct(Long[] array) throws SecurityException;

  /**
   * 取消精选产品
   * 
   * @param pick
   * @param array
   * @return
   */
  void unpickProduct(Long[] array) throws SecurityException;

  /**
   * 精选主题
   * 
   * @param pick
   * @param array
   * @return
   */
  void pickTopic(Long[] array) throws SecurityException;

  /**
   * 取消精选主题
   * 
   * @param pick
   * @param array
   * @return
   */
  void unpickTopic(Long[] array) throws SecurityException;

}
