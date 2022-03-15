package app.folder.medical_appointment_booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.folder.medical_appointment_booking.Session.SesionManagement;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private TextView textView;
    private RequestQueue queue;
    private FirebaseAuth googleSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.txtError);

        queue = Volley.newRequestQueue(this);
    }

    public void MoveToHistoryAppointmentPage(View view) {
        Intent intent = new Intent(this, PatientAppointmentActivity.class);
        startActivity(intent);
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
    public void moveToLoginPage(){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void  logout(){
        SesionManagement sessionManagement = new SesionManagement(this);
        sessionManagement.removeSession();
        FirebaseAuth.getInstance().signOut();
        moveToLoginPage();
        finish();
    }
//end logout add menu to title bar and logout Code

}