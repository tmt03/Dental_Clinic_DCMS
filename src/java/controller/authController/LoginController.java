package controller.authController;

import dal.DAO;
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ntawo
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        DAO d = new DAO();
        User u = d.check(user, pass);
        HttpSession session = request.getSession();
        if (u == null) {
            request.setAttribute("error", "Username or password is invalid!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            session.setAttribute("account", u);
            String role = u.getRole();
            if ("admin".equals(role)) {
                response.sendRedirect("admin.jsp");
            } else if ("doctor".equals(role)) {
                response.sendRedirect("doctor.jsp");
            } else if ("nurse".equals(role)) {
                response.sendRedirect("doctor.jsp");
            } else {
                response.sendRedirect("home1.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
