    <html>
        <title> inputForm </title>

        <body>
            <form action="${pageContext.request.contextPath}/add" method="post">


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

                 <input type="submit" name="Submit" /> <br/>
            </form>
        </body>

    </html>
