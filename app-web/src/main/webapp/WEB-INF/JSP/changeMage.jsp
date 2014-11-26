   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ page isELIgnored="false"%>

    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/validator.js" />"></script>
    <html>
        <body>
            <form action='<spring:url value="/mage/update" />' method="post">

                <span>Please enter the information of mage to change: </span><br/><br/>

                <label path="id">
                    Mage id:
                </label>
                <input type="text" name="id" value=<c:out value="${mage.mage_id}"/> readonly/>
                </br>

                <label path="name">
                    Name:
                </label>
                <input type="text" name="name" value=<c:out value="${mage.name}"/> />
                </br>

                <label path="level">
                    Level:
                </label>
                <input type="text" name="level" value=<c:out value="${mage.level}"/> />
                </br>

                <label path="exp">
                    Experience:
                </label>
                <input type="text" name="exp" value=<c:out value="${mage.exp}"/> />
                </br>

                <input class="sub" type="submit" value="Send" name="Submit" onClick="return Formdata(this.form)" /> <br/>
            </form>
        </body>

    </html>
