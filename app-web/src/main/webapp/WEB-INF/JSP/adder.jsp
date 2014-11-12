    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/validator.js" />"></script>
    <html>
        <body>
            <form action='<spring:url value="/add" />' method="post">

                <span>Please enter the information of user: </span><br/><br/>
                <label path="name">
                    Name:
                </label>
                <input type="text" name="userName"  />
                </br>
                <label path="login">
                    Login:
                </label>
                <input type="text" name="login" />
                </br>

                 <input class="sub" type="submit" value="Send" name="Submit" onClick="return Formdata(this.form)" /> <br/>
            </form>
        </body>

    </html>
