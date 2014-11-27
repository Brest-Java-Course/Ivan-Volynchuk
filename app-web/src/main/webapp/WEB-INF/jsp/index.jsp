<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Document</title>
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">

    </head>
    <body>
        <div class="wrap">
            <menu>
                <ul>
                    <li><a href="/">Mages</a></li>
                    <li><a href="/scroll/allScrolls">Scrolls</a></li>
                </ul>
            </menu>

            <h2>List of mages:</h2>
            <div class="cont">
                <div class="content">
                    <table>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Level</th>
                            <th>Experience</th>
                            <th>Scroll amount</th>
                            <th>Average manacost of scrolls</th>
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
                    <a href='<spring:url value="/mage/add" />'>Add mage</a>
                    </div>
                </div>
                <hr>
            </div>
        </div>
    </body>
</html>
<script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
<script src="<c:url value="/resources/js/chooser.js" />"></script>