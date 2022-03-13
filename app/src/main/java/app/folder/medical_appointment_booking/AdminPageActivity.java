package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.folder.medical_appointment_booking.dto.AppointmentDTO;
import app.folder.medical_appointment_booking.dto.SpecialistDTO;

public class AdminPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }

    public void MoveToAdminAppointmentPage(View view) {
        Intent intent = new Intent(this, AdminAppointmentActivity.class);
        startActivity(intent);
    }

    public void MoveToAdminDoctorPage(View view) {
        Intent intent = new Intent(this, AdminDoctorActivity.class);
        startActivity(intent);
    }
}