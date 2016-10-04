<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Page</title>
<script type="text/javascript">
	function formSubmit()
	{
		document.getElementById("logoutForm").submit();
	}
</script>
</head>
<body>

	<h1>Title : Spring Security Remember Me Example - Update Form</h1>
	<br />
	<h1>Message : This page is for ROLE_ADMIN and fully authenticated only
            (Remember me cookie is not allowed!)</h1>
	<br />
	<h2>Update Account Information...</h2>
	<br />
	<br />
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form id="logoutForm" action="${logoutUrl}" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<h2>Click <a href="javascript:formSubmit()">Here</a> to Logout.</h2>

</body>
</html>