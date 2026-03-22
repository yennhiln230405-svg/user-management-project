<%-- 
    Document   : viewCart
    Created on : Mar 2, 2026, 10:48:30 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
    </head>

    <body>

        <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <h1>Your selected items</h1>

        <c:if test="${sessionScope.CART != null && not empty sessionScope.CART.cart}">

            <c:set var="totalAmount" value="0"/>

            <table border="1">

                <thead>
                    <tr>
                        <th>NO</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Edit</th>
                        <th>Remove</th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="product" items="${sessionScope.CART.cart.values()}" varStatus="counter">

                        <c:set var="itemTotal" value="${product.price * product.quantity}"/>
                        <c:set var="totalAmount" value="${totalAmount + itemTotal}"/>

                        <tr>

                    <form action="MainController" method="POST">

                        <td>${counter.count}</td>

                        <td>
                            <input type="text" name="id" value="${product.id}" readonly>
                        </td>

                        <td>${product.name}</td>

                        <td>
                            <fmt:formatNumber value="${product.price}" maxFractionDigits="2"/>
                        </td>

                        <td>
                            <input type="number" name="quantity" value="${product.quantity}" min="1" required>
                        </td>

                        <td>
                            <fmt:formatNumber value="${itemTotal}" maxFractionDigits="2"/>$
                        </td>

                        <td>
                            <input type="submit" name="action" value="Edit">
                        </td>

                        <td>
                            <input type="submit" name="action" value="Remove">
                        </td>

                    </form>

                </tr>

            </c:forEach>

            <tr>
                <td colspan="5"><b>Total Payment</b></td>
                <td colspan="3">
                    <b>
                        <fmt:formatNumber value="${totalAmount}" maxFractionDigits="2"/>$
                    </b>
                </td>
            </tr>

        </tbody>
    </table>

    <form action="MainController" method="POST">
        <input type="submit" name="action" value="Checkout"/>
    </form>

</c:if>

<c:if test="${sessionScope.CART == null || empty sessionScope.CART.cart}">
    Your cart is empty !!!
</c:if>

<br>

<a href="shopping.jsp">Mua them di !!</a>

<br><br>

<c:if test="${requestScope.ERROR_MESSAGE != null}">
    ${requestScope.ERROR_MESSAGE}
</c:if>

<c:if test="${requestScope.SUCCESS_MESSAGE != null}">
    ${requestScope.SUCCESS_MESSAGE}
</c:if>

</body>
</html>