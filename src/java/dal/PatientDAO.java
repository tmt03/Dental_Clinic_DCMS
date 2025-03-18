
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author thanm
 */
public class PatientDAO extends DBContext{
    
    
    public List<User> getPatients() {
        List<User> patients = new ArrayList<>();
        String sql = "SELECT [tbl_userID],\n"
                + "       [tbl_username],\n"
                + "       [tbl_password],\n"
                + "       [tbl_displayName],\n"
                + "       [tbl_email],\n"
                + "       [tbl_address],\n"
                + "       [tbl_mobile],\n"
                + "       [tbl_role],\n"
                + "       [tbl_others],\n"
                + "       [tbl_image],\n"
                + "       YEAR(GETDATE()) - YEAR([tbl_dob]) AS [age]\n"
                + "  FROM [dbo].[User]\n"
                + " WHERE [tbl_role] = 'patient';";
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
                user.setAge(Integer.parseInt(resultSet.getString("age")));
                patients.add(user);
            }
        } catch (SQLException e) {
        }
        return patients;
    }
    
}
