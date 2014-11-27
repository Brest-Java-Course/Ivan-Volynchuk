   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ page isELIgnored="false"%>

    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <html>
        <body>
            <form action='<spring:url value="/scroll/update" />' method="post">

                <span>Please enter the information of scroll to change: </span><br/><br/>


                <input type="hidden" name="scrollId" value=<c:out value="${scroll.scroll_id}"/> readonly/>
                </br>

                <label path="description">
                    Description:
                </label>
                <input type="text" name="description" value=<c:out value="${scroll.description}"/> />
                </br>

                <label path="durability">
                    Durability:
                </label>
                <input type="number" min="0" name="durability" value=<c:out value="${scroll.durability}"/> />
                </br>

                <label path="creationDate">
                    Creation date:
                </label>
                <input id="date" type="text" name="creationDate" value=<c:out value="${scroll.creation_date}"/> />
                </br>

                <label path="manaCost">
                    Mana Cost:
                </label>
                <input type="number"  min="0" name="manaCost" value=<c:out value="${scroll.mana_cost}"/> />
                </br>

                <label path="mageId">
                    Mage owner:
                </label>

                <select name="mageId">
                    <option value="">No mage</option>

                    <c:forEach items="${mags}" var="mage">
                        <option value="${mage.mage_id}" ${mage.mage_id == scroll.mage_id ? 'selected="selected"' : ''}>
                            <c:out value="${mage.name}"/>
                        </option>
                    </c:forEach>
                </select>
                </br>

                <input id="sub" class="sub" type="submit" value="Send" name="Submit" onClick="return Formdata(this.form)" /> <br/>
            </form>
        </body>
    </html>
    <script src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
    <script src="<c:url value="/resources/js/tools/dateValidator.js" />"></script>
    <script src="<c:url value="/resources/js/tools/inputScrollValidator.js" />"></script>

