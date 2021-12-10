<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="form123"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add New User</title>
<style type="text/css">
<%@include file ="css/styles.css"%>
</style>
</head>
<body>
	<jsp:include page="_header.jsp" />
	<jsp:include page="_menu.jsp" />

	<fmt:setLocale value="en_US" scope="session" />
	<div align="center">
		<h2>New Accounts</h2>
		<form123:form action="${contextPath}/account" method="post" modelAttribute="accountForm">
			<table border="0" cellpadding="5">
			
				<tr>
					<form123:errors style="color: red;" path="userName"
					class="error-message"></form123:errors>
					<th>User Name:</th>
				<c:if test="${not empty accountForm.userName}">
					<form123:hidden path="userName" placeholder="Enter your username" /> ${accountForm.userName}			
				</c:if>
				
				<c:if test="${empty accountForm.userName}">
						<form123:input type="text" id="userName" path="userName"
				placeholder="Enter your username" />
				</c:if>		
				</tr>
				
				<tr>
					<form123:errors style="color: red;" path="password" class="error-message"></form123:errors>
					<th>Pass Word:</th>
					<form123:input type="password" id="password" path="password"
					placeholder="Enter your password" />
				</tr>
				
				<tr>
					<form123:errors style="color: red;" path="userRole"
					class="error-message"></form123:errors>
					<th>User Role:</th>
			<form123:select path="userRole" id="role">
				<form123:option value="MANAGER" label="MANAGER" />
				<form123:option value="EMPLOYEE" label="EMPLOYEE" />
			</form123:select>
			<form123:errors path="userRole" class="error-message"></form123:errors>
			<form123:errors style="color: red;" path="enabled"
				class="error-message"></form123:errors>
				</tr>
				<tr>
				<th>Enable: </th>
			<form123:select path="enabled" id="enabled">
				<form123:option value="1" label="Yes" />
				<form123:option value="0" label="No" />
			</form123:select>
			<form123:errors path="active" class="error-message"></form123:errors>
				</tr>

				<tr>
					<td colspan="2" align="center">
					<input type="submit" name="Save">
					<input type="reset" value="Reset" /> 
					</td>
				</tr>
			</table>
		</form123:form>
	</div>
		<jsp:include page="_footer.jsp" />
</body>
</html>