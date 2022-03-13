package app.folder.medical_appointment_booking.dto;

public class Specialist {
    private int ID;
    private String Name;

    public Specialist(int ID, String name) {
        this.ID = ID;
        this.Name = name;
    }

    public Specialist() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
