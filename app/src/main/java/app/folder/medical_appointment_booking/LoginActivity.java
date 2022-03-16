package app.folder.medical_appointment_booking;

import androidx.annotation.NonNull;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import app.folder.medical_appointment_booking.dao.AccountDAO;
import app.folder.medical_appointment_booking.dto.Account;
import app.folder.medical_appointment_booking.dto.Appointment;
import app.folder.medical_appointment_booking.Session.SesionManagement;

public class LoginActivity extends AppCompatActivity {
    EditText  username,password;
    Button btnLogin;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    public final static int RC_SIGN_IN = 100;

    @Override
    protected void onStart() {
        super.onStart();
        // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // updateUI(account);
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
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
        createRequest();
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });
        AccountDAO dao = new AccountDAO(LoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.Login(username.getText().toString(), password.getText().toString(), new AccountDAO.AccountmentResponseListener() {
                    @Override
                    public void onError(String message) {
                      Toast.makeText(LoginActivity.this, "Login Fail."+message, Toast.LENGTH_SHORT).show();

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
    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("843044638216-4c8ejgs2ojk0r1kfmha9gkmhnlh2nvsv.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                AccountDAO dao = new AccountDAO(LoginActivity.this);
                dao.Register(account.getDisplayName(),"",account.getId());
                dao.GoogleLogin(account.getId(), new AccountDAO.AccountmentResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(LoginActivity.this,"Login Error:"+message,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Account account) {
                        SesionManagement sesionManagement = new SesionManagement(LoginActivity.this);
                        sesionManagement.saveSession(account);
                        Toast.makeText(LoginActivity.this, "Login thanh cong user :"+account.getUserName(), Toast.LENGTH_SHORT).show();
                        MoveToPatientHome();
                    }
                });
            }catch (ApiException e){
                //Toast.makeText(this,"Loi ở onActivityResult :"+e, Toast.LENGTH_SHORT).show();
                Log.d("c","CodeAPIEX: " +String.valueOf(e.getStatusCode()));
                Toast.makeText(this, "Lỗi ở login google"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this, "Sorry auth failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void SignIn(){
        Intent SignInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(SignInIntent,RC_SIGN_IN);
    }

}