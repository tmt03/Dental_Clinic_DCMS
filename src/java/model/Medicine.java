/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Medicine {
    private int tbl_medicineID;
    private String tbl_medicineName;
    private String tbl_instruction;
    private int tbl_resultID;
    private int tbl_appointmentID;

    public int getTbl_appointmentID() {
        return tbl_appointmentID;
    }

    public void setTbl_appointmentID(int tbl_appointmentID) {
        this.tbl_appointmentID = tbl_appointmentID;
    }
    

    public Medicine() {
    }

    public Medicine(int tbl_medicineID, String tbl_medicineName, String tbl_instruction, int tbl_resultID) {
        this.tbl_medicineID = tbl_medicineID;
        this.tbl_medicineName = tbl_medicineName;
        this.tbl_instruction = tbl_instruction;
        this.tbl_resultID = tbl_resultID;
    }

    public int getTbl_medicineID() {
        return tbl_medicineID;
    }

    public void setTbl_medicineID(int tbl_medicineID) {
        this.tbl_medicineID = tbl_medicineID;
    }

    public String getTbl_medicineName() {
        return tbl_medicineName;
    }

    public void setTbl_medicineName(String tbl_medicineName) {
        this.tbl_medicineName = tbl_medicineName;
    }

    public String getTbl_instruction() {
        return tbl_instruction;
    }

    public void setTbl_instruction(String tbl_instruction) {
        this.tbl_instruction = tbl_instruction;
    }

    public int getTbl_resultID() {
        return tbl_resultID;
    }

    public void setTbl_resultID(int tbl_resultID) {
        this.tbl_resultID = tbl_resultID;
    }
    
    
}
