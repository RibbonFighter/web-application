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



<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
</head>
<body>
	<nav>
	<input type="checkbox" id="check">
	<label for="check" class="checkbtn">
		<i class="fas fa=bars"></i>
	</label>
	<ul>
		<li><a class="home" href="${contextPath }/">Home</a> | </li>
		<li><a href="${contextPath }/productList">Product List</a> | </li>
		
		<security:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
			<li><a href="${contextPath }/orderList">Order List</a></li> |
 | 
		</security:authorize>
		
		<li><a href="${contextPath }/shoppingCart">My Cart</a></li> |
		<security:authorize access="hasRole('ROLE_MANAGER')"> 
			<li><a href="${contextPath }/product">Create Product</a></li> |
			<li><a href="${contextPath}/accountList">User List</a> </li>
		</security:authorize>
		</ul>
	</nav>
</body>
</html>