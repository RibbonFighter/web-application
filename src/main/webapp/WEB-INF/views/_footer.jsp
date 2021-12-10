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
<title>Footer</title>

<style type="text/css">
<%@include file ="css/styles.css"%>
</style>

<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
</head>
<body>
	<section class="footer-container">
		<div class="social">
		<a href="#"><i class="fab fa-facebook-f"></i></a>
		<a href="#"><i class="fab fa-twitter"></i></a>
		</div>
		
		<ul class="list">
			<li>
				<a href="${contextPath }/">Home</a>
			</li>
			<li>
				<a href="#">Services</a>
			</li>
			<li>
				<a href="#">About</a>
			</li>
			<li>
				<a href="#">Terms</a>
			</li>
			<li>
				<a href="#">Privacy Policy</a>
			</li>
		</ul>
		<p class="copyright">
		@Copy right <a href="http://..." target="_blank">Ribbon</a>
		</p>
	</section>
</body>
</html>