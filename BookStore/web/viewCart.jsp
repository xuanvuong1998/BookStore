<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
        <style>
            th, td {
                padding: 0 5px;
            }
        </style>
    </head>
    <body>
        <s:if test="#session.USER != null">
            <h3 style="color: orangered">Welcome, <s:property value="#session.USER.fullname"/>!</h3>
            <s:a href="logout">Logout</s:a>
        </s:if>
        <s:else>
            <s:a href="login.jsp" >Login</s:a>
        </s:else>

        <s:a href="searchBook">
            <h4>Home</h4>
        </s:a>

        <h1>View Cart</h1>

        <s:if test="#session.CART == null || #session.CART.isEmpty()">
            <h3>Your cart is empty.</h3>
        </s:if>
        <s:else>
            <s:set var="details" value="#session.CART.orderDetailsCollection"/>
            <s:if test="#details == null || #details.isEmpty()">
                <h3>Your cart is empty.</h3>
            </s:if>  
            <s:else>
                <s:set var="totalPrice" value="%{0}"/>
                <p><em>There are <strong>${details.size()} books</strong> in your cart.</em></p>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Book name</th>
                            <th>Author</th>
                            <th>Image</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="#details" status="counter">
                            <s:form action="updateCart" method="POST" theme="simple">
                                <tr>
                                    <td>
                                        ${counter.count}
                                    </td>
                                    <td>
                                        ${bookId.name}
                                    </td>
                                    <td>
                                        ${bookId.author}
                                    </td>
                                    <td>
                                        <img src="${bookId.image}" height="90px"/>
                                    </td>
                                    <td style="text-align: right">
                                        <s:number name="price" currency="vnd"/> vnd
                                    </td>
                                    <td>
                                        <s:textfield name="quantity" cssStyle="text-align: center; width: 100px" type="number" min="1" required="required"/>
                                    </td>
                                    <td>
                                        <s:set var="total" value="%{price * quantity}"/>
                                        <s:number name="#total" currency="vnd"/> vnd
                                    </td>
                                    <td>
                                        <s:submit 
                                            name="btAction"
                                            value="Update" 
                                            cssStyle="margin-top: 5px; color: steelblue"/>
                                        <br/>
                                        <s:submit 
                                            name="btAction"
                                            value="Delete"
                                            cssStyle="margin-top: 5px; color: salmon"
                                            onclick="return confirmIfDelete('%{bookId.name}')"/>

                                        <s:hidden name="detailPosition" value="%{#counter.count - 1}"/>
                                    </td>   
                                </tr>

                                <s:set var="totalPrice" value="%{#totalPrice + price * quantity}"/>
                            </s:form>
                        </s:iterator>
                    </tbody>
                </table>

                <p><em>Total price: <strong><s:number name="#totalPrice" currency="vnd"/> vnd</strong></em></p>

                <s:form action="confirmCart" method="POST">
                    <s:select label="Payment method" 
                              list="#session.PAYMENT_METHODS" 
                              listKey="id"
                              listValue="name"
                              name="paymentMethodId"
                              />
                    <s:if test="%{!#session.DISCOUNTS.isEmpty()}">
                        <s:select label="Discount"
                                  list="#session.DISCOUNTS"
                                  listKey="id"
                                  listValue="discountCode"
                                  name="discountId"
                                  />
                    </s:if>
                    <s:submit value="Confirm"/>
                </s:form>
            </s:else>
        </s:else>

        <s:if test="message != null && !message.isEmpty()">
            <h3 style="color: teal"><em>${message}</em></h3>
                </s:if>

        <script>
            function confirmIfDelete(productName) {
                return confirm('You you want to delete ' + productName + '?');
            }
        </script>
    </body>
</html>
