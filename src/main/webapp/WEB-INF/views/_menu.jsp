<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu</title>
<style type="text/css">
<%@include file ="css/styles.css"%>
</style>

</head>
<body>
	<div class="menu-container">
		<a href="${contextPath }/">Home</a> |
		<a href="${contextPath }/productList">Product List</a> |
		<a href="${contextPath }/shoppingCart">My Cart</a> |
		<security:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
			<a href="${contextPath }/orderList">Order List</a> |
			<a href="${contextPath }/userList">User List</a> |
		</security:authorize>
		
		<security:authorize access="hasRole('ROLE_MANAGER')">
			<a href="${contextPath }/product">Create Product</a> |
			<a href="${contextPath }/user">Create User</a>
		</security:authorize>
	</div>
</body>
</html>