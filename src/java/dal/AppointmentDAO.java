package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;

public class AppointmentDAO extends DBContext {

    public List<Appointment> getAppointmentsNeedConfirm(int controllerID) {
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
                + "	and a.tbl_status = 'validate'";
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

}
