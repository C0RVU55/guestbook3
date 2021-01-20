<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>guest book model 2</title>
</head>
<body>

	<form action="/guestbook3/guest/delete" method="get">
	
		비밀번호 <input type="text" name="password"> 
		<button type="submit">확인</button>
		<!--코드 no--> <input type="hidden" name="no" value="${param.no}">
	</form>
	
	<a href="/guestbook3/guest/list">메인으로 돌아가기</a>
	
</body>
</html>