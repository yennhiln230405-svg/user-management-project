/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa26.t2s2.controller;

import fa26.t2s2.user.UserDAO;
import fa26.t2s2.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
    
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String USER_PAGE = "user.jsp";
    private static final String US = "US";
    private static final String ADMIN_PAGE = "admin.jsp";
    private static final String AD = "AD";
    private static final String INCORRECT_MESSAGE = "Incorrect userID or password";
    private static final String NOT_SUPPORT_MESSAGE = "Your role is not supported";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = LOGIN_PAGE;
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            UserDAO dao = new UserDAO();
            UserDTO loginUser = dao.checkLogin(userID, password);
// xac thuc o day
        if(loginUser != null){
            HttpSession session = request.getSession();
            session.setAttribute("LOGIN_USER", loginUser);
// phan quyen tai day
        String roleID = loginUser.getRoleID().trim();
        if(AD.equals(roleID)){
            url = ADMIN_PAGE;
        }else if (US.equals(roleID)){
            url = USER_PAGE;
        }else{
            request.setAttribute("ERROR_MESSAGE", NOT_SUPPORT_MESSAGE);
        }
        }else{
            request.setAttribute("ERROR_MESSAGE", INCORRECT_MESSAGE);
        }
        
        
        }catch (Exception e){
            log("Error at LoginController: " + e.toString());
        }finally{
            request.getRequestDispatcher(url).forward(request, response);
        }
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
