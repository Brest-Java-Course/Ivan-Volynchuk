<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
    <title> Users </title>
    <link href="<c:url value="/resources/css/index.css" />" rel="stylesheet">
    <body>

            <TABLE>
                    <TR>
                            <TH>Id</TH> <TH>Name</TH> <TH>Login</TH>
                    </TR>

                    <c:forEach items="${users}" var="user">
                         <TR>
                            <TD>
                                <c:out value="${user.userId}"/>
                            </TD>

                            <TD>
                                <c:out value="${user.userName}"/>
                            </TD>

                            <TD>
                                <c:out value="${user.login}"/>
                            </TD>

                            <TD class="add">
                                <a href='<spring:url value="/update" >  <spring:param name="id" value="${user.userId}" />   </spring:url>'>
                                +
                                </a>
                            </TD>

                            <TD class="del">
                                <a href='<spring:url value="/delete" >  <spring:param name="id" value="${user.userId}" />   </spring:url>'>
                                -
                                </a>
                            </TD>
                         </TR>
                    </c:forEach>
            </TABLE>
            <div class="wrapp">
            <a href='<spring:url value="/add" />'>Add user</a>
            <span>|</span>
            <a href='<spring:url value="/add" />'>Empty</a>
            </div>
    </body>

</html>
