<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기 완료</title>
<script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
    <style>
      .main {
        width: 400px;
        height: 300px;
        margin: 200px auto;
      }
      h3 {
        text-align: center;
        color: #0070c0;
      }

      img{
        width: 160px;
        margin: 24px;
      }

      div{
        text-align: center;
      }

      .what{
        margin: 6px;
        font-size: 20px;
        color: gray;
      }

      #find_btn{
        margin-top: 20px;
      }
      button {
        width: 30%;
        height: 40px;
        margin: 20px 5px;
        font-size: 15px;
        background-color: #c5d8f1;
        color: #0070c0;
        border: none;
        border-radius: 5px;
      }
    </style>
  </head>
  <body>
    <div class="main">
      <h3>아이디 찾기완료</h3>
      <hr />
      <div id="find_img">
        <img src="./image/lock.png">
      </div>
      <div id="find_content">
        <span class="what"> <b style="color:black">${name}</b> 님의 아이디는</span>
        <br/>
        <span class="what"> <b style="color:#0070c0">${findId}</b> 입니다.</span>
      </div>
        <div id="find_btn">
          <button class="find_button" onclick="location.href='memLogin'">로그인</button>
          <button class="find_button" onclick="location.href='memfindPw'">비밀번호 찾기</button>
        </div>
    </div>
</body>
</html>