
package controller.appointmentController;

import dal.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Appointment;
import model.Service;
import model.User;
import java.util.logging.Logger;
import controller.businessLogic.ValidateBookAppointment;
import controller.businessLogic.ParameterParser;
import controller.businessLogic.RecaptchaValidator;
import dal.AppointmentDAO;
import dal.ServiceDAO;


/**
 * Servlet xử lý yêu cầu đặt lịch hẹn (appointment) cho hệ thống nha khoa.
 */
@WebServlet(name = "AppointmentServlet", urlPatterns = {"/appointment"})
public class AppointmentServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AppointmentServlet.class.getName());
    private static final String APPOINTMENT_VIEW = "appointment.jsp";
    private static final String PAYMENT_VIEW = "vnpay.jsp";
    private static final float DEFAULT_REVENUE = 100000; // Nên đưa vào config
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadServicesAndDoctors(request);
        request.getRequestDispatcher(APPOINTMENT_VIEW).forward(request, response);
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

        // Xử lý đặt lịch hẹn
        try {
            processAppointmentBooking(request, response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            handleError(request, response, e.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Error processing appointment: " + e.getMessage());
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
     * Xử lý logic đặt lịch hẹn.
     */
    private void processAppointmentBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String time = request.getParameter("time");
        int doctorID = ParameterParser.parseInt(request.getParameter("doctor"), "Invalid doctor ID");
        int patientID = ParameterParser.parseInt(request.getParameter("patient"), "Invalid patient ID");
        String note = request.getParameter("note");
        String status = "pending";
        int serviceID = ParameterParser.parseInt(request.getParameter("service"), "Invalid service ID");

        LocalDate date = LocalDate.parse(request.getParameter("date"), DATE_FORMATTER);
        //gọi validateDate từ BL
        ValidateBookAppointment.validateDate(date);

        AppointmentDAO ApointmentDAO = new AppointmentDAO();
        if (!ApointmentDAO.checkAppointmentAvailability(date, time, doctorID)) {
            List<Appointment> conflictingAppointments = ApointmentDAO.getDoctorAppointmentsOnDate(doctorID, date);
            request.setAttribute("conflictingAppointments", conflictingAppointments);
            throw new IllegalStateException("Thời gian này đã có người đặt. Vui lòng chọn thời gian khác.");
        }

        ApointmentDAO.addAppointment(time, doctorID, patientID, note, status, date, serviceID, DEFAULT_REVENUE);
        List<Appointment> appointments = ApointmentDAO.getNewAppointmentsForPatient(patientID);
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher(PAYMENT_VIEW).forward(request, response);
    }


    /**
     * Xử lý và hiển thị thông báo lỗi.
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        loadServicesAndDoctors(request);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher(APPOINTMENT_VIEW).forward(request, response);
    }
}