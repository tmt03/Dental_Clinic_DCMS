/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "EditEmployee", urlPatterns = {"/Employee"})
public class EditEmployee extends HttpServlet {

    private DAO dao;

    @Override
    public void init() throws ServletException {
        dao = new DAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            String username = request.getParameter("username");
            User user = dao.check(username, "");
            request.setAttribute("user", user);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String displayName = request.getParameter("displayName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");
        String others = request.getParameter("others");
        String image = request.getParameter("image");
        String dob = request.getParameter("dob");

        User user = new User();
        user.setUsername(username);
        user.setDisplayName(displayName);
        user.setEmail(email);
        user.setAddress(address);
        user.setMobile(mobile);
        user.setPassword(password);
        user.setOthers(others);
        user.setImage(image);
         user.setDob(dob);

            
        try {
            if ("add".equals(action)) {
                if (!dao.existedUser(username) && !dao.existedEmail(email)) {
                    dao.register(user);
                    response.sendRedirect("viewEmployee?msg=added");
                } else {
                    response.sendRedirect("viewEmployee?msg=error");
                }
            } else if ("edit".equals(action)) {
                dao.updateUser(displayName, email, address, mobile, others, image, username);
                response.sendRedirect("viewEmployee?msg=edited");
            }
        } catch (SQLException e) {
            out.print("error:An error occurred: " + e.getMessage());
        }
    }
}
