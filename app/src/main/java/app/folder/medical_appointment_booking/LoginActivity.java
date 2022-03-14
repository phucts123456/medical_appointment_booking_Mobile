package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.folder.medical_appointment_booking.dao.AccountDAO;
import app.folder.medical_appointment_booking.dto.Account;
import app.folder.medical_appointment_booking.dto.Appointment;
import app.folder.medical_appointment_booking.Session.SesionManagement;

public class LoginActivity extends AppCompatActivity {
    EditText  username,password;
    Button btnLogin;

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }
    private void checkSession(){
        SesionManagement sesionManagement = new SesionManagement(LoginActivity.this);
        int userID = sesionManagement.getUserID();
        /*if(userID != -1){
            //user id logged in and move to mainActivity
            MoveToDoctorHome();
        }else {
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        AccountDAO dao = new AccountDAO(LoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.Login(username.getText().toString(), password.getText().toString(), new AccountDAO.AccountmentResponseListener() {
                    @Override
                    public void onError(String message) {
                       // Toast.makeText(LoginActivity.this, "Login Fail.Wrong username or password", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(Account account) {
                        SesionManagement sesionManagement = new SesionManagement(LoginActivity.this);
                        sesionManagement.saveSession(account);
                        Log.d("loginError","Account ở LoginActivity"+account.toString());
                        if(account.getRole()==1){
                            MoveToAdminPage();
                        }else if(account.getRole()==2){
                            MoveToDoctorHome();

                        }else{
                            MoveToPatientHome();
                        }

                    }
                });

            }
        });
    }

    public void MoveToPatientHome(){
        Log.d("MoveToPatientHome","MoveToPatientHome");
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
    }
    public void MoveToDoctorHome(){
        Log.d("MoveToDoctorHome","MoveToDoctorHome");
        Intent intent = new Intent(LoginActivity.this,Doctor_HomePage_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
    }
    public void MoveToAdminPage(){
        Log.d("MoveToAdminPage","MoveToAdminPage");
        Intent intent = new Intent(LoginActivity.this,AdminPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.regis_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnRegis:
                MoveToRegisPage();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void MoveToRegisPage() {
        Intent intent = new Intent(LoginActivity.this,register_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}