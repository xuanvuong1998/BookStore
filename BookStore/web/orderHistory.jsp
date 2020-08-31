<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <title>Order History Page</title>
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

            <h1>Order History</h1>
            <table border="1">
            <s:form action="orderHistory" method="POST">
                <tbody>
                    <tr>
                        <td>Book name: </td>
                        <td>
                            <s:textfield name="bookName" label="Book name"/>
                        </td>
                    </tr>
                    <tr>
                        <td>From date: </td>
                        <td>
                            <s:textfield name="fromDateStr" label="From date" type="date"/>
                        </td>
                    </tr>
                    <tr>
                        <td>To date: </td>
                        <td>
                            <s:textfield name="toDateStr" label="To date" type="date"/>
                        </td>
                    </tr>
                </tbody>
                <s:submit value="Search"/>
            </s:form>
        </table>
        <s:if test="orders == null || orders.isEmpty()">
            <h3>Your shopping history is empty.</h3>
        </s:if>
        <s:else>
            <p><em>We found <strong>${orders.size()} order(s)</strong> in your history orders.</em></p>
            <s:iterator value="orders" var="order">
                <strong>Order #<s:property value="id"/></strong>
                <br/>
                Created date: <s:date name="createdDate" format="dd/MM/yyyy hh:mm:ss"/>

                <s:set var="totalPrice" value="%{0}"/>
                <s:set var="details" value="orderDetailsCollection"/>
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
                                    <td style="text-align: center">
                                        ${quantity}
                                    </td>
                                    <td>
                                        <s:set var="total" value="%{price * quantity}"/>
                                        <s:number name="#total" currency="vnd"/> vnd
                                    </td>
                                </tr>
                                <s:set var="totalPrice" value="%{#totalPrice + price * quantity}"/>
                            </s:form>
                        </s:iterator>
                    </tbody>
                </table>
                <s:if test="discountId != null">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Discount code</th>
                                <th>Discount percent</th>
                            </tr>
                        </thead>
                        <tbody>
                        <td>
                            <strong><s:text name="%{discountId.discountCode}"/></strong>
                        </td>
                        <td>
                            <strong><s:number name="%{discountId.discountPercent}"/>%</strong>
                            <s:set var="totalPrice" value="%{#totalPrice * (100 - discountId.discountPercent) / 100}" />
                        </td>
                    </tbody>
                </table>
            </s:if>
            Final price: <strong><s:number name="#totalPrice" currency="vnd"/> vnd</strong> <br/> 
        </s:iterator>
    </s:else>
</body>
</html>
