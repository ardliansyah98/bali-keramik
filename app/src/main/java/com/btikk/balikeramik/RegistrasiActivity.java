package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.configs.AppConfig;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrasiActivity extends AppCompatActivity {
    private AppConfig appConfig = new AppConfig();
    private TextInputLayout TILNama, TILEmail, TILNoTelp, TILPassword;
    private TextInputEditText EtNama, EtEmail, EtNoTelp, EtPassword, EtPassword2;
    private MaterialButton btnRegister;
    private RelativeLayout loadingPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        // set objects
        TILNama = (TextInputLayout) findViewById(R.id.TilNamaLengkap);
        TILEmail = (TextInputLayout) findViewById(R.id.TilEmailRegister);
        TILNoTelp = (TextInputLayout) findViewById(R.id.TilNoTelp);
        TILPassword = (TextInputLayout) findViewById(R.id.TilPasswordRegistrasi);
        EtNama = findViewById(R.id.TxtNamaLengkap);
        EtEmail = findViewById(R.id.TxtEmailRegister);
        EtNoTelp = findViewById(R.id.TxtNoTelp);
        EtPassword = findViewById(R.id.TxtPasswordRegistrasi);
        EtPassword2 = findViewById(R.id.TxtConfirmPasswordRegistrasi);
        btnRegister = findViewById(R.id.btnRegistrasi);
        loadingPage = (RelativeLayout) findViewById(R.id.loading_page);
        
        btnRegister.setOnClickListener(v -> {
            register();
        });

    }

    private void register() {
        String nama = EtNama.getText().toString();
        String email = EtEmail.getText().toString();
        String noTelp = EtNoTelp.getText().toString();
        String password = EtPassword.getText().toString();
        String password2 = EtPassword2.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.AuthUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                loadingPage.setVisibility(View.VISIBLE);
                if(jsonObject.optString("error").equals("false")){
                    // success
                    Toast.makeText(this, "Registrasi sukses!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            loadingPage.setVisibility(View.GONE);
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("register", "1");
                params.put("nama_lengkap", nama);
                params.put("email", email);
                params.put("no_telp", noTelp);
                params.put("password", password);
                return params;
            }
        };
    }
}