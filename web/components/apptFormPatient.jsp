<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Appt Start -->
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
                    <form action="core" method="post">
                        <div class="row g-3">
                            <input type="hidden" name="action" value="PATIENT_BOOK_APPOINTMENT">
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
                            <p style="color: red;">Lịch của bác sĩ đã bị trùng vào các giờ sau:</p>
                            <ul style="color: red;">
                                <c:forEach items="${conflictingAppointments}" var="appt">
                                    <li>${appt.tbl_time}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Appt End -->