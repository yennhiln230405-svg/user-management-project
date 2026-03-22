<%-- 
    Document   : shopping
    Created on : Mar 2, 2026, 10:05:50 AM
    Author     : ASUS
--%>

<%@page import="fa26.t2s2.shopping.Product"%>
<%@page import="java.util.List"%>
<%@page import="fa26.t2s2.shopping.ProductDAO"%>
<%@page import="fa26.t2s2.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mina Store</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !"US".equals(loginUser.getRoleID())) {
                response.sendRedirect("login.jsp");
                return;
            }
            ProductDAO dao = new ProductDAO();
            List<Product> list = dao.getListProduct();

        %>
        <h1>Welcome to my Store!</h1>
        <form action="MainController" method="POST">
            <select name="product">
                <!--             <option value="1-T Shirt-15">T shirt-15$</option>
                                <option value="2-Pant-15">Pant-15$</option>
                                <option value="3-Dress-20">Dress-20$</option>
                                <option value="4-Short-10">Short-10$</option>
                                <option value="5-Hat-50">Hat-50$</option>
                                <option value="6-Balo-30">Balo-30$</option>
                            </select>-->
               
                


                <%
                    for (Product p : list) {
                %>

                <option value="<%=p.getId()%>-<%=p.getName()%>-<%=p.getPrice()%>">
                    <%=p.getName()%> - $<%=p.getPrice()%>
                </option>

                <%
                    }
                %>

            </select>

            <select name="quantity">
                <option value="1">1 item</option>
                <option value="2">2 items</option>
                <option value="3">3 items</option>
                <option value="4">4 items</option>
                <option value="5">5 items</option>
                <option value="10">10 items</option>
            </select>

            <input type="submit" name="action" value="Add"/>
            <input type="submit" name="action" value="View"/>



        </form>


        ${requestScope.MESSAGE}
    </body>
</html>
