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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public void Login(String username, String password, AccountmentResponseListener accountmentResponseListener){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        String hashPassword = bytesToHex(encodedhash);
        String requestString = "https://medical-appointment-booking.herokuapp.com/api/Accounts/SignIn";
        Log.d("loginError","request Login String: "+requestString);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username",username);
            jsonObject.put("password",hashPassword);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, requestString,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(context,response.toString(),Toast.LENGTH_SHORT).show();
                        //tra ve 2
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
                        Log.d("loginError","response lúc login : "+response.toString());

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Wrong username or password",Toast.LENGTH_LONG).show();
                accountmentResponseListener.onError("Lỗi ở loginDAO");
            }
        }

        );
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public void GoogleLogin(String ggID, AccountmentResponseListener accountmentResponseListener){

        String requestString = "https://medical-appointment-booking.herokuapp.com/api/Accounts/loginWithGoogleID?ID="+ggID;
        Log.d("accountggURL",requestString);
        Log.d("loginError","request Login String: "+requestString);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestString,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("accountgg",response.toString());

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
                       // Log.d("accountgg",account.toString());
                        accountmentResponseListener.onResponse(account);
                        //Toast.makeText(context,account.toString(),Toast.LENGTH_LONG).show();


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Wrong username or password",Toast.LENGTH_LONG).show();
                accountmentResponseListener.onError("Lỗi ở loginDAO");
            }
        }

        );
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public void Register(String username, String password,String googleID){
        JSONObject jsonObject = new JSONObject();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        String hashPassword = bytesToHex(encodedhash);
        try {
            jsonObject.put("userName",username);
            jsonObject.put("password",hashPassword);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        //  Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,CreateAccountUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Toast.makeText(context, "UserName  :"+response.getString("userName") +" create Success", Toast.LENGTH_SHORT).show();
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) ;
        // Adding request to request queue
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
