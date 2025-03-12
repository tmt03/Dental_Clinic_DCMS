<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Service" %>
<%@ page import="dal.DAO" %>
<%@ page import="java.util.List" %>

<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" class="light-style layout-menu-fixed" dir="ltr" data-theme="theme-default" data-assets-path="assets/" data-template="vertical-menu-template-free">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
        <title>View Profile</title>
        <meta name="description" content="">

        <!-- Favicon -->
        <link rel="icon" type="image/x-icon" href="assets/img/favicon/favicon.ico">

        <!-- Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap" rel="stylesheet">

        <!-- Core CSS -->
        <link rel="stylesheet" href="assets/vendor/css/core.css" class="template-customizer-core-css">
        <link rel="stylesheet" href="assets/vendor/css/theme-default.css" class="template-customizer-theme-css">
        <link rel="stylesheet" href="assets/css/demo.css">

        <!-- Vendors CSS -->
        <link rel="stylesheet" href="assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css">

        <!-- Custom CSS -->
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
        <style>
            body {
                font-family: 'Public Sans', sans-serif;
                background-color: #f5f5f5;
                color: #333;
            }

            .container-profile {
                max-width: 800px;
                margin: auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .form-label {
                font-weight: 600;
                color: #555;
            }

            .form-control {
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                border-radius: 4px;
                padding: 8px 12px;
                transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
            }

            .form-control:focus {
                border-color: #5e72e4;
                outline: 0;
                box-shadow: 0 0 0 0.2rem rgba(94, 114, 228, 0.25);
            }

            .btn-primary {
                background-color: #5e72e4;
                border-color: #5e72e4;
            }

            .btn-primary:hover {
                background-color: #4851db;
                border-color: #4851db;
            }

            .btn-success {
                background-color: #2dce89;
                border-color: #2dce89;
            }

            .btn-success:hover {
                background-color: #24a46d;
                border-color: #24a46d;
            }

            .btn-secondary {
                background-color: #f0f0f0;
                color: #555;
                border-color: #f0f0f0;
            }

            .btn-secondary:hover {
                background-color: #e0e0e0;
                color: #555;
                border-color: #e0e0e0;
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

        <!-- Layout wrapper -->
        <div class="layout-wrapper layout-content-navbar">
            <div class="layout-container">
                <!-- Content -->
                <div class="content-wrapper">
                    <div class="container-profile">

                        <!-- Breadcrumbs -->
                        <h4 class="fw-bold py-3 mb-4">
                            <c:choose>
                                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'patient'}">
                                    <a href="home1.jsp" class="text-muted fw-light">Home</a> / Profile
                                </c:when>
                                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'doctor'}">
                                    <a href="doctor.jsp" class="text-muted fw-light">Home</a> / Profile
                                </c:when>
                                <c:when test="${sessionScope.account != null && sessionScope.account.role == 'admin'}">
                                    <a href="admin.jsp" class="text-muted fw-light">Home</a> / Profile
                                </c:when>
                            </c:choose>
                        </h4>

                        <!-- Profile Details Card -->
                        <div class="card mb-4">
                            <h3 class="card-header">Profile Details</h3>
                            <div class="card-body">
                                <!-- Status Message -->
                                <c:if test="${requestScope.STATUS != null}">
                                    <h6 style="color: green">${requestScope.STATUS}</h6>
                                </c:if>
                                <c:if test="${requestScope.ERROR != null}">
                                    <h6 style="color: red">${requestScope.ERROR}</h6>
                                </c:if>

                                <!-- Profile Form -->
                                <form id="edit_profile_form" action="EditProfileServlet" method="get">
                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label class="form-label">User Name</label>
                                            <input class="form-control" type="text" name="username" value="${sessionScope.account.username}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Role</label>
                                            <input class="form-control" type="text" name="role" value="${sessionScope.account.role}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Full Name</label>
                                            <input class="form-control" type="text" name="display-name" value="${sessionScope.account.displayName}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">E-mail</label>
                                            <input class="form-control" type="text" name="email" value="${sessionScope.account.email}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Phone Number</label>
                                            <input class="form-control" type="text" name="mobile" value="${sessionScope.account.mobile}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Address</label>
                                            <input class="form-control" type="text" name="address" value="${sessionScope.account.address}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">Others Information:</label>
                                            <input class="form-control" type="text" name="others" value="${sessionScope.account.others}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <input class="form-control" type="hidden" name="image" value="${sessionScope.account.image}" readonly>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="form-label">DOB:</label>
                                            <input class="form-control" type="text" name="dob" value="${sessionScope.account.dob}" readonly>
                                        </div>
                                    </div>

                                    <!-- Edit Button -->
                                    <div class="mt-3">
                                        <button onclick="editProfile()" type="button" id="editButton" class="btn btn-primary me-2">Edit Profile</button>
                                        <button type="button" id="saveButton" class="btn btn-success" style="display: none;" onclick="saveProfile()">Save</button>
                                        <button type="button" id="resetButton" class="btn btn-secondary ms-2" style="display: none;" onclick="resetChanges()">Reset Changes</button>&nbsp;&nbsp;
                                        <button type="button" id="cancelButton" class="btn btn-secondary" style="display: none;" onclick="cancelEdit()">Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- / Content -->

                <!-- Footer -->
                <footer class="content-footer footer bg-footer-theme">
                    <div class="container-xxl d-flex flex-wrap justify-content-between py-2 flex-md-row flex-column">
                        <!-- Footer content if any -->
                    </div>
                </footer>
                <!-- / Footer -->

                <!-- Content backdrop for overlay effect -->
                <div class="content-backdrop fade"></div>
            </div>
            <!-- / Layout container -->

            <!-- Overlay for responsive menu toggle -->
            <div class="layout-overlay layout-menu-toggle"></div>
        </div>
        <!-- / Layout wrapper -->

        <!-- Core JS libraries -->
        <script src="assets/vendor/libs/jquery/jquery.js"></script>
        <script src="assets/vendor/libs/popper/popper.js"></script>
        <script src="assets/vendor/js/bootstrap.js"></script>
        <script src="assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>
        <script src="../assets/vendor/js/menu.js"></script>

        <!-- Main JS -->
        <script src="assets/js/main.js"></script>

        <!-- Page-specific JS -->
        <script>
                                            function editProfile() {
                                                var inputs = document.querySelectorAll('.form-control');
                                                inputs.forEach(input => {
                                                    var fieldName = input.getAttribute('name');
                                                    if (fieldName !== 'username' && fieldName !== 'role') {
                                                        input.removeAttribute('readonly');
                                                        input.classList.add('editable');
                                                    }
                                                });

                                                document.getElementById('editButton').style.display = 'none';
                                                document.getElementById('saveButton').style.display = 'inline-block';
                                                document.getElementById('cancelButton').style.display = 'inline-block';
                                                document.getElementById('resetButton').style.display = 'inline-block'; // Show reset button
                                            }


                                            function cancelEdit() {
                                                var inputs = document.querySelectorAll('.form-control');
                                                inputs.forEach(input => {
                                                    input.setAttribute('readonly', 'true');
                                                    input.classList.remove('editable');
                                                });

                                                document.getElementById('editButton').style.display = 'inline-block';
                                                document.getElementById('saveButton').style.display = 'none';
                                                document.getElementById('cancelButton').style.display = 'none';
                                                document.getElementById('resetButton').style.display = 'none'; // Hide reset button again
                                            }

                                            function saveProfile() {
                                                // Show confirmation dialog
                                                if (confirm("Are you sure you want to save changes?")) {
                                                    document.getElementById('edit_profile_form').submit(); // Submit the form
                                                } else {
                                                    // Do nothing if canceled
                                                }
                                            }

                                            function resetChanges() {
                                                // Fetch initial values and reset the form to its original state
                                                var originalValues = {
                                                    username: "${sessionScope.account.username}",
                                                    role: "${sessionScope.account.role}",
                                                    displayName: "${sessionScope.account.displayName}",
                                                    email: "${sessionScope.account.email}",
                                                    mobile: "${sessionScope.account.mobile}",
                                                    address: "${sessionScope.account.address}",
                                                    others: "${sessionScope.account.others}",
                                                    image: "${sessionScope.account.image}",
                                                    dob: "${sessionScope.account.dob}"
                                                };

                                                var inputs = document.querySelectorAll('.form-control');
                                                inputs.forEach(input => {
                                                    var fieldName = input.getAttribute('name');
                                                    if (fieldName && originalValues[fieldName] !== undefined) {
                                                        input.value = originalValues[fieldName];
                                                    }
                                                });
                                            }
        </script>
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
        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>
</html>
