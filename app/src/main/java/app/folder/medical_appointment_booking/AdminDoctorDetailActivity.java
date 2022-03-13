package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.folder.medical_appointment_booking.Session.SesionManagement;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;
import app.folder.medical_appointment_booking.dto.DoctorDTO;
import app.folder.medical_appointment_booking.dto.SpecialistDTO;

public class AdminDoctorDetailActivity extends AppCompatActivity {

    private EditText edtId, edtSpecialistId, edtFullName, edtAcademicRank;
    private Spinner spnSpecialistId;
    private Button btnUpdate, btnCancel;
    private CheckBox chkMale;
    private RequestQueue queue, specialistQueue;
    private String selectedSpinner;
    private int selectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_doctor_detail);

        edtId = findViewById(R.id.edtId);
        //edtSpecialistId = findViewById(R.id.edtSpecialistId);
        spnSpecialistId = findViewById(R.id.spnSpecialistId);
        edtFullName = findViewById(R.id.edtFullName);
        edtAcademicRank = findViewById(R.id.edtAcademicRank);
        chkMale = findViewById(R.id.chkMale);
        btnUpdate= findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnDetailCancel);

        DoctorDTO doctor = (DoctorDTO) getIntent().getExtras().get("DoctorDTO");
        edtId.setText(String.valueOf(doctor.getId()));
        edtFullName.setText(doctor.getFullName());
        edtAcademicRank.setText(doctor.getAcademicRank());
        chkMale.setChecked(doctor.isMale());

        edtId.setEnabled(false);

        List<SpecialistDTO> list = new ArrayList<>();
        List<String> listName = new ArrayList<>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialistQueue = Volley.newRequestQueue(this);
        String url ="https://medical-appointment-booking.herokuapp.com/api/Specialists";

        JsonRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;
                            for(int i=0; i< jsonArray.length(); i++){
                                JSONObject specialistObject =jsonArray.getJSONObject(i);
                                list.add(new SpecialistDTO(specialistObject.getInt("id"), specialistObject.getString("name")));
                                listName.add(specialistObject.getString("name"));
                            }
                            spnSpecialistId.setAdapter(dataAdapter);
                            spnSpecialistId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    int position = spnSpecialistId.getSelectedItemPosition();
                                    selectedId = list.get(position).getID();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response.toString());
                        Log.d("List", list.toString());
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

        specialistQueue.add(request);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDoctorDetailActivity.this.setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public void UpdateDoctor(View view) {
        queue = Volley.newRequestQueue(this);
        String url ="https://medical-appointment-booking.herokuapp.com/api/Doctors/" + edtId.getText().toString();
        JSONObject object = new JSONObject();
        try{
            try {
                object.put("id", edtId.getText().toString());
                object.put("specialistId", selectedId);

            } catch (NumberFormatException e){
                e.printStackTrace();
            }
            object.put("fullName", edtFullName.getText());
            object.put("academicRank", edtAcademicRank.getText());
            object.put("isMale", chkMale.isChecked());

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
        Intent intent = new Intent(this, AdminDoctorActivity.class);
        startActivity(intent);
    }
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
        this.finish();
    }
}