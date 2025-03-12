<%-- 
    Document   : index
    Created on : Jul 16, 2024, 10:35:41 AM
    Author     : ntawo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Service" %>
<%@ page import="dal.DAO" %>
<%@ page import="java.util.List" %>

<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Payment Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            body {
                background-color: #f8f9fa;
                font-family: Arial, sans-serif;
            }
            .container {
                max-width: 600px;
                margin-top: 30px;
                background-color: #fff;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }
            h1 {
                color: #343a40;
            }
            .btn-pay, .btn-confirm {
                background-color: #28a745;
                color: #fff;
                border: none;
                padding: 10px 20px;
                font-size: 18px;
                cursor: pointer;
                border-radius: 5px;
                transition: background-color 0.3s ease;
                margin-top: 10px;
            }
            .btn-pay:hover, .btn-confirm:hover {
                background-color: #218838;
            }
            .alert {
                display: none;
            }
        </style>
        <script type="text/javascript">
            function payWithVNPay(a = "") {
                var amount = a; // Change the amount as desired
                var bankCode = ""; // Add bank code if needed
                var language = "vn"; // Default language is Vietnamese
                
                $.ajax({
                    url: "vnpayajax",
                    type: "POST",
                    data: {
                        amount: amount,
                        bankCode: bankCode,
                        language: language
                    },
                    success: function (response) {
                        var data = JSON.parse(response);
                        if (data.code === "00") {
                            var paymentUrl = data.data;
                            window.location.href = paymentUrl;
                        } else {
                            showError(data.message);
                        }
                    },
                    error: function (xhr, status, error) {
                        showError("An unexpected error occurred. Please try again.");
                        console.error("Error:", error);
                    }
                });
            }
            
            function showError(message) {
                var alertBox = document.getElementById("alertBox");
                alertBox.innerHTML = message;
                alertBox.style.display = "block";
            }
        </script>
    </head>
    <body>
        <div class="container text-center">
            <h1>Appointment Confirmation</h1>
            <table class="table table-bordered">

                <c:forEach var="appointment" items="${appointments}">          

                    <tr>
                        <th>Patient</th>
                        <td>${appointment.getPatient()}</td>
                    </tr>
                    <tr>
                        <th>Service</th>
                        <td>${appointment.getServiceName()}</td>
                    </tr>
                    <tr>
                        <th>Doctor</th>
                        <td>${appointment.getController()}</td>
                    </tr>
                    <tr>
                        <th>Date</th>
                        <td><%= request.getParameter("date") %></td>
                    </tr>
                    <tr>
                        <th>Time</th>
                        <td><%= request.getParameter("time") %></td>
                    </tr>
                    <tr>
                        <th>Note</th>
                        <td><%= request.getParameter("note") %></td>
                    </tr>              
                </table>    
                <h6>If you confirmed your information is correct then you will have to pay a deposit = 100.000VND for the appointment. Thank you for selecting our services</h6>
                <button class="btn-confirm" onclick="payWithVNPay('100000')">Confirm & Pay</button>
            </c:forEach>
            <a href="appointment" class="btn btn-primary py-2 px-4 ms-3">Cancel & Return to Appointment</a>
            <div id="alertBox" class="alert alert-danger mt-4" role="alert"></div>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>

