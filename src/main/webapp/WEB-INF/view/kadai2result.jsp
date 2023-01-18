<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.kadai1dto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一覧表示</title>
</head>
<body>

	<table border="1">
		<tr>
			<th>氏名</th>
			<th>年齢</th>
			<th>性別</th>
			<th>電話番号</th>
			<th>メールアドレス</th>
		</tr>		
	<%
	List<kadai1dto> list = (ArrayList<kadai1dto>)request.getAttribute("kadai2");
	for(kadai1dto s : list) {
	%>
		<tr>
			<td><%=s.getName() %>
			<td><%=s.getAge() %></td>
			<td><%=s.getGender() %></td>
			<td><%=s.getPhone() %></td>
			<td><%=s.getMail() %></td>
		</tr>
	<%} %>
	
	</table>
	<a href="./">戻る</a>
</body>
</html>