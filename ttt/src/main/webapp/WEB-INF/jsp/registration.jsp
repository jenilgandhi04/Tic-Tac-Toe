<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
<form:form modelAttribute="user">
<table>
  <tr>
    <td>Username:</td>
    <td><form:input path="username" /> <span style="color: red;"><form:errors path="username" /></span></td>
  </tr>
  <tr>
    <td>Password:</td>
    <td><form:input path="password" /> <span style="color: red;"><form:errors path="password" /></span></td>
  </tr>
  <tr>
    <td>Email:</td>
    <td><form:input path="email" /></td>
  </tr>
  <tr>
    <td colspan="2">
    <form:hidden path="enabled" value="true"/>
    <form:hidden path="gamesAsPlayer1"/>
    <form:hidden path="gamesAsPlayer2"/>
    <form:hidden path="gamesWon"/>
    <input type="submit" name="registration" value="Registration" /></td>
  </tr>
</table>
</form:form>
</body>
</html>