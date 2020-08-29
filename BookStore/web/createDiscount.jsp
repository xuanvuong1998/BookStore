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

        <h1>Create Discount</h1>

        <s:form method="POST" action="createDiscount">
            <s:textfield name="discountCode" label="Discount code" required="required"/>
            <s:textfield name="discountPercent" label="Discount percent (max: 100)" value="0" type="number" min="0" max="100" placeholder="%" cssStyle="text-align: right"/>
            <s:select 
                label="User"
                list="#session.USERS" 
                name="username" 
                listKey="username"
                listValue="fullname"/>
            <s:submit value="Create Discount"/>
            <s:reset value="Reset"/>
        </s:form>

        <s:if test="message != null && !message.isEmpty()">
            <h3 style="color: greenyellow"><em>${message}</em></h3>
                </s:if>

        <s:a href="''">Home</s:a>
    </body>
</html>
