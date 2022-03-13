package app.folder.medical_appointment_booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import app.folder.medical_appointment_booking.Session.SesionManagement;

public class Doctor_HomePage_Activity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home_page);
        btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoveToAppointmentListForDoctor();
            }
        });
    }
//logout add menu to title bar and logout Code
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
        finish();
    }
//end logout add menu to title bar and logout Code

    public void MoveToAppointmentListForDoctor(){
        Intent intent = new Intent(this,appointment_list_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}