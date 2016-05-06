<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>content.jsp</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script>
$.ajaxSetup({
	type : "POST",
	async : true,
	dataType : "json",
	error : function(xhr){
		alert("error html = " + xhr.statusText);
	}
});

$(document).ready(function(){
	$("#commentWrite").on("click",function(){		
		$.ajax({	
			url:"/bbs/commentWrite.comment",
// 			data{}에서는 EL을 ""로 감싸야함..그외에는 그냥 사용
			data:{				
				commentContent:$("#commentContent").val(),
				articleNum:"${article.articleNum}"
			},
// 			beforeSend : function(){
// 				alert("시작전");
// 			},
			complete: function(){
				alert("comment가 정성적으로 입력되었습니다");
			},
			success:function(data){							
				$("#commentContent").val("");
				showHtml(data,1);
				
			}					
		}); 
	});	
});

function getComment(commPageNum, event){
// 	event.preventDefault();
	$.ajax({			
		url:"/bbs/commentRead.comment",	
		data:{
			articleNum:"${article.articleNum}",
// 			숫자와 문자연산에서 +를 제외하고는 숫자 우선
			commentRow:commPageNum*10
		},
		success:function(data){
			showHtml(data,commPageNum,event);
		}				
	}); 	
}

function showHtml(data,commPageNum){	
	var html="<table border='1' width='500' align='center'>";
	//@ResponseBody 사용시
	$.each(data, function(index,item){
	//MappingJacksonJsonView 사용시
// 	$.each (data.comment, function(index,item){
		var formatted_date = new Date(item.commentDate);
		html +="<tr>";
		html +="<td>"+(index+1)+"</td>";
		html +="<td>"+item.id+"</td>";
		html +="<td>"+item.commentContent+"</td>";
		html += '<td>' + formatted_date.toLocaleString() + '</td>';				
		html +="<td>"+item.articleNum+"</td>";					
		html +="</tr>";					
	});		
	html +="</table>";
	commPageNum=parseInt(commPageNum);
	if("${article.commentCount}">commPageNum*10){			
		nextPageNum=commPageNum+1;				
		html +="<br /><input type='button' onclick='getComment(nextPageNum,event)' value='다음comment보기'><br>";
	}
	$("#showComment").html(html);	
	$("#commentContent").val("");
	$("#commentContent").focus();
}
</script>
</head>
<body>
	<form action="/bbs/replyForm.bbs" method="post">
		<input type="hidden" id="id" value="${article.id}"> 
		<input type="hidden" id="articleNum" value="${article.articleNum}">
		<input type="hidden" name="pageNum" value="${pageNum}"> 
		<input type="hidden" name="depth" value="${article.depth}"> 
		<input type="hidden" name="pos" value="${article.pos}"> 
		<input type="hidden" name="groupId" value="${article.groupId}">
		<input type="hidden" id=commentCount value="${article.commentCount }">
		<table border="1" width="500" align="center">
			<tr>
				<td>글쓴이</td>
				<td>${article.id}</td>
				<td>조회수</td>
				<td>${article.hit}</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>${article.title}</td>
				<td>날짜</td>
				<td>${article.writeDate}</td>
			</tr>
			<tr>
				<td colspan="2">다운로드</td>
				<td colspan="2">
				<c:if test="${article.fileStatus != 0}">
				  
					<c:if test="${fileList!=null}">
					 <ul> 
					  <c:forEach var="file" items="${fileList}" >
					  <li>
						<a href="/bbs/download.bbs?storedFname=${file.storedFname}&originFname=${file.originFname}">${file.originFname}</a>
					  </li>
					  </c:forEach>
					 </ul>
					</c:if>
				</c:if></td>
				
			</tr>
			<tr>
				<td colspan="4"><xmp>${article.content}</xmp></td>
			</tr>

			<tr>
				<c:if test="${id !=null}">
					<td colspan="4" align="right"><input type="submit"
						style="color: pink; background-color: black; font-size: 10pt"
						value="Reply"> <c:if test="${id ==article.id}">
							<input type="button"
								style="color: pink; background-color: black; font-size: 10pt"
								value="Edit"
								onclick="document.location.href='/bbs/update.bbs?articleNum=${article.articleNum}&pageNum=${pageNum}&fileStatus=${article.fileStatus}'">
							<input type="button"
								style="color: pink; background-color: black; font-size: 10pt"
								value="Delete"
								onclick="document.location.href='/bbs/delete.bbs?articleNum=${article.articleNum}&pageNum=${pageNum}'">
						</c:if> <c:if test="${id !=article.id}">
							<input type="button"
								style="color: pink; background-color: black; font-size: 10pt"
								value="Edit" disabled="disabled">
							<input type="button"
								style="color: pink; background-color: black; font-size: 10pt"
								value="Delete" disabled="disabled">
						</c:if> <input type="button"
						style="color: pink; background-color: black; font-size: 10pt"
						value="Back to the list"
						onclick="document.location.href='/bbs/list.bbs?pageNum=${pageNum}'">
					</td>
				</c:if>

				<c:if test="${id ==null}">
					<td colspan="4" align="right"><input type="submit"
						style="color: pink; background-color: black; font-size: 10pt"
						value="Reply" disabled="disabled"> <input type="button"
						style="color: pink; background-color: black; font-size: 10pt"
						value="Edit" disabled="disabled"> <input type="button"
						style="color: pink; background-color: black; font-size: 10pt"
						value="Delete" disabled="disabled"> <input type="button"
						style="color: pink; background-color: black; font-size: 10pt"
						value="Back to the list"
						onclick="document.location.href='/bbs/list.bbs?pageNum=${pageNum}'">
					</td>
				</c:if>
			</tr>
			<tr>
				<td colspan="4" align="center"><h1><font color="red">▼ ▼ ▼COMMENT HERE▼ ▼ ▼</font></h1></td>
			</tr>
			<tr>
				<td colspan="4"><textarea rows="5" cols="70"
						name="commentContent" id="commentContent"></textarea> <c:if
						test="${id ==null}">
						<input type="button"
							style="color: pink; background-color: black; font-size: 10pt"
							value="Write New Comment" disabled="disabled">
					</c:if> <c:if test="${id!=null}">
						<input type="button"
							style="color: pink; background-color: black; font-size: 10pt"
							value="Write New Comment" id="commentWrite">
					</c:if> <input type="button"
					style="color: pink; background-color: black; font-size: 10pt"
					value="Read Comment(${article.commentCount})" id="commentRead"
					onClick="getComment(1,event)">
				</td>
			</tr>
		</table>
	</form>

	<form>
		<div>
			<div id="showComment" align="center"></div>
			<input type="hidden" id="commPageNum" value="1">
		</div>
	</form>



</body>
</html>