<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
</head>
<body>
<form action="/bbs/write.bbs" method="post" enctype="multipart/form-data">
	<table border="2" width="200" align="center">
	<tr>
		<td>글쓴이 :</td>
		<td>${id}</td>
	</tr>
	<tr>
		<td>제목 : </td>
		<td><input type="text" name="title"></td>
	</tr>
	<tr>
		<td colspan="2">
		<textarea cols="50" rows="20" name ="content"></textarea>
		</td>
	</tr>
	<tr>
		<td>첨부 : </td>
		<td><input type="file"
						value="upload" name="fname" multiple></td>
	</tr> 
	<tr>
		<td><input type="submit"
						style="color: pink; background-color: black; font-size: 20pt"
						value="write"></td>
<!-- 		<td><input type="reset" value="글쓰기 취소"></td> -->
		<td><input type="button"
						style="color: pink; background-color: black; font-size: 20pt"
						value="Back to the list"
						onclick="document.location.href='/bbs/list.bbs?pageNum=1'"></td>
<!-- 		<td><input type="button" value="목록으로" onclick="document.location.href='/BBS/list.bbs?pageNum=1"></td> -->
	</tr>
	</table>
</form>
</body>
</html>