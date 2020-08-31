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
                    
            <h1>Add New Book</h1>
            <table border="1">
                <tbody>
                <s:form method="POST" action="createBook">
                    <tr>
                        <td>Name: </td>
                        <td>
                            <s:textfield name="name" label="Name" required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Image url: </td>
                        <td>
                            <s:textfield name="image" label="Image Url"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Author: </td>
                        <td>
                            <s:textfield name="author" label="Author" required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Description: </td>
                        <td>
                            <s:textarea name="description" label="Description" rows="4"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Price: </td>
                        <td>
                            <s:textfield name="price" label="Price" value="0" type="number" min="0" placeholder="vnd" cssStyle="text-align: right"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Quantity: </td>
                        <td>
                            <s:textfield name="quantity" label="Quantity" value="0" type="number" min="0" cssStyle="text-align: right"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Category: </td>
                        <td>
                            <s:select 
                                label="Category"
                                list="#session.CATEGORIES" 
                                name="categoryId" 
                                listKey="id"
                                listValue="name"/>
                        </td>
                    </tr>
                    <tr>
                        <td><s:submit value="Create Product"/></td>
                        <td><s:reset value="Reset"/></td>
                    </tr>
                </s:form>
            </tbody>
        </table>
        <s:if test="message != null && !message.isEmpty()">
            <h3 style="color: green"><em>${message}</em></h3>
                </s:if>
            </s:if>
    </body>
</html>
