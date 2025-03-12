package model;

import java.util.Date;

public class User {

    private int userID;
    private String username;
    private String email;
    private String displayName;
    private String password;
    private String address;
    private String mobile;
    private String role;
    private String others;
    private String isActive;
    private String image;
    private String dob;
    private int age;

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    
    public User(int userID, String username, String email, String displayName, String password, String address, String mobile, String role, String others, String isActive, String image, String dob, int age) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.password = password;
        this.address = address;
        this.mobile = mobile;
        this.role = role;
        this.others = others;
        this.isActive = isActive;
        this.image = image;
        this.age = age;
        this.dob = dob;
    }

    public User() {
    }
}
