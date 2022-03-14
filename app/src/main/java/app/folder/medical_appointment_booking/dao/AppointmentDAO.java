package app.folder.medical_appointment_booking.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.folder.medical_appointment_booking.Pattern.MySingleton;
import app.folder.medical_appointment_booking.Session.SesionManagement;
import app.folder.medical_appointment_booking.dto.Account;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;

public class AppointmentDAO {
    public List<AppointmentDTO> loadFromInternal() {
        List<AppointmentDTO> result = new ArrayList<>();
        AppointmentDTO flower = null;

        return result;
    }
    private final String  request_url = "https://medical-appointment-booking.herokuapp.com/api/Appointments/";
    private final String  doctorByID_url = "https://medical-appointment-booking.herokuapp.com/api/Doctors/";
    private  final  String updateResult =  "https://medical-appointment-booking.herokuapp.com/updateResult/";
    Context context;

    public AppointmentDAO(Context context) {
        this.context = context;
    }
    public interface  DoctorResponseListener{
        void onError(String message);
        void onResponse(app.folder.medical_appointment_booking.dto.Doctor doctor);
    }
    public interface  AppointmentResponseListener{
        void onError(String message);
        void onResponse(app.folder.medical_appointment_booking.dto.Appointment appointment);
    }
    public interface  AccountResponseListener{
        void onError(String message);
        void onResponse(app.folder.medical_appointment_booking.dto.Account account);
    }
    public interface  VolleyResponseListener{
        void onError(String message);
        void onResponse(List<app.folder.medical_appointment_booking.dto.Appointment> appointmentList);
    }
    public void updateAppointmentResult(Context context,int id,String result){
        JSONObject jsonObject = new JSONObject();
        try {
            try
            {
                jsonObject.put("id", id );
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
            jsonObject.put("result",result);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String updateURL = updateResult+id;
        Log.d("updateResult",updateURL);
        //  Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, updateURL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("updateResult","Response: "+response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Updated ID : "+id, Toast.LENGTH_SHORT).show();
                    }
                }
        ) ;
        // Adding request to request queue
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public void getAppointmentDetail(AppointmentResponseListener appointmentResponseListener,int appID){
        String requestString = request_url+appID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestString, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        app.folder.medical_appointment_booking.dto.Appointment appointment = new app.folder.medical_appointment_booking.dto.Appointment();
                        app.folder.medical_appointment_booking.dto.Doctor doctor = new app.folder.medical_appointment_booking.dto.Doctor();
                        app.folder.medical_appointment_booking.dto.Specialist spec = new app.folder.medical_appointment_booking.dto.Specialist();
                        app.folder.medical_appointment_booking.dto.Account account = new Account();
                        try {



                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            // SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            appointment.setAppointmentDate(format.parse(response.getString("appointmentDate")));
                            if(response.getString("isApproved")!=null){
                                appointment.setApproved(Boolean.parseBoolean(response.getString("isApproved")));
                            }
                            if(response.getString("bhty")!=null){
                                appointment.setApproved(Boolean.parseBoolean(response.getString("bhty")));
                            }
                            JSONObject Doctor = response.getJSONObject("doctor");
                            JSONObject Account = response.getJSONObject("account");
                            account.setUserName(Account.getString("userName"));
                            doctor.setFullName(Doctor.getString("fullName"));
                            doctor.setSpecialistId(Doctor.getInt("specialistId"));
                            doctor.setAcademicRank(Doctor.getString("academicRank"));
                            JSONObject specObject = Doctor.getJSONObject("specialist");
                            spec.setName(specObject.getString("name"));
                            spec.setID(specObject.getInt("id"));

                            doctor.setSpec(spec);

                            doctor.setIsMale(Doctor.getBoolean("isMale"));
                            appointment.setId(response.getInt("id"));
                            appointment.setNote(response.getString("note"));

                            appointment.setDoctorId(response.getInt("doctorId"));
                            appointment.setAccountId(response.getInt("accountId"));
                            appointment.setResult(response.getString("result"));
                            appointment.setDoctor(doctor);
                            appointment.setAccount(account);
                            appointmentResponseListener.onResponse(appointment);
                        } catch (JSONException | ParseException exception) {
                            exception.printStackTrace();
                        }


                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();

                appointmentResponseListener.onError("Loi ở getDetailinDAO"+error.toString());
            }
        }

        );
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public void getAppointmentList(VolleyResponseListener volleyResponseListener,String date){

        Toast.makeText(context,date.toString(),Toast.LENGTH_SHORT);
        List<app.folder.medical_appointment_booking.dto.Appointment> appointmentList = new ArrayList<app.folder.medical_appointment_booking.dto.Appointment>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateNow = new Date();
        if (date == ""){
            date = dateFormat.format(dateNow);
        }
        SesionManagement sesionManagement = new SesionManagement(context);
        int docID = sesionManagement.getDoctorID();
       // Log.d("doctorID:","Doctorid o appointmentList: "+String.valueOf(docID));
        String getListURL = "https://medical-appointment-booking.herokuapp.com/api/appsCustomGet/appListForDoc?docID="+docID+"&filterDate="+date;
        //Log.d("url",getListURL);
       // Toast.makeText(context, getListURL, Toast.LENGTH_SHORT).show();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, getListURL,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(context,response.toString(),Toast.LENGTH_SHORT).show();
                        try {

                            for (int i =0 ; i < response.length();i++){
                                app.folder.medical_appointment_booking.dto.Appointment appointment = new app.folder.medical_appointment_booking.dto.Appointment();
                                JSONObject requestAppointment = response.getJSONObject(i);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                // SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                appointment.setAppointmentDate(format.parse(requestAppointment.getString("appointmentDate")));
                                if(requestAppointment.getString("isApproved")!=null){
                                    appointment.setApproved(Boolean.parseBoolean(requestAppointment.getString("isApproved")));
                                }
                                if(requestAppointment.getString("bhty")!=null){
                                    appointment.setApproved(Boolean.parseBoolean(requestAppointment.getString("bhty")));
                                }
                                // appointment.setBhty(requestAppointment.getBoolean("bhty"));
                                appointment.setId(requestAppointment.getInt("id"));
                                appointment.setNote(requestAppointment.getString("note"));
                                appointment.setDoctorId(requestAppointment.getInt("doctorId"));
                                appointment.setAccountId(requestAppointment.getInt("accountId"));
                                appointment.setResult(requestAppointment.getString("result"));
                                appointmentList.add(appointment);

                            }

                            volleyResponseListener.onResponse(appointmentList);
                        }catch (Exception ex){
                            Log.d("Lỗi Load list", ex.getMessage());;
                            Toast.makeText(context,"AppoinmentDate2: "+ ex.toString(),Toast.LENGTH_LONG).show();

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();

                volleyResponseListener.onError("Loi ở appointmentList"+error.toString());
            }
        }

        );

// Add the request to the RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
}
