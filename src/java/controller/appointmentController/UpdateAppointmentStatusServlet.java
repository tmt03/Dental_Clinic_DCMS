package controller.appointmentController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;
import dal.AppointmentDAO;
import model.Appointment;
import model.User;

import static Service.Email.sendTo;

/**
 * Servlet xử lý yêu cầu cập nhật trạng thái cuộc hẹn và gửi email thông báo.
 * Hỗ trợ các vai trò: doctor, nurse, patient.
 */
@WebServlet(name = "UpdateAppointmentStatusServlet", urlPatterns = {"/updateAppointmentStatus"})
public class UpdateAppointmentStatusServlet extends HttpServlet {

    private static final AppointmentDAO dao = new AppointmentDAO();

    /**
     * Xử lý yêu cầu POST để cập nhật trạng thái cuộc hẹn.
     *
     * @param request  Đối tượng yêu cầu HTTP chứa thông tin cuộc hẹn và trạng thái mới.
     * @param response Đối tượng phản hồi HTTP.
     * @throws ServletException Nếu có lỗi xử lý servlet.
     * @throws IOException      Nếu có lỗi nhập/xuất.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra session và người dùng
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy các parameter từ request
        String appointmentID = request.getParameter("appointmentID");
        String newStatus = request.getParameter("newStatus");
        String rejectReason = request.getParameter("rejectReason");
        String controllerID = request.getParameter("controllerID");

        try {
            // Kiểm tra appointmentID hợp lệ
            int appID = Integer.parseInt(appointmentID);

            // Lấy email của bệnh nhân
            String patientEmail = dao.getEmailByAppointmentID(appID);
            if (patientEmail == null) {
                throw new IllegalStateException("Patient email not found for appointment ID: " + appID);
            }

            // Cập nhật trạng thái và gửi email
            updateStatusAndNotify(request, appID, newStatus, rejectReason, patientEmail);

            // Điều hướng theo vai trò người dùng
            redirectUser(user, request, response, controllerID);
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Invalid appointment ID format.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (IllegalStateException e) {
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Cập nhật trạng thái cuộc hẹn và gửi email thông báo đến bệnh nhân.
     *
     * @param request      Đối tượng yêu cầu HTTP để set thông báo.
     * @param appointmentID ID của cuộc hẹn.
     * @param newStatus    Trạng thái mới (approve, validate, reject).
     * @param rejectReason Lý do từ chối (nếu có).
     * @param patientEmail Email của bệnh nhân.
     */
    private void updateStatusAndNotify(HttpServletRequest request, int appointmentID, String newStatus,
                                       String rejectReason, String patientEmail) {
        switch (newStatus) {
            case "accept":
                dao.updateAppointmentStatus(String.valueOf(appointmentID), newStatus);
                sendEmail(request, patientEmail, "Appointment Accepted",
                        "Your appointment with ID " + appointmentID + " has been accepted.");
                break;

            case "validate":
                dao.updateAppointmentStatus(String.valueOf(appointmentID), newStatus);
                request.setAttribute("msg", "Appointment status updated successfully");
                break;

            case "reject":
                if (rejectReason == null || rejectReason.trim().isEmpty()) {
                    request.setAttribute("msg", "Reject reason is required.");
                    return;
                }
                dao.updateAppointmentStatus(String.valueOf(appointmentID), newStatus);
                sendEmail(request, patientEmail, "Appointment Rejected",
                        "Your appointment with ID " + appointmentID + " has been rejected. Reason: " + rejectReason);
                break;

            default:
                request.setAttribute("msg", "Invalid status: " + newStatus);
                throw new IllegalArgumentException("Unsupported status: " + newStatus);
        }
    }

    /**
     * Gửi email thông báo và set thông báo tương ứng.
     *
     * @param request Đối tượng yêu cầu HTTP để set thông báo.
     * @param email   Email của người nhận.
     * @param subject Chủ đề email.
     * @param content Nội dung email.
     */
    private void sendEmail(HttpServletRequest request, String email, String subject, String content) {
        boolean emailSent = sendTo(email, subject, content);
        if (emailSent) {
            request.setAttribute("msg", "Appointment status updated and email sent successfully");
        } else {
            request.setAttribute("msg", "Appointment status updated but failed to send email");
        }
    }

    /**
     * Điều hướng người dùng đến trang phù hợp dựa trên vai trò.
     *
     * @param user     Người dùng hiện tại.
     * @param request  Đối tượng yêu cầu HTTP.
     * @param response Đối tượng phản hồi HTTP.
     * @throws ServletException Nếu có lỗi xử lý servlet.
     * @throws IOException      Nếu có lỗi nhập/xuất.
     */
    private void redirectUser(User user, HttpServletRequest request, HttpServletResponse response, String controllerID)
            throws ServletException, IOException {
        String role = user.getRole();
        RequestDispatcher dispatcher;
        System.out.println(role + "chó chết");
        switch (role) {
            case "doctor":
                request.setAttribute("controllerID", controllerID);
                dispatcher = request.getRequestDispatcher("/viewMedicalAppointment");
                dispatcher.forward(request, response);
                break;
            case "nurse":
                dispatcher = request.getRequestDispatcher("/viewMedicalAppointment");
                dispatcher.forward(request, response);
                break;
            case "patient":
                String patientID = request.getParameter("userID");
                if (patientID == null || patientID.trim().isEmpty()) {
                    request.setAttribute("msg", "User ID is required for patient role.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
                List<Appointment> appointments = dao.getAppointmentsForPatient(Integer.parseInt(patientID));
                request.setAttribute("appointments", appointments);
                dispatcher = request.getRequestDispatcher("yourAppointment.jsp");
                dispatcher.forward(request, response);
                break;

            default:
                dispatcher = request.getRequestDispatcher("home1.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    /**
     * Trả về mô tả ngắn gọn về servlet.
     *
     * @return Mô tả servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet to update appointment status and notify via email.";
    }
}