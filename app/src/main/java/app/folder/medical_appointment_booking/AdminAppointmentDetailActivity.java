package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.folder.medical_appointment_booking.adapter.AppointmentAdapter;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;

public class AdminAppointmentDetailActivity extends AppCompatActivity {

    private EditText edtNote;
    private TextView txtDoctor, txtAccountId, txtResult, txtBHYT, txtDate, txtId;
    private Button btnUpdate, btnCancel;
    private CheckBox chkApprove;
    private RequestQueue queue, doctorQueue;
    List<AppointmentDTO> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointment_detail);
        txtId = findViewById(R.id.txtId);
        txtDate = findViewById(R.id.txtDate);
        edtNote = findViewById(R.id.edtNote);
        txtDoctor = findViewById(R.id.txtDoctor);
        txtAccountId = findViewById(R.id.txtAccountId);
        txtResult = findViewById(R.id.txtResult);
        btnUpdate= findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnDetailCancel);
        txtBHYT = findViewById(R.id.txtBHYT);
        chkApprove = findViewById(R.id.chkApprove);

        AppointmentDTO appointment = (AppointmentDTO) getIntent().getExtras().get("AppointmentDTO");
        System.out.println(appointment.id);
        txtId.setText(String.valueOf(appointment.getId()));
        txtDate.setText(appointment.getAppointmentDate().toString());
        edtNote.setText(appointment.getNote());
        txtAccountId.setText(String.valueOf(appointment.getAccountId()));
        txtResult.setText(appointment.getResult());
        if(appointment.getBhyt() == true){
            txtBHYT.setText("Có BHYT");
        } else {
            txtBHYT.setText("Không có BHYT");
        }
        chkApprove.setChecked(appointment.isApproved());

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
        }) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        doctorQueue.add(request);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminAppointmentDetailActivity.this.setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public void Save(View view) {
        queue = Volley.newRequestQueue(this);
        String url ="https://medical-appointment-booking.herokuapp.com/api/Appointments/Admin/" + txtId.getText().toString();
        JSONObject object = new JSONObject();
        try{
            try {
                object.put("id", txtId.getText().toString());

            } catch (NumberFormatException e){
                e.printStackTrace();
            }
            object.put("isApproved", chkApprove.isChecked());
            object.put("note", edtNote.getText());

        } catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println(object.toString());
        JsonRequest request = new JsonObjectRequest(Request.Method.PUT, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        queue.add(request);
        Intent intent = new Intent(this, AdminAppointmentActivity.class);
        startActivity(intent);
    }
}