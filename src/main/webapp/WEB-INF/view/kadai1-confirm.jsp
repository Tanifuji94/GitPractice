<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.kadai1dto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員登録画面</title>
</head>
<body>
	<p>下記の内容で登録します。よろしいですか？</p>
	<%
		kadai1dto kadai1 = (kadai1dto)session.getAttribute("input_data");
	%>
	名前：<%=kadai1.getName() %><br>
	年齢：<%=kadai1.getAge() %><br>
	性別：<%=kadai1.getGender() %><br>
	電話番号：<%=kadai1.getPhone() %><br>
	メールアドレス：<%=kadai1.getMail() %><br>
	パスワード：*******<br>
	<a href="kadai1ExecuteServlet">OK</a><br>
	<a href="kadai1FormServlet">戻る</a>
</body>
</html>