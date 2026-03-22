<%-- 
    Document   : createUser
    Created on : Feb 26, 2026, 10:21:45 AM
    Author     : ASUS
--%>

<%@page import="fa26.t2s2.user.UserError"%>
<%@page import="fa26.t2s2.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
         <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            if (loginUser == null || !"AD".equals(loginUser.getRoleID().trim())) {
                response.sendRedirect("login.jsp");
                return;
            }
             
        %>
        Create new User:
        <form action="MainController" method="POST">
            User ID:<input type="text" name="userID" required=""/>${requestScope.USER_ERROR.userIDError}
            </br>Full Name:<input type="text" name="fullName" required=""/>${requestScope.USER_ERROR.fullIDError}
            </br>Role ID:<select name="roleID">
                <option value="US">US</option>
                <option value="AD">AD</option>
            </select>
            </br>Password:<input type="password" name="password" required=""/>
            </br>Confirm:<input type="password" name="confirm" required=""/>${requestScope.USER_ERROR.confirmIDError}
            </br><input type="submit" name="action" value="Create"/>
            <input type="reset" value="Reset"/>
            ${requestScope.USER_ERROR.error}
            
            
    
            
        </form>

    </body>
</html>
