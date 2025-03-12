<%-- 
    Document   : doctor
    Created on : May 31, 2024, 11:30:12 PM
    Author     : ntawo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Service" %>
<%@ page import="dal.DAO" %>
<%@ page import="java.util.List" %>

<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>
<!DOCTYPE html>
<html>
    <head>
        <title>DentCare - D_Homepage</title>
        <link href="css/style_doctor.css" rel="stylesheet">
        <script src="js/doctor.js?v=1.0"></script>
    </head> 
    <body>
        <div class="header">
            <img src="logo.png">
            Dental Clinic Management
        </div>
        <div id="sidebar" class="sidebar">
            <c:if test="${sessionScope.account==null}">
                <a href="login.jsp" class="btn btn-primary py-2 px-4 ms-3"><i class="ms-Icon" aria-hidden="true">🔑</i> <span>Login</span></a>                    
            </c:if> 
            <c:if test="${sessionScope.account!=null}">
                <a href="#" onclick="submitViewPatientForm()"><i class="ms-Icon" aria-hidden="true">👁</i> <span>View Patient</span></a>
                <c:if test="${sessionScope.account.role == 'nurse'}">
                    <a href="#" onclick="submitViewMedicalAppointmentListFormForNurse()"><i class="ms-Icon" aria-hidden="true">👁</i> <span>View Pending Medical Appointment List</span></a>                    
                </c:if> 
                <c:if test="${sessionScope.account.role == 'doctor'}">
                    <a href="#" onclick="submitViewMedicalAppointmentListForm()"><i class="ms-Icon" aria-hidden="true">👁</i> <span>View Medical Appointment List</span></a>
                    <a href="#" onclick="submitViewMedicalAppointmentHistoryListForm()"><i class="ms-Icon" aria-hidden="true">👁</i> <span>View Medical Appointment History List</span></a>

                </c:if>              
                <a href="profile.jsp" onclick="redirectToProfile()"><i class="ms-Icon" aria-hidden="true">👤</i> <span>Profile</span></a>
                <a href="changePassword.jsp" onclick="redirectToChangePassword()"><i class="ms-Icon" aria-hidden="true">🔒</i> <span>Change Password</span></a>
                <a href="logout" onclick="redirectToLogout()"><i class="ms-Icon" aria-hidden="true">🚪</i> <span>Logout</span></a>
            </c:if>
        </div>

        <div class="sidebar-toggle" onclick="toggleSidebar()">☰</div>

        <div class="content">
            <div class="dashboard">
                <h1>Dashboard</h1>
                <c:if test="${sessionScope.account.role == 'nurse'}">
                    <button onclick="toggleForm('add')">Add New Patient</button>
                </c:if> 


            </div>

            <p>Here you can manage your dental clinic tasks and patient information.</p>
            <%
             String msg = request.getParameter("msg");
            if("added".equals(msg)){
            %> 
            <h3 class="alert" style="color: green;">Patient add successfully!</h3>
            <%   }
            %>
            <%             
            if("error".equals(msg)){
            %> 
            <h3 class="alert" style="color: red;">Error: Email/Username already existed</h3>
            <%   }
            %>
            <%             
            if("edited".equals(msg)){
            %> 
            <h3 class="alert" style="color: green;">Patient edit successfully!</h3>
            <%   }
            %>

            <form id="viewPatientForm" action="viewpatient" method="get">
                <input type="submit" value="View Patient" style="display: none;">
            </form>

            <ul class="patient-list">
                <c:if test="${sessionScope.account!=null}">
                    <c:forEach var="patient" items="${patients}">
                        <li>
                            ${patient.displayName} - ${patient.username}
                            <label>
                                <button onclick="toggleForm('edit', '${patient.username}', '${patient.displayName}', '${patient.email}', '${patient.address}', '${patient.mobile}', '${patient.others}', '${patient.image}', '${patient.age}')">Edit</button>
                                <button onclick="toggleForm('view', '${patient.username}', '${patient.displayName}', '${patient.email}', '${patient.address}', '${patient.mobile}', '${patient.others}', '${patient.image}', '${patient.age}')">View Patient Detail</button>                               
                            </label>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>

            <form id="viewMedicalAppointmentForm" action="viewMedicalAppointment" method="get">
                <input type="hidden" name="controllerID" value="${sessionScope.account.userID}">
                <input type="submit" value="View Medical Appointment" style="display: none;">
            </form>
            <form id="viewMedicalAppointmentFormForNurse" action="viewMedicalAppointment" method="post">
                <input type="submit" value="View Medical Appointment" style="display: none;">
            </form>
            <%             
            if("appointment".equals(msg)){
            %> 
            <ul class="patient-list">
                <c:if test="${sessionScope.account!=null}">
                    <c:if test="${not empty sessionScope.statusMessage}">
                        <div class="success-message">
                            ${sessionScope.statusMessage}
                        </div>
                        <c:remove var="statusMessage" scope="session" />
                    </c:if>
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th width="10%">ID</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Service Name</th>
                                <th>Doctor/Nurse</th>
                                <th>Patient</th>
                                <th>Note</th>
                                <th>Status</th>
                                <th>Control</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${appointments}">
                                <tr>
                                    <td>${appointment.tbl_appointmentID}</td>
                                    <td>${appointment.getDate()}</td>
                                    <td>${appointment.tbl_time}</td>
                                    <td>${appointment.serviceName}</td>
                                    <td>${appointment.controller}</td>
                                    <td>${appointment.patient}</td>
                                    <td>${appointment.note}</td>
                                    <td>${appointment.status}</td>
                                    <c:if test="${sessionScope.account.role == 'nurse'}">                             
                                        <td>
                                            <div>
                                                <form action="updateAppointmentStatus" method="post">
                                                    <input type="hidden" name="appointmentID" value="${appointment.tbl_appointmentID}">
                                                    <input type="hidden" name="newStatus" value="accept">
                                                    <button type="submit" name="Accept">Accept</button>
                                                </form>
                                                <button type="button" onclick="showRejectModal('${appointment.tbl_appointmentID}')">Reject</button>
                                            </div>
                                        </td>
                                    </c:if>
                                    <c:if test="${sessionScope.account.role == 'doctor'}">
                                        <td>
                                            <input type="hidden" name="appointmentID" value="${appointment.tbl_appointmentID}">
                                            <input type="hidden" name="newStatus" value="completed">
                                            <button type="button" onclick="doAddResult('${appointment.tbl_appointmentID}', '${appointment.getDate()}', '${appointment.serviceName}', '${appointment.controller}')">Add Result</button>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>

                        <!-- Modal for Reject Reason -->
                        <div id="rejectModal" style="display:none;">
                            <form id="rejectForm" action="updateAppointmentStatus" method="post">
                                <input type="hidden" name="appointmentID" id="rejectAppointmentID">
                                <input type="hidden" name="newStatus" value="reject">
                                <label for="rejectReason">Reason for Rejection:</label>
                                <textarea name="rejectReason" id="rejectReason" required></textarea>
                                <button type="submit">Submit</button>
                            </form>
                        </div>

                        <script>
                            function showRejectModal(appointmentID) {
                                document.getElementById('rejectAppointmentID').value = appointmentID;
                                document.getElementById('rejectModal').style.display = 'block';
                            }
                        </script>
                    </table>
                </c:if>
            </ul>
            <%   }
            %>

            <form id="viewMedicalAppointmentHistoryForm" action="viewHistory" method="get">
                <input type="hidden" name="controllerID" value="${sessionScope.account.userID}">
                <input type="submit" value="View Medical Appointment History" style="display: none;">
            </form>
            <%             
            if("appointment_h".equals(msg)){
            %> 
            <ul class="patient-list">
                <c:if test="${sessionScope.account!=null}">
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th width="10%">No</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Service Name</th>
                                <th>Doctor/Nurse</th>
                                <th>Patient</th>
                                <th>Note</th>
                                <th>Status</th>
                                <th>Control</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${appointments_h}">
                            <form action="viewresult" method="get" >
                                <tr>
                                    <td>${appointment.tbl_appointmentID}</td>
                                    <td>${appointment.getDate()}</td>
                                    <td>${appointment.tbl_time}</td>
                                    <td>${appointment.serviceName}</td>
                                    <td>${appointment.controller}</td>
                                    <td>${appointment.patient}</td>
                                    <td>${appointment.note}</td>
                                    <td>${appointment.status}</td>
                                <label>
                                    <td>
                                        <button onclick="doViewResult('${appointment.tbl_appointmentID}')">Edit Result</button> 
                                    </td>
                                </label>
                                </tr>
                                <input type="hidden" name="tbl_appointmentID" value="${appointment.tbl_appointmentID}">

                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </ul>
            <%   }
            %>

        </div>     

        <div id="addPatientForm" class="form-container">
            <button class="close-btn" onclick="closeForm('addPatientForm')">X</button>
            <h2>Add Patient</h2>
            <form action="patient?action=add" method="post">
                <div class="form-field">
                    <label>Username:</label>
                    <input type="text" name="username" required>
                </div>
                <div class="form-field">
                    <label>Full Name:</label>
                    <input type="text" name="displayName" required>
                </div>
                <div class="form-field">
                    <label>Email:</label>
                    <input type="email" name="email" required>
                </div>
                <div class="form-field">
                    <label>Date Of Birth:</label>
                    <input type="date" name="dob" required>
                </div>
                <div class="form-field">
                    <label>Password:</label>
                    <input type="password" name="password" required>
                </div>
                <input type="submit" value="Add Patient">
            </form>
        </div>

        <div id="viewPatientDetailForm" class="form-container">
            <button class="close-btn" onclick="closeForm('viewPatientDetailForm')">X</button>
            <h2>View Patient Detail</h2>
            <form>
                <div class="form-field">
                    <label>Username:</label>
                    <input type="text" id="viewUsername" name="username" required readonly>
                </div>
                <div class="form-field">
                    <label>Full Name:</label>
                    <input type="text" id="viewDisplayName" name="displayName" required readonly>
                </div>
                <div class="form-field">
                    <label>Email:</label>
                    <input type="email" id="viewEmail" name="email" required readonly>
                </div>
                <div class="form-field">
                    <label>Address:</label>
                    <input type="text" id="viewAddress" name="address" required readonly>
                </div>
                <div class="form-field">
                    <label>Mobile:</label>
                    <input type="text" id="viewMobile" name="mobile" required readonly>
                </div>
                <div class="form-field">
                    <label>Others Information:</label>
                    <input type="text" id="viewOthers" name="others" required readonly>
                </div>
                <div class="form-field">

                    <input type="hidden" id="viewImage" name="image" required readonly>
                </div>
                <div class="form-field">
                    <label>Age:</label>
                    <input type="text" id="viewAge" name="age" required readonly>
                </div>
            </form>
        </div>


        <div id="editPatientForm" class="form-container">
            <button class="close-btn" onclick="closeForm('editPatientForm')">X</button>
            <h2>Edit Patient</h2>
            <form action="patient?action=edit" method="post">
                <div class="form-field">
                    <label>Username:</label>
                    <input type="text" id="editUsername" name="username" value="" required readonly>
                </div>
                <div class="form-field">
                    <label>Full Name:</label>
                    <input type="text" id="editDisplayName" name="displayName" value="" required>
                </div>
                <div class="form-field">
                    <label>Email:</label>
                    <input type="email" id="editEmail" name="email" value="" required readonly>
                </div>
                <div class="form-field">
                    <label>Address:</label>
                    <input type="text" id="editAddress" name="address" value="" required>
                </div>
                <div class="form-field">
                    <label>Mobile:</label>
                    <input type="text" id="editMobile" name="mobile" value="" required>
                </div>
                <div class="form-field">
                    <label>Other Information:</label>
                    <input type="text" id="editOthers" name="others" value="" required>
                </div>
                <div class="form-field">

                    <input type="hidden" id="editImage" name="image" value="" required>
                </div>
                <div class="form-field">
                    <label>Age:</label>
                    <input type="text" id="editAge" name="age" value="" required>
                </div>
                <input type="submit" value="Edit Patient">
            </form>
        </div>
    </body>
</html>
