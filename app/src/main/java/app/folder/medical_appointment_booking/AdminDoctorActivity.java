package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.folder.medical_appointment_booking.adapter.AppointmentAdapter;
import app.folder.medical_appointment_booking.adapter.DoctorAdapter;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;
import app.folder.medical_appointment_booking.dto.DoctorDTO;

public class AdminDoctorActivity extends AppCompatActivity {

    private TextView textView;
    private RequestQueue queue;
    private List<DoctorDTO> lst;
    private GridView gridView;
    private DoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_doctor);

        textView = findViewById(R.id.txtError);
        gridView = findViewById(R.id.gridView);

        queue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private void jsonParse(){
        lst = new ArrayList<>();
        String url ="https://medical-appointment-booking.herokuapp.com/api/Doctors";

        JsonRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            JSONArray jsonArray = response;
                            for(int i=0; i< jsonArray.length(); i++){
                                JSONObject appointmentObject =jsonArray.getJSONObject(i);


                                int id = appointmentObject.getInt("id");
                                int SpecialistId = appointmentObject.getInt("specialistId");
                                String name = appointmentObject.getString("fullName");
                                String AcademicRank = appointmentObject.getString("academicRank");
                                int accountId = appointmentObject.getInt("accountId");
                                boolean male = appointmentObject.getBoolean("isMale");
                                DoctorDTO doctor = new DoctorDTO(id, SpecialistId, name, AcademicRank, accountId, male);
                                lst.add(doctor);
                            }
                            adapter = new DoctorAdapter(lst);
                            gridView.setAdapter(adapter);

                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(AdminDoctorActivity.this, AdminDoctorDetailActivity.class);
                                    DoctorDTO selectedDoctor = (DoctorDTO) gridView.getItemAtPosition(i);

                                    DoctorDTO detail = new DoctorDTO();
                                    for(DoctorDTO f : lst){
                                        if(f.getId() == selectedDoctor.getId()){
                                            detail = f;
                                            break;
                                        }
                                    }

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("DoctorDTO", detail);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, 234);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    public void CreateDoctor(View view) {
        Intent intent = new Intent(this, AdminCreateDoctorActivity.class);
        startActivity(intent);
    }

    public void BackToAdminPage(View view) {
        Intent intent = new Intent(this, AdminPageActivity.class);
        startActivity(intent);
    }
}