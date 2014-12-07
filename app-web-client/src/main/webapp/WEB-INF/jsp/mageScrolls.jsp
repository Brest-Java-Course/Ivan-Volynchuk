<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    </head>
    <body>
        <div class="wrap">
            <jsp:include page="menu.jsp" />
            <jsp:include page="error.jsp" />
            <br/>
            <h2>
                <spring:message code="scroll.header" />
                <c:out value="${mage.name}"/>
            </h2>
            <div class="cont">
                <div class="filter">
                    <input type="text" id="date1" placeholder="<spring:message code="scroll.filter.from" />" />
                    <input type="text" id="date2" placeholder="<spring:message code="scroll.filter.to" />" />
                    <input type="button" id="filter" value="<spring:message code="scroll.filter.button" />"/>

                    <spring:message code="scroll.filter.header" />

                    <select name="1" id="1">
                        <option value="0">
                            <spring:message code="scroll.filter.type.none" />
                        </option>
                        <option value="1">
                            <spring:message code="scroll.filter.type.after" />
                        </option>
                        <option value="2">
                            <spring:message code="scroll.filter.type.before" />
                        </option>
                        <option value="3">
                            <spring:message code="scroll.filter.type.between" />
                        </option>
                    </select>
                </div>
                <div class="content">
                     <table id="table" class="table">
                        <tr>
                            <th><spring:message code="scroll.id" /></th>
                            <th><spring:message code="scroll.desc" /></th>
                            <th><spring:message code="scroll.dur" /></th>
                            <th><spring:message code="scroll.date" /></th>
                            <th><spring:message code="scroll.mana" /></th>
                        </tr>

                        <c:forEach items="${mage.magicScrollList}" var="scroll">
                            <tr id="scroll<c:out value="${scroll.scroll_id}"/>" >
                                <td><c:out value="${scroll.scroll_id}"/></td>
                                <td><c:out value="${scroll.description}"/></td>
                                <td><c:out value="${scroll.durability}"/></td>
                                <td><c:out value="${scroll.creation_date}"/></td>
                                <td><c:out value="${scroll.mana_cost}"/></td>
                                <td class="add"><a href='<spring:url value="/scroll/updateMageScroll" >  <spring:param name="id" value="${scroll.scroll_id}"/>  <spring:param name="mageId" value="${mage.mage_id}"/>  </spring:url>'>&#9998</a></td>
                                <td class="del"><a href='<spring:url value="/scroll/deleteMageScroll" >  <spring:param name="mageId" value="${mage.mage_id}"/> <spring:param name="scrollId" value="${scroll.scroll_id}"/>   </spring:url>'>x</a></td>
                            </tr>
                        </c:forEach>
                     </table>
                    <div class="wrapp">
                        <td class="add">
                        <a href='<spring:url value="/scroll/addToMage" >  <spring:param name="id" value="${mage.mage_id}"/>   </spring:url>'>
                            <spring:message code="scroll.add.link" />
                        </a>
                    </div>
                </div>
                <hr>
            </div>
        </div>
    </body>
</html>
<script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
<script src="<c:url value="/resources/js/mageScrollFilter.js" />"></script>
<script src="<c:url value="/resources/js/tools/dateValidator.js" />"></script>