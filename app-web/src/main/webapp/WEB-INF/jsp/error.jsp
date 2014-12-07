<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<div id="error_div" class="error_div">

    <c:if test="${!empty message}">
        <c:out value="${message}"/>
    </c:if>

</div>