package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.folder.medical_appointment_booking.Session.SesionManagement;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;

public class PatientAppointmentDetailActivity extends AppCompatActivity {

    private TextView txtDoctor, txtAccountId, txtResult, txtBHYT, txtDate, txtId, txtApprove, txtNote;
    private RequestQueue queue, doctorQueue;
    List<AppointmentDTO> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointment_detail);
        txtId = findViewById(R.id.txtId);
        txtDate = findViewById(R.id.txtDate);
        txtNote = findViewById(R.id.txtNote);
        txtDoctor = findViewById(R.id.txtDoctor);
        txtResult = findViewById(R.id.txtResult);
        txtBHYT = findViewById(R.id.txtBHYT);
        txtApprove = findViewById(R.id.txtApprove);

        AppointmentDTO appointment = (AppointmentDTO) getIntent().getExtras().get("AppointmentDTO");
        System.out.println(appointment.id);
        txtId.setText(String.valueOf(appointment.getId()));
        txtDate.setText(appointment.getAppointmentDate().toString());
        txtNote.setText(appointment.getNote());
        txtResult.setText(appointment.getResult());
        if(appointment.getBhyt() == true){
            txtBHYT.setText("Có BHYT");
        } else {
            txtBHYT.setText("Không có BHYT");
        }

        if(appointment.isApproved() == true){
            txtApprove.setText("Đã xác nhận");
        } else {
            txtApprove.setText("Chưa xác nhận");
        }

        doctorQueue = Volley.newRequestQueue(this);
        String url ="https://medical-appointment-booking.herokuapp.com/api/Doctors/" + String.valueOf(appointment.getDoctorId());

        JsonRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            txtDoctor.setText(response.getString("fullName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                error.printStackTrace();
            }
        });

        doctorQueue.add(request);
    }
    //logout add menu to title bar and logout Code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Btnlogout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void  logout(){
        SesionManagement sessionManagement = new SesionManagement(this);
        sessionManagement.removeSession();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
//end logout add menu to title bar and logout Code
}