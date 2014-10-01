<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hosted Games</title>
</head>
<body>
<p>Hosted Games :</p>
	<table border='1'>
		<tr>
			<th>Sr #</th>
			<th>Host</th>
			<th>Join Game</th>
		</tr>
		<c:set var="i" value="0" />
		<c:forEach var="host" items="${listHostedGames}">
			<tr>
				<td>${i+1}</td>
				<td>${host}</td>
				<td><a href="./joinHostedGame.html?host=${host}" >Join</a></td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>