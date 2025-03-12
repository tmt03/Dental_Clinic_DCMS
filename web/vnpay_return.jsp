<%-- 
    Document   : vnpay_return
    Created on : Jul 16, 2024, 10:15:45 PM
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
        <title>VNPay Return Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                padding: 20px;
            }
            .container {
                background-color: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            h1 {
                color: #343a40;
            }
            table {
                width: 100%;
            }
            th, td {
                padding: 15px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
                font-weight: bold;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            .btn-return {
                background-color: #007bff;
                color: #fff;
                border: none;
                padding: 10px 20px;
                font-size: 18px;
                cursor: pointer;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }
            .btn-return:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="text-center mb-4">VNPay Transaction Details</h1>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Parameter</th>
                        <th>Value</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Amount</td>
                        <td><%= request.getParameter("vnp_Amount") %></td>
                    </tr>
                    <tr>
                        <td>Bank Code</td>
                        <td><%= request.getParameter("vnp_BankCode") %></td>
                    </tr>
                    <tr>
                        <td>Bank Transaction No</td>
                        <td><%= request.getParameter("vnp_BankTranNo") %></td>
                    </tr>
                    <tr>
                        <td>Card Type</td>
                        <td><%= request.getParameter("vnp_CardType") %></td>
                    </tr>
                    <tr>
                        <td>Order Info</td>
                        <td><%= request.getParameter("vnp_OrderInfo") %></td>
                    </tr>
                    <tr>
                        <td>Pay Date</td>
                        <td><%= request.getParameter("vnp_PayDate") %></td>
                    </tr>
                    <tr>
                        <td>Response Code</td>
                        <td><%= request.getParameter("vnp_ResponseCode") %></td>
                    </tr>
                    <tr>
                        <td>Terminal Code</td>
                        <td><%= request.getParameter("vnp_TmnCode") %></td>
                    </tr>
                    <tr>
                        <td>Transaction No</td>
                        <td><%= request.getParameter("vnp_TransactionNo") %></td>
                    </tr>
                    <tr>
                        <td>Transaction Status</td>
                        <td><%= request.getParameter("vnp_TransactionStatus") %></td>
                    </tr>
                    <tr>
                        <td>Transaction Reference</td>
                        <td><%= request.getParameter("vnp_TxnRef") %></td>
                    </tr>
                    <tr>
                        <td>Secure Hash</td>
                        <td><%= request.getParameter("vnp_SecureHash") %></td>
                    </tr>
                </tbody>
            </table>
            <button type="submit" onclick="viewPatientAppointment(${sessionScope.account.userID})" class="btn btn-primary btn-return">Return To Your Appointment List</button>
        </div>
        <script src="js/main.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
