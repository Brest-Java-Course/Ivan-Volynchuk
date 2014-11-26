   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ page isELIgnored="false"%>

    <link href="<c:url value="/resources/css/input.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/validator.js" />"></script>
    <html>
        <body>
            <form action='<spring:url value="/scroll/update" />' method="post">

                <span>Please enter the information of scroll to change: </span><br/><br/>

                <label path="scrollId">
                    Scroll id:
                </label>
                <input type="text" name="scrollId" value=<c:out value="${scroll.scroll_id}"/> readonly/>
                </br>

                <label path="description">
                    Description:
                </label>
                <input type="text" name="description" value=<c:out value="${scroll.description}"/> />
                </br>

                <label path="durability">
                    Durability:
                </label>
                <input type="text" name="durability" value=<c:out value="${scroll.durability}"/> />
                </br>

                <label path="creationDate">
                    Creation date:
                </label>
                <input type="text" name="creationDate" value=<c:out value="${scroll.creation_date}"/> />
                </br>

                <label path="manaCost">
                    Mana Cost:
                </label>
                <input type="text" name="manaCost" value=<c:out value="${scroll.mana_cost}"/> />
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

                <input class="sub" type="submit" value="Send" name="Submit" onClick="return Formdata(this.form)" /> <br/>
            </form>
        </body>
    </html>
