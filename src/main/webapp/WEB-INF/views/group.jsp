<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="snippets/_header.jsp" %>
<div class="container">
<div class="card-columns column-4" style="margin-top: 20px;">
  <c:forEach items="${groups}" var="g">
  <div class="card">
    <div class="card-block">
      <h4 class="card-title"><a href="/groups/${g.id}">${g.title}</a></h4>
      <p class="card-text">${g.description}</p>
      <p class="card-text">
        <small class="text-muted">${g.count} 关注</small> · 
        <small class="text-muted">${g.count} 站点</small> · 
        <small class="text-muted">已关注</small>
      </p>
    </div>
  </div>
  </c:forEach>
</div>
</div>
</body>
</html>