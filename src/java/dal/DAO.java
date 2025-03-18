/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import model.Medicine;
import model.Result;
import model.Service;

/**
 *
 * @author ntawo
 */
public class DAO extends DBContext {

    //CHECK
    public User check(String username, String password) {
        String sql = "SELECT [tbl_userID],\n"
                + "       [tbl_username],\n"
                + "       [tbl_password],\n"
                + "       [tbl_displayName],\n"
                + "       [tbl_email],\n"
                + "       [tbl_address],\n"
                + "       [tbl_mobile],\n"
                + "       [tbl_role],\n"
                + "       [tbl_others],\n"
                + "       [tbl_isActive],\n"
                + "       [tbl_image],\n"
                + "       [tbl_dob],\n"
                + "       YEAR(GETDATE()) - YEAR([tbl_dob]) AS [age]\n"
                + "  FROM [dbo].[User]\n"
                + " WHERE tbl_username = ? AND tbl_password = ? AND tbl_isActive = 'active'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUsername(username);
                u.setPassword(password);
                u.setUserID(rs.getInt("tbl_userID"));
                u.setRole(rs.getString("tbl_role"));
                u.setDisplayName(rs.getString("tbl_displayname"));
                u.setEmail(rs.getString("tbl_email"));
                u.setAddress(rs.getString("tbl_address"));
                u.setMobile(rs.getString("tbl_mobile"));
                u.setOthers(rs.getString("tbl_others"));
                u.setIsActive(rs.getString("tbl_isActive"));
                u.setImage(rs.getString("tbl_image"));
                u.setDob(rs.getString("tbl_dob"));
                u.setAge(rs.getInt("age"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean existedUser(String username) {
        String sql = "SELECT [tbl_userID],\n"
                + "       [tbl_username],\n"
                + "       [tbl_password],\n"
                + "       [tbl_displayName],\n"
                + "       [tbl_email],\n"
                + "       [tbl_address],\n"
                + "       [tbl_mobile],\n"
                + "       [tbl_role],\n"
                + "       [tbl_others],\n"
                + "       [tbl_isActive],\n"
                + "       [tbl_image],\n"
                + "       YEAR(GETDATE()) - YEAR([tbl_dob]) AS [age]\n"
                + "  FROM [dbo].[User]\n"
                + " WHERE tbl_username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean existedEmail(String email) {
        String sql = "SELECT [tbl_userID],\n"
                + "       [tbl_username],\n"
                + "       [tbl_password],\n"
                + "       [tbl_displayName],\n"
                + "       [tbl_email],\n"
                + "       [tbl_address],\n"
                + "       [tbl_mobile],\n"
                + "       [tbl_role],\n"
                + "       [tbl_others],\n"
                + "       [tbl_isActive],\n"
                + "       [tbl_image],\n"
                + "       YEAR(GETDATE()) - YEAR([tbl_dob]) AS [age]\n"
                + "  FROM [dbo].[User]\n"
                + " WHERE tbl_email=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void register(User u) {
        String sql = "INSERT INTO [dbo].[User] "
                + "(tbl_username, "
                + "tbl_displayName, "
                + "tbl_email, "
                + "tbl_dob, "
                + "[tbl_password]) "
                + "VALUES "
                + "(?, "
                + "?, "
                + "?, "
                + "?, "
                + "?)";
        try {
            System.out.println("Registering user: " + u.getUsername() + ", " + u.getDisplayName() + ", " + u.getPassword() + ", " + u.getEmail() + ", " + u.getDob());
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, u.getUsername());
            st.setString(2, u.getDisplayName());
            st.setString(3, u.getEmail());
            st.setString(4, u.getDob());
            st.setString(5, u.getPassword());
            int rowsInserted = st.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public boolean checkMail(String mail) {
        String sql = "select * from [User] "
                + "where tbl_email = ?";
        try {
            PreparedStatement ab = connection.prepareStatement(sql);
            ab.setString(1, mail);
            ResultSet rs = ab.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    public boolean checkUsernameAndPsw(String username, String psw) {
        String sql = "SELECT * FROM [User] "
                + "WHERE tbl_username = ? "
                + "AND tbl_password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, psw);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println("");
        }
        return false;
    }


    public void updateUser(String displayName, String email, String address, String mobile, String others, String image, String username) throws SQLException {
        String sql = "UPDATE [User] SET \n"
                + "	[tbl_displayName] = ?, \n"
                + "	[tbl_email] = ?, \n"
                + "	[tbl_address] = ?, \n"
                + "	[tbl_mobile] = ?, \n"
                + "	[tbl_others] = ?, \n"
                + "	[tbl_image] = ? \n"
                + "WHERE [tbl_username] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, displayName);
            st.setString(2, email);
            st.setString(3, address);
            st.setString(4, mobile);
            st.setString(5, others);
            st.setString(6, image);
            st.setString(7, username);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println("");
        }
    }
    
    public boolean existedService(String serviceName) {
        String sql = "SELECT * FROM [service] "
                + "WHERE tbl_serviceName = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, serviceName);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return false;
    }

    public Service getServiceByName(String serviceName) {
        String sql = "SELECT * FROM [service] "
                + "WHERE tbl_serviceName = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, serviceName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Service service = new Service();
                service.setServiceName(rs.getString("tbl_serviceName"));
                service.setDescription(rs.getString("tbl_description"));
                service.setPrice(rs.getFloat("tbl_price"));
                service.setTime(rs.getInt("tbl_time"));
                return service;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return null;
    }

    public int getserviceID(int appointmentID) {
        List<Appointment> Appointments = new ArrayList<>();
        String sql = "select a.tbl_serviceID from Appointment a\n"
                + "where a.tbl_appointmentID=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service a = new Service();
                a.setServiceID(rs.getInt("tbl_serviceID"));
                return a.getServiceID();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Service getServiceByID(int serviceID) {
        String sql = "SELECT * FROM [service] " + "WHERE tbl_serviceID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, serviceID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Service service = new Service();
                service.setServiceID(rs.getInt("tbl_serviceID"));
                service.setServiceName(rs.getString("tbl_serviceName"));
                service.setDescription(rs.getString("tbl_description"));
                service.setPrice(rs.getFloat("tbl_price"));
                service.setTime(rs.getInt("tbl_time"));
                service.setImage(rs.getString("tbl_image"));
                return service;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return null;
    }

}
