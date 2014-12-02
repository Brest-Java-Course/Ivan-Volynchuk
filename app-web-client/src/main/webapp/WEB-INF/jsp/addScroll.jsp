    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page isELIgnored="false"%>
    <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">

    <html>
        <body>
            <form action='<spring:url value="/scroll/add" />' method="post">

                <span>
                    <spring:message code="scroll.add.desc" />
                </span><br/><br/>

                <input type="text" name="description" placeholder="<spring:message code="scroll.desc" />" />
                </br>

                <input type="number" name="durability" min="0" placeholder="<spring:message code="scroll.dur" />"/>
                </br>

                <input id="date" type="text" name="creationDate" placeholder="<spring:message code="scroll.date" />" />
                </br>

                <input type="number" name="manaCost" min="0" placeholder="<spring:message code="scroll.mana" />"/>
                </br>

                 <input id="sub" class="sub" type="submit" value="<spring:message code="scroll.add.link" />" name="Submit" /> <br/>
            </form>
        </body>
    </html>
    <script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
    <script src="<c:url value="/resources/js/tools/dateValidator.js" />"></script>
    <script src="<c:url value="/resources/js/tools/inputScrollValidator.js" />"></script>

