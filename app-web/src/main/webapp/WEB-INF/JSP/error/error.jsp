<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<button onclick="history.back()">Back to Previous Page</button>

<c:if test="${not empty datetime}">
		<h1>Date: ${exception.datetime}</h1>
		</br>
</c:if>
<c:if test="${not empty exception}">
		<h1>Error code: ${exception} : System Errors</h1>
		</br>
</c:if>
<c:if test="${not empty url}">
		<h1>Request URI: ${exception.datetime} : System Errors</h1>
		</br>
</c:if>

</body>
</html>