
package controller.authController;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String rpass = request.getParameter("rpass");
        String email = request.getParameter("email");
        String displayname = request.getParameter("displayname");
        String dob = request.getParameter("dob");

        if (!pass.equals(rpass)) {
            String ms = "Passwords do not match.";
            request.setAttribute("ms", ms);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        DAO d = new DAO();
        User u = d.check(user, pass);

        if (u != null || d.existedUser(user)) {
            String ms = "Username already exists.";
            request.setAttribute("ms", ms);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (d.existedEmail(email)) {
            String ms = "Email already exists.";
            request.setAttribute("ms", ms);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            User us = new User();
            us.setUsername(user);
            us.setPassword(pass);
            us.setEmail(email);
            us.setDisplayName(displayname);
            us.setDob(dob);
            d.register(us);
            request.setAttribute("success", "Registration successful! Please log in.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
