<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Book Page</title>
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

        <h1>Search Book</h1>

        <s:form action="searchBook">
            <s:textfield name="bookName" label="Book name" cssStyle="text-align: center"/>
            <s:textfield name="categoryName" label="Category name" cssStyle="text-align: center"/>
            <s:textfield name="minPrice" label="Min price (vnd)" cssStyle="text-align: right" type="number" min="0"/>
            <s:textfield name="maxPrice" label="Max price (vnd)" cssStyle="text-align: right" type="number" min="0"/>
            <s:submit value="Search"/>
        </s:form>

        <s:if test="#session.USER != null && #session.USER.isAdmin">
            <s:a href="createBook.jsp">Create new book</s:a>
        </s:if>
        <s:else>
            <s:a href="viewCart.jsp">View cart</s:a>
            <s:if test="#session.USER != null && !#session.USER.isAdmin">
                <s:a href="orderHistory" cssStyle="margin-left: 20px">View shopping history</s:a>
            </s:if>
        </s:else>

        <s:if test="message != null && !message.isEmpty()">
            <h4 style="color: greenyellow">
                <em>${message}</em>
            </h4>
        </s:if>

        <s:if test="books != null && !books.isEmpty()">
            <h4><em>Found ${booksCount} books</em></h4>
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Image</th>
                            <s:if test="#session.USER != null && #session.USER.isAdmin">
                            <th>Image Url</th>
                            </s:if>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Author</th>
                        <th>Import Date</th>
                            <s:if test="#session.USER != null && #session.USER.isAdmin">
                            <th>Quantity</th>
                            </s:if>
                        <th>Category</th>
                            <s:if test="#session.USER != null && #session.USER.isAdmin">
                            <th>Is active</th>
                            </s:if>
                            <s:if test="#session.USER != null && #session.USER.isAdmin">
                            <th>Admin Action</th>
                            </s:if>
                            <s:else>
                            <th>Action</th>
                            </s:else>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="books" status="counter">
                        <tr>
                            <s:form action="updateBook" method="POST" theme="simple">
                                <!-- No. -->
                                <td style="text-align: center">
                                    <s:property value="#counter.count"/>
                                </td>

                                <!--Name-->
                                <td style="width: 200px">
                                    <s:if test="#session.USER != null && #session.USER.isAdmin">
                                        <s:textfield name="name"/>
                                    </s:if>
                                    <s:else>
                                        <s:property value="name"/>
                                    </s:else>
                                </td>

                                <!--Image-->
                                <td>
                                    <img src="${image}" height="90px"/>
                                </td>

                                <!--Image Url - Admin only-->
                                <s:if test="#session.USER != null && #session.USER.isAdmin">
                                    <td>
                                        <s:textfield name="imageUrl" value="%{image}"/>
                                    </td>
                                </s:if>

                                <!--Description-->
                                <td style="width: 450px">
                                    <s:if test="#session.USER != null && #session.USER.isAdmin">
                                        <s:textarea name="description" rows="5" cssStyle="width: 100%"/>
                                    </s:if>
                                    <s:else>
                                        <s:property value="description"/>
                                    </s:else>
                                </td>

                                <!--Price-->
                                <td style="text-align: right">

                                    <s:if test="#session.USER != null && #session.USER.isAdmin">
                                        <s:textfield name="price" type="number" min="0" cssStyle="width: 100px; text-align: right; display: inline"/> vnd
                                    </s:if>
                                    <s:else>
                                        <s:number name="price" currency="vnd"/> vnd
                                    </s:else>
                                </td>

                                <!--Author-->
                                <td style="text-align: right">
                                    <s:if test="#session.USER != null && #session.USER.isAdmin">
                                        <s:textfield name="author"/>
                                    </s:if>
                                    <s:else>
                                        <s:property value="author"/>
                                    </s:else>
                                </td>

                                <!--Import date-->
                                <td style="text-align: center">
                                    <s:if test="#session.USER != null && #session.USER.isAdmin">
                                        <s:date name="importDate" var="formattedDate" format="yyyy-MM-dd"/>
                                        <s:textfield name="importDate" value="%{formattedDate}" type="date"/>
                                    </s:if>
                                    <s:else>
                                        <s:date name="importDate" format="dd/MM/yyyy"/>
                                    </s:else>
                                </td>

                                <!--Quantity-->
                                <s:if test="#session.USER != null && #session.USER.isAdmin">
                                    <td>
                                        <s:textfield name="quantity" type="number" min="0" cssStyle="width: 70px; text-align: right; display: inline"/>
                                    </td>
                                </s:if>

                                <!--Category-->
                                <td>
                                    <s:if test="#session.USER != null && #session.USER.isAdmin">
                                        <s:if test="#session.CATEGORIES != null">
                                            <s:select 
                                                list="#session.CATEGORIES" 
                                                name="categoryId" 
                                                listKey="id"
                                                listValue="name"
                                                value="categoryId.id"
                                                theme="simple" />
                                        </s:if>
                                    </s:if>
                                    <s:else>
                                        <s:property value="categoryId.name"/>
                                    </s:else>
                                </td>

                                <!--Status - Admin only-->
                                <s:if test="#session.USER != null && #session.USER.isAdmin">
                                    <td>
                                        <s:select name="isActive" 
                                                  list="#{'true':'Yes', 'false':'No'}"
                                                  value="isActive"/>
                                        
                                    </td>
                                </s:if>

                                <!--Action-->
                                <s:if test="#session.USER != null && #session.USER.isAdmin">
                                    <td style="text-align: center">
                                        <s:submit 
                                            name="btAction" 
                                            value="Update"
                                            cssStyle="margin-top: 5px; color: steelblue"/>
                                        <s:hidden name="bookId" value="%{id}"/>
                                        <br/>
                                        <s:submit 
                                            name="btAction" 
                                            value="Delete"
                                            cssStyle="margin-top: 5px; color: salmon"
                                            onclick="return confirmIfDelete('%{name}')"/>
                                    </td>
                                </s:if>
                            </s:form>

                            <s:else>
                                <s:form action="addToCart" method="POST" theme="simple">
                                    <td style="text-align: center">
                                        <s:submit value="Add to cart"/>
                                        <s:hidden name="bookId" value="%{id}"/>
                                        <s:hidden name="bookName"/>
                                        <s:hidden name="categoryName"/>
                                        <s:hidden name="minPrice"/>
                                        <s:hidden name="maxPrice"/>
                                    </td>
                                </s:form>
                            </s:else>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>

        </s:if>
        <s:else>
            <h3><em>No products found!</em></h3>
        </s:else>

        <script>
            function confirmIfDelete(bookName) {
                return confirm('You you want to delete ' + bookName + '?');
            }
        </script>
    </body>
</html>
