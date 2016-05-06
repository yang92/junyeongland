$(function() {
	$("input[type=submit]").on("click", function(event) {
		event.preventDefault();
		var id = $("#id").val();
		var pass = $("#pass").val();

		if (id == '') {
			alert("아이디를 입력하세요.");
			$("#id").focus();
			return false;
		}
		
		if (pass == '') {
			alert("비밀번호를 입력하세요.");
			$("#pass").focus();
			return false;
		}
//		if(pass != )
		$("#loginForm").submit();
		return true;
	});
	
	$("input[type=button]").on("click",function(){
		document.location.href='/views/joinMember.jsp'
	});
});