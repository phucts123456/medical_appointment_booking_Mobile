package app.folder.medical_appointment_booking.dto;

import java.io.Serializable;

public class DoctorDTO implements Serializable {
    public int id;
    public int SpecialistId;
    public String fullName;
    public String AcademicRank;
    public int AccountId;
    public boolean male;

    public DoctorDTO() {
    }

    public DoctorDTO(int id, int specialistId, String fullName, String academicRank, int accountId, boolean male) {
        this.id = id;
        SpecialistId = specialistId;
        this.fullName = fullName;
        AcademicRank = academicRank;
        AccountId = accountId;
        this.male = male;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpecialistId() {
        return SpecialistId;
    }

    public void setSpecialistId(int specialistId) {
        SpecialistId = specialistId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAcademicRank() {
        return AcademicRank;
    }

    public void setAcademicRank(String academicRank) {
        AcademicRank = academicRank;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }
}
