package app.folder.medical_appointment_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.folder.medical_appointment_booking.dao.AccountDAO;
import app.folder.medical_appointment_booking.dto.Account;

public class register_activity extends AppCompatActivity {
    EditText edtUserName ,edtPassword;
    Button btnRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtPassword = findViewById(R.id.edtPasswordRegis);
        edtUserName = findViewById(R.id.edtUserNameRegis);
        btnRegis = findViewById(R.id.btnRegis);
        AccountDAO dao = new AccountDAO(register_activity.this);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.Register(edtUserName.getText().toString(),edtPassword.getText().toString());
                MoveToLoginPage();
            }
        });
    }
    public void MoveToLoginPage(){
        Intent intent = new Intent(register_activity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}