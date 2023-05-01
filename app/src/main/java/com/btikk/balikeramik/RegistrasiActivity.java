package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.GetDate;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrasiActivity extends AppCompatActivity {
    private AppConfig appConfig = new AppConfig();
    private TextInputLayout TILNama, TILEmail, TILNoTelp, TILPassword, TILPassword2;
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
        TILPassword2 = (TextInputLayout) findViewById(R.id.TilConfirmPasswordRegistrasi);
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
        String nama = EtNama.getText().toString().trim();
        String email = EtEmail.getText().toString().trim();
        String noTelp = EtNoTelp.getText().toString().trim();
        String password = EtPassword.getText().toString().trim();
        String password2 = EtPassword2.getText().toString().trim();

        if(TextUtils.isEmpty(nama)){
            TILNama.setError("Nama tidak boleh kosong!");
            TILNama.requestFocus();
        } else if(TextUtils.isEmpty(email)){
            TILEmail.setError("Email tidak boleh kosong!");
            TILEmail.requestFocus();
        } else if(TextUtils.isEmpty(noTelp)){
            TILNoTelp.setError("Nomor Telepon tidak boleh kosong!");
            TILNoTelp.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            TILPassword.setError("Password tidak boleh kosong!");
            TILPassword.requestFocus();
        } else if(TextUtils.isEmpty(password2)){
            TILPassword2.setError("Konfirmasi Password tidak boleh kosong!");
            TILPassword2.requestFocus();
        } else if(!password.equals(password2)){
            TILPassword2.setError("Password tidak sama!");
            TILPassword2.requestFocus();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.AuthUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                loadingPage.setVisibility(View.VISIBLE);
                if(jsonObject.optString("error").equals("false")){
                    // success
                    Toast.makeText(this, "Registrasi sukses!", Toast.LENGTH_LONG).show();
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
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}