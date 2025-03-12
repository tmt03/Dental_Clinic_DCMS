package controller;

import dal.DAO;
import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Result;

public class SaveResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String appointmentID = request.getParameter("tbl_appointmentID");
        
        
        System.out.println("doGet - appointmentID: " + appointmentID); // Debugging line
        request.setAttribute("appointmentID", appointmentID);
        request.getRequestDispatcher("result1.jsp?msg=" + appointmentID).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String createTimeStr = request.getParameter("createTime").trim();
        String description = request.getParameter("description");
        String appointmentIDStr = request.getParameter("appointmentID");

        System.out.println("doPost - appointmentID: " + appointmentIDStr); // Debugging line

        if (appointmentIDStr == null || appointmentIDStr.isEmpty()) {
            request.setAttribute("error", "Appointment ID is missing!");
            request.getRequestDispatcher("result1.jsp").forward(request, response);
            return;
        }

        int appointmentID = Integer.parseInt(appointmentIDStr);
        try {
            Date createTime = Date.valueOf(createTimeStr);
            Result result = new Result();
            result.setCreateTime(createTime);
            result.setDescription(description);
            result.setAppointmentID(appointmentID);

            DAO dao = new DAO();
            dao.saveResult(result);
            dao.updateAppointmentStatus(request.getParameter("appointmentID"), "completed");
            float revenue=dao.getRevenue(dao.getserviceID(appointmentID));
           dao.updateRevenue(appointmentID, revenue);
            request.setAttribute("success", "Result has been saved successfully!");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date format!");
        }

        request.getRequestDispatcher("addMedicine.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for saving results";
    }
}
