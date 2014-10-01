<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Saved Games</title>
</head>
<body>
	
	<div align="center"><h4>Saved Games</h4>

	<table border='1'>
		<tr>
			<th>Sr #</th>
			<th>Opponent</th>
			<th>Start Time</th>
			<th>Last Save Time</th>
			<th>Resume</th>
		</tr>
		<c:forEach var="i" begin="0" end="${fn:length(listSavedGames)-1}">
			<c:set var="game" scope="request" value="${listSavedGames.get(i)}" />
			<tr>
				<td>${i+1}</td>
				<td>${game.player2.playerName}</td>
				<td>${game.startTime}</td>
				<td>${game.lastSaveTime}</td>
				<td><a href='./resumeGame.html?gameId=${game.gameId}'>Resume</a></td>
			</tr>
		</c:forEach>

	</table>
	</div>
</body>
</html>