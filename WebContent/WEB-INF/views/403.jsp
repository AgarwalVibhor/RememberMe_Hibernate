<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Page</title>
<script type="text/javascript">
	function formSubmit()
	{
		document.getElementById("logoutForm").submit();
	}
</script>
</head>
<body>
	
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form id="logoutForm" action="${logoutUrl}" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	
	<h1>${title}</h1>
	<br />
	<c:choose>
		<c:when test="${not empty username}">
			<h1>Hello ${username} , ${message}</h1>
		</c:when>
		<c:otherwise>
			<h1>${message}</h1>
		</c:otherwise>
	</c:choose>
	
	<br />
	<br />
	
	<h2>Click <a href='<c:url value="/hello" />'>Here</a> to go to the Hello Page.</h2>
	<br />
	<br />
	<h2>Click <a href="javascript:formSubmit()">Here</a> to logout.</h2>

</body>
</html>