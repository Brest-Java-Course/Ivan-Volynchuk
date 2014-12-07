   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ page isELIgnored="false"%>
   <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <html>
        <body>
            <form action='<spring:url value="/scroll/updateMageScroll" />' method="post">
                <jsp:include page="error.jsp" />
                <br/>
                <span>
                    <spring:message code="mage.update.desc" />
                </span><br/><br/>


                <input type="hidden" name="scrollId" value=<c:out value="${scroll.scroll_id}"/> readonly/>
                </br>

                <label path="description">
                    <spring:message code="scroll.desc" />:
                </label>
                <input type="text" name="description" value=<c:out value="${scroll.description}"/> />
                </br>

                <label path="durability">
                    <spring:message code="scroll.dur" />:
                </label>
                <input type="number" min="0" name="durability" value=<c:out value="${scroll.durability}"/> />
                </br>

                <label path="creationDate">
                    <spring:message code="scroll.date" />:
                </label>
                <input type="text" id="date" name="creationDate" value=<c:out value="${scroll.creation_date}"/> />
                </br>

                <label path="manaCost">
                    <spring:message code="scroll.mana" />:
                </label>
                <input type="number" min="0"  name="manaCost" value=<c:out value="${scroll.mana_cost}"/> />
                </br>

                <label path="mageId">
                    <spring:message code="scroll.mage" />:
                </label>

                <select name="mageId">

                    <c:forEach items="${mags}" var="mage">
                        <option value="${mage.mage_id}" ${mage.mage_id == scroll.mage_id ? 'selected="selected"' : ''}>
                            <c:out value="${mage.name}"/>
                        </option>
                    </c:forEach>

                </select>
                </br>

                <input id="sub" class="sub" type="submit" value="<spring:message code="mage.update.link" />" name="Submit"/> <br/>
            </form>
        </body>
    </html>
    <script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
    <script src="<c:url value="/resources/js/tools/dateValidator.js" />"></script>
    <script src="<c:url value="/resources/js/tools/inputScrollValidator.js" />"></script>

