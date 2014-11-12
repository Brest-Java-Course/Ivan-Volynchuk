    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/validator.js" />"></script>

    <html>
        <title> inputForm </title>

        <body>

            <form action='<spring:url value="/update" />' method="post">

                <input type="hidden" name="userId" value=${user.userId} />
                <span>Please provide details of the required data change:</span><br/><br/>
                <label path="name">
                    Name:
                </label>
                <input type="text" name="userName" value=${user.userName} />
                </br>
                <label path="login">
                    Login:
                </label>
                <input type="text" name="login" value=${user.login} />
                </br>

                 <input class="sub" type="submit" name="Submit" value="Send" onClick="return Formdata(this.form)"/> <br/>
            </form>
        </body>

    </html>
