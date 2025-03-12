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

    public void changePasswordByUserName(String username, String newPsw) {
        String sql = "UPDATE [User] SET "
                + "[tbl_password] = ? "
                + "WHERE tbl_username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newPsw);
            st.setString(2, username);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println("");
        }
    }

    public void changePasswordByEmail(String email, String newPsw) {
        String sql = "UPDATE [User] SET "
                + "[tbl_password] = ? "
                + "WHERE tbl_email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newPsw);
            st.setString(2, email);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println("");
        }
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

    public void updateProfile(String displayName, String email, String address, String mobile, String others, String image, String dob, String username) throws SQLException {
        String sql = "UPDATE [User] SET \n"
                + "	[tbl_displayName] = ?, \n"
                + "	[tbl_email] = ?, \n"
                + "	[tbl_address] = ?, \n"
                + "	[tbl_mobile] = ?, \n"
                + "	[tbl_others] = ?, \n"
                + "	[tbl_image] = ?, \n"
                + "	[tbl_dob] = ? \n"
                + "WHERE [tbl_username] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, displayName);
            st.setString(2, email);
            st.setString(3, address);
            st.setString(4, mobile);
            st.setString(5, others);
            st.setString(6, image);
            st.setString(7, dob);
            st.setString(8, username);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NullPointerException e) {
            System.out.println("");
        }
    }

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

    public void addNewEmployee(String username, String displayName, String email, String psw, String dob, String role) {
        String sql = "INSERT INTO [User] (\n"
                + "    [tbl_username],\n"
                + "    [tbl_displayName],\n"
                + "    [tbl_email],\n"
                + "    [tbl_password],\n"
                + "    [tbl_address],\n"
                + "    [tbl_mobile],\n"
                + "    [tbl_role],\n"
                + "    [tbl_others],\n"
                + "	[tbl_image],\n"
                + "	[tbl_dob]\n"
                + ")\n"
                + "VALUES (\n"
                + "    ?, \n"
                + "    ?, \n"
                + "    ?,\n"
                + "    ?, \n"
                + "    '', \n"
                + "    '', \n"
                + "    ?, \n"
                + "    '',\n"
                + "	'',\n"
                + "	?\n"
                + ");";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, displayName);
            st.setString(3, email);
            st.setString(4, psw);
            st.setString(5, role);

            st.setString(6, dob);
            st.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            System.out.println(e);
        }
    }

    public List<User> getEmployees() {
        List<User> employees = new ArrayList<>();
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
                + "WHERE (tbl_role = 'doctor' AND tbl_isActive = 'active')"
                + "OR (tbl_role = 'nurse' AND tbl_isActive = 'active')";
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
                employees.add(user);
            }
        } catch (SQLException e) {
        }
        return employees;
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

    public void deleteEmployee(String name) {
        String sql = "UPDATE [User] SET tbl_isActive = 'inactive' WHERE tbl_username = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteService(String name) {
        String sql = "DELETE FROM [dbo].[Service]  "
                + "WHERE [tbl_serviceName]=?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Appointment> getAppointmentsForDoctor(int controllerID) {
        List<Appointment> Appointments = new ArrayList<>();

        String sql = "SELECT \n"
                + "    a.tbl_appointmentID,\n"
                + "    CONVERT(char(11), a.tbl_date) AS date,\n"
                + "	a.tbl_time time,\n"
                + "	s.tbl_serviceName,\n"
                + "    u.tbl_displayName AS controller,\n"
                + "    u1.patient,\n"
                + "	a.tbl_note,\n"
                + "	a.tbl_status\n"
                + "FROM \n"
                + "    Appointment a\n"
                + "LEFT JOIN \n"
                + "    [User] u ON a.tbl_controllerID = u.tbl_userID\n"
                + "LEFT JOIN \n"
                + "    (\n"
                + "        SELECT \n"
                + "            a.tbl_appointmentID,\n"
                + "            a.tbl_time,\n"
                + "            u.tbl_displayName AS patient\n"
                + "        FROM \n"
                + "            Appointment a\n"
                + "        LEFT JOIN \n"
                + "            [User] u ON a.tbl_patientID = u.tbl_userID\n"
                + "    ) u1 ON u1.tbl_appointmentID = a.tbl_appointmentID\n"
                + "	left join Service s on s.tbl_serviceID =a.tbl_serviceID where a.tbl_controllerID = ?\n"
                + "	and a.tbl_status = 'accept' ORDER BY tbl_date";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setInt(1, controllerID);
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setTbl_appointmentID(resultSet.getInt("tbl_appointmentID"));
                Appointment.setDate(resultSet.getString("date"));
                Appointment.setTbl_time(resultSet.getString("time"));
                Appointment.setServiceName(resultSet.getString("tbl_serviceName"));
                Appointment.setController(resultSet.getString("controller"));
                Appointment.setPatient(resultSet.getString("patient"));
                Appointment.setNote(resultSet.getString("tbl_note"));
                Appointment.setStatus(resultSet.getString("tbl_status"));
                Appointments.add(Appointment);
            }
        } catch (SQLException e) {
        }
        return Appointments;
    }

    public List<Appointment> getAppointmentsForPatient(int patienID) {
        List<Appointment> Appointments = new ArrayList<>();

        String sql = "SELECT \n"
                + "    a.tbl_appointmentID,\n"
                + "    CONVERT(char(11), a.tbl_date) AS date,\n"
                + "	a.tbl_time time,\n"
                + "	s.tbl_serviceName,\n"
                + "    u.tbl_displayName AS controller,\n"
                + "    u1.patient,\n"
                + "	a.tbl_note,\n"
                + "	a.tbl_status\n"
                + "FROM \n"
                + "    Appointment a\n"
                + "LEFT JOIN \n"
                + "    [User] u ON a.tbl_controllerID = u.tbl_userID\n"
                + "LEFT JOIN \n"
                + "    (\n"
                + "        SELECT \n"
                + "            a.tbl_appointmentID,\n"
                + "            a.tbl_time,\n"
                + "            u.tbl_displayName AS patient\n"
                + "        FROM \n"
                + "            Appointment a\n"
                + "        LEFT JOIN \n"
                + "            [User] u ON a.tbl_patientID = u.tbl_userID\n"
                + "    ) u1 ON u1.tbl_appointmentID = a.tbl_appointmentID\n"
                + "	left join Service s on s.tbl_serviceID =a.tbl_serviceID where a.tbl_patientID = ?\n";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setInt(1, patienID);
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setTbl_appointmentID(resultSet.getInt("tbl_appointmentID"));
                Appointment.setDate(resultSet.getString("date"));
                Appointment.setTbl_time(resultSet.getString("time"));
                Appointment.setServiceName(resultSet.getString("tbl_serviceName"));
                Appointment.setController(resultSet.getString("controller"));
                Appointment.setPatient(resultSet.getString("patient"));
                Appointment.setNote(resultSet.getString("tbl_note"));
                Appointment.setStatus(resultSet.getString("tbl_status"));
                Appointments.add(Appointment);
            }
        } catch (SQLException e) {
        }
        return Appointments;
    }

    public List<Appointment> getNewAppointmentsForPatient(int patienID) {
        List<Appointment> Appointments = new ArrayList<>();

        String sql = "SELECT TOP 1\n"
                + "    a.tbl_appointmentID,\n"
                + "    CONVERT(char(11), a.tbl_date) AS date,\n"
                + "    a.tbl_time AS time,\n"
                + "    s.tbl_serviceName,\n"
                + "    u.tbl_displayName AS controller,\n"
                + "    u1.patient,\n"
                + "    a.tbl_note,\n"
                + "    a.tbl_status,\n"
                + "	a.tbl_revenue\n"
                + "FROM \n"
                + "    Appointment a\n"
                + "LEFT JOIN \n"
                + "    [User] u ON a.tbl_controllerID = u.tbl_userID\n"
                + "LEFT JOIN \n"
                + "    (\n"
                + "        SELECT \n"
                + "            a.tbl_appointmentID,\n"
                + "            a.tbl_time,\n"
                + "            u.tbl_displayName AS patient\n"
                + "        FROM \n"
                + "            Appointment a\n"
                + "        LEFT JOIN \n"
                + "            [User] u ON a.tbl_patientID = u.tbl_userID\n"
                + "    ) u1 ON u1.tbl_appointmentID = a.tbl_appointmentID\n"
                + "LEFT JOIN \n"
                + "    Service s ON s.tbl_serviceID = a.tbl_serviceID\n"
                + "WHERE \n"
                + "    a.tbl_patientID = ?\n"
                + "ORDER BY \n"
                + "    a.tbl_appointmentID DESC";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setInt(1, patienID);
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setTbl_appointmentID(resultSet.getInt("tbl_appointmentID"));
                Appointment.setDate(resultSet.getString("date"));
                Appointment.setTbl_time(resultSet.getString("time"));
                Appointment.setServiceName(resultSet.getString("tbl_serviceName"));
                Appointment.setController(resultSet.getString("controller"));
                Appointment.setPatient(resultSet.getString("patient"));
                Appointment.setNote(resultSet.getString("tbl_note"));
                Appointment.setStatus(resultSet.getString("tbl_status"));
                Appointment.setRevenue(Float.parseFloat(resultSet.getString("tbl_revenue")));
                Appointments.add(Appointment);
            }
        } catch (SQLException e) {
        }
        return Appointments;
    }

    public List<Appointment> getAppointmentsHistoryForDoctor(int controllerID) {
        List<Appointment> Appointments_H = new ArrayList<>();

        String sql = "SELECT \n"
                + "    a.tbl_appointmentID,\n"
                + "    CONVERT(char(11), a.tbl_date) AS date,\n"
                + "	a.tbl_time time,\n"
                + "	s.tbl_serviceName,\n"
                + "    u.tbl_displayName AS controller,\n"
                + "    u1.patient,\n"
                + "	a.tbl_note,\n"
                + "	a.tbl_status\n"
                + "FROM \n"
                + "    Appointment a\n"
                + "LEFT JOIN \n"
                + "    [User] u ON a.tbl_controllerID = u.tbl_userID\n"
                + "LEFT JOIN \n"
                + "    (\n"
                + "        SELECT \n"
                + "            a.tbl_appointmentID,\n"
                + "            a.tbl_time,\n"
                + "            u.tbl_displayName AS patient\n"
                + "        FROM \n"
                + "            Appointment a\n"
                + "        LEFT JOIN \n"
                + "            [User] u ON a.tbl_patientID = u.tbl_userID\n"
                + "    ) u1 ON u1.tbl_appointmentID = a.tbl_appointmentID\n"
                + "	left join Service s on s.tbl_serviceID =a.tbl_serviceID where a.tbl_controllerID = ?\n"
                + "	and a.tbl_status = 'completed'";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setInt(1, controllerID);
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment_H = new Appointment();
                Appointment_H.setTbl_appointmentID(resultSet.getInt("tbl_appointmentID"));
                Appointment_H.setDate(resultSet.getString("date"));
                Appointment_H.setTbl_time(resultSet.getString("time"));
                Appointment_H.setServiceName(resultSet.getString("tbl_serviceName"));
                Appointment_H.setController(resultSet.getString("controller"));
                Appointment_H.setPatient(resultSet.getString("patient"));
                Appointment_H.setNote(resultSet.getString("tbl_note"));
                Appointment_H.setStatus(resultSet.getString("tbl_status"));
                Appointments_H.add(Appointment_H);
            }
        } catch (SQLException e) {
        }
        return Appointments_H;
    }

//    public void addService(Service service) {
//        String sql = "INSERT INTO [service] "
//                + "(tbl_serviceName, "
//                + "tbl_description, "
//                + "tbl_price, "
//                + "tbl_time) "
//                + "VALUES "
//                + "(?, "
//                + "?, "
//                + "?, "
//                + "?)";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setString(1, service.getServiceName());
//            st.setString(2, service.getDescription());
//            st.setFloat(3, service.getPrice());
//            st.setInt(4, service.getTime());
//            st.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("SQL Exception: " + e);
//        }
//    }
//    public void updateService(Service service) {
//        String sql = "UPDATE [service] SET "
//                + "tbl_description = ?, "
//                + "tbl_price = ?, "
//                + "tbl_time = ?,"
//                + "tbl_serviceName = ? "
//                + "WHERE tbl_serviceID = ?";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setString(1, service.getDescription());
//            st.setFloat(2, service.getPrice());
//            st.setInt(3, service.getTime());
//            st.setString(4, service.getServiceName());
//            st.setInt(5, service.getServiceID());
//            st.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("SQL Exception: " + e);
//        }
//    }
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

    public void addAppointment(String time, int controllerID, int patientID, String note, String status, LocalDate date, int serviceID, float revenue) {
        String sql = "INSERT INTO [Appointment] (tbl_time, tbl_controllerID, tbl_patientID, tbl_note, tbl_status, tbl_date, tbl_serviceID,tbl_revenue) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, time);
            st.setInt(2, controllerID);
            st.setInt(3, patientID);
            st.setString(4, note);
            st.setString(5, status);  // Add this line
            st.setString(6, date.toString());
            st.setInt(7, serviceID);
            st.setFloat(8, revenue);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public boolean checkAppointmentAvailability(LocalDate date, String time, int controllerID) {
        String sql = "SELECT \n"
                + "    COUNT(*) \n"
                + "FROM \n"
                + "    [Appointment] \n"
                + "WHERE \n"
                + "    tbl_date = ? \n"
                + "    AND tbl_time = ? \n"
                + "    AND tbl_controllerID = ?\n"
                + "	and tbl_status != 'reject' and tbl_status != 'cancel'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, date.toString());
            st.setString(2, time);
            st.setInt(3, controllerID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return false;
    }

    public List<Appointment> getDoctorAppointmentsOnDate(int doctorID, LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM [Appointment] WHERE tbl_date = ? AND tbl_controllerID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, date.toString());
            st.setInt(2, doctorID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setTbl_appointmentID(rs.getInt("tbl_appointmentID"));
                appointment.setDate(rs.getString("tbl_date"));
                appointment.setTbl_time(rs.getString("tbl_time"));
                appointment.setServiceName(rs.getString("tbl_serviceID")); // Assuming serviceID as serviceName for simplicity
                appointment.setController(String.valueOf(doctorID));
                appointment.setPatient(String.valueOf(rs.getInt("tbl_patientID")));
                appointment.setNote(rs.getString("tbl_note"));
                appointment.setStatus(rs.getString("tbl_status"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return appointments;
    }

    public void saveResult(Result result) {
        String sql = "INSERT INTO Result "
                + "(tbl_createTime, "
                + "tbl_description, "
                + "tbl_appointmentID) "
                + "VALUES "
                + "(?, "
                + "?, "
                + "?)";
        try {
            PreparedStatement r = connection.prepareStatement(sql);
            r.setDate(1, (Date) result.getCreateTime());
            r.setString(2, result.getDescription());
            r.setInt(3, result.getAppointmentID());
            r.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public List<Result> getResults(int appointmentID) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT \n"
                + "    s.tbl_createTime, \n"
                + "    s.tbl_description, \n"
                + "    u.tbl_username AS patient, \n"
                + "    u1.doctor, \n"
                + "    s.tbl_resultID AS id,a.tbl_appointmentID as appointmentID\n"
                + "FROM \n"
                + "    Result s\n"
                + "LEFT JOIN \n"
                + "    Appointment a ON a.tbl_appointmentID = s.tbl_appointmentID\n"
                + "LEFT JOIN \n"
                + "    [User] u ON a.tbl_patientID = u.tbl_userID\n"
                + "LEFT JOIN \n"
                + "    (\n"
                + "        SELECT \n"
                + "            a.tbl_appointmentID, \n"
                + "            u.tbl_username AS doctor\n"
                + "        FROM \n"
                + "            [User] u\n"
                + "        LEFT JOIN \n"
                + "            Appointment a ON a.tbl_controllerID = u.tbl_userID\n"
                + "    ) u1 ON u1.tbl_appointmentID = s.tbl_appointmentID where a.tbl_appointmentID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setInt(1, appointmentID);
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Result result = new Result();
                result.setCreateTime(resultSet.getDate("tbl_createTime"));
                result.setDescription(resultSet.getString("tbl_description"));
                result.setPatient(resultSet.getString("patient"));
                result.setDoctor(resultSet.getString("doctor"));
                result.setResultID(resultSet.getInt("id"));
                result.setAppointmentID(resultSet.getInt("appointmentID"));

                results.add(result);
            }
        } catch (SQLException e) {
        }
        return results;
    }

    public void updateResult(int id, String description) {
        String sql = "UPDATE Result SET "
                + "tbl_description = ?  \n"
                + " WHERE tbl_appointmentID = ?;";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, description);
            pt.setInt(2, id);
            pt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public List<Appointment> getAppointmentsForNurse() {
        List<Appointment> Appointments_H = new ArrayList<>();

        String sql = "SELECT \n"
                + "    a.tbl_appointmentID,\n"
                + "    CONVERT(char(11), a.tbl_date) AS date,\n"
                + "	a.tbl_time time,\n"
                + "	s.tbl_serviceName,\n"
                + "    u.tbl_displayName AS controller,\n"
                + "    u1.patient,\n"
                + "	a.tbl_note,\n"
                + "	a.tbl_status\n"
                + "FROM \n"
                + "    Appointment a\n"
                + "LEFT JOIN \n"
                + "    [User] u ON a.tbl_controllerID = u.tbl_userID\n"
                + "LEFT JOIN \n"
                + "    (\n"
                + "        SELECT \n"
                + "            a.tbl_appointmentID,\n"
                + "            a.tbl_time,\n"
                + "            u.tbl_displayName AS patient\n"
                + "        FROM \n"
                + "            Appointment a\n"
                + "        LEFT JOIN \n"
                + "            [User] u ON a.tbl_patientID = u.tbl_userID\n"
                + "    ) u1 ON u1.tbl_appointmentID = a.tbl_appointmentID\n"
                + "	left join Service s on s.tbl_serviceID =a.tbl_serviceID where \n"
                + "	a.tbl_status = 'pending' ORDER BY tbl_date";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment_H = new Appointment();
                Appointment_H.setTbl_appointmentID(resultSet.getInt("tbl_appointmentID"));
                Appointment_H.setDate(resultSet.getString("date"));
                Appointment_H.setTbl_time(resultSet.getString("time"));
                Appointment_H.setServiceName(resultSet.getString("tbl_serviceName"));
                Appointment_H.setController(resultSet.getString("controller"));
                Appointment_H.setPatient(resultSet.getString("patient"));
                Appointment_H.setNote(resultSet.getString("tbl_note"));
                Appointment_H.setStatus(resultSet.getString("tbl_status"));
                Appointments_H.add(Appointment_H);
            }
        } catch (SQLException e) {
        }
        return Appointments_H;
    }

    public void updateAppointmentStatus(String appointmentID, String newStatus) {
        String sql = "UPDATE Appointment SET "
                + "tbl_status = ? "
                + "WHERE tbl_appointmentID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, newStatus);
            stm.setString(2, appointmentID);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public void saveMedicine(Medicine medicine) {
        String sql = "INSERT INTO Medicine (tbl_medicineName, tbl_instruction, tbl_resultID) VALUES (?, ?,(  select top 1 tbl_resultID from Result order by tbl_resultID desc))";
        try {
            PreparedStatement m = connection.prepareStatement(sql);
            m.setString(1, medicine.getTbl_medicineName());
            m.setString(2, medicine.getTbl_instruction());

            m.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public List<Medicine> getMedicine() {
        List<Medicine> medicineList = new ArrayList<>();

        String sql = "SELECT [tbl_medicineID], [tbl_medicineName], [tbl_instruction], [tbl_resultID] FROM [SWP391_Project_Prototype].[dbo].[Medicine]";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Medicine medicine = new Medicine();

                medicine.setTbl_medicineName(resultSet.getString("tbl_medicineName"));
                medicine.setTbl_resultID(resultSet.getInt("tbl_resultID"));
                medicine.setTbl_instruction(resultSet.getString("tbl_instruction"));
                medicineList.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicineList;
    }

    public void updateMedicine(int id, String tbl_medicineName, String tbl_instruction) {
        String sql = "UPDATE Medicine SET tbl_medicineName = ?, tbl_instruction = ? WHERE tbl_resultID = ?";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, tbl_medicineName);
            pt.setString(2, tbl_instruction);
            pt.setInt(3, id);
            pt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public List<Medicine> getMedicineByResultID(int resultID) {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM Medicine WHERE tbl_resultID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, resultID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setTbl_resultID(rs.getInt("tbl_resultID"));
                medicine.setTbl_medicineName(rs.getString("tbl_medicineName"));
                medicine.setTbl_instruction(rs.getString("tbl_instruction"));
                medicines.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    public static void main(String[] args) {
        // Step 2: Instantiate the DAO
        DAO dao = new DAO();
        List<User> patients = dao.getEmployees();
        // Step 3: Call the getPatients method
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            for (User patient : patients) {
                System.out.println("Username: " + patient.getUserID());
                System.out.println("Age: " + patient.getAge());
            }
        }
        // Step 4: Print the results

    }

    public float getRevenue(int serviceID) {
        List<Appointment> Appointments = new ArrayList<>();
        String sql = "select tbl_price from Service\n"
                + "where tbl_serviceID=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, serviceID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setRevenue(rs.getFloat("tbl_price"));
                return a.getRevenue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public void updateRevenue(int appointmentID, float revenue) {
        String sql = "UPDATE Appointment \n"
                + "SET tbl_revenue = ? \n"
                + "WHERE tbl_appointmentID = ?;\n"
                + "select * from Appointment";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setFloat(1, revenue);
            stm.setInt(2, appointmentID);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public float getTotalRevenueByTime(LocalDate date1, LocalDate date2) {
        List<Appointment> Appointments = new ArrayList<>();
        String sql = "select sum(tbl_revenue) TotalRevenue from Appointment a\n"
                + "where a.tbl_date>= ? and a.tbl_date<= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, date1.toString());
            ps.setString(2, date2.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setRevenue(rs.getFloat("TotalRevenue"));
                return a.getRevenue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Appointment> getRevenueByTime(LocalDate date1, LocalDate date2) {
        List<Appointment> Appointments = new ArrayList<>();

        String sql = "select tbl_date,u.tbl_displayName,sum(a.tbl_revenue) TotalRevenue from Appointment a \n"
                + "left join [User] u on u.tbl_userID=a.tbl_controllerID\n"
                + "where a.tbl_date>= ? and a.tbl_date<= ? \n"
                + "group by tbl_date,u.tbl_displayName\n"
                + "order by TotalRevenue desc";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, date1.toString());
            pt.setString(2, date2.toString());
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setDate(resultSet.getString("tbl_date"));
                Appointment.setController(resultSet.getString("tbl_displayName"));
                Appointment.setRevenue(resultSet.getFloat("TotalRevenue"));
                Appointments.add(Appointment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Appointments;
    }

    public float getTotalRevenueFor1Day(LocalDate date) {
        List<Appointment> Appointments = new ArrayList<>();
        String sql = "select sum(tbl_revenue) TotalRevenue from Appointment a\n"
                + "where a.tbl_date = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, date.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setRevenue(rs.getFloat("TotalRevenue"));
                return a.getRevenue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Appointment> getRevenueFor1Day(LocalDate date) {
        List<Appointment> Appointments = new ArrayList<>();

        String sql = "select u.tbl_displayName,sum(a.tbl_revenue) TotalRevenue from Appointment a \n"
                + "left join [User] u on u.tbl_userID=a.tbl_controllerID\n"
                + "where a.tbl_date=?\n"
                + "group by u.tbl_displayName\n"
                + "order by TotalRevenue desc";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, date.toString());
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setDate(date.toString());
                Appointment.setController(resultSet.getString("tbl_displayName"));
                Appointment.setRevenue(resultSet.getFloat("TotalRevenue"));
                Appointments.add(Appointment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Appointments;
    }

    public List<Appointment> getRevenueByTimeService(LocalDate date1, LocalDate date2) {
        List<Appointment> Appointments = new ArrayList<>();

        String sql = "select tbl_date,s.tbl_serviceName,sum(a.tbl_revenue) TotalRevenue from Appointment a \n"
                + "left join Service s on s.tbl_serviceID=a.tbl_serviceID\n"
                + "where a.tbl_date>=? and a.tbl_date<= ? \n"
                + "group by tbl_date,s.tbl_serviceName\n"
                + "order by tbl_date desc,TotalRevenue desc";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, date1.toString());
            pt.setString(2, date2.toString());
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setDate(resultSet.getString("tbl_date"));
                Appointment.setServiceName(resultSet.getString("tbl_serviceName"));
                Appointment.setRevenue(resultSet.getFloat("TotalRevenue"));
                Appointments.add(Appointment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Appointments;
    }

    public List<Appointment> getRevenueFor1DayService(LocalDate date) {
        List<Appointment> Appointments = new ArrayList<>();

        String sql = "select tbl_date,s.tbl_serviceName,sum(a.tbl_revenue) TotalRevenue from Appointment a \n"
                + "left join Service s on s.tbl_serviceID=a.tbl_serviceID\n"
                + "where a.tbl_date=?\n"
                + "group by tbl_date,s.tbl_serviceName\n"
                + "order by tbl_date desc,TotalRevenue desc";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, date.toString());
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setDate(date.toString());
                Appointment.setServiceName(resultSet.getString("tbl_serviceName"));
                Appointment.setRevenue(resultSet.getFloat("TotalRevenue"));
                Appointments.add(Appointment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Appointments;
    }

    public float getTotalRevenue() {
        List<Appointment> Appointments = new ArrayList<>();
        String sql = "select sum(tbl_revenue) TotalRevenue from Appointment a\n"
                + "where a.tbl_date <= GETDATE()";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setRevenue(rs.getFloat("TotalRevenue"));
                return a.getRevenue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Appointment> getRevenueWithDate() {
        List<Appointment> Appointments = new ArrayList<>();

        String sql = "select a.tbl_date,sum(a.tbl_revenue) TotalRevenue from Appointment a \n"
                + "left join [User] u on u.tbl_userID=a.tbl_controllerID\n"
                + "where a.tbl_date<=getDate()\n"
                + "group by a.tbl_date\n"
                + "order by a.tbl_date desc";
        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            ResultSet resultSet = pt.executeQuery();
            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setDate(resultSet.getString("tbl_date"));
                Appointment.setRevenue(resultSet.getFloat("TotalRevenue"));
                Appointments.add(Appointment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return Appointments;
    }

    public String getEmailByAppointmentID(int appointmentID) {
        String email = null;
        String sql = "select u.tbl_email from [User] u\n"
                + "where u.tbl_userID=(select a.tbl_patientID from Appointment a\n"
                + "where a.tbl_appointmentID=?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString("tbl_email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
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

    public void updateService(Service service) {
        String sql = "UPDATE [service] SET " + "tbl_description = ?, " + "tbl_price = ?, " + "tbl_time = ?," + "tbl_serviceName = ?, " + "tbl_image = ? " + "WHERE tbl_serviceID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, service.getDescription());
            st.setFloat(2, service.getPrice());
            st.setInt(3, service.getTime());
            st.setString(4, service.getServiceName());
            st.setString(5, service.getImage());
            st.setInt(6, service.getServiceID());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public void addService(Service service) {
        String sql = "INSERT INTO [service] (tbl_serviceName, tbl_description, tbl_price, tbl_time, tbl_image) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, service.getServiceName());
            st.setString(2, service.getDescription());
            st.setFloat(3, service.getPrice());
            st.setInt(4, service.getTime());
            st.setString(5, service.getImage());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

}
