   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ page isELIgnored="false"%>

    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">

    <html>
        <body>
            <form action='<spring:url value="/scroll/addToMage" />' method="post">

                <span>Adding scroll to mage: </span><br/><br/><br/><br/>
                <span>Please enter the information of scroll: </span><br/>

                <input type="hidden" name="id"  value="${mageId}"/>
                </br>

                <input type="text" name="description" placeholder="Description" />
                </br>

                <input type="number" name="durability" min="0" placeholder="Durability"/>
                </br>

                <input type="text" name="creationDate" placeholder="Creation date" />
                </br>

                <input type="number" name="manaCost" min="0" placeholder="Mana cost"/>
                </br>

                 <input class="sub" type="submit" value="Send" name="Submit" onClick="return Formdata(this.form)" /> <br/>
            </form>
        </body>
    </html>

    <script src="<c:url value="/resources/js/tools/dateValidator.js" />"></script>
    <script src="<c:url value="/resources/js/tools/addScrollValidator.js" />"></script>
