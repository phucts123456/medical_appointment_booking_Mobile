package app.folder.medical_appointment_booking.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
    private int Id;
    private Date AppointmentDate;
    private int DoctorId;
    private boolean Bhty;
    private  boolean isApproved;
    private String Note;
    private int AccountId;
    private String Result;
    private Account account;
    private Doctor doctor;

    public Appointment() {
    }

    public Appointment(int id, Date appointmentDate, int doctorId, boolean bhty, boolean isApproved, String note, int accountId, String result, Account account, Doctor doctor) {
        Id = id;
        AppointmentDate = appointmentDate;
        DoctorId = doctorId;
        Bhty = bhty;
        this.isApproved = isApproved;
        Note = note;
        AccountId = accountId;
        Result = result;
        this.account = account;
        this.doctor = doctor;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public boolean isBhty() {
        return Bhty;
    }

    public void setBhty(boolean bhty) {
        Bhty = bhty;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
    public String getBhtyWithString(boolean bhyt){
        String status ="";
        if(Bhty == true){
            status = "Có";
        }
        else {
            status = "Không";
        }
        return status;
    }
    public String getStatusWithString(boolean isApproved){
        String status ="";

        if(isApproved == true){
            status = "Chấp nhận";
        }
        else if(isApproved == false) {
            status = "Từ chối";
        }

        return status;
    }
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(AppointmentDate);
        return  "Id=" + Id +"\n"+
                "AppointmentDate:" + strDate +"\n"+
                "Bhty:" +getBhtyWithString(Bhty)  +"\n"+
                "Note: " + Note + "\n"+
                "Kết quả khám:" + "Nhấn để xem chi tiết" ;
    }
}
