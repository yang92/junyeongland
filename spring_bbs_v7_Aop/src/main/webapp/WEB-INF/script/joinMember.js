$.ajaxSetup({
	type : "POST",
	async : true,
	dataType : "json",
	error : function(xhr){
		alert("error html=" + xhr.statusText);
	}
});

$(function(){
	$("#id").on("blur",function(){
		$.ajax({
			url : "/BBS/joinIdCheck.bbs",
			data : {
				inputId : $("#id").val()
			},
			success : function(data){
				var html;
				if(data.idUseStatus=="1"){
					html="<b>How creative you are!</b>"
					$("#idCheckStatus").html(html).css("color","green");
				}else{
					html="<b>Hey, Think creatively, man~</b>"
					$("#idCheckStatus").html(html).css("color","red");
				}
			}
		});
	});
});