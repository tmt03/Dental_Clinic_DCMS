<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Service" %>
<%@ page import="dal.*" %>
<%@ page import="java.util.List" %>

<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>DentCare - Dental Clinic Website Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free HTML Templates" name="keywords">
        <meta content="Free HTML Templates" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500;600;700&family=Open+Sans:wght@400;600&display=swap" rel="stylesheet"> 

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />
        <link href="lib/twentytwenty/twentytwenty.css" rel="stylesheet" />

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
                    <a href="home1.jsp" class="nav-item nav-link active">Home</a>
                    <a href="about.jsp" class="nav-item nav-link">About</a>
                    <a href="service.jsp" class="nav-item nav-link">Service</a>
                    
                    <a href="contact.jsp" class="nav-item nav-link">Contact</a>
                </div>
                <!--                <button type="button" class="btn text-dark" data-bs-toggle="modal" data-bs-target="#searchModal"><i class="fa fa-search"></i></button>-->

                <c:if test="${sessionScope.account==null}">
                    <a href="login.jsp" class="btn btn-primary py-2 px-4 ms-3">Login</a>
                    <a href="register.jsp" class="btn btn-primary py-2 px-4 ms-3">Register</a>
                </c:if> 
                <c:if test="${sessionScope.account!=null}">
                    <a href="appointment" class="btn btn-primary py-2 px-4 ms-3">Appointment</a>
                    <a href="#" onclick="viewPatientAppointment(${sessionScope.account.userID})" class="btn btn-primary py-2 px-4 ms-3">Your Appointment</a>
                    <a href="profile.jsp" class="btn btn-primary py-2 px-4 ms-3">Profile</a>
                    <a href="changePassword.jsp" class="btn btn-primary py-2 px-4 ms-3">Change Password</a>
                    <a href="logout" class="btn btn-primary py-2 px-4 ms-3">Logout</a>
                </c:if>
            </div>
        </nav>
        <!-- Navbar End -->

        <!-- Full Screen Search Start -->
        <div class="modal fade" id="searchModal" tabindex="-1">
            <div class="modal-dialog modal-fullscreen">
                <div class="modal-content" style="background: rgba(9, 30, 62, .7);">
                    <div class="modal-header border-0">
                        <button type="button" class="btn bg-white btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body d-flex align-items-center justify-content-center">
                        <div class="input-group" style="max-width: 600px;">
                            <input type="text" class="form-control bg-transparent border-primary p-3" placeholder="Type search keyword">
                            <button class="btn btn-primary px-4"><i class="bi bi-search"></i></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Full Screen Search End -->

        <!-- Hero Start -->
        <div class="container-fluid bg-primary py-5 hero-header mb-5">
            <div class="row py-3">
                <div class="col-12 text-center">
                    <h1 class="display-3 text-white animated zoomIn">Services</h1>
                    <a href="" class="h4 text-white">Home</a>
                    <i class="far fa-circle text-white px-2"></i>
                    <a href="" class="h4 text-white">Services</a>
                </div>
            </div>
        </div>
        <!-- Hero End -->

        <!-- Services Start -->
        <div class="container-fluid py-5">
            <div class="container">
                <div class="row g-5">                   
                    <%
                        ServiceDAO dao = new ServiceDAO();
                        List<Service> services = dao.getService();
                        for (Service service : services) {
                    %>
                    <div class="col-lg-4 wow slideInUp" data-wow-delay="0.3s">
                        <div class="service-item">
                            <div class="position-relative rounded-top" style="z-index: 1;">
                                <img class="img-fluid rounded-top w-100" src="<%= service.getImage() %>" alt="">
                            </div>
                            <div class="service-text position-relative bg-light text-center rounded-bottom p-4 pt-5">
                                <h4 class="mb-2"><%= service.getServiceName() %></h4>
                                <p class="text-primary mb-0"><%= service.getDescription() %></p>
                                <p class="text-primary mb-0"><%= service.getPrice() %></p>
                            </div>
                        </div>
                    </div>
                    <% } %>

                </div>
            </div>
        </div>
        <!-- Services End -->

        <!-- Newsletter -->
        <jsp:include page="components/newLetter.jsp" />

        <!-- Footer -->
        <jsp:include page="components/footer.jsp" />


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square rounded back-to-top"><i class="bi bi-arrow-up"></i></a>


        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="lib/tempusdominus/js/moment.min.js"></script>
        <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="lib/twentytwenty/jquery.event.move.js"></script>
        <script src="lib/twentytwenty/jquery.twentytwenty.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>

</html>