<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>



	<table border="1" width="700" cellpadding= "2" cellspacing="2" align="center">
		<tr>
			<td align="center" width="50">글번호</td>
			<td align="center" width="250">제 목</td>
			<td align="center" width="100">작성자</td>
			<td align="center" width="150">작성일</td>
			<td align="center" width="50">조회</td>
		</tr>
		<c:forEach var="article" items="${masterArticleList}" >
			<tr height="30">
				<td align="center" width="50">
					<c:out value="${article.articleNum}"/>
				</td>
				<td width="250">
					
					<c:if test="${article.depth > 0}">
						<img src ="resources/blank.png"  width="${20 * article.depth}" height="20">
						<img src="resources/reply.png" width="30"height="20">
					</c:if>
					<c:if test="${article.depth == 0}">
<!-- 	    				<img src="images/white.gif"  width="0"  height="16"> -->
	  				</c:if>   
	  				  
	  				<a href="/bbs/content.bbs?articleNum=${article.articleNum}&pageNum=${pageNum}&fileStatus=${article.fileStatus}">
	  				${article.title}</a> &nbsp;&nbsp;[${article.commentCount}]
<%-- 	  				<c:if test="${article.hit >=20 }"> --%>
<!-- 	  					<img src="resources/HIT.png"  border="0" height="20"> -->
<%-- 	  				</c:if> --%>
	  				<c:if test="${article.fileStatus ==1}">
	  					<img src="resources/디스켓.png" width="30" height="20">
	  				</c:if>
				</td>
				<td align="center" width="100">${article.id}</td>
				<td align="center" width="150">${article.writeDate}</td>
				<td align="center" width="50">${article.hit}</td>
				
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5" align="center" height="40">
			${pageCode}
			</td>
		</tr>
	</table>
</body>
</html>