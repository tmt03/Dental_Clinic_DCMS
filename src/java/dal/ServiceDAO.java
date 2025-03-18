
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Service;
import model.User;

public class ServiceDAO extends DBContext{
    
    public List<Service> getService() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM [Service] ";
        try {

            PreparedStatement pt = connection.prepareStatement(sql);
            ResultSet resultSet = pt.executeQuery();

            while (resultSet.next()) {
                Service service = new Service();
                service.setServiceID(resultSet.getInt("tbl_serviceID"));
                service.setServiceName(resultSet.getString("tbl_serviceName"));
                service.setDescription(resultSet.getString("tbl_description"));
                service.setTime(Integer.parseInt(resultSet.getString("tbl_time")));
                service.setPrice(resultSet.getInt("tbl_price"));
                service.setImage(resultSet.getString("tbl_image"));
                services.add(service);
            }
        } catch (SQLException e) {
        }
        return services;
    }
    
    public List<User> getDoctor() {
        List<User> employees = new ArrayList<>();
        String sql = "SELECT * FROM [user] \n"
                + "WHERE tbl_role = 'doctor'";
        try {

            PreparedStatement pt = connection.prepareStatement(sql);
            ResultSet resultSet = pt.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUserID(Integer.parseInt(resultSet.getString("tbl_userID")));
                user.setUsername(resultSet.getString("tbl_username"));
                user.setDisplayName(resultSet.getString("tbl_displayname"));
                user.setEmail(resultSet.getString("tbl_email"));
                user.setAddress(resultSet.getString("tbl_address"));
                user.setMobile(resultSet.getString("tbl_mobile"));
                user.setRole(resultSet.getString("tbl_role"));
                user.setOthers(resultSet.getString("tbl_others"));
                user.setImage(resultSet.getString("tbl_image"));

                employees.add(user);
            }
        } catch (SQLException e) {
        }
        return employees;
    }
}
