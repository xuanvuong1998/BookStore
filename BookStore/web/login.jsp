<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Welcome to Book Store</h1>
        <h2>Login</h2>
        <form action="login" method="POST">
            <table border="0">
                <tbody>
                    <tr>
                        <td>Username</td>
                        <td>
                            <input type="text" name="username" value="" required/>
                        </td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td>
                            <input type="password" name="password" value="" required/>
                        </td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" value="Login" />
            <input type="reset" value="Reset" />
        </form>
        
        <s:if test="message != null || !message.isEmpty()">
            <h3 style="color: orangered"><em><s:property value="message"/></em></h3>
        </s:if>
    </body>
</html>
