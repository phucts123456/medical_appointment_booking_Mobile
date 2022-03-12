package app.folder.medical_appointment_booking.dto;

import java.io.Serializable;
import java.util.Date;

public class AppointmentDTO implements Serializable {
    public int id;
    public Date AppointmentDate;
    public int DoctorId;
    public boolean Bhyt;
    public boolean isApproved;
    public String note;
    public int accountId;
    public String result;

    public AppointmentDTO() {
    }

    public AppointmentDTO(int id, Date appointmentDate, int doctorId, boolean bhyt, boolean isApproved, String note, int accountId, String result) {
        this.id = id;
        AppointmentDate = appointmentDate;
        DoctorId = doctorId;
        Bhyt = bhyt;
        this.isApproved = isApproved;
        this.note = note;
        this.accountId = accountId;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean getBhyt() {
        return Bhyt;
    }

    public void setBhyt(boolean bhyt) {
        Bhyt = bhyt;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
