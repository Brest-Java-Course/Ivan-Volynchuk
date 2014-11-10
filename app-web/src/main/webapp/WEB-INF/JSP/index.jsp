<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <title> Users </title>

    <body>

            <TABLE BORDER>
                    <TR>
                            <TH>userId</TH> <TH>userName</TH> <TH>login</TH>
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

                         </TR>
                    </c:forEach>
            </TABLE>
    </body>

</html>
