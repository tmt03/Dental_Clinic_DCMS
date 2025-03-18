
package controller.appointmentController;

import dal.AppointmentDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Appointment;

public class viewMedicalAppointmentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet viewMedicalAppointmentServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet viewMedicalAppointmentServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String controllerID = request.getParameter("controllerID");
        AppointmentDAO ApointmentDAO = new AppointmentDAO();
        List<Appointment> a = ApointmentDAO.getAppointmentsForDoctor(Integer.parseInt(controllerID));
        request.setAttribute("appointments", a);
        RequestDispatcher dispatcher = request.getRequestDispatcher("doctor.jsp?msg=appointment");
        dispatcher.forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        AppointmentDAO ApointmentDAO = new AppointmentDAO();
        List<Appointment> a = ApointmentDAO.getAppointmentsForNurse();
        request.setAttribute("appointments", a);
        RequestDispatcher dispatcher = request.getRequestDispatcher("doctor.jsp?msg=appointment");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
