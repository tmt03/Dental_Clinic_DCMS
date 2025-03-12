/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Result {
    private int resultID;
    private String description;
    private int appointmentID;
    private Date createTime;
    private String Patient;
    private String Doctor;
    



    public String getPatient() {
        return Patient;
    }

    public String getDoctor() {
        return Doctor;
    }
    public Result() {
    }

    public Result(int resultID, String description, int appointmentID, Date createTime) {
        this.resultID = resultID;
        
        this.description = description;
        this.appointmentID = appointmentID;
        this.createTime = createTime;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setPatient(String Patient) {
        this.Patient = Patient;
    }

    public void setDoctor(String Doctor) {
        this.Doctor = Doctor;
    }

   
    
    
}
