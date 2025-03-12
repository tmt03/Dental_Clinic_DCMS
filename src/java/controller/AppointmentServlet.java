/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import model.Appointment;
import model.Service;
import model.User;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "Appointment", urlPatterns = {"/appointment"})
public class AppointmentServlet extends HttpServlet {

    private void loadServicesAndDoctors(HttpServletRequest request) {
        DAO dao = new DAO();
        List<Service> services = dao.getService();
        request.setAttribute("services", services);
        List<User> doctors = dao.getDoctor();
        request.setAttribute("doctors", doctors);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadServicesAndDoctors(request);
        request.getRequestDispatcher("appointment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String recaptchaResponse = request.getParameter("g-recaptcha-response");
        boolean isCaptchaVerified = verifyRecaptcha(recaptchaResponse);

        if (!isCaptchaVerified) {
            loadServicesAndDoctors(request);
            request.setAttribute("errorMessage", "Captcha verification failed. Please try again.");
            request.getRequestDispatcher("appointment.jsp").forward(request, response);
            return;
        }

        try {
            String time = request.getParameter("time");
            int controllerID = Integer.parseInt(request.getParameter("doctor"));
            int patientID = Integer.parseInt(request.getParameter("patient"));
            String note = request.getParameter("note");
            String status = "pending";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(request.getParameter("date"), formatter);

            LocalDate today = LocalDate.now();
            if (!date.isAfter(today)) {
                loadServicesAndDoctors(request);
                request.setAttribute("errorMessage", "Bạn chỉ có thể đặt lịch trong tương lai.");
                request.getRequestDispatcher("appointment.jsp").forward(request, response);
                return;
            }

            int serviceID = Integer.parseInt(request.getParameter("service"));

            DAO dao = new DAO();
            boolean isAvailable = dao.checkAppointmentAvailability(date, time, controllerID);

            if (isAvailable) {
                float revenue = 100000;
                dao.addAppointment(time, controllerID, patientID, note, status, date, serviceID, revenue);
                String PatientID = request.getParameter("patient");

                List<Appointment> a = dao.getNewAppointmentsForPatient(Integer.parseInt(PatientID));
                request.setAttribute("appointments", a);
                RequestDispatcher dispatcher = request.getRequestDispatcher("vnpay.jsp");
                dispatcher.forward(request, response);
            } else {
                List<Appointment> conflictingAppointments = dao.getDoctorAppointmentsOnDate(controllerID, date);
                loadServicesAndDoctors(request);
                request.setAttribute("errorMessage", "Thời gian này đã có người đặt. Vui lòng chọn thời gian khác.");
                request.setAttribute("conflictingAppointments", conflictingAppointments);
                request.getRequestDispatcher("appointment.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            loadServicesAndDoctors(request);
            request.setAttribute("errorMessage", "Đã xảy ra lỗi. Vui lòng thử lại.");
            request.getRequestDispatcher("appointment.jsp").forward(request, response);
        }
    }

    private boolean verifyRecaptcha(String recaptchaResponse) {
        String secretKey = "6LeerBIqAAAAAPphZrfsfrpyN4q8jVviHbz2pvRo";
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "secret=" + secretKey + "&response=" + recaptchaResponse;

        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            return json.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
