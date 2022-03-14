package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import app.folder.medical_appointment_booking.Session.SesionManagement;
import app.folder.medical_appointment_booking.dao.AppointmentDAO;
import app.folder.medical_appointment_booking.dto.Appointment;
import jp.wasabeef.richeditor.RichEditor;

public class AppointmentDetailActivity extends AppCompatActivity {
    TextView txtSpec,txtDocName,txtGender,txtRank,txtDate,txtNote,txtStatus,txtPatient;
    EditText edtResult;
    Button updateBtn;
    //private RichEditor mEditor;
   // private TextView mPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        txtPatient = findViewById(R.id.txtPatient);
        txtDate = findViewById(R.id.txtDate);
        txtDocName = findViewById(R.id.txtDocName);
        txtGender = findViewById(R.id.txtGender);
        txtRank = findViewById(R.id.txtAcaRank);
        txtNote = findViewById(R.id.txtNote);
        txtStatus = findViewById(R.id.txtStatus);
        txtSpec = findViewById(R.id.txtSpec);
        edtResult = findViewById(R.id.edtResult);
        updateBtn = findViewById(R.id.btnUpdate);
       /*mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);*/
        Intent intent = getIntent();
        int id = intent.getIntExtra("AppointmentID",0);
        AppointmentDAO dao = new AppointmentDAO(AppointmentDetailActivity.this);
        dao.getAppointmentDetail(new AppointmentDAO.AppointmentResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(AppointmentDetailActivity.this,message.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Appointment appointment) {
                //Toast.makeText(AppointmentDetailActivity.this,appointment.toString(), Toast.LENGTH_SHORT).show();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = formatter.format(appointment.getAppointmentDate());
                txtDate.setText(strDate);
                txtDocName.setText(appointment.getDoctor().getFullName());
                txtNote.setText(appointment.getNote());
                txtRank.setText(appointment.getDoctor().getAcademicRank());
                //txtStatus.setText(appointment.getStatusWithString(appointment.isApproved()));
                txtStatus.setText(String.valueOf(appointment.isApproved()));
                edtResult.setText(appointment.getResult());
                txtSpec.setText(appointment.getDoctor().getSpec().getName());
                txtGender.setText(appointment.getDoctor().getGenderToReturnString(appointment.getDoctor().isMale()));
                txtPatient.setText(appointment.getAccount().getUserName());
            }
        }, id);

       updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.updateAppointmentResult(AppointmentDetailActivity.this,id,edtResult.getText().toString());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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

    @Override
    protected void onPause() {
        super.onPause();
    }


}