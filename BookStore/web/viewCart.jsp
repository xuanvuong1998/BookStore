<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <title>View Cart Page</title>
        <style>
            th, td {
                padding: 0 5px;
            }
        </style>
    </head>
    <body>
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
            <h1>Your Cart</h1>

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
                <p><em>There are <strong>${details.size()} book(s)</strong> in your cart.</em></p>
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
                <h1><em>Total price: <strong><s:number name="#totalPrice" currency="vnd"/> vnd</strong></em></h1>

                <table border="1">
                    <s:form action="confirmCart" method="POST">
                        <tbody>
                            <tr>
                                <td>Payment method: </td>
                                <td>
                                    <s:select label="Payment method" 
                                              list="#session.PAYMENT_METHODS" 
                                              listKey="id"
                                              listValue="name"
                                              name="paymentMethodId"
                                              />
                                </td>
                            </tr>
                            <tr>
                                <td>Discount code: </td>
                                <td>
                                    <s:if test="%{!#session.DISCOUNTS.isEmpty()}">
                                        <s:select label="Discount"
                                                  list="#session.DISCOUNTS"
                                                  listKey="id"
                                                  listValue="discountDisplayLabel"
                                                  name="discountId"
                                                  />
                                    </s:if>
                                </td>
                            </tr>
                        </tbody>
                        <s:submit value="Confirm"/>
                    </s:form>
                </table>
                
            </s:else>
        </s:else>

        <s:if test="message != null && !message.isEmpty()">
            <h3 style="color: red"><em>${message}</em></h3>
                </s:if>

        <script>
            function confirmIfDelete(productName) {
                return confirm('Do you want to delete ' + productName + ' from Cart?');
            }
        </script>
    </body>
</html>
