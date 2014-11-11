<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                                <a href="${pageContext.request.contextPath}/mvc/update?id=${user.userId}">+</a>
                            </TD>

                            <TD class="del">
                                <a href="${pageContext.request.contextPath}/mvc/delete?id=${user.userId}">-</a>
                            </TD>
                         </TR>
                    </c:forEach>
            </TABLE>
            <div class="wrapp">
            <a href="${pageContext.request.contextPath}/mvc/add">Add user</a>
            <a href="${pageContext.request.contextPath}/mvc/add">Empty</a>
            </div>
    </body>

</html>
