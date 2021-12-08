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
</head>
<body>
	<jsp:include page="_header.jsp" />
	<jsp:include page="_menu.jsp" />

	<fmt:setLocale value="en_US" scope="session" />
	<div align="center">
		<h2>New Accounts</h2>
		<form123:form action="insert" method="post" modelAttribute="account">
			<table border="0" cellpadding="5">
				<tr>
					<th>User Name:</th>
					<td><input type="text" name="userName"></td>
					<!-- phai giong ten thuoc tinh ben entity : user-->
				</tr>
				<tr>
					<th>Pass Word:</th>
					<td><input type="text" name="password"></td>
				</tr>


				<!-- 
				<tr>
				
					<th>Roles(1 is for User, 2 is for Admin):</th>
					<td><input type="text" name="user_roles"></td>
				 	
					<th align="right">Role: </th>
					<c:forEach items="${user_roles }" var="roles">
					<td><form123:checkbox path="rolesvalue" value="${roles }"
					label="${role }" /></td>
					</c:forEach>
					</tr>
				-->

				<tr>
					<td colspan="2" align="center"><input type="submit"
						name="Save"></td>
				</tr>
			</table>
		</form123:form>
	</div>
		<jsp:include page="_footer.jsp" />
</body>
</html>