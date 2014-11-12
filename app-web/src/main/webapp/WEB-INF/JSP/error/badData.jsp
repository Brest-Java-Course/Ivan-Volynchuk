<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Tell the JSP Page that please do not ignore Expression Language -->
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <title>Incorrect input data</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<button onclick="history.back()">Back to Previous Page</button>

<c:if test="${not empty exception.message}">
		<h1>Description: ${exception.message}</h1>
		</br>
</c:if>

<c:if test="${not empty exception.place}">
		<h1>Place of origin: ${exception.place}</h1>
		</br>
</c:if>

</body>
</html>