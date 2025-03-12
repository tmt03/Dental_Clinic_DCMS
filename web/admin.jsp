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
        <title>DentCare - A_Homepage</title>
        <link href="css/style_admin.css" rel="stylesheet">
        <script src="js/admin.js?v=1.0"></script>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome CSS -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <style>
            .header a {
                color: inherit; /* Giữ nguyên màu văn bản hiện tại */
                text-decoration: none; /* Loại bỏ gạch chân */
            }
            .header a:hover {
                color: inherit; /* Giữ nguyên màu văn bản hiện tại khi hover */
            }
        </style>
    </head>
    <body>
        <div class="header">
            <a href="home1.jsp">

                Dental Clinic Management
            </a>
        </div>

        <div id="sidebar" class="sidebar">
            <c:if test="${sessionScope.account == null}">
                <a href="login.jsp" class="btn btn-primary py-2 px-4 ms-3">
                    <i class="ms-Icon" aria-hidden="true">🔑</i> <span>Login</span>
                </a>                    
            </c:if> 
            <c:if test="${sessionScope.account != null}">
                <a href="#" onclick="submitViewEmployeeForm()"><i class="ms-Icon" aria-hidden="true">👁</i> <span>View Employee</span></a>
                <a href="#" onclick="submitViewServiceForm()"><i class="ms-Icon" aria-hidden="true">👁</i> <span>View Service</span></a>
                <a href="#" onclick="submitViewRevenueForm()"><i class="ms-Icon" aria-hidden="true">👁</i> <span>View Revenue</span></a>
                <a href="profile.jsp" onclick="redirectToProfile()"><i class="ms-Icon" aria-hidden="true">👤</i> <span>Profile</span></a>
                <a href="changePassword.jsp" onclick="redirectToChangePassword()"><i class="ms-Icon" aria-hidden="true">🔒</i> <span>Change Password</span></a>
                <a href="logout" onclick="redirectToLogout()"><i class="ms-Icon" aria-hidden="true">🚪</i> <span>Logout</span></a>
            </c:if>
        </div>

        <div class="sidebar-toggle" onclick="toggleSidebar()">☰</div>

        <div class="content">
            <div class="dashboard">
                <h1>Dashboard</h1>
                <c:if test="${sessionScope.account != null}">
                    <button onclick="toggleForm('add')">Add New Employee</button>
                </c:if>
                <c:if test="${sessionScope.account != null}">
                    <button onclick="toggleForm1('addservice')">Add New Service</button>
                </c:if>
            </div>
            <p>Here you can manage your dental clinic tasks and Employee/Service information.</p>
            <%
            String msg = request.getParameter("msg");
            if ("added".equals(msg)) {
            %> 
            <h3 class="alert" style="color: green;">Employee add successfully!</h3>
            <% } %>
            <%
            if ("error".equals(msg)) {
            %> 
            <h3 class="alert" style="color: red;">Error: Email/Username already existed</h3>
            <% } %>
            <%
            if ("edited".equals(msg)) {
            %> 
            <h3 class="alert" style="color: green;">Employee edit successfully!</h3>
            <% } %>
            <%
            if ("addedservice".equals(msg)) {
            %> 
            <h3 class="alert" style="color: green;">Service add successfully!</h3>
            <% } %>
            <%
            if ("errorservice".equals(msg)) {
            %> 
            <h3 class="alert" style="color: red;">Error: Service already existed</h3>
            <% } %>
            <%
            if ("editedservice".equals(msg)) {
            %> 
            <h3 class="alert" style="color: green;">Service edit successfully!</h3>
            <% } %>

            <form id="viewEmployeeForm" action="viewEmployee" method="get">
                <input type="submit" value="View Employee" style="display: none;">
            </form>
            <form id="viewServiceForm" action="service" method="get">
                <input type="submit" value="View Service" style="display: none;">
            </form>
            <form id="viewRevenueForm" action="viewRevenue" method="get">
                <input type="submit" value="View Revenue" style="display: none;">
            </form>
            <ul id="resultContainer1" class="Employee-list">
                <c:if test="${sessionScope.account != null}">
                    <c:forEach var="Employee" items="${Employees}">
                        <li>
                            ${Employee.displayName} - ${Employee.username}
                            <label>
                                <button onclick="toggleForm('view', '${Employee.username}', '${Employee.displayName}', '${Employee.email}', '${Employee.address}', '${Employee.mobile}', '${Employee.others}', '${Employee.image}', '${Employee.age}')">View Employee Detail</button>
                                <button onclick="doDelete('${Employee.username}')">Delete</button>
                            </label>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
            <ul id="resultContainer2" class="Service-list">
                <c:if test="${sessionScope.account != null}">
                    <c:forEach var="Service" items="${Services}">
                        <li>
                            ${Service.serviceName}
                            <label>
                                <button onclick="showServiceDetailForm('${Service.serviceID}', '${Service.serviceName}', '${Service.description}', '${Service.price}', '${Service.time}', '${Service.image}')">View Service Detail</button>
                                <button onclick="doDeleteService('${Service.serviceName}')">Delete</button>
                            </label>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
            <%             
   if("viewTotalRevenue".equals(msg)){
            %> 
            <form action="searchFor1Day" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchFor1DayService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTime" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>   
            <form action="searchByTimeService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>         


            <ul id="resultContainer2" class="Service-list">
                <c:if test="${sessionScope.account!=null}">
                    <h1>Total Revenue: ${requestScope.totalRevenue}</h1>
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Revenue</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${DayAndRevenue}">
                                <tr>
                                    <td>${appointment.getDate()}</td>
                                    <td>${appointment.getRevenue()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </ul>
            <%   }
            %>
            <%             
            if("searchFor1Day".equals(msg)){
            %> 
            <form action="searchFor1Day" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchFor1DayService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTime" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTimeService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <ul id="resultContainer2" class="Service-list">
                <c:if test="${sessionScope.account!=null}">
                    <h1>Revenue: ${requestScope.totalRevenue}</h1>
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Doctor</th>
                                <th>Revenue</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${DayAndRevenue}">
                                <tr>
                                    <td>${appointment.getDate()}</td>
                                    <td>${appointment.getController()}</td>
                                    <td>${appointment.getRevenue()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </ul>
            <%   }
            %>
            <%             
           if("searchByTime".equals(msg)){
            %> 
            <form action="searchFor1Day" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchFor1DayService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTime" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTimeService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <ul id="resultContainer2" class="Service-list">
                <c:if test="${sessionScope.account!=null}">
                    <h1>Revenue: ${requestScope.totalRevenue}</h1>
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Doctor</th>
                                <th>Revenue</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${DayAndRevenue}">
                                <tr>
                                    <td>${appointment.getDate()}</td>
                                    <td>${appointment.getController()}</td>
                                    <td>${appointment.getRevenue()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </ul>
            <%   }
            %>
            <%             
           if("searchByTimeService".equals(msg)){
            %> 
            <form action="searchFor1Day" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchFor1DayService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTime" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTimeService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <ul id="resultContainer2" class="Service-list">
                <c:if test="${sessionScope.account!=null}">
                    <h1>Revenue: ${requestScope.totalRevenue}</h1>
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Service</th>
                                <th>Revenue</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${DayAndRevenue}">
                                <tr>
                                    <td>${appointment.getDate()}</td>
                                    <td>${appointment.getServiceName()}</td>
                                    <td>${appointment.getRevenue()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </ul>
            <%   }
            %>
            <%             
           if("searchFor1DayService".equals(msg)){
            %> 
            <form action="searchFor1Day" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchFor1DayService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue for 1 day: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTime" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Doctors revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <form action="searchByTimeService" method="get">
                <div class="date" id="date" data-target-input="nearest">
                    Search Services revenue from: <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date1" id="date1" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    to : <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date2" id="date2" required max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                    <button type="submit" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </form>
            <ul id="resultContainer2" class="Service-list">
                <c:if test="${sessionScope.account!=null}">
                    <h1>Revenue: ${requestScope.totalRevenue}</h1>
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Service</th>
                                <th>Revenue</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${DayAndRevenue}">
                                <tr>
                                    <td>${appointment.getDate()}</td>
                                    <td>${appointment.getServiceName()}</td>
                                    <td>${appointment.getRevenue()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </ul>
            <%   }
            %>
        </div>     

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <div id="addEmployeeForm" class="form-container">
            <button class="close-btn" onclick="closeForm('addEmployeeForm')">X</button>
            <h2>Add Employee</h2>
            <form action="addNewEmp?action=add" method="post">
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
                    <label>Dob:</label>
                    <input type="date" name="dob" required>
                </div>
                <div class="form-field">
                    <label>Password:</label>
                    <input type="password" name="password" required>
                </div>
                <div class="form-field">
                    <label>Role:</label>
                    <select name="role" required>
                        <option value="doctor">Doctor</option>
                        <option value="nurse">Nurse</option>
                    </select>
                </div>
                <input type="submit" value="Add Employee">
            </form>
        </div>

        <div id="viewEmployeeDetailForm" class="form-container">
            <button class="close-btn" onclick="closeForm('viewEmployeeDetailForm')">X</button>
            <h2>View Employee Detail</h2>
            <form action="Employee?action=edit" method="post" id="viewEditEmployeeForm" enctype="multipart/form-data">
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
                    <label>Other Information:</label>
                    <input type="text" id="viewOthers" name="others" required readonly>
                </div>
                <div class="form-field">

                    <input type="hidden" id="editImage" name="image" value="" required>
                </div>
                <div class="form-field">
                    <label>Age:</label>
                    <input type="text" id="viewAge" name="age" required readonly>
                </div>
                <button type="button" id="editButton" onclick="enableEditing('viewEmployeeDetailForm')">Edit</button>
                <button type="submit" id="saveButton" style="display: none;">Save</button>
            </form>
        </div>

        <div id="editEmployeeForm" class="form-container">
            <button class="close-btn" onclick="closeForm('editEmployeeForm')">X</button>
            <h2>Edit Employee</h2>
            <form action="Employee?action=edit" method="post" enctype="multipart/form-data">
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
                    <input type="text" id="editOthers" name="others" required>
                </div>
                <div class="form-field">

                    <input type="hidden" id="editImage" name="image" value="" required>
                </div>
                <div class="form-field">
                    <label>Age:</label>
                    <input type="text" id="editAge" name="age" value="" required readonly>
                </div>
                <input type="submit" value="Edit Employee">
            </form>
        </div>

        <form id="viewEmployeeDetailHiddenForm" action="viewEmployee" method="get" style="display: none;"></form>

        <div id="addServiceForm" class="form-container">
            <button class="close-btn" onclick="closeForm('addServiceForm')">X</button>
            <h2>Add Service</h2>
            <form action="service" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="addservice">
                <div class="form-field">
                    <label>Service Name:</label>
                    <input type="text" name="serviceName" required>
                </div>
                <div class="form-field">
                    <label>Description:</label>
                    <input type="text" name="description" required>
                </div>
                <div class="form-field">
                    <label>Price:</label>
                    <input type="number" step="0.01" name="price" required>
                </div>
                <div class="form-field">
                    <label>Duration:</label>
                    <input type="number" name="time" required>
                </div>
                <div class="form-field">
                    <label>Image:</label>
                    <input type="file" name="imageFile" required>
                </div>
                <input type="submit" value="Add Service">
            </form>
        </div>


        <div id="viewServiceDetailForm" class="form-container">
            <button class="close-btn" onclick="closeForm('viewServiceDetailForm')">X</button>
            <h2>View Service Detail</h2>
            <form action="service?action=editservice" method="post" id="viewEditServiceForm" enctype="multipart/form-data">
                <div class="form-field">
                    <input type="hidden" id="viewServiceID" name="serviceID" required readonly>
                </div>
                <div class="form-field">
                    <label>Service Name:</label>
                    <input type="text" id="viewServiceName" name="serviceName" required readonly>
                </div>
                <div class="form-field">
                    <label>Description:</label>
                    <input type="text" id="viewDescription" name="description" required readonly>
                </div>
                <div class="form-field">
                    <label>Price:</label>
                    <input type="text" id="viewPrice" name="price" required readonly>
                </div>
                <div class="form-field">
                    <label>Duration:</label>
                    <input type="text" id="viewTime" name="time" required readonly>
                </div>
                <div class="form-field">
                    <label>Image:</label>
                    <img id="viewServiceImage" src="" width="100" height="100" alt="Service Image"/>
                    <input type="file" name="imageFile" id="serviceImageFile">
                </div>
                <button type="button" id="editButtonService" onclick="enableEditing('viewServiceDetailForm')">Edit</button>
                <button type="submit" id="saveButtonService" style="display: none;">Save</button>
            </form>
        </div>

        <script>
            function showServiceDetailForm(serviceID, serviceName, description, price, time, image) {
                const viewServiceForm = document.getElementById('viewServiceDetailForm');

                document.getElementById('viewServiceID').value = serviceID;
                document.getElementById('viewServiceName').value = serviceName;
                document.getElementById('viewDescription').value = description;
                document.getElementById('viewPrice').value = price;
                document.getElementById('viewTime').value = time;
                document.getElementById('viewServiceImage').src = image;

                viewServiceForm.classList.add('active');
            }

            function closeForm(formId) {
                document.getElementById(formId).classList.remove('active');
            }
        </script>

        <style>
            .form-container {
                display: none;
            }

            .form-container.active {
                display: block;
            }
        </style>
    </body>
</html>