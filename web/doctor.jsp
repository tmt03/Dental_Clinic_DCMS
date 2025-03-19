<%-- 
    Document   : doctor
    Created on : May 31, 2024, 11:30:12 PM
    Author     : ntawo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- Thiết lập header chống cache --%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>DentCare - Doctor Dashboard</title>
        <link href="${contextPath}/css/style_doctor.css" rel="stylesheet">
        <script src="${contextPath}/js/doctor.js?v=1.0"></script>
    </head>
    <body>
        <!-- Header -->
        <div class="header">
            <img src="${contextPath}/logo.png" alt="Logo">
            <span>Dental Clinic Management</span>
        </div>

        <!-- Sidebar -->
        <div id="sidebar" class="sidebar">
            <c:choose>
                <c:when test="${empty sessionScope.account}">
                    <a href="${contextPath}/login.jsp" class="btn btn-primary py-2 px-4 ms-3">
                        <i class="ms-Icon" aria-hidden="true">🔑</i> <span>Login</span>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="#" onclick="submitViewPatientForm()">
                        <i class="ms-Icon" aria-hidden="true">👁</i> <span>View Patient</span>
                    </a>
                    <c:if test="${sessionScope.account.role == 'nurse'}">
                        <a href="#" onclick="submitViewMedicalAppointmentListFormForNurse()">
                            <i class="ms-Icon" aria-hidden="true">👁</i> <span>View Pending Medical Appointment List</span>
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.account.role == 'doctor'}">
                        <a href="#" onclick="submitViewMedicalAppointmentListForm()">
                            <i class="ms-Icon" aria-hidden="true">👁</i> <span>View Medical Appointment List</span>
                        </a>
                        <a href="#" onclick="submitViewMedicalAppointmentNeedConfirmListForm()">
                            <i class="ms-Icon" aria-hidden="true">👁</i> <span>View Medical Appointment Need Confirm List</span>
                        </a>
                    </c:if>
                    <a href="${contextPath}/profile.jsp" onclick="redirectToProfile()">
                        <i class="ms-Icon" aria-hidden="true">👤</i> <span>Profile</span>
                    </a>
                    <a href="${contextPath}/changePassword.jsp" onclick="redirectToChangePassword()">
                        <i class="ms-Icon" aria-hidden="true">🔒</i> <span>Change Password</span>
                    </a>
                    <a href="${contextPath}/core?action=LOGOUT" onclick="redirectToLogout()">
                        <i class="ms-Icon" aria-hidden="true">🚪</i> <span>Logout</span>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Sidebar Toggle -->
        <div class="sidebar-toggle" onclick="toggleSidebar()">☰</div>

        <!-- Main Content -->
        <div class="content">
            <div class="dashboard">
                <h1>Dashboard</h1>
                <c:if test="${sessionScope.account.role == 'nurse'}">
                    <button onclick="toggleForm('add')">Add New Patient</button>
                </c:if>
            </div>

            <p>Here you can manage your dental clinic tasks and patient information.</p>

            <!-- Thông báo -->
            <c:if test="${not empty param.msg}">
                <c:choose>
                    <c:when test="${param.msg == 'added'}">
                        <h3 class="alert" style="color: green;">Patient added successfully!</h3>
                    </c:when>
                    <c:when test="${param.msg == 'error'}">
                        <h3 class="alert" style="color: red;">Error: Email/Username already existed</h3>
                    </c:when>
                    <c:when test="${param.msg == 'edited'}">
                        <h3 class="alert" style="color: green;">Patient edited successfully!</h3>
                    </c:when>
                </c:choose>
            </c:if>

            <!-- Danh sách bệnh nhân -->
            <c:if test="${param.msg == 'patients' and not empty sessionScope.account}">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Display Name</th>
                            <th>Username</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="patient" items="${patients}">
                            <tr>
                                <td>${patient.displayName}</td>
                                <td>${patient.username}</td>
                                <td>
                                    <button onclick="toggleForm('edit', '${patient.username}', '${patient.displayName}', '${patient.email}', '${patient.address}', '${patient.mobile}', '${patient.others}', '${patient.image}', '${patient.age}')">
                                        Edit
                                    </button>
                                    <button onclick="toggleForm('view', '${patient.username}', '${patient.displayName}', '${patient.email}', '${patient.address}', '${patient.mobile}', '${patient.others}', '${patient.image}', '${patient.age}')">
                                        View Patient Detail
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <!-- Danh sách cuộc hẹn -->
            <c:if test="${param.msg == 'appointment' and not empty sessionScope.account}">
                <c:if test="${not empty sessionScope.statusMessage}">
                    <div class="success-message">${sessionScope.statusMessage}</div>
                    <c:remove var="statusMessage" scope="session"/>
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
                                <td>${appointment.date}</td>
                                <td>${appointment.tbl_time}</td>
                                <td>${appointment.serviceName}</td>
                                <td>${appointment.controller}</td>
                                <td>${appointment.patient}</td>
                                <td>${appointment.note}</td>
                                <td>${appointment.status}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${sessionScope.account.role == 'nurse'}">
                                            <div>
                                                <form action="${contextPath}/core" method="post">
                                                    <input type="hidden" name="appointmentID" value="${appointment.tbl_appointmentID}">
                                                    <input type="hidden" name="action" value="NURSE_VALIDATE_APPOINTMENT">
                                                    <input type="hidden" name="newStatus" value="validate">
                                                    <button type="submit">Validate</button>
                                                </form>
                                                <button type="button" onclick="showRejectModal('${appointment.tbl_appointmentID}')">Reject</button>
                                            </div>
                                        </c:when>
                                        <c:when test="${sessionScope.account.role == 'doctor'}">
                                            <button type="button" onclick="doAddResult('${appointment.tbl_appointmentID}', '${appointment.date}', '${appointment.serviceName}', '${appointment.controller}')">
                                                Add Result
                                            </button>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Modal cho lý do từ chối -->
                <div id="rejectModal" style="display: none;">
                    <form id="rejectForm" action="${contextPath}/core" method="post">
                        <input type="hidden" name="action" value="REJECT_APPOINTMENT">
                        <input type="hidden" name="appointmentID" id="rejectAppointmentID">
                        <input type="hidden" name="newStatus" value="reject">
                        <label for="rejectReason">Reason for Rejection:</label>
                        <textarea name="rejectReason" id="rejectReason" required></textarea>
                        <button type="submit">Submit</button>
                        <button type="button" onclick="closeModal()">Cancel</button>
                    </form>
                </div>
            </c:if>

            <!-- Danh sách cuộc hẹn cần xác nhận -->
            <c:if test="${param.msg == 'appointmentneedconfirm' and not empty sessionScope.account}">
                <c:if test="${not empty sessionScope.statusMessage}">
                    <div class="success-message">${sessionScope.statusMessage}</div>
                    <c:remove var="statusMessage" scope="session"/>
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
                                <td>${appointment.date}</td>
                                <td>${appointment.tbl_time}</td>
                                <td>${appointment.serviceName}</td>
                                <td>${appointment.controller}</td>
                                <td>${appointment.patient}</td>
                                <td>${appointment.note}</td>
                                <td>${appointment.status}</td>
                                <td>
                                    <c:if test="${sessionScope.account.role == 'doctor'}">
                                        <div>
                                            <form action="${contextPath}/core" method="post">
                                                <input type="hidden" name="action" value="DOCTOR_APPROVE_APPOINTMENT">
                                                <input type="hidden" name="appointmentID" value="${appointment.tbl_appointmentID}">
                                                <input type="hidden" name="controllerID" value="${sessionScope.account.userID}">
                                                <input type="hidden" name="newStatus" value="accept">
                                                <button type="submit">Approve</button>
                                            </form>
                                            <button type="button" onclick="showRejectModal('${appointment.tbl_appointmentID}')">Reject</button>
                                        </div>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <!-- Modal cho lý do từ chối -->
                <div id="rejectModal" style="display: none;">
                    <form id="rejectForm" action="${contextPath}/core" method="post">
                        <input type="hidden" name="action" value="REJECT_APPOINTMENT">
                        <input type="hidden" name="appointmentID" id="rejectAppointmentID">
                        <input type="hidden" name="controllerID" value="${sessionScope.account.userID}">
                        <input type="hidden" name="newStatus" value="reject">
                        <label for="rejectReason">Reason for Rejection:</label>
                        <textarea name="rejectReason" id="rejectReason" required></textarea>
                        <button type="submit">Submit</button>
                        <button type="button" onclick="closeModal()">Cancel</button>
                    </form>
                </div>
            </c:if>

        <!-- Form ẩn để gửi yêu cầu -->
        <form id="viewPatientForm" action="${contextPath}/core" method="get">
            <input type="hidden" name="action" value="VIEW_PATIENT_LIST">
            <input type="submit" value="View Patient" style="display: none;">
        </form>
        <form id="viewMedicalAppointmentForm" action="${contextPath}/core" method="get">
            <input type="hidden" name="action" value="VIEW_APPOINTMENT_LIST">
            <input type="hidden" name="controllerID" value="${sessionScope.account.userID}">
            <input type="submit" value="View Medical Appointment" style="display: none;">
        </form>
        <form id="viewMedicalAppointmentFormForNurse" action="${contextPath}/core" method="post">
            <input type="hidden" name="action" value="VIEW_APPOINTMENT_NEED_VALIDATE">
            <input type="submit" value="View Medical Appointment" style="display: none;">
        </form>
        <form id="viewMedicalAppointmentNeedConfirmForm" action="${contextPath}/core" method="get">
            <input type="hidden" name="action" value="VIEW_APPOINTMENT_NEED_CONFIRM">
            <input type="hidden" name="controllerID" value="${sessionScope.account.userID}">
            <input type="submit" value="View Medical Appointment Need Confirm" style="display: none;">
        </form>

        <!-- Form thêm bệnh nhân -->
        <div id="addPatientForm" class="form-container">
            <button class="close-btn" onclick="closeForm('addPatientForm')">X</button>
            <h2>Add Patient</h2>
            <form action="${contextPath}/patient?action=add" method="post">
                <div class="form-field">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-field">
                    <label for="displayName">Full Name:</label>
                    <input type="text" id="displayName" name="displayName" required>
                </div>
                <div class="form-field">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-field">
                    <label for="dob">Date Of Birth:</label>
                    <input type="date" id="dob" name="dob" required>
                </div>
                <div class="form-field">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <input type="submit" value="Add Patient">
            </form>
        </div>

        <!-- Form xem chi tiết bệnh nhân -->
        <div id="viewPatientDetailForm" class="form-container">
            <button class="close-btn" onclick="closeForm('viewPatientDetailForm')">X</button>
            <h2>View Patient Detail</h2>
            <form action="${contextPath}/patient?action=view" method="post">
                <div class="form-field">
                    <label for="viewUsername">Username:</label>
                    <input type="text" id="viewUsername" name="username" readonly required>
                </div>
                <div class="form-field">
                    <label for="viewDisplayName">Full Name:</label>
                    <input type="text" id="viewDisplayName" name="displayName" readonly required>
                </div>
                <div class="form-field">
                    <label for="viewEmail">Email:</label>
                    <input type="email" id="viewEmail" name="email" readonly required>
                </div>
                <div class="form-field">
                    <label for="viewAddress">Address:</label>
                    <input type="text" id="viewAddress" name="address" readonly required>
                </div>
                <div class="form-field">
                    <label for="viewMobile">Mobile:</label>
                    <input type="text" id="viewMobile" name="mobile" readonly required>
                </div>
                <div class="form-field">
                    <label for="viewOthers">Others Information:</label>
                    <input type="text" id="viewOthers" name="others" readonly required>
                </div>
                <div class="form-field">
                    <input type="hidden" id="viewImage" name="image" readonly required>
                </div>
                <div class="form-field">
                    <label for="viewAge">Age:</label>
                    <input type="text" id="viewAge" name="age" readonly required>
                </div>
            </form>
        </div>

        <!-- Form chỉnh sửa bệnh nhân -->
        <div id="editPatientForm" class="form-container">
            <button class="close-btn" onclick="closeForm('editPatientForm')">X</button>
            <h2>Edit Patient</h2>
            <form action="${contextPath}/patient?action=edit" method="post">
                <div class="form-field">
                    <label for="editUsername">Username:</label>
                    <input type="text" id="editUsername" name="username" value="" readonly required>
                </div>
                <div class="form-field">
                    <label for="editDisplayName">Full Name:</label>
                    <input type="text" id="editDisplayName" name="displayName" value="" required>
                </div>
                <div class="form-field">
                    <label for="editEmail">Email:</label>
                    <input type="email" id="editEmail" name="email" value="" readonly required>
                </div>
                <div class="form-field">
                    <label for="editAddress">Address:</label>
                    <input type="text" id="editAddress" name="address" value="" required>
                </div>
                <div class="form-field">
                    <label for="editMobile">Mobile:</label>
                    <input type="text" id="editMobile" name="mobile" value="" required>
                </div>
                <div class="form-field">
                    <label for="editOthers">Other Information:</label>
                    <input type="text" id="editOthers" name="others" value="" required>
                </div>
                <div class="form-field">
                    <input type="hidden" id="editImage" name="image" value="" required>
                </div>
                <div class="form-field">
                    <label for="editAge">Age:</label>
                    <input type="text" id="editAge" name="age" value="" required>
                </div>
                <input type="submit" value="Edit Patient">
            </form>
        </div>
    </body>
</html>