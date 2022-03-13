package app.folder.medical_appointment_booking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.folder.medical_appointment_booking.Session.SesionManagement;
import app.folder.medical_appointment_booking.dao.AppointmentDAO;
import app.folder.medical_appointment_booking.dto.Appointment;
import app.folder.medical_appointment_booking.adapter.AppointmetntAdapter;
//import app.folder.medical_appointment_booking.adapter.AppointmentAdapter;

public class appointment_list_Activity extends AppCompatActivity {
    private AppointmetntAdapter adapter;
    ListView lvAppointment;
    EditText datePicker;
    Button btnFilter;
    final Calendar myCalendar= Calendar.getInstance();
   // Date datefilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        datePicker = findViewById(R.id.edtDateFilter);
        btnFilter = findViewById(R.id.btnFilter);
        SesionManagement sesionManagement = new SesionManagement(this);
       //Initiation of Date Picker
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(appointment_list_Activity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Click to search

        lvAppointment = (ListView)findViewById(R.id.lvAppointment);
        AppointmentDAO dao = new AppointmentDAO(appointment_list_Activity.this);
        int AppointmentID =0;
        adapter = new AppointmetntAdapter();

        dao.getAppointmentList(new AppointmentDAO.VolleyResponseListener() {

            @Override
            public void onError(String message) {
           Toast.makeText(appointment_list_Activity.this,"Lỗi load list"+message.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<Appointment> appointmentList) {
                ArrayAdapter<Appointment> arrayAdapter
                        = new ArrayAdapter<Appointment>(appointment_list_Activity.this, android.R.layout.simple_list_item_1 , appointmentList);
                adapter.setAppointmentDTOList(appointmentList);
                lvAppointment.setAdapter(arrayAdapter);
                lvAppointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Appointment dto = (Appointment) lvAppointment.getItemAtPosition(position);
                        Intent intent = new Intent(appointment_list_Activity.this,AppointmentDetailActivity.class);
                        intent.putExtra("AppointmentID",dto.getId());
                        startActivity(intent);
                    }
                });

            }
        },"");

        btnFilter.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
                String dateFilter = "";
                if(datePicker.getText().toString()!=null){
                    dateFilter =datePicker.getText().toString();
                }
                dao.getAppointmentList(new AppointmentDAO.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<Appointment> appointmentList) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        Date datefilter = null;
                        try {
                            datefilter = formatter.parse(datePicker.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter<Appointment> arrayAdapter
                                = new ArrayAdapter<Appointment>(appointment_list_Activity.this, android.R.layout.simple_list_item_1 , appointmentList);
                        adapter.setAppointmentDTOList(appointmentList);
                        lvAppointment.setAdapter(arrayAdapter);
                        lvAppointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Appointment dto = (Appointment) lvAppointment.getItemAtPosition(position);
                                Intent intent = new Intent(appointment_list_Activity.this,AppointmentDetailActivity.class);
                                intent.putExtra("AppointmentID",dto.getId());
                                startActivity(intent);
                            }
                        });
                    }
                },dateFilter);
            }
        });






    }
    private void updateLabel(){
        String myFormat="dd-MMM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        datePicker.setText(dateFormat.format(myCalendar.getTime()));
    }

}


/*
    private void JsonStringFromRequset(){
       // RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://medical-appointment-booking.herokuapp.com/api/Appointments";
        final String respondRequest = null;
        //Type type = new TypeToken<List<Appointment>>(){}.getType();
// Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String appoinemntDate = "";
                        Appointment a1 = null;
                        try {
                            JSONObject appointment = response.getJSONObject(0);
                            appoinemntDate = appointment.getString("appointmentDate");
                            Date appoinemntDateTypeDateTime = new Date(appoinemntDate);
                             a1 = new Appointment(1,appoinemntDateTypeDateTime,1,true,true,null
                                    ,1,null,null,null);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        Toast.makeText(appointment_list_Activity.this,"AppoinmentDate"+ a1.getAppointmentDate(),Toast.LENGTH_LONG).show();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appointment_list_Activity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }

        );

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }*/