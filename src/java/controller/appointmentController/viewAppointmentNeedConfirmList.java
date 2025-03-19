package controller.appointmentController;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Appointment;
import dal.AppointmentDAO;

/**
 *
 * @author thanm
 */
public class viewAppointmentNeedConfirmList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet viewAppointmentNeedConfirmList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet viewAppointmentNeedConfirmList at " + request.getContextPath() + "</h1>");
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
        
        String controllerID = (String) request.getAttribute("controllerID");
        if (controllerID == null || controllerID.trim().isEmpty()) {
            controllerID = request.getParameter("controllerID");
        }
        
        AppointmentDAO ApointmentDAO = new AppointmentDAO();
        List<Appointment> a = ApointmentDAO.getAppointmentsNeedConfirm(Integer.parseInt(controllerID));
        request.setAttribute("appointments", a);
        RequestDispatcher dispatcher = request.getRequestDispatcher("doctor.jsp?msg=appointmentneedconfirm");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
