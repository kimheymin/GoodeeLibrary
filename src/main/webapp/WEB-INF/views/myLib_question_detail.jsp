<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<style>
    #qdBackground{
        background-color: wheat;
             width: 900px;
            height: 500px;
            position: absolute;
            left: 28%;
            top: 35%;
    }
    table{
        position: absolute;
        left: 15%;
        top: 5%;
        background-color: white;
    }
   td{
     
       border: 1px solid gray;
       width: 300px;
       height: 100px;
       vertical-align: top;
   }
</style>
<body>
    <div id="qdBackground">
        <table>
           <tr>
               <td>제목

               </td>
               <td>유형

               </td>
           </tr>
           <tr>
            <td>작성자

            </td>
            <td>날짜

            </td>
        </tr>
        <tr> 
            <td colspan="2">문의내용

            </td>
        </tr>
        <tr>
            <td colspan="2">답변내용
                관리자가글쓰면 수정안됨
            </td>
        </tr>
        </table>

    </div>
</body>
</html>