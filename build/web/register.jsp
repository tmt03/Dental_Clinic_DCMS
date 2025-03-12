<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Service" %>
<%@ page import="dal.DAO" %>
<%@ page import="java.util.List" %>

<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>DentCare - Register</title>
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
        <script>
            $(document).ready(function () {
                $('.register-container').addClass('animate__animated animate__fadeInDown'); // Example animation trigger
            });
        </script>
        <style>
            body {
                font-family: 'Open Sans', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
            }

            .register-container {
                max-width: 500px;
                margin: 30px auto;
                background-color: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
                text-align: center;
            }

            .register-container h2 {
                margin-bottom: 10px;
                color: #333;
                font-size: 24px;
            }

            .register-container form {
                width: 100%;
            }

            .register-container .group {
                margin-bottom: 10px;
                width: 100%;
            }

            .register-container input[type="text"],
            .register-container input[type="password"] {
                width: 100%;
                padding: 15px;
                margin: 5px 0;
                display: inline-block;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 16px;
            }
            .register-container input[type="email"],
            .register-container input[type="email"] {
                width: 100%;
                padding: 15px;
                margin: 5px 0;
                display: inline-block;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 16px;
            }

            .register-container input[type="submit"] {
                width: 100%;
                background-color: #00a8e8;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: 0.3s;
            }

            .register-container input[type="submit"]:hover {
                background-color: #007bb5;
            }

            .register-container .check {
                margin-right: 10px;
            }

            .register-container .hr {
                border: 1px solid #ccc;
                margin: 10px 0;
            }

            .register-container .foot a {
                color: #00a8e8;
                text-decoration: none;
                font-size: 14px;
                transition: color 0.3s;
            }

            .register-container .foot a:hover {
                color: #007bb5;
            }

            .register-container p {
                margin-top: 15px;
                color: #333;
                font-size: 14px;
            }

            .register-container p a {
                color: #00a8e8;
                text-decoration: none;
                transition: color 0.3s;
            }

            .register-container p a:hover {
                color: #007bb5;
                text-decoration: underline;
            }

            .footer {
                background-color: #333;
                color: #fff;
                padding: 40px 20px;
                text-align: center;
                margin-top: 40px;
            }

            .footer .container {
                display: flex;
                justify-content: space-between;
                flex-wrap: wrap;
                text-align: left;
            }

            .footer .footer-section {
                flex: 1;
                padding: 10px;
                min-width: 200px;
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

            @media (max-width: 768px) {
                .register-container {
                    padding: 30px;
                    margin: 20px;
                }
            }

            @media (max-width: 480px) {
                .register-container {
                    padding: 20px;
                    margin: 10px;
                }

                .register-container h2 {
                    font-size: 20px;
                }

                .register-container input[type="submit"] {
                    font-size: 14px;
                    padding: 12px;
                }
            }
        </style>
    </head>
    <body>
        
        <!-- Navbar Start -->
        <nav class="navbar navbar-expand-lg bg-white navbar-light shadow-sm px-5 py-3 py-lg-0">
            <a href="home1.jsp" class="navbar-brand p-0">
                <h1 class="m-0 text-primary"><i class="fa fa-tooth me-2"></i>DCMS</h1>
            </a>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <div class="navbar-nav ms-auto py-0">
                    <a href="home1.jsp" class="nav-item nav-link active">Home</a>
                </div>
                
                <a href="login.jsp" class="btn btn-primary py-2 px-4 ms-3">Login</a>
            </div>
        </nav>
        <!-- Navbar End -->

        <!-- Register Form -->
        <div class="register-container">
            <h2>Register to DentCare</h2>
            <form action="register" method="post" class="animate__animated animate__fadeIn">
                <div class="group">
                    <input id="user" type="text" class="input" name="user" placeholder="Create your Username" required>
                </div>
                <div class="group">
                    <input id="pass" type="password" class="input" name="pass" placeholder="Create your Password" required>
                </div>
                <div class="group">
                    <input id="rpass" type="password" class="input" name="rpass" placeholder="Repeat your Password" required>
                </div>
                <div class="group">
                    <input id="displayname" type="text" class="input" name="displayname" placeholder="Enter your FullName" required>
                </div>
                <div class="group">
                    <input id="email" type="email" class="input" name="email" placeholder="Enter your Email Address" required>
                </div>
                <div class="group">
                    Enter your Date Of Birth &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp<input id="dob" type="date" class="input" name="dob" placeholder="Enter your Date Of Birth" required>
                </div>
                <div class="group">
                    <input type="submit" class="button" value="Sign Up">
                </div>
            </form>
            <% if (request.getAttribute("ms") != null) { %>
            <div style="color: red;"><%= request.getAttribute("ms") %></div>
            <% } %>
            <p class="animate__animated animate__fadeInUp">Already have an account? <a href="login.jsp">Login here</a></p>
        </div>


        <!-- Footer Start -->
        <div class="container-fluid bg-dark text-light py-5 wow fadeInUp" data-wow-delay="0.3s" style="margin-top: -75px;">
            <div class="container pt-5">
                <div class="row g-5 pt-4">
                    <div class="col-lg-3 col-md-6">
                        <h3 class="text-white mb-4">DentCare</h3>
                        <p>A genuine smile comes from the heart, but a healthy smile needs good dental care</p>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <h3 class="text-white mb-4">Popular Links</h3>
                        <div class="d-flex flex-column justify-content-start">
                            <a class="text-light mb-2" href="#"><i class="bi bi-arrow-right text-primary me-2"></i>Home</a>

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
                            <a class="btn btn-lg btn-primary btn-lg-square rounded me-2" href="#"><i class="fab fa-twitter fw-normal"></i></a>
                            <a class="btn btn-lg btn-primary btn-lg-square rounded me-2" href="#"><i class="fab fa-facebook-f fw-normal"></i></a>
                            <a class="btn btn-lg btn-primary btn-lg-square rounded me-2" href="#"><i class="fab fa-linkedin-in fw-normal"></i></a>
                            <a class="btn btn-lg btn-primary btn-lg-square rounded" href="#"><i class="fab fa-instagram fw-normal"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer End -->

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
        <script src="js/main.js"></script>
        <!-- Other scripts as per login.jsp -->
    </body>
</html>
