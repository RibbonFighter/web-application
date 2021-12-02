<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index</title>
<style type="text/css">
<%@include file ="css/styles.css"%>
</style>
</head>
<body>
	<jsp:include page="_header.jsp" />
	<jsp:include page="_menu.jsp" />

	<div class="page_title">Shopping Cart</div>
	
	<div class="demo-container">
		<h3>Content</h3>
		
		<ul>
			<li>Buy Online</li>
			<li>Admin pages</li>
			<li>Reports</li>
		</ul>
	</div>
	
	
		<jsp:include page="_footer.jsp" />
</body>
</html>