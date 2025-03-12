package controller.doctor;

import dal.DAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Medicine;

@WebServlet(name = "viewAndUpdateMedicineServlet", urlPatterns = {"/viewAndUpdateMedicine"})
public class viewAndUpdateMedicineServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultID = request.getParameter("id");
         String Appointmentid = request.getParameter("Appointmentid");
        if (resultID != null && !resultID.isEmpty()) {
            DAO dao = new DAO();
            List<Medicine> medicines = dao.getMedicineByResultID(Integer.parseInt(resultID));
            request.setAttribute("medicines", medicines);
        }

        request.getRequestDispatcher("viewAndUpdateMedicine.jsp?Appointmentid="+Appointmentid).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int resultID = Integer.parseInt(request.getParameter("resultID"));
        String medicineName = request.getParameter("medicineName");
        String instruction = request.getParameter("instruction");
        
        DAO dao = new DAO();
        dao.updateMedicine(resultID, medicineName, instruction);

       
            List<Medicine> medicines = dao.getMedicineByResultID(resultID);
            request.setAttribute("medicines", medicines);
  
        doGet(request, response);
        
    }

    @Override
    public String getServletInfo() {
        return "Servlet for viewing and updating medicine information.";
    }
}
