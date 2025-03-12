<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Service" %>
<%@ page import="dal.DAO" %>
<%@ page import="java.util.List" %>

<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>DentCare - ChangePassword</title>
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
        <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet">

        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <!-- Add custom styles here -->
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f0f2f5;
                margin: 0;
            }
            .navbar {
                background-color: #ffffff;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                z-index: 1000;
            }
            .container {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                text-align: center;
                margin: 20px auto;
                width: 350px;
                max-width: 90%;
                box-sizing: border-box;
                margin-top: 50px;
            }
            h2 {
                font-weight: 600;
                margin-bottom: 20px;
                color: #1877f2; /* Facebook blue */
            }
            .form-group {
                margin-bottom: 20px;
                text-align: left;
            }
            label {
                display: block;
                margin-bottom: 5px;
                color: #1c1e21; /* Facebook dark text */
                font-weight: 600;
            }
            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 12px;
                border: 1px solid #dddfe2;
                border-radius: 5px;
                font-size: 14px;
                color: #1c1e21; /* Facebook dark text */
                box-sizing: border-box;
            }
            .submit-button {
                background-color: #1877f2; /* Facebook blue */
                color: #ffffff;
                border: none;
                padding: 12px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                font-weight: 600;
                text-transform: uppercase;
                transition: background-color 0.3s ease;
                width: 100%;
            }
            .submit-button:hover {
                background-color: #146eb4; /* Darker shade of Facebook blue */
            }
            .cancel-link {
                color: #1877f2; /* Facebook blue */
                text-decoration: none;
                font-size: 14px;
                font-weight: 600;
                margin-top: 10px;
                display: inline-block;
                transition: color 0.3s ease;
            }
            .cancel-link:hover {
                color: #0e4da4; /* Darker shade of Facebook blue on hover */
            }
            .error-message {
                color: red;
                margin-top: 10px;
            }

            .footer {
                background-color: #333;
                color: #fff;
                padding: 20px 20px;
                text-align: center;
                margin-top: 140px;
            }

            .footer .footer-container {
                display: flex;
                justify-content: center; /* Center horizontally */
                flex-wrap: wrap;
                text-align: center; /* Center text within each section */
            }

            .footer .footer-section {
                flex: 1;
                padding: 10px;
                min-width: 200px;
                max-width: 300px; /* Example limit to avoid overflow */
            }
            .footer .footer-section ul {
                list-style: none; /* Remove default bullets */
                padding: 0; /* Remove default padding */
            }

            .footer .footer-section ul li {
                margin-bottom: 8px; /* Adjust margin as needed */
                padding-left: 0; /* Remove default left padding */
            }

            .footer .footer-section ul li:before {
                content: "\2022"; /* Unicode character for bullet point */
                color: #fff; /* Color of the bullet */
                display: inline-block;
                width: 1em; /* Adjust spacing between bullet and text */
                margin-left: -1em; /* Adjust negative margin to bring bullet closer */
            }


            .footer h3 {
                color: #fff;
                margin-bottom: 20px;
                font-size: 20px;
            }

            .footer p, .footer a {
                color: #fff;
                margin-bottom: 10px;
                text-decoration: none;
                font-size: 14px;
            }

            .footer a:hover {
                text-decoration: underline;
            }

            .footer .social a {
                margin: 0 10px;
                color: #fff;
                text-decoration: none;
                font-size: 20px;
                transition: color 0.3s;
            }

            .footer .social a:hover {
                color: #00a8e8;
            }
        </style>
    </head>
    <body>


        <!-- Navbar Start -->
        <nav class="navbar navbar-expand-lg bg-white navbar-light shadow-sm px-5 py-3 py-lg-0">
            <c:choose>
                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'patient'}">
                    <a href="home1.jsp" class="navbar-brand p-0">
                        <h1 class="m-0 text-primary"><i class="fa fa-tooth me-2"></i>DCMS</h1>
                    </a>
                </c:when>
                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'doctor'}">
                    <a href="doctor.jsp" class="navbar-brand p-0">
                        <h1 class="m-0 text-primary"><i class="fa fa-tooth me-2"></i>DCMS</h1>
                    </a>
                </c:when>
                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'nurse'}">
                    <a href="doctor.jsp" class="navbar-brand p-0">
                        <h1 class="m-0 text-primary"><i class="fa fa-tooth me-2"></i>DCMS</h1>
                    </a>
                </c:when>
                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'admin'}">
                    <a href="admin.jsp" class="navbar-brand p-0">
                        <h1 class="m-0 text-primary"><i class="fa fa-tooth me-2"></i>DCMS</h1>
                    </a>
                </c:when>
            </c:choose>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <div class="navbar-nav ms-auto py-0">
                    <c:choose>
                        <c:when test="${sessionScope.account != null && sessionScope.account.role == 'patient'}">
                            <a href="home1.jsp" class="nav-item nav-link active">Home</a>
                        </c:when>
                        <c:when test="${sessionScope.account != null && sessionScope.account.role == 'doctor' || sessionScope.account != null && sessionScope.account.role == 'nurse' }">
                            <a href="doctor.jsp" class="nav-item nav-link active">Home</a>
                        </c:when>
                        <c:when test="${sessionScope.account != null && sessionScope.account.role == 'admin'}">
                            <a href="admin.jsp" class="nav-item nav-link active">Home</a>
                        </c:when>
                    </c:choose>  
                </div>

            </div>
        </nav>
        <!-- Navbar End -->

        <div class="container">
            <h2>Change Password</h2>
            <form action="ChangePassword" method="POST">
                <!-- Remove the username input field -->
                <div class="form-group">
                    <label for="oldPassword">Old Password:</label>
                    <input type="password" id="oldPassword" name="oldPassword" required>
                </div>
                <div class="form-group">
                    <label for="newPassword">New Password:</label>
                    <input type="password" id="newPassword" name="newPassword" required>
                </div>
                <div class="form-group">
                    <label for="reenterPassword">Re-Enter Password:</label>
                    <input type="password" id="reenterPassword" name="reenterPassword" required>
                </div>
                <div class="error-message">${requestScope.message}</div>
                <button type="submit" class="submit-button">Change Password</button>
            </form>
            <c:choose>
                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'patient'}">
                    <a href="home1.jsp" class="cancel-link">Cancel</a>
                </c:when>
                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'doctor'}">
                    <a href="doctor.jsp" class="cancel-link">Cancel</a>
                </c:when>
                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'nurse'}">
                    <a href="doctor.jsp" class="cancel-link">Cancel</a>
                </c:when>
                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'admin'}">
                    <a href="admin.jsp" class="cancel-link">Cancel</a>
                </c:when>
            </c:choose>

        </div>

        <!-- Footer -->
        <footer class="footer">
            <div class="footer-container">
                <div class="footer-section">
                    <h3>DentCare</h3>
                    <p>A genuine smile comes from the heart, but a healthy smile needs good dental care</p>
                </div>
                <div class="footer-section">
                    <h3>Quick Links</h3>
                    <ul>
                        <li><a href="#">About Us</a></li>
                        <li><a href="#">Services</a></li>
                        <li><a href="#">Contact</a></li>
                    </ul>
                </div>
                <div class="footer-section">
                    <h3>Follow Us</h3>
                    <div class="social">
                        <a href="#"><i class="fab fa-facebook-f"></i></a>
                        <a href="#"><i class="fab fa-twitter"></i></a>
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
        </footer>
        <!-- JavaScript Libraries -->
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
