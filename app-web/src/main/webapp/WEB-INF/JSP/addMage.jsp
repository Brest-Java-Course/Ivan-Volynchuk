   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ page isELIgnored="false"%>

    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <html>
        <body>
            <form action='<spring:url value="/mage/add" />' method="post">

                <span>Please enter the information of mage: </span><br/><br/>

                <input type="text" name="name" placeholder="Name" />
                </br>

                <input type="number" name="level" placeholder="Level" min="0" />
                </br>

                <input type="number" name="experience" placeholder="Experience" min="0" />
                </br>

                <input id="sub" class="sub" type="submit" value="Send" name="Submit" onClick="return Formdata(this.form)" /> <br/>
            </form>
        </body>
    </html>
    <script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
    <script src="<c:url value="/resources/js/tools/inputMageNullValidator.js" />"></script>
