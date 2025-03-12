package controller.admin;

import dal.DAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Service;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 *
 * @author ntawo
 */
@WebServlet("/service")
@MultipartConfig
public class addEditService extends HttpServlet {

    private DAO dao;

    @Override
    public void init() throws ServletException {
        dao = new DAO(); // Initialize your DAO instance
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Service> services = dao.getService();
        request.setAttribute("Services", services);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("addservice".equals(action)) {
            addService(request, response);
        } else if ("editservice".equals(action)) {
            editService(request, response);
        } else if ("delete".equals(action)) {
            deleteService(request, response);
        }
    }

    private void addService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String serviceName = request.getParameter("serviceName");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String time = request.getParameter("time");

        Service service = new Service();
        service.setServiceName(serviceName);
        service.setDescription(description);
        service.setPrice(Float.parseFloat(price));
        service.setTime(Integer.parseInt(time));

        // Handle image upload
        Part imagePart = request.getPart("imageFile");
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/") + "images/" + fileName;
            try (InputStream inputStream = imagePart.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
                service.setImage(request.getContextPath() + "/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!dao.existedService(serviceName)) {
            dao.addService(service);
            response.sendRedirect("service?msg=addedservice");
        } else {
            response.sendRedirect("service?msg=errorservice");
        }
    }

    private void editService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String serviceName = request.getParameter("serviceName");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String time = request.getParameter("time");
        int serviceID = Integer.parseInt(request.getParameter("serviceID"));

        Service service = new Service();
        service.setServiceName(serviceName);
        service.setDescription(description);
        service.setPrice(Float.parseFloat(price));
        service.setTime(Integer.parseInt(time));
        service.setServiceID(serviceID);

        // Handle image upload
        Part imagePart = request.getPart("imageFile");
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/") + "images/" + fileName;
            try (InputStream inputStream = imagePart.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
                service.setImage(request.getContextPath() + "/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If no new image is uploaded, get the existing image path
            Service existingService = dao.getServiceByID(serviceID);
            service.setImage(existingService.getImage());
        }

        dao.updateService(service);
        response.sendRedirect("service?msg=editedservice");
    }

    private void deleteService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String serviceName = request.getParameter("serviceName");
        dao.deleteService(serviceName);
        response.sendRedirect("service?msg=deletedservice");
    }
}
