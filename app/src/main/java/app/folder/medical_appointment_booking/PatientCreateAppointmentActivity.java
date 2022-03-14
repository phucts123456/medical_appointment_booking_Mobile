package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.DatePicker;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Calendar;

import app.folder.medical_appointment_booking.dto.DoctorDTO;

public class PatientCreateAppointmentActivity extends AppCompatActivity {

    private EditText edtNote, edtDate;
    private Spinner spnDoctor;
    private CheckBox chkBHYT;
    private Button btnCancel;
    private RequestQueue queue, doctorQueue;
    private int selectedId;
    final Calendar myCalendar= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_create_appointment);
        spnDoctor = findViewById(R.id.spnDoctor);
        edtNote = findViewById(R.id.edtNote);
        edtDate = findViewById(R.id.edtDate);
        chkBHYT = findViewById(R.id.chkBHYT);
        btnCancel = findViewById(R.id.btnDetailCancel);

        List<DoctorDTO> list = new ArrayList<>();
        List<String> listName = new ArrayList<>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorQueue = Volley.newRequestQueue(this);
        String url ="https://medical-appointment-booking.herokuapp.com/api/Doctors";

        JsonRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject doctorObject = null;
                        try {
                            JSONArray jsonArray = response;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                doctorObject = jsonArray.getJSONObject(i);
                                list.add(new DoctorDTO(doctorObject.getInt("id"), doctorObject.getInt("specialistId"), doctorObject.getString("fullName"), doctorObject.getString("academicRank"), doctorObject.getInt("accountId"), doctorObject.getBoolean("isMale")));
                                listName.add(doctorObject.getString("fullName"));
                            }
                            spnDoctor.setAdapter(dataAdapter);
                            spnDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    int position = spnDoctor.getSelectedItemPosition();
                                    selectedId = list.get(position).getId();
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
                        Log.d("Listname", listName.toString());
                        Log.d("Object", doctorObject.toString());
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

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }

            private void updateLabel(){
                String myFormat="dd-MMM-yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                edtDate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PatientCreateAppointmentActivity.this, date, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientCreateAppointmentActivity.this.setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public void SaveAppointment(View view) {
        queue = Volley.newRequestQueue(this);
        String url ="https://medical-appointment-booking.herokuapp.com/api/Appointments";
        JSONObject object = new JSONObject();
        try{
            try {
                object.put("doctorid", selectedId);

            } catch (NumberFormatException e){
                e.printStackTrace();
            }
            object.put("bhty", chkBHYT.isChecked());
            object.put("appointmentDate", edtDate.getText().toString());
            object.put("note", edtNote.getText().toString());

        } catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println(object.toString());
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, url, object,
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
        Intent intent = new Intent(this, PatientAppointmentActivity.class);
        startActivity(intent);
    }
}