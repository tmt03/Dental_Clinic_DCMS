/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author ADMIN
 */
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Arrays;

import java.sql.*;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;

public class Email {

    //fastshitduck100@gmail.com
    //zecj ivyq pimm xhvi
    public static final String MYEMAIL = "fastshitduck100@gmail.com";
    public static final String PASSWORD = "zecj ivyq pimm xhvi";
    public static final String DEBUG = "true";
    public static final String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    
    public static final String SUBJECTVERIFICATIONCODE = "Verification code";
    public static final String VERIFICATIONCODE = "Your Verification code is: ";
    public static final String NEWPASSWORD = "Your news password is: ";
    public static final String SUBJECTNEWPASSWORD = "Reset Password";
    public static final String SUBJECTNEWUSERNAME = "New Account";
    public static final String NEWACCOUNT = "You have been granted an account to log into the DentCare system.\n Your username is: ";

    public static boolean sendTo(String to, String subject, String content) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("check pass");
                return new PasswordAuthentication(MYEMAIL, PASSWORD);
            }
        };
        //create session
        Session session = Session.getInstance(props, auth);
        //create message
        MimeMessage message = new MimeMessage(session);
        System.out.println("check session");
        try {
            //create message
            //content type
            message.addHeader("Content-type", "text/HTML");

            message.setFrom(MYEMAIL);
            //nguoi nhan
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            //Subject
            message.setSubject(subject);
            //content
            message.setContent(content, "text/plain");
            //send
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendVerificationCode(String to, String code) {
        return sendTo(to, SUBJECTVERIFICATIONCODE, "Hello " + extractUsername(to) + "\n" +  VERIFICATIONCODE + code + ".\n" + "Use this code to verify your account. Please do not share this code with anyone.\nRegards,\nDentCare Team.");
    }

    public static boolean sendNewPassword(String to, String password) {
        return sendTo(to, SUBJECTNEWPASSWORD, NEWPASSWORD + password + ".\n" + "Use this password to login your account. Please do not share this password with anyone.\nRegards,\nDentCare Team.");
    }

    public static boolean sendEmail(String to, String subject, String content) {
        return sendTo(to, subject, content);
    }

    public static boolean sendNewAccount(String to, String username, String password) {
        return sendTo(to, SUBJECTNEWUSERNAME, NEWACCOUNT + username + ".\n "
                + "Your password is: " + password + ".\nUse this username and password to login your account. Please do not share this username with anyone.");
    }
    public static boolean sendBookingAppointment(String to, String username, Date date, String service, String room, LocalTime startTime, LocalTime endTime) {
        String code = generateRandomCode(6);
        return sendTo(to, "Booking Appointment - [" + code + "]", "Dear " + username + "\n We are pleased to confirm your booking. Below are the details of your appointment:\n\n"
                                                                                 + "Order Code: " + code + "\n"
                                                                                 + "Date: " + date + " " + startTime + "-" + endTime + "\n"
                                                                                 + "Service: " + service + "\n"
                                                                                 + "Room: " + room + "\n\n"
                                                                                 + "Should you need to make any changes to your appointment or have any questions, please do not hesitate to contact us.\n"
                                                                                 + "Thank you for choosing our services. We look forward to serving you.\n\n"  + "Best regards,\n" + "DentCare Team.");
    }
    public static String extractUsername(String email) {
        String[] parts = email.split("@");
        if (parts.length == 2) {
            return parts[0];
        }
        return null; // Invalid email format
    }
    public static String generateVerificationCode() {
        // Generate a random verification code
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10)); // 0-9 digits
        }
        return String.valueOf(code);
    }
        public static String generateRandomCode(int length) {
        // Define the characters to use (lowercase letters and numbers)
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";

        // Create a secure random number generator
        SecureRandom random = new SecureRandom();

        // Generate the random code
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }
//        public static void main(String[] args) {
//            sendTo("nta.work03@gmail.com", "abc", "t.anh");
//    }
}
