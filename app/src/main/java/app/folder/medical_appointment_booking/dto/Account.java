package app.folder.medical_appointment_booking.dto;

public class Account {
    private  int Id;
    private  String UserName;
    private String PassWord;
    private int Role;
    private  int DotorID;
    public Account(int id, String userName, String passWord, int role) {
        Id = id;
        UserName = userName;
        PassWord = passWord;
        Role = role;
    }

    public Account() {
    }

    public int getDotorID() {
        return DotorID;
    }

    public void setDotorID(int dotorID) {
        DotorID = dotorID;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "Account{" +
                "Id=" + Id +
                ", UserName='" + UserName + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", Role=" + Role +
                '}';
    }
}
