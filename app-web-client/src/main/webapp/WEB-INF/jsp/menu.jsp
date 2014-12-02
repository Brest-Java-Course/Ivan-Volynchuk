<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<menu>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/mage/">
                <spring:message code="menu.mage" />
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/scroll/allScrolls">
                <spring:message code="menu.scroll" />
            </a>
        </li>
    </ul>
</menu>