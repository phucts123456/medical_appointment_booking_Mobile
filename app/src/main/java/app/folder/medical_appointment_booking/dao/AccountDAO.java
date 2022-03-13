package app.folder.medical_appointment_booking.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import app.folder.medical_appointment_booking.LoginActivity;
import app.folder.medical_appointment_booking.dto.Account;
import app.folder.medical_appointment_booking.dto.Appointment;
import app.folder.medical_appointment_booking.dto.Doctor;
import app.folder.medical_appointment_booking.dto.Specialist;
import app.folder.medical_appointment_booking.Pattern.MySingleton;

public class AccountDAO {
    private final String requestUrl = "https://medical-appointment-booking.herokuapp.com/api/Accounts/";
    private final String CreateAccountUrl = "http://medical-appointment-booking.herokuapp.com/api/Accounts";
    public interface  AccountmentResponseListener{
        void onError(String message);
        void onResponse(Account account);
    }
    Context context;
    public AccountDAO(Context context) {

        this.context = context;
    }
    public void Login(String username, String password, AccountmentResponseListener accountmentResponseListener){

        String requestString = "https://medical-appointment-booking.herokuapp.com/api/Accounts/SignIn?username="+username+"&password="+password;
        Log.d("loginResquestString",requestString);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestString,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Account account = new Account();
                        try {
                            account.setUserName(response.getString("userName"));
                            account.setRole(response.getInt("role"));
                            account.setPassWord(response.getString("password"));
                            account.setId(response.getInt("id"));
                            account.setDotorID(response.getInt("doctorID"));
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }

                        accountmentResponseListener.onResponse(account);
                        //Toast.makeText(context,account.toString(),Toast.LENGTH_LONG).show();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                accountmentResponseListener.onError("Lỗi ở loginDAO");
            }
        }

        );
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public void Register(String username, String password){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userName",username);
            jsonObject.put("password",password);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }





        //  Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,CreateAccountUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Toast.makeText(context, "UserName  :"+response.getString("userName") +"Create Success", Toast.LENGTH_SHORT).show();
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) ;
        // Adding request to request queue
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
