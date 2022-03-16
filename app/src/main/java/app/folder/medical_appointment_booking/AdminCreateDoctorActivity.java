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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.folder.medical_appointment_booking.Session.SesionManagement;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;
import app.folder.medical_appointment_booking.dto.SpecialistDTO;

public class AdminCreateDoctorActivity extends AppCompatActivity {

    private EditText edtFullName, edtAcademicRank, edtUsername, edtPassword;
    private Spinner spnSpecialistId;
    private Button btnSave, btnCancel;
    private CheckBox chkMale;
    private RequestQueue queue, specialistQueue;
    private String selectedSpinner;
    private int selectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_doctor);
        spnSpecialistId = findViewById(R.id.spnSpecialistId);
        edtFullName = findViewById(R.id.edtFullName);
        edtAcademicRank = findViewById(R.id.edtAcademicRank);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        chkMale = findViewById(R.id.chkMale);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnDetailCancel);

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
                                    selectedSpinner = spnSpecialistId.getSelectedItem().toString();
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
                AdminCreateDoctorActivity.this.setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void SaveDoctor(View view) {
        queue = Volley.newRequestQueue(this);
        String url ="https://medical-appointment-booking.herokuapp.com/api/Doctors/Admin";
        JSONObject object = new JSONObject();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String password = edtPassword.getText().toString();
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        String hashPassword = bytesToHex(encodedhash);
        try{
            try {
                object.put("specialistId", selectedId);//edtSpecialistId.getText().toString());

            } catch (NumberFormatException e){
                e.printStackTrace();
            }
            object.put("fullName", edtFullName.getText());
            object.put("academicRank", edtAcademicRank.getText());
            object.put("isMale", chkMale.isChecked());
            object.put("username", edtUsername.getText());
            object.put("password", hashPassword);

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