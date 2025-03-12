/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import static Service.Email.sendTo;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.DAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Appointment;
import model.User;

/**
 *
 * @author ntawo
 */
@WebServlet(name = "UpdateAppointmentStatusServlet", urlPatterns = {"/updateAppointmentStatus"})
public class UpdateAppointmentStatusServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateAppointmentStatusServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateAppointmentStatusServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        // Kiểm tra session
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("account");

        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy các parameter từ request
        String appointmentID = request.getParameter("appointmentID");
        String newStatus = request.getParameter("newStatus");
        String rejectReason = request.getParameter("rejectReason");

        // Khởi tạo DAO và cập nhật trạng thái cuộc hẹn
        DAO dao = new DAO();
        dao.updateAppointmentStatus(appointmentID, newStatus);

        // Lấy email của bệnh nhân từ cơ sở dữ liệu
        String patientEmail = dao.getEmailByAppointmentID(Integer.parseInt(appointmentID));

        // Xử lý gửi email theo trạng thái mới
        if ("accept".equals(newStatus)) {
            String subject = "Appointment Accepted";
            String content = "Your appointment with ID " + appointmentID + " has been accepted.";
            boolean emailSent = sendTo(patientEmail, subject, content);

            if (emailSent) {
                request.setAttribute("msg", "Appointment status updated and email sent successfully");
            } else {
                request.setAttribute("msg", "Appointment status updated but failed to send email");
            }
        } else if ("reject".equals(newStatus) && rejectReason != null) {
            String subject = "Appointment Rejected";
            String content = "Your appointment with ID " + appointmentID + " has been rejected. Reason: " + rejectReason;
            boolean emailSent = sendTo(patientEmail, subject, content);

            if (emailSent) {
                request.setAttribute("msg", "Appointment status updated and email sent successfully");
            } else {
                request.setAttribute("msg", "Appointment status updated but failed to send email");
            }
        } else {
            request.setAttribute("msg", "Appointment status updated successfully");
        }

        // Xử lý điều hướng theo vai trò của người dùng
        String role = u.getRole();
        if ("doctor".equals(role)) {
            request.getRequestDispatcher("doctor.jsp").forward(request, response);
        } else if ("nurse".equals(role)) {
            request.getRequestDispatcher("doctor.jsp").forward(request, response);
        } else if ("patient".equals(role)) {
            String PatientID = request.getParameter("userID");
            List<Appointment> a = dao.getAppointmentsForPatient(Integer.parseInt(PatientID));
            request.setAttribute("appointments", a);
            RequestDispatcher dispatcher = request.getRequestDispatcher("yourAppointment.jsp");
            dispatcher.forward(request, response);
        } else {
            request.getRequestDispatcher("home1.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to update appointment status";
    }

}
