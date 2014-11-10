<html>
    <title> inputForm </title>

    <body>
        <form action="${pageContext.request.contextPath}/mvc/submitData" method="post">

            <label path="name">
                Name:
            </label>
            <input type="text" name="userName"/>

             <label path="login">
                 Login:
             </label>
             <input type="text" name="login"/>


             <input type="submit" name="Submit" /> <br/>
        </form>
    </body>

</html>
