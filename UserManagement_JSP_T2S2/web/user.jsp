<%-- 
    Document   : user
    Created on : Jan 22, 2026, 10:42:24 AM
    Author     : ASUS
--%>

<%@page import="fa26.t2s2.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>
    <body>
    <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
        <c:redirect url="login.jsp"></c:redirect>
    </c:if>
    <a href="shopping.jsp">Shopping</a>

    User ID: ${sessionScope.LOGIN_USER.userID}
    Full Name: ${sessionScope.LOGIN_USER.fullName}
    Role ID: ${sessionScope.LOGIN_USER.roleID}
    Password: ${sessionScope.LOGIN_USER.password}

    <form action="MainController" method="POST" style="display:inline;">
        <input type="submit" name="action" value="Logout"/>
    </form>
</body>
</html>