   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ page isELIgnored="false"%>

    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <html>
        <body>
            <form action='<spring:url value="/mage/update" />' method="post">

                <span>Please enter the information of mage to change: </span><br/><br/>

                <input type="hidden" name="id" value=<c:out value="${mage.mage_id}"/> readonly/>
                </br>

                <label path="name">
                    Name:
                </label>
                <input class="name" type="text" name="name" value=<c:out value="${mage.name}"/> />
                </br>

                <label path="level">
                    Level:
                </label>
                <input type="number" name="level" value=<c:out value="${mage.level}"/> min="0" />
                </br>

                <label path="exp">
                    Experience:
                </label>
                <input  type="number" name="exp" value=<c:out value="${mage.exp}"/>  min="0" />
                </br>
                <input id="sub" class="sub" type="submit" value="Send" name="Submit" /> <br/>
            </form>
        </body>
    </html>
    <script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
    <script src="<c:url value="/resources/js/tools/inputMageNullValidator.js" />"></script>
