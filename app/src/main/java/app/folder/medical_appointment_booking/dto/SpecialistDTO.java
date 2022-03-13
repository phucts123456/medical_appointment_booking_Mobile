package app.folder.medical_appointment_booking.dto;

import java.io.Serializable;

public class SpecialistDTO implements Serializable {
    public int ID;
    public String Name;

    public SpecialistDTO() {
    }

    public SpecialistDTO(int ID, String name) {
        this.ID = ID;
        Name = name;
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
