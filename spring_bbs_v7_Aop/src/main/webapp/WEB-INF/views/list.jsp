<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR" Content="Microsoft Visual Studio .NET 7.1">
<meta name="CODE_LANGUAGE" Content="C#">
<meta name="vs_defaultClientScript" content="JavaScript">
<meta name="vs_targetSchema" content="http://schemas.microsoft.com/intellisense/ie5">
<title>준영랜드</title>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script>
  
  document.oncontextmenu = function (e) {
   alert("소스 보지 마센 ㅋㅋㅋㅋ :p");
   return false;
}
 
  </script>
 <script type="text/javascript">
function keydowncheck()
{
var result = true;
var keycode = event.keyCode;
if(123 == keycode)
{
result = false;
}
return result;
}
</script>


</head>
<body>
<body onkeydown="return keydowncheck();">

<%-- <h1><center><font color="red">ㅎ1000 아 데이터 베이스를 확인해 보렴</font></center></h1> --%>
	<input type="hidden" id="title" value="${article.title}">

	<c:if test="${id!=null}">
		<%@include file = "loginOk.jsp" %>
	</c:if>
	
<!-- 	id값이 null이면 로그인창이 뜨게 한다 -->

	<c:if test="${id==null}">
		<%@include file = "login.jsp" %>

	</c:if>

<center><img src ="resources/main.gif"  width="400" height="200" title="YANG BONO" ></center>

<!-- 낫시...ㅠㅠㅠㅠ -->
<!-- <div style=" position: fixed; right: 20px; bottom:20px;"><img src = "resources/낫시.gif" title="낫시!!!낫시!!!낫시!!!!" ></div> -->

	<center><b><br/><img src ="resources/new.gif"  width="500" height="100" ><br/>
	<input type="button"
			style="color: pink; background-color: black; font-size: 20pt"
			value="GOOGLE 위성 위치서비스"
			onclick="document.location.href='/bbs/location.bbs'"><br/>
	(전체 글:${totalCount})</b>
		
<%-- 	<H1><center>공 지 사 항</center></H1> --%>
<%-- 	<%@include file = "master.jsp"%> --%>
	
<%-- 	<H1><center>자 유 게 시 판</center></H1> --%>
	
	<table width="700">
		<tr>
			<td align="right">
				<a href = "/bbs/writeForm.bbs">글쓰기</a>
			</td>
		</tr>
	</table>

	
	
	<table border="1" width="700" cellpadding= "2" cellspacing="2" align="center">
		<tr>
			<td align="center" width="50">글번호</td>
			<td align="center" width="250">제 목</td>
			<td align="center" width="100">작성자</td>
			<td align="center" width="150">작성일</td>
			<td align="center" width="50">조회</td>
		</tr>
		<c:forEach var="article" items="${articleList}" >
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
	  				<c:if test="${article.hit >=20 }">
	  					<img src="resources/HIT.png"  border="0" height="20">
	  				</c:if>
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
	<br/>
	<form id="form_serch" method="get" action="./">
		<select id="sch_type" name="sch_type">
			<option value="subject" selected="selected"> 제목</option>
			<option value="content">내용</option>
			<option value="user_name">작성자</option>
		</select>
		<input type="text" id="sch_value" name="sch_value"/>
		<button type="button" style="color: pink; background-color: black; font-size: 12pt" onclick="search();">search</button>
	</form>
	<script>
		function search(){
		
			var sch_value = jQuery('#form_search #sch_value').val();
			if(sch_value == ''){alert('검색어를 입력하세요.');}
			else{
				jQuery('#form_search').submit();
			}
		}
	</script>
	<br/><br/><br/><br/><br/>
</center>
</body>
</html>