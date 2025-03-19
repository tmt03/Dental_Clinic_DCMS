<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Newsletter Start -->
<c:choose>
    <c:when test="${sessionScope.account != null && sessionScope.account.role == 'patient'}">
        <div class="container-fluid position-relative pt-5 wow fadeInUp" data-wow-delay="0.1s" style="display: none;">
            <div class="container">
                <div class="bg-primary p-5">
                    <form class="mx-auto" style="max-width: 600px;">
                        <div class="input-group">
                            <input type="text" class="form-control border-white p-3" placeholder="Your Email">
                            <a href="register.jsp" class="btn btn-dark px-4">Sign Up</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${sessionScope.account != null && sessionScope.account.role == 'doctor' || sessionScope.account != null && sessionScope.account.role == 'nurse' }">
        <div class="container-fluid position-relative pt-5 wow fadeInUp" data-wow-delay="0.1s" style="display: none;">
            <div class="container">
                <div class="bg-primary p-5">
                    <form class="mx-auto" style="max-width: 600px;">
                        <div class="input-group">
                            <input type="text" class="form-control border-white p-3" placeholder="Your Email">
                            <a href="register.jsp" class="btn btn-dark px-4">Sign Up</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${sessionScope.account != null && sessionScope.account.role == 'admin'}">
        <div class="container-fluid position-relative pt-5 wow fadeInUp" data-wow-delay="0.1s" style="display: none;">
            <div class="container">
                <div class="bg-primary p-5">
                    <form class="mx-auto" style="max-width: 600px;">
                        <div class="input-group">
                            <input type="text" class="form-control border-white p-3" placeholder="Your Email">
                            <a href="register.jsp" class="btn btn-dark px-4">Sign Up</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${empty sessionScope.account}">
        <div class="container-fluid position-relative pt-5 wow fadeInUp" data-wow-delay="0.1s" style="z-index: 1;">
            <div class="container">
                <div class="bg-primary p-5">
                    <form class="mx-auto" style="max-width: 600px;">
                        <div class="input-group">
                            <input type="text" class="form-control border-white p-3" placeholder="Your Email">
                            <a href="register.jsp" class="btn btn-dark px-4">Sign Up</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>
<!-- Newsletter End -->
