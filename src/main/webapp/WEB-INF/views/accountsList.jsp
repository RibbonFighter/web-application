<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users Management</title>
</head>
<body>
	<jsp:include page="_header.jsp" />
	<jsp:include page="_menu.jsp" />

	<fmt:setLocale value="en_US" scope="session" />

	<div class="page_title">User List</div>
	
	<form method="get" action="search">
			<input type="text" name="keyword" /> <input type="submit"
				value="Search" />
	</form>
	<div align="center">
		<table border="1" padding="5">
			<tr>
				<th>Name</th>
				<th>Pass</th>
				<th>Enabled</th>
				<th>Roles</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${listAccounts }" var="account">
				<tr>
					<td>${account.userName }</td>
					<td style="visibility: hidden;">${account.password }</td>
					<td>${account.enabled }</td>
					<td>${account.userRole }</td>
					<td><a href="edit?userName=${account.userName }">Edit</a>
						&nbsp;&nbsp;&nbsp; <a href="delete?userName=${account.userName }">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<jsp:include page="_footer.jsp" />
</body>
</html>