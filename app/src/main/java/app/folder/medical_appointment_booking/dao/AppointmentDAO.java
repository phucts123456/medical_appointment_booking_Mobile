package app.folder.medical_appointment_booking.dao;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.folder.medical_appointment_booking.dto.AppointmentDTO;

public class AppointmentDAO {
    public List<AppointmentDTO> loadFromInternal() {
        List<AppointmentDTO> result = new ArrayList<>();
        AppointmentDTO flower = null;

        return result;
    }
}
