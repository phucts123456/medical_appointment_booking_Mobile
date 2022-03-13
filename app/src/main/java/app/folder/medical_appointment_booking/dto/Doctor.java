package app.folder.medical_appointment_booking.dto;

public class Doctor {
    private int Id;
    private  int SpecialistId;
    private String  FullName;
    private  String AcademicRank;
    private int AccountId;
    private boolean isMale;
    private Specialist spec;

    public Doctor(int id, int specialistId, String fullName, String academicRank, int accountId, boolean isMale, Specialist spec) {
        Id = id;
        SpecialistId = specialistId;
        FullName = fullName;
        AcademicRank = academicRank;
        AccountId = accountId;
        this.isMale = isMale;
        this.spec = spec;
    }

    public Doctor( ) {
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public Specialist getSpec() {
        return spec;
    }

    public void setSpec(Specialist spec) {
        this.spec = spec;
    }

    public int getId() {
        return Id;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSpecialistId() {
        return SpecialistId;
    }

    public void setSpecialistId(int specialistId) {
        SpecialistId = specialistId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
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
    public String getGenderToReturnString(boolean isMale){
        String gender = "";
        if(isMale == true ){
            gender = "Nam";
        }
        else if(isMale == false){
            gender ="Nữ";
        }
        return gender;
    }
}
