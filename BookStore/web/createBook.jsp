<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Book Page</title>
    </head>
    <body>
        <s:if test="#session.USER != null">
            <h3 style="color: orangered">Welcome, <s:property value="#session.USER.fullname"/>!</h3>
            <s:a href="logout">Logout</s:a>
        </s:if>
        <s:else>
            <s:a href="login.jsp" >Login</s:a>
        </s:else>

        <h1>Create Book</h1>

        <s:form method="POST" action="createBook">
            <s:textfield name="name" label="Name" required="required"/>
            <s:textfield name="image" label="Image Url"/>
            <s:textfield name="author" label="Author" required="required"/>
            <s:textarea name="description" label="Description" rows="4"/>
            <s:textfield name="price" label="Price" value="0" type="number" min="0" placeholder="vnd" cssStyle="text-align: right"/>
            <s:textfield name="quantity" label="Quantity" value="0" type="number" min="0" cssStyle="text-align: right"/>
            <s:select 
                label="Category"
                list="#session.CATEGORIES" 
                name="categoryId" 
                listKey="id"
                listValue="name"/>
            <s:submit value="Create Product"/>
            <s:reset value="Reset"/>
        </s:form>

        <s:if test="message != null && !message.isEmpty()">
            <h3 style="color: greenyellow"><em>${message}</em></h3>
                </s:if>

        <s:a href="''">Home</s:a>
    </body>
</html>
