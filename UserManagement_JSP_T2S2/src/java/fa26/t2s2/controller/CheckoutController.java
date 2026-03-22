/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa26.t2s2.controller;

import fa26.t2s2.user.OrderDAO;
import fa26.t2s2.shopping.Cart;
import fa26.t2s2.shopping.Product;
import fa26.t2s2.user.UserDTO;
import fa26.t2s2.user.UserProductUserDAO;
import fa26.t2s2.utils.DBUtilsUserProduct;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */

@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckoutController extends HttpServlet {

    private static final String ERROR = "viewCart.jsp";
    private static final String SUCCESS = "viewCart.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession(false);
        try {
            if(session == null){
                request.setAttribute("ERROR_MESSAGE", "Session does not exist.");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if(loginUser == null){
                request.setAttribute("ERROR_MESSAGE", "You must login before checkout.");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            Cart cart = (Cart) session.getAttribute("CART");
            if(cart == null || cart.getCart() == null || cart.getCart().isEmpty()){
                request.setAttribute("ERROR_MESSAGE", "Your cart is empty.");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            Map<String, Product> items = cart.getCart();
            Connection conn = null;
            boolean ok = false;
            try {
                conn = DBUtilsUserProduct.getConnection();
                conn.setAutoCommit(false);
                
                OrderDAO orderDAO = new OrderDAO();
                UserProductUserDAO userDAO = new UserProductUserDAO();
                
                String emailKey = loginUser.getUserID();
                int uid = userDAO.getOrCreateUidByEmail(conn, emailKey);
                if(uid <= 0){
                    throw new Exception("Cannot create/find user in UserProduct.Users.");
                }
                for (Product p : items.values()) {
                    int pid = Integer.parseInt(p.getId());
                    int buyQty = p.getQuantity();
                    
                    if(buyQty <= 0){
                        throw new Exception("Invalid quantity for product id=" + pid);
                    }
                    int stock = orderDAO.getStockForUpdate(conn, pid);
                    if(stock < 0){
                        throw new Exception("Product not found: pid=" + pid);
                    }
                    if(stock < buyQty){
                        throw new Exception("Not enough stock for product pid=" + pid + ".Stock=" + stock);
                    }
                }
                double total = 0;
                for (Product p : items.values()) {
                    int pid = Integer.parseInt(p.getId());
                    int buyQty = p.getQuantity();
                    
                    double dbPrice = orderDAO.getPriceForUpdate(conn, pid);
                    if(dbPrice < 0){
                        throw new Exception("Cannot read price from DB for pid=" + pid);
                    }
                    total += dbPrice * buyQty;
                }
                int oid = orderDAO.insertOrder(conn, uid, total);
                if(oid <= 0){
                    throw new Exception("Insert order failed.");
                }
                for (Product p : items.values()) {
                    int pid = Integer.parseInt(p.getId());
                    int buyQty = p.getQuantity();
                    
                    double dbPrice = orderDAO.getPriceForUpdate(conn, pid);
                    orderDAO.insertOrderDetail(conn, oid, pid, dbPrice, buyQty);
                    orderDAO.updateProductQuantity(conn, pid, buyQty);
                }
                conn.commit();
                ok = true;
            } catch (Exception e) {
                if(conn != null){
                    try{
                        conn.rollback();
                    }catch(Exception ignored){}
                }
                request.setAttribute("ERROR_MESSAGE", e.getMessage());
            }finally{
                if(conn != null){
                    try {
                        conn.setAutoCommit(true);
                        conn.close();
                    } catch (Exception ignored) {}
                }
            }
            
            if (ok){
                session.removeAttribute("CART");
                request.setAttribute("SUCCESS_MESSAGE", "Checkout successfully!");
                url = SUCCESS;
            }else{
                url = ERROR;
            }                
            
        } catch (Exception ex) {
            request.setAttribute("ERROR_MESSAGE", ex.getMessage());
            url = ERROR;
        }
        request.getRequestDispatcher(url).forward(request, response);
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
