<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="name" value="${pageContext.request.userPrincipal.name}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>

<style type="text/css">
<%@include file ="css/styles.css"%>
</style>


<%--  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" /> --%>
</head>
<body>
	<div class="header-container">
		<div class="site-name">Ribbon Online Shop</div>
		<div class="header-bar">
			<c:if test="${name != null }">
			Hello <a href="${contextPath }/accountInfo">${name }</a>
			&nbsp;|&nbsp;
			<a href="${contextPath }/logout">Logout</a>		
			</c:if>
			<c:if test="${name == null }">
				<a href="${contextPath }/login">Login</a>
			</c:if>
		</div>
	</div>
	
	
	
</body>
</html>