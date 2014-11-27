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
            <h2>List of scrolls:</h2>
            <div class="cont">
                <div class="filter">
                    <input type="text" id="date1" placeholder="From" />
                    <input type="text" id="date2" placeholder="To" />
                    <input type="button" id="filter" value="Filter"/>
                    Types of filtration:
                    <select name="1" id="1">
                        <option value="0">No filter</option>
                        <option value="1">After date</option>
                        <option value="2">Before date</option>
                        <option value="3">Between dates</option>
                    </select>
                </div>
                <div class="content">
                    <table>
                        <tr>
                            <th>Id</th>
                            <th>Description</th>
                            <th>Durability</th>
                            <th>Creation date</th>
                            <th>Mana cost</th>
                        </tr>

                        <c:forEach items="${scrolls}" var="scroll">
                            <tr>
                                <td><c:out value="${scroll.scroll_id}"/></td>
                                <td><c:out value="${scroll.description}"/></td>
                                <td><c:out value="${scroll.durability}"/></td>
                                <td><c:out value="${scroll.creation_date}"/></td>
                                <td><c:out value="${scroll.mana_cost}"/></td>
                                <td class="add"><a href='<spring:url value="/scroll/update">  <spring:param name="id" value="${scroll.scroll_id}"/>   </spring:url>'>&#9998</a></td>
                                <td class="del"><a href='<spring:url value="/scroll/delete2"> <spring:param name="scrollId" value="${scroll.scroll_id}"/>   </spring:url>'>x</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="wrapp">
                        <td class="add"><a href='<spring:url value="/scroll/add"></spring:url>'>Add scroll</a>
                    </div>
                </div>
                <hr>
            </div>
        </div>
    </body>
</html>
<script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
<script src="<c:url value="/resources/js/tools/dateValidator.js" />"></script>
<script src="<c:url value="/resources/js/allScrollsFilter.js" />"></script>