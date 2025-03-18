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
    <!-- Spinner Start -->
    <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-grow text-primary m-1" role="status">
            <span class="sr-only">Loading...</span>
        </div>
        <div class="spinner-grow text-dark m-1" role="status">
            <span class="sr-only">Loading...</span>
        </div>
        <div class="spinner-grow text-secondary m-1" role="status">
            <span class="sr-only">Loading...</span>
        </div>
    </div>
    <!-- Spinner End -->

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
                </c:when>
                <c:otherwise>
                    <a href="appointment" class="btn btn-primary py-2 px-4 ms-3 active">Appointment</a>
                    <a href="#" onclick="viewPatientAppointment(${sessionScope.account.userID})" class="btn btn-primary py-2 px-4 ms-3 active">Your Appointment</a>
                    <a href="profile.jsp" class="btn btn-primary py-2 px-4 ms-3">Profile</a>
                    <a href="changePassword.jsp" class="btn btn-primary py-2 px-4 ms-3">Change Password</a>
                    <a href="logout" class="btn btn-primary py-2 px-4 ms-3">Logout</a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
    <!-- Navbar End -->

    <!-- Hero Start -->
    <div class="container-fluid bg-primary py-5 hero-header mb-5">
        <div class="row py-3">
            <div class="col-12 text-center">
                <h1 class="display-3 text-white animated zoomIn">Appointment</h1>
                <a href="home1.jsp" class="h4 text-white">Home</a>
                <i class="far fa-circle text-white px-2"></i>
                <a href="appointment" class="h4 text-white">Appointment</a>
            </div>
        </div>
    </div>
    <!-- Hero End -->

    <!-- Appointment Start -->
    <div class="container-fluid bg-primary bg-appointment mb-5 wow fadeInUp" data-wow-delay="0.1s" style="margin-top: 90px;">
        <div class="container">
            <div class="row gx-5">
                <div class="col-lg-6 py-5">
                    <div class="py-5">
                        <h1 class="display-5 text-white mb-4">We Are A Certified and Award Winning Dental Clinic You Can Trust</h1>
                        <p class="text-white mb-0">Eirmod sed tempor lorem ut dolores. Aliquyam sit sadipscing kasd ipsum. Dolor ea et dolore et at sea ea at dolor, justo ipsum duo rebum sea invidunt voluptua. Eos vero eos vero ea et dolore eirmod et. Dolores diam duo invidunt lorem. Elitr ut dolores magna sit. Sea dolore sanctus sed et. Takimata takimata sanctus sed.</p>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="appointment-form h-100 d-flex flex-column justify-content-center text-center p-5 wow zoomIn" data-wow-delay="0.6s">
                        <h1 class="text-white mb-4">Make Appointment</h1>
                        <form action="appointment" method="post">
                            <div class="row g-3">
                                <input type="hidden" name="patient" id="patient" value="${sessionScope.account.userID}" readonly>
                                <div class="col-12 col-sm-6">
                                    <select class="form-select bg-light border-0" style="height: 55px;" id="service" name="service" required>
                                        <option value="" disabled selected>Select A Service</option>
                                        <c:forEach items="${services}" var="service">
                                            <option value="${service.serviceID}">${service.serviceName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-12 col-sm-6">
                                    <select class="form-select bg-light border-0" style="height: 55px;" id="doctor" name="doctor" required>
                                        <option value="" disabled selected>Select Doctor</option>
                                        <c:forEach items="${doctors}" var="doctor">
                                            <option value="${doctor.userID}">${doctor.displayName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-12 col-sm-6">
                                    <div class="date" id="date" data-target-input="nearest">
                                        <input type="date" class="form-control bg-light border-0 datetimepicker-input" placeholder="Appointment Date" style="height: 55px;" name="date" id="date" required min="${currentDate}">
                                    </div>
                                </div>
                                <div class="col-12 col-sm-6">
                                    <select class="form-select bg-light border-0" style="height: 55px;" id="time" name="time" required>
                                        <option value="7:00 - 9:00">7:00 - 9:00</option>
                                        <option value="9:00 - 11:00">9:00 - 11:00</option>
                                        <option value="13:00 - 15:00">13:00 - 15:00</option>
                                        <option value="15:00 - 17:00">15:00 - 17:00</option>
                                        <option value="20:00 - 22:00">20:00 - 22:00</option>
                                    </select>
                                </div>
                                <div class="col-12">
                                    <textarea class="form-control bg-light border-0" name="note" id="note" style="height: 100px;" placeholder="Appointment Note" required></textarea>
                                </div>
                                <div class="col-12">
                                    <div class="g-recaptcha" data-sitekey="6LeerBIqAAAAANsMQuwEvC2L9XqXVW3HDca-XiFk"></div>
                                </div>
                                <div class="col-12">
                                    <button class="btn btn-dark w-100 py-3" type="submit">Make Appointment</button>
                                </div>
                            </div>
                        </form>
                        <c:if test="${not empty errorMessage}">
                            <p style="color: red;">${errorMessage}</p>
                            <c:if test="${not empty conflictingAppointments}">
                                <p style="color: red;">Doctor's schedule conflicts at the following times:</p>
                                <ul style="color: red;">
                                    <c:forEach items="${conflictingAppointments}" var="appointment">
                                        <li>${appointment.tbl_time}</li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Appointment End -->

    <!-- Newsletter Start -->
    <c:if test="${empty sessionScope.account}">
        <div class="container-fluid position-relative pt-5 wow fadeInUp" data-wow-delay="0.1s" style="z-index: 1;">
            <div class="container">
                <div class="bg-primary p-5">
                    <form action="subscribe" method="post" class="mx-auto" style="max-width: 600px;">
                        <div class="input-group">
                            <input type="email" name="email" class="form-control border-white p-3" placeholder="Your Email" required>
                            <button type="submit" class="btn btn-dark px-4">Sign Up</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>
    <!-- Newsletter End -->

    <!-- Footer Start -->
    <div class="container-fluid bg-dark text-light py-5 wow fadeInUp" data-wow-delay="0.3s">
        <div class="container pt-5">
            <div class="row g-5 pt-4">
                <div class="col-lg-3 col-md-6">
                    <h3 class="text-white mb-4">DentCare</h3>
                    <p>A genuine smile comes from the heart, but a healthy smile needs good dental care</p>
                </div>
                <div class="col-lg-3 col-md-6">
                    <h3 class="text-white mb-4">Popular Links</h3>
                    <div class="d-flex flex-column justify-content-start">
                        <a class="text-light mb-2" href="home1.jsp"><i class="bi bi-arrow-right text-primary me-2"></i>Home</a>
                        <a class="text-light mb-2" href="about.jsp"><i class="bi bi-arrow-right text-primary me-2"></i>About</a>
                        <a class="text-light mb-2" href="service.jsp"><i class="bi bi-arrow-right text-primary me-2"></i>Service</a>
                        <a class="text-light mb-2" href="contact.jsp"><i class="bi bi-arrow-right text-primary me-2"></i>Contact</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <h3 class="text-white mb-4">Get In Touch</h3>
                    <p class="mb-2"><i class="bi bi-geo-alt text-primary me-2"></i>Khu Giáo dục và Đào tạo – Khu Công nghệ cao Hòa Lạc – Km29 Đại lộ Thăng Long, H. Thạch Thất, TP. Hà Nội</p>
                    <p class="mb-2"><i class="bi bi-envelope-open text-primary me-2"></i>daihocfpt@fpt.edu.vn</p>
                    <p class="mb-0"><i class="bi bi-telephone text-primary me-2"></i>024 7300 1866</p>
                </div>
                <div class="col-lg-3 col-md-6">
                    <h3 class="text-white mb-4">Follow Us</h3>
                    <div class="d-flex">
                        <a class="btn btn-lg btn-primary btn-lg-square rounded me-2" href="https://twitter.com"><i class="fab fa-twitter fw-normal"></i></a>
                        <a class="btn btn-lg btn-primary btn-lg-square rounded me-2" href="https://facebook.com"><i class="fab fa-facebook-f fw-normal"></i></a>
                        <a class="btn btn-lg btn-primary btn-lg-square rounded me-2" href="https://linkedin.com"><i class="fab fa-linkedin-in fw-normal"></i></a>
                        <a class="btn btn-lg btn-primary btn-lg-square rounded" href="https://instagram.com"><i class="fab fa-instagram fw-normal"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Footer End -->

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