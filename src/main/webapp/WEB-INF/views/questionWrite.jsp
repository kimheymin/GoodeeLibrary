<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	
</head>
<style>
	#editable{
		overflow: scroll;
		width: 500px;
		height: 240px;
	}
    #qwBackground{
        background-color: wheat;
             width: 900px;
            height: 500px;
            position: absolute;
            left: 28%;
            top: 35%;
    }
    .gw1{
        position: absolute;
        left:27%;
        top:5%;
    }
    .gw2{
        position: absolute;
        left:70%;
        top:5%;
    }
    .gw3{
        position: absolute;
        left:27%;
        top:10%;
    }
    .gw4{
        position: absolute;
        left:27%;
        top:20%;
        width:400px;
        height: 220px;
        resize: none;
    }
    .gw5{
        position: absolute;
        left:27%;
        top:70%;
    }
    .gw6{
        position: absolute;
        left:27%;
        top:80%;
        background-color: white;
        width: 80px;
    }
    .gw7{
        position: absolute;
        left:47%;
        top:80%;
    }
</style>
<body>

    <div id="qwBackground">
        <form action="questionWriting">
            <input type="text" value="${loginId}" readonly class="gw1" name="loginId">
                <div class="gw2" style="background-color: white;">
                    문의유형
                    <select name="type" id="selbox">
                        <option value="Q001">열람실</option>
                        <option value="Q002">도서</option>
                        <option value="Q003">서비스</option>
                        <option value="Q004">기타</option>
                    </select>
                </div>
            <input type="text" placeholder="제목을 작성해주세요"  class="gw3" name="subject">
            <div id="editable" contenteditable="true" class="gw4" style="background-color: white; border: 1px solid black " ></div>
			<input id="content" type="hidden" name="content" value=""/>
            <input type="button" value="사진업로드"  class="gw5" onclick="fileUp()"/>
            <label class="gw6"><input type="radio" name="false" value="FALSE" id="tfC">비공개
            								<input type="radio" name="false" value="TRUE" checked id="tfCK"> 공개
            </label>
            <input id="save" type="button" value="작성완료"  class="gw7"/>
        </form>
    </div>
</body>
<script>
/* $(document).keydown(function(e){   
    if(e.target.nodeName == "IMG" && e.target.nodeName == "B"){       
        if(e.keyCode === 8){   
        return false;
        }
    }
}); */

$("#save").click(function(){
	//editable 에 있는 내용을 content의 value 넣기
	$("#editable a").find("b").remove();
	$("#editable a").removeAttr('onclick');
	$('#content').val($('#editable').html());
	$('form').submit();
});

function fileUp(){
	window.open('uploadForm','file_upload','width=400,height=100,top=280');
}

function del(elem){
	console.log(elem);
	var newFileName = elem.id.substring(elem.id.lastIndexOf("/")+1);
	console.log(newFileName);
	
	//1.실제 파일 삭제 요청	
	$.ajax({
		url:'fileDelete',
		type:'get',
		data:{"fileName":newFileName},
		dataType:'json',
		success:function(d){
			console.log(d);
			if(d.success == 1){
				//2. 파일 삭제 요청이 완료 되면 화면에 나타난 사진 삭제				
				$(elem).remove();//이미지와 X버튼 삭제
			}
		},
		error:function(e){
			console.log(e);
		}
	});
	
}
</script>
</html>