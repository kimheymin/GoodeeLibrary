<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>J-Query</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="resources/js/jquery.twbsPagination.js" type="text/javascript"></script>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse: collapse;
		padding: 5px 5px;
	}

</style>
</head>
<body>
	<div>
		<table>
			<tr>
				<th>신청번호</th>
				<th>서명</th>
				<th>출판사</th>
				<th>신청회원ID</th>
				<th>승인여부</th>
			</tr>
			<tbody id="list">
				<!-- 불러온 데이터 뿌리는 영역 -->
			</tbody>
			<tr>
				<td id="paging" colspan="6">
					<!-- 플러그인 사용 -->
					<div class="container">
						<nav aria-label="page navigation" style="text: align: center">
							<ul class="pagination" id="pagination"></ul>
						</nav>
					</div> <!-- // 플러그인 사용 -->
				</td>
			</tr>
		</table>
	</div>
</body>
<script>
	var showPage = 1;

	// 몇개를 보여줄 것인지/몇페이지
	listCall(showPage); // 시작하자마자 이 함수를 호출

	function listCall(reqPage) {
		var reqUrl = './hopeBookManage/' + reqPage;
		$.ajax({
			url : reqUrl,
			type : 'GET',
			data : {},
			dataType : 'JSON',
			success : function(data) {
				console.log(data);
				showPage = data.currPage;
				listPrint(data.list);
				$("#pagination").twbsPagination({
					startPage : data.currPage, // 시작 페이지
					totalPages : data.range, // 생성 가능한 최대 페이지
					visiblePages : 10, // 5개씩 보여 주겠다.(1~5)
					onPageClick : function(evt, page) { // 각 페이지를 눌렀을 경우
						listCall(page);
					}
				});
			},
			error : function(error) {
				console.log(error);
			}
		});
	}

	function listPrint(list) {
		var content = "";

		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				content += "<tr>";
				content += "<td>"+list[i].hopeBooksNumber+"</td>";
				content += "<td><a href='hopeBookDetail?hopeBooksNumber="+list[i].hopeBooksNumber+"'>"+list[i].hb_bookName+"</a></td>";
				content += "<td>"+list[i].publisher+"</td>";
				content += "<td>"+list[i].id+"</td>";
				if(list[i].hb_state == 'H001') {					
					content += "<td>신청중</td>";
				} else if(list[i].hb_state == 'H002') {					
					content += "<td>승인</td>";
				} else {					
					content += "<td>거절</td>";
				}
				content += "</tr>";
			}
		} else {
			content = "<h2>해당 목록이 없습니다.</h2>";
		}
		

		$("#list").empty();
		$("#list").append(content);
	}
</script>
</html>