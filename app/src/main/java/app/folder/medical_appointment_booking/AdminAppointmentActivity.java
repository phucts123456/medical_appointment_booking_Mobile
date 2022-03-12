package app.folder.medical_appointment_booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import app.folder.medical_appointment_booking.adapter.AppointmentAdapter;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;

public class AdminAppointmentActivity extends AppCompatActivity {

    private TextView textView;
    private RequestQueue queue;
    private List<AppointmentDTO> lst;
    private GridView gridView;
    private AppointmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointment);

        textView = findViewById(R.id.txtError);
        gridView = findViewById(R.id.gridView);

        queue = Volley.newRequestQueue(this);

        jsonParse();

        //textView.setText("Welcome back Admin, click to Edit the note\n");
        //adapter = new AppointmentAdapter(lst);
        //gridView.setAdapter(adapter);
    }

    private void jsonParse(){
        lst = new ArrayList<>();
        String url ="https://medical-appointment-booking.herokuapp.com/api/Appointments";

        JsonRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            JSONArray jsonArray = response;
                            for(int i=0; i< jsonArray.length(); i++){
                                JSONObject appointmentObject =jsonArray.getJSONObject(i);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                int id = appointmentObject.getInt("id");
                                Date appointmentDate = null;
                                try {
                                    appointmentDate = format.parse(appointmentObject.getString("appointmentDate"));
                                    System.out.println(appointmentDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                int DoctorId = appointmentObject.getInt("doctorId");
                                boolean Bhyt = appointmentObject.getBoolean("bhty");
                                boolean isApproved = appointmentObject.getBoolean("isApproved");
                                String note = appointmentObject.getString("note");
                                int accountId = appointmentObject.getInt("accountId");
                                String Result = appointmentObject.getString("result");
                                AppointmentDTO appointment = new AppointmentDTO(id, appointmentDate, DoctorId, Bhyt, isApproved, note, accountId, Result);
                                lst.add(appointment);
                                //textView.setText("Welcome back Admin, click to Edit the note\n");
                            }
                            adapter = new AppointmentAdapter(lst);
                            gridView.setAdapter(adapter);

                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(AdminAppointmentActivity.this, AdminAppointmentDetailActivity.class);
                                    AppointmentDTO selectedAppointment = (AppointmentDTO) gridView.getItemAtPosition(i);

                                    AppointmentDTO detail = new AppointmentDTO();
                                    for(AppointmentDTO f : lst){
                                        if(f.getId() == selectedAppointment.getId()){
                                            detail = f;
                                            break;
                                        }
                                    }

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("AppointmentDTO", detail);
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
}