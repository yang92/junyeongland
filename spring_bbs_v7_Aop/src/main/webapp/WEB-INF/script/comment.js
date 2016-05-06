
	$.ajaxSetup({
		type : "POST",
		async : true,
		dataType : "json",
		error : function(xhr) {
			alert("error html=" + xhr.statusText);
		}
	});

	$(document).ready(function() {
		$("#commentWrite").on("click", function() {
			$.ajax({
				url : "/bbs/commentWrite.comment",
				data : {
					//data{}에서 EL은 ""로 감싸야 한다.
					articleNum : $("#articleNum").val(),
					commentContent : $("#commentContent").val()
				},
				beforeSend : function(){
					alert("시작전");
				},
				complete : function(){
					alert("완료후");
				},
				success : function(data) {
					if (data.result == 1) {
						alert("Write New comment complete");
						$("#commentContent").val("");
						showHtml(data.commentList,1);
					}
				}
			});
		});
	});
	//commentRead
	//id,content,date
	//each를 써서 계속 뿌림
	//
	function getComment(commPageNum, event) {
		$.ajax({
			url : "/bbs/commentRead.comment",

			data : {
				//id : $("#id").val(),
				articleNum : $("#articleNum").val(),
				//commentContent : $("#commentContent").val()
				//숫자와 문자 연산중에서는 +를 제외하고는 숫자가 먼저다
				commentRow : commPageNum * 10
				//글번호를 보내야함, 해당 글번호의 모든 comment를 다 읽어옴
				//서버측에서는 CommentDto에 하나의 comment를 저장... 최종적으로 ArrayList
				//JSONArray를 이용하여 JSON으로 변환함
			},
			success : function(data) {
				showHtml(data,commPageNum);
			}
		});
	};

	function showHtml(data,commPageNum) {
		var html = "<h1><font color='pink' align='center'>Comments</font></h1><table border='1' width='500'>";
		html += "<tr width='500' align='center'><td>No.</td><td>ID</td><td>content</td><td>date</td></tr>";
		
		$.each(data, function(index, item) {
			html += "<tr>";
			html += "<td>" + (index + 1) + "</td>";
			html += "<td>" + item.id + "</td>";
			html += "<td>" + item.commentContent + "</td>";
			html += "<td>" + item.commentDate + "</td>";
			html += "</tr>";
		});
		
		html += "</table>";
		commPageNum = parseInt(commPageNum);
		if ($("#commentCount").val() > commPageNum * 10) {
			nextPageNum = commPageNum + 1;
			html += "<br /><input type='button' onclick='getComment(nextPageNum,event)' value='Read Next Comments'><br/>";
		}
		$("#showComment").html(html);
		$("#commentContent").val("");
		$("#commentContent").focus();
	};