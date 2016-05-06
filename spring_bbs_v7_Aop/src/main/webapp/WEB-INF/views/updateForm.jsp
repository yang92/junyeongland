<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script>
$(document).ready(function() {
//		id 값으로 읽어 올려면 하나만 선택되어짐..클래스 사용
	$(".delFile").on("click", function() {
		var fileNum=$(this).attr("fileNum");			
		$(this).parent().remove();
		var delFileNum ="<input type='hidden' name='fileNumList' value='"+fileNum+"'>";		
		$(delFileNum).appendTo("form");	
				
	});

});
</script>
</head>
<body>
<form action="/bbs/update.bbs" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="pageNum" value="${pageNum}">               
    	<input type="hidden" name="fileStatus" value="${fileStatus}" > 
    	<input type="hidden" name="articleNum" value="${articleNum}">
	
	<table border="2" width="200" align="center">
	<tr>
		<td>글쓴이 :</td>
		<td>${id}</td>
	</tr>
	<tr>
		<td>제목 : </td>
		<td><input type="text" name="title" value="${article.title} "></td>
	</tr>
	<tr>
		<td colspan="2">
		<textarea cols="50" rows="20" name ="content">${article.content}</textarea>
		</td>
	</tr>
	
	<tr>
		<td>uploaded file(s)</td>
		<td>
<!-- 		업로드 된 파일 삭제하기 -->
			<c:if test="${fileList!=null}">
				<ul id="delGroup">
					<c:forEach var="file" items="${fileList}">
					<li>${file.originFname}
					<input type="button" fileNum="${file.fileNum}" value="delete" class="delFile">
					</li>
					</c:forEach>
				</ul>
			</c:if>
		</td>
	</tr>
	
	<tr>
		<td colspan="2"><input type="file" name="fname" value="파일 첨부" multiple></td>
	</tr>  
	
	<tr>
		<td><input type="submit" value="Submit"></td>
		<td><input type="reset" value="Cancel"></td>
	</tr>
	
	</table>
	
</form>
</body>
</html>