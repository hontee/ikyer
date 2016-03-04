package com.kuaiba.site;

/**
 * 前端请求URL
 * @author larry.qi
 *
 */
public interface HttpIds {
	
	String ABOUT = "/about";
	String SEARCH = "/search";
	String LOGIN = "login";
	String LOGOUT = "logout";
	String USER_HOME = "/{name}/dashbord";
	String USER_PROFILE = "/{name}/profile";
	String GROUPS = "/groups";
	String GROUPS_BY_ID = "/groups/{id}";
	String HOME = "";
	String WEBSITE_HIT = "/{id}/hit";
	String RECOMMEND = "/share";
	String BOOKMARK_FOLLOW = "/bookmarks/{id}/follow";
	String BOOKMARK_UNFOLLOW = "/bookmarks/{id}/unfollow";
	
}
