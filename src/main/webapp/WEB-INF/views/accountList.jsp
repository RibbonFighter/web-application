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


	<div class="page_title">User List</div>
	
	<form method="get" action="search">
			<input type="text" name="keyword" /> <input type="submit"
				value="Search" />
			<a href="${pageContext.request.contextPath}/account"><span>Add New Account</span></a>						
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
			<c:forEach items="${paginationAccountInfos.list}" var="accountInfo">
				<tr>
					<td>${accountInfo.userName }</td>
					<td style="visibility: hidden;">${accountInfo.password }</td>
					<td>${accountInfo.enabled }</td>
					<td>${accountInfo.userRole }</td>
					<td><a href="${contextPath}/account?userName=${accountInfo.userName }">Edit</a>
	&nbsp;&nbsp;&nbsp;  <a href="${contextPath}/deleteAccount?userName=${accountInfo.userName }">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<c:if test="${paginationAccountInfos.totalPages > 1}">
					<div class="pagination" style=" font-size: 20px;">

						<c:forEach items="${paginationAccountInfos.navigationPages}"
							var="page">
							<c:if test="${page != -1}">
								<a href="accountList?page=${page}" class="nav-item">${page}</a>

							</c:if>
							<c:if test="${page == -1}">
								<span class="nav-item"> ... </span>
							</c:if>
						</c:forEach>

					</div>
				</c:if>
	</div>
	<jsp:include page="_footer.jsp" />
</body>
</html>