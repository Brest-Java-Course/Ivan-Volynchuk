<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    </head>
    <body>
        <div class="wrap">

            <jsp:include page="menu.jsp" />

            <h2><spring:message code="mage.header" /></h2>
            <div class="cont">
                <form action='<spring:url value="/mage/search/name" />' method="get">
                    <div class="filter">
                        <input type="text" name="name" id="name" placeholder="<spring:message code="mage.search.placeHolder" />" />
                        <input type="submit" value="<spring:message code="mage.search.button" />" />
                    </div>
                <form>

                <div class="content">
                    <table>
                        <tr>
                            <th><spring:message code="mage.id" /></th>
                            <th><spring:message code="mage.name" /></th>
                            <th><spring:message code="mage.lvl" /></th>
                            <th><spring:message code="mage.exp" /></th>
                            <th><spring:message code="mage.amt" /></th>
                            <th><spring:message code="mage.avg" /></th>
                        </tr>

                        <c:forEach items="${mages}" var="mage">
                            <tr>
                                <td><c:out value="${mage.mage_id}"/></td>
                                <td><c:out value="${mage.name}"/></td>
                                <td><c:out value="${mage.level}"/></td>
                                <td><c:out value="${mage.exp}"/></td>
                                <td><c:out value="${mage.scroll_amount}"/></td>
                                <td><c:out value="${mage.average_manacost}"/></td>
                                <td class="add"><a href='<spring:url value="/mage/showMageScrolls" >  <spring:param name="id" value="${mage.mage_id}"/>   </spring:url>'>&#8595</a></td>
                                <td class="del"><a href='<spring:url value="/mage/delete" >  <spring:param name="id" value="${mage.mage_id}"/>   </spring:url>'>x</a></td>
                                <td class="upd"><a href='<spring:url value="/mage/update" >  <spring:param name="id" value="${mage.mage_id}"/>   </spring:url>'>&#9998</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="wrapp">
                        <a href='<spring:url value="/mage/add" />'>
                            <spring:message code="mage.add.link" />
                        </a>
                    </div>
                </div>
                <hr>
            </div>
        </div>
    </body>
</html>
<script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
<script src="<c:url value="/resources/js/chooser.js" />"></script>