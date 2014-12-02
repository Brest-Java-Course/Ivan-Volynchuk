    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page isELIgnored="false"%>
    <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <html>
        <body>
            <form action='<spring:url value="/mage/add" />' method="post">

                <span>
                    <spring:message code="mage.add.desc" />
                </span><br/><br/>

                <input type="text" name="name" placeholder="<spring:message code="mage.name" />" />
                </br>

                <input type="number" name="level" placeholder="<spring:message code="mage.lvl" />" min="0" />
                </br>

                <input type="number" name="experience" placeholder="<spring:message code="mage.exp" />" min="0" />
                </br>

                <input id="sub" class="sub" type="submit" value="<spring:message code="mage.add.link" />" name="Submit" onClick="return Formdata(this.form)" /> <br/>
            </form>
        </body>
    </html>
    <script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
    <script src="<c:url value="/resources/js/tools/inputMageNullValidator.js" />"></script>
