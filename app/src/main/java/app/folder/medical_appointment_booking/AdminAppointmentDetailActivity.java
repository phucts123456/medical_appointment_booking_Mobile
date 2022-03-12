package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.List;

import app.folder.medical_appointment_booking.dto.AppointmentDTO;

public class AdminAppointmentDetailActivity extends AppCompatActivity {

    private EditText edtId, edtDate, edtDoctorId, edtNote, edtAccountId, edtResult;
    private Button btnUpdate, btnCancel;
    private CheckBox chkBHYT, chkApprove;
    List<AppointmentDTO> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointment_detail);
        edtId = findViewById(R.id.edtId);
        //edtDate = findViewById(R.id.edtDate);
        edtNote = findViewById(R.id.edtNote);
        edtDoctorId = findViewById(R.id.edtDoctorId);
        edtAccountId = findViewById(R.id.edtAccountId);
        edtResult = findViewById(R.id.edtResult);
        btnUpdate= findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnDetailCancel);
        chkBHYT = findViewById(R.id.chkBHYT);
        chkApprove = findViewById(R.id.chkApprove);

        AppointmentDTO appointment = (AppointmentDTO) getIntent().getExtras().get("AppointmentDTO");
        System.out.println(appointment.id);
        edtId.setText(String.valueOf(appointment.getId()));
        //edtDate.setText(appointment.getAppointmentDate().toString());
        edtDoctorId.setText(String.valueOf(appointment.getDoctorId()));
        edtNote.setText(appointment.getNote());
        edtAccountId.setText(String.valueOf(appointment.getAccountId()));
        edtResult.setText(appointment.getResult());
        chkBHYT.setChecked(appointment.getBhyt());
        chkApprove.setChecked(appointment.isApproved());

        edtId.setEnabled(false);
        edtDoctorId.setEnabled(false);
        edtAccountId.setEnabled(false);
        edtResult.setEnabled(false);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminAppointmentDetailActivity.this.setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}