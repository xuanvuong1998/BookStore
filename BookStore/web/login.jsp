<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <title>Login Page</title>
    </head>
    <body>
        <div class="container-fluid">
            <ul class="nav navbar-nav">
                <li>
                    <s:a href="searchBook">Back to home</s:a>
                    </li>
                </ul>
            </div>
            <h2>Please login to continue!</h2>

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
                        <tr>
                            <td>
                                <input type="submit" value="Login" />
                            </td>
                            <td>
                                <input type="reset" value="Reset" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>

        <s:if test="message != null || !message.isEmpty()">
            <h3 style="color: red"><em><s:property value="message"/></em></h3>
                </s:if>
    </body>
</html>
