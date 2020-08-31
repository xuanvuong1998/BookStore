<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <title>Create Book Page</title>
    </head>
    <body>
        <s:if test="#session.USER != null && #session.USER.isAdmin">
            <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <s:if test="#session.USER != null">
                        <li>
                            <a style="color: blue" >Welcome, <s:property value="#session.USER.fullname"/></a>
                        </li>

                    </s:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <s:if test="#session.USER != null">
                        <li>
                            <s:a href="logout" cssStyle="color: red">Logout</s:a>
                            </li>
                    </s:if>
                </ul>

                <ul class="nav navbar-nav">
                    <li>
                        <s:a href="searchBook">Home</s:a>
                        </li>
                    </ul>
                </div>

                <h1>Create New Discount Code</h1>
                <table border="1">

                <s:form method="POST" action="createDiscount">
                    <tbody>
                        <tr>
                            <td>Discount code: </td>
                            <td>
                                <s:textfield name="discountCode" label="Discount code" required="required"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Discount percent (max: 100)</td>
                            <td>
                                <s:textfield name="discountPercent" label="Discount percent (max: 100)" value="0" type="number" min="0" max="100" placeholder="%" cssStyle="text-align: right"/>
                            </td>
                        </tr>
                        <tr>
                            <td>For user: </td>
                            <td>
                                <s:select 
                                    label="User"
                                    list="#session.USERS" 
                                    name="username" 
                                    listKey="username"
                                    listValue="fullname"/>
                            </td>
                        </tr>
                        <tr>
                            <td><s:submit value="Create Discount"/></td>
                            <td><s:reset value="Reset"/></td>
                        </tr>
                    </tbody>
                </s:form>
            </table>
            <s:if test="message != null && !message.isEmpty()">
                <h3 style="color: greenyellow"><em>${message}</em></h3>
                    </s:if>
                </s:if>
    </body>
</html>
