    <html>
        <title> inputForm </title>

        <body>
            <form action="${pageContext.request.contextPath}/update" method="post">


                <label path="Id">
                    Id:
                </label>
                <input type="text" name="userId" readonly="readonly" value=${user.userId} />
                </br>
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

                 <input type="submit" name="Submit" /> <br/>
            </form>
        </body>

    </html>
