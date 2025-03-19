

package controller.appointmentController;

import dal.AppointmentDAO;
import dal.ServiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import model.Appointment;
import model.Service;
import model.User;
import controller.businessLogic.ParameterParser;
import controller.businessLogic.RecaptchaValidator;
import controller.businessLogic.ValidateBookAppointment;

/**
 * Servlet xử lý yêu cầu đặt lịch hẹn (appointment) cho khách vãng lai (Guest).
 */
@WebServlet(name = "GuestService", urlPatterns = {"/guestService"})
public class GuestService extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GuestService.class.getName());
    private static final String PAYMENT_VIEW = "vnpay.jsp";
    private static final float DEFAULT_REVENUE = 100000; // Nên đưa vào config
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadServicesAndDoctors(request);
        request.setAttribute("currentDate", LocalDate.now().toString());
        request.getRequestDispatcher("appointment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xác minh reCAPTCHA
        String recaptchaResponse = request.getParameter("g-recaptcha-response");
        if (!RecaptchaValidator.verifyRecaptcha(recaptchaResponse)) {
            handleError(request, response, "Captcha verification failed. Please try again.");
            return;
        }

        // Xử lý đặt lịch hẹn cho Guest
        try {
            processGuestAppointmentBooking(request, response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            handleError(request, response, e.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Error processing guest appointment: " + e.getMessage());
            handleError(request, response, "Đã xảy ra lỗi. Vui lòng thử lại.");
        }
    }

    /**
     * Tải danh sách dịch vụ và bác sĩ từ cơ sở dữ liệu.
     */
    private void loadServicesAndDoctors(HttpServletRequest request) {
        ServiceDAO serviceDao = new ServiceDAO();
        List<Service> services = serviceDao.getService();
        List<User> doctors = serviceDao.getDoctor();
        request.setAttribute("services", services);
        request.setAttribute("doctors", doctors);
    }

    /**
     * Xử lý logic đặt lịch hẹn cho Guest.
     */
    private void processGuestAppointmentBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy các tham số từ form
        String name = request.getParameter("guestName");
        String email = request.getParameter("guestEmail");
        String phone = request.getParameter("guestMobile");
        String time = request.getParameter("time");
        int doctorID = ParameterParser.parseInt(request.getParameter("doctor"), "Invalid doctor ID");
        String note = request.getParameter("note");
        String status = "pending";
        int serviceID = ParameterParser.parseInt(request.getParameter("service"), "Invalid service ID");
        LocalDate date = LocalDate.parse(request.getParameter("date"), DATE_FORMATTER);

        ValidateBookAppointment.validateDate(date);

        // Kiểm tra lịch bác sĩ
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        if (!appointmentDAO.checkAppointmentAvailability(date, time, doctorID)) {
            List<Appointment> conflictingAppointments = appointmentDAO.getDoctorAppointmentsOnDate(doctorID, date);
            request.setAttribute("conflictingAppointments", conflictingAppointments);
            throw new IllegalStateException("Thời gian này đã có người đặt. Vui lòng chọn thời gian khác.");
        }

        // Tạo và lưu lịch hẹn cho Guest
        appointmentDAO.addAppointmentForGuest(time, doctorID, note, status, date, serviceID, DEFAULT_REVENUE, name, email, phone);
        List<Appointment> appointments = appointmentDAO.getNewAppointmentsForGuest(email); //Cho Guest view sau khi book xong (phát triển sau)
        request.setAttribute("guest_view", appointments);
        request.getRequestDispatcher(PAYMENT_VIEW).forward(request, response);
    }

    /**
     * Xử lý và hiển thị thông báo lỗi.
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        loadServicesAndDoctors(request);
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("currentDate", LocalDate.now().toString());
        request.getRequestDispatcher("/guestService").forward(request, response);
    }
}