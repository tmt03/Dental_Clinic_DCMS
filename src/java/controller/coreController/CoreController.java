package controller.coreController;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author thanm
 */
public class CoreController extends HttpServlet {

    private String act;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        act = request.getParameter("action");

        if (act == null || act.trim().isEmpty()) {
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        //Điều hướng theo action
        handleRequest(request, response, act);
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response, String act) throws ServletException, IOException {
        switch (act) {
            case "LOGIN":
                request.getRequestDispatcher("/login").forward(request, response);
                break;
            case "REGISTER":
                request.getRequestDispatcher("/register").forward(request, response);
                break;
            case "LOGOUT":
                request.getRequestDispatcher("/logout").forward(request, response);
                break;
            case "PATIENT_BOOK_APPOINTMENT":
                request.getRequestDispatcher("/appointment").forward(request, response);
                break;
            case "GUEST_BOOK_APPOINTMENT":
                request.getRequestDispatcher("/guestService").forward(request, response);
                break;
            case "VIEW_BOOK_APPOINTMENT_FORM":
                request.getRequestDispatcher("/appointment").forward(request, response);
                break;
            case "NURSE_VALIDATE_APPOINTMENT":
                request.getRequestDispatcher("/updateAppointmentStatus").forward(request, response);
                break;
            case "REJECT_APPOINTMENT":
                request.getRequestDispatcher("/updateAppointmentStatus").forward(request, response);
                break;
            case "DOCTOR_APPROVE_APPOINTMENT":
                request.getRequestDispatcher("/updateAppointmentStatus").forward(request, response);
                break;
            case "VIEW_PATIENT_LIST":
                request.getRequestDispatcher("/viewpatient").forward(request, response);
                break;
            case "VIEW_APPOINTMENT_LIST":
                request.getRequestDispatcher("/viewMedicalAppointment").forward(request, response);
                break;
            case "VIEW_APPOINTMENT_NEED_VALIDATE":
                request.getRequestDispatcher("/viewMedicalAppointment").forward(request, response);
                break;
            case "VIEW_APPOINTMENT_NEED_CONFIRM":
                request.getRequestDispatcher("/viewNeedCf").forward(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

}
