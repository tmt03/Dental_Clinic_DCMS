/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ntawo
 */
public class Appointment {

    private int tbl_appointmentID;
    private String Date;
    private String tbl_time;
    private String serviceName;
    private String controller;
    private String patient;
    private String note;
    private String status;
    private float revenue;

    public int getTbl_appointmentID() {
        return tbl_appointmentID;
    }

    public void setTbl_appointmentID(int tbl_appointmentID) {
        this.tbl_appointmentID = tbl_appointmentID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTbl_time() {
        return tbl_time;
    }

    public void setTbl_time(String tbl_time) {
        this.tbl_time = tbl_time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public Appointment() {
    }

}
