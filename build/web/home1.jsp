<%-- 
    Document   : appointment
    Created on : Jun 30, 2024, 3:52:24 PM
    Author     : ASUS
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>DentCare - Make an Appointment</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="keywords" content="dental clinic, appointment, DentCare">
        <meta name="description" content="Book an appointment with DentCare, a certified and award-winning dental clinic.">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;600&display=swap" rel="stylesheet">
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Template Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <!-- Spinner -->
        <jsp:include page="components/spinner.jsp" />
        
        <!-- Navbar Start -->
        <nav class="navbar navbar-expand-lg bg-white navbar-light shadow-sm px-5 py-3 py-lg-0">
            <a href="home1.jsp" class="navbar-brand p-0">
                <h1 class="m-0 text-primary"><i class="fa fa-tooth me-2"></i>DCMS</h1>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <div class="navbar-nav ms-auto py-0">
                    <a href="home1.jsp" class="nav-item nav-link">Home</a>
                    <a href="about.jsp" class="nav-item nav-link">About</a>
                    <a href="service.jsp" class="nav-item nav-link">Service</a>
                    <a href="contact.jsp" class="nav-item nav-link">Contact</a>
                </div>
                <c:choose>
                    <c:when test="${empty sessionScope.account}">
                        <a href="login.jsp" class="btn btn-primary py-2 px-4 ms-3">Login</a>
                        <a href="register.jsp" class="btn btn-primary py-2 px-4 ms-3">Register</a>
                        <a href="core?action=GUEST_BOOK_APPOINTMENT" class="btn btn-primary py-2 px-4 ms-3">Book as Guest</a>
                    </c:when>
                    <c:otherwise>
                        <a href="core?action=VIEW_BOOK_APPOINTMENT_FORM" class="btn btn-primary py-2 px-4 ms-3">Appointment</a>
                        <a href="#" onclick="viewPatientAppointment(${sessionScope.account.userID})" class="btn btn-primary py-2 px-4 ms-3 active">Your Appointment</a>
                        <a href="profile.jsp" class="btn btn-primary py-2 px-4 ms-3">Profile</a>
                        <a href="changePassword.jsp" class="btn btn-primary py-2 px-4 ms-3">Change Password</a>
                        <a href="core?action=LOGOUT" class="btn btn-primary py-2 px-4 ms-3">Logout</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
        <!-- Navbar End -->

        <!-- Hero -->
        <jsp:include page="components/hero.jsp" />  

        <!-- Newsletter -->
        <jsp:include page="components/newLetter.jsp" />

        <!-- Footer  -->
        <jsp:include page="components/footer.jsp" />

        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square rounded back-to-top"><i class="bi bi-arrow-up"></i></a>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>