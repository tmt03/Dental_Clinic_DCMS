<%-- 
    Document   : result
    Created on : Jun 1, 2024, 8:54:25 AM
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
    <title>Result</title>
</head>
<body>
    <div id="messageDiv" style="display:none;">
        <% String status = request.getParameter("status"); %>
        <% if ("success".equals(status)) { %>
            <h1>Operation Successful</h1>
            <p>The operation was completed successfully.</p>
        <% } else { %>
            <h1>Error</h1>
            <p>There was an error processing your request. Please try again.</p>
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) { %>
                <p>Error details: <%= errorMessage %></p>
            <% } %>
        <% } %>
        <a href="home1.jsp">Back to Homepage</a>
    </div>
    <script>
        document.getElementById("messageDiv").style.display = "block";
    </script>
</body>
</html>


