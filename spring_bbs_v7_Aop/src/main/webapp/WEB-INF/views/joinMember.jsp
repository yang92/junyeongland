<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Yo, Sign Up here</title>
</head>
<body>
<h3><center>Register for my creativity.<br/> And <font color="green">be thankful to the administrator</font> for giving me a chance to be a creative man.</center></h3><br/><br/>

<form action = "/BBS/joinMember.bbs" method="post">
	<center>
			<label for="id"> I D : <input type="text" name="id" id="id"></label><br/><br/>
			<div id="idCheckStatus"></div>
			<label for="pass"> PASS : <input type="text" name="pass" id="pass"></label><br/><br/>
			<label for="name"> 이름 : <input type="text" name="name" id="name"></label><br/><br/>
			
			<input type="button" style="color:pink ; background-color:black ; font-size:20pt" value="Create ID for my creation"><br/>
			<input type = "reset" style="color:pink ; background-color:black ; font-size:20pt" value="I renounce my creation"><br/><br/>
	</center>
	</form>
	<script src="script/jquery-1.12.2.min.js"></script>
	<script src="script/joinMember.js"></script>
</body>
</html>