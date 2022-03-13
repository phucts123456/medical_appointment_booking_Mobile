package app.folder.medical_appointment_booking.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import app.folder.medical_appointment_booking.dto.Account;

public class SesionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user_id";
    String SESSION_D_KEY = "session_doctor_id";
    String ROLE_KEY = "session_role";
    public SesionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveSession(Account account){
        int id = account.getId();
        Log.d("LoginUser","NGười dùng" + String.valueOf(id));

        int docID= account.getDotorID();
        Log.d("Doctor","NGười dùng" + String.valueOf(docID));
        int role = account.getRole();
        editor.putInt(SESSION_KEY,id).commit();
        editor.putInt(SESSION_D_KEY,docID).commit();
        editor.putInt(ROLE_KEY,role).commit();
    }
    public int getUserID(){
        //sharedPreferences.getInt(SESSION_KEY,-1);
        return  sharedPreferences.getInt(SESSION_KEY,-1);
    }
    public int getDoctorID(){
        //sharedPreferences.getInt(SESSION_D_KEY,-1);
        return  sharedPreferences.getInt(SESSION_D_KEY,-1);
    }
    public int getRoleID(){
        return  sharedPreferences.getInt(ROLE_KEY,-1);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
        editor.putInt(SESSION_D_KEY,-1).commit();
    }

}
