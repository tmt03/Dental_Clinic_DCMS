package controller;

import Service.Email;
import dal.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet("/ForgotPassword")
public class FogotPasswordController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgotPasswordController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }

    public static String generatePassword(int length) {
        String normalChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String specialChars = "!@#$%^&*()_-+=[]{}<>,.?/|";
        String digits = "0123456789";

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        password.append(normalChars.charAt(random.nextInt(normalChars.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));

        for (int i = 0; i < length - 3; i++) {
            int charType = random.nextInt(3);
            switch (charType) {
                case 0:
                    password.append(normalChars.charAt(random.nextInt(normalChars.length())));
                    break;
                case 1:
                    password.append(specialChars.charAt(random.nextInt(specialChars.length())));
                    break;
                case 2:
                    password.append(digits.charAt(random.nextInt(digits.length())));
                    break;
            }
        }

        char[] passwordChars = password.toString().toCharArray();
        for (int i = 0; i < passwordChars.length; i++) {
            int randomIndex = random.nextInt(passwordChars.length);
            char temp = passwordChars[i];
            passwordChars[i] = passwordChars[randomIndex];
            passwordChars[randomIndex] = temp;
        }

        return new String(passwordChars);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        String randomPass = generatePassword(9);
        Email Email = new Email();
        String email = request.getParameter("emailOrMobile");
        if (dao.checkMail(email)) {
            Email.sendNewPassword(email, randomPass);
            dao.changePasswordByEmail(email, randomPass);
            request.setAttribute("message", "Sent new password to your email");
            request.setAttribute("messageType", "success");
        } else {
            request.setAttribute("message", "Email not found");
            request.setAttribute("messageType", "error");
        }
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
