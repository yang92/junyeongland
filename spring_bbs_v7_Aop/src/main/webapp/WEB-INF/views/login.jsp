<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>
	<form action = "/bbs/login.bbs" method="post" id="loginForm">
	<center>
		<input type="hidden" name="loginPath" value="${writeForm}">
			<label for="id"> I D : <input type="text" name="id" id="id"></label>
			<label for="pass"> PASS : <input type="text" name="pass" id="pass"></label>
			<input type ="submit" style="color:pink ; background-color:black ; font-size:15pt" value="Sign In">
			<input type="button" style="color:pink ; background-color:black ; font-size:15pt" value="Register">
			<input type = "reset" style="color:pink ; background-color:black ; font-size:15pt" value="Cancel"><br/><br/>
	</center>
	</form>
	<script src="script/jquery-1.12.2.min.js"></script>
	<script src="script/login.js"></script>
</body>
</html>