<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <title> Users </title>

    <body>
            <label path="userId">
                Id of User:
            </label>
            <label path="userName">
                Name:
            </label>
            <label path="login">
                Login:
            </label>

            <c:forEach items="${users}" var="user">
                <h2>
                    <c:out value="${user.userId}"/>
                </h2>
                <h2>
                    <c:out value="${user.userName}"/>
                </h2>
                <h2>
                    <c:out value="${user.login}"/>
                </h2></br>
            </c:forEach>

    </body>

</html>
