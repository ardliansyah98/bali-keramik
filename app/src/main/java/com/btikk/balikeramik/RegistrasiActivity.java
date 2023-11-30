package com.btikk.balikeramik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    private final AppConfig appConfig = new AppConfig();
    private TextInputLayout TILNama, TILEmail, TILNoTelp, TILPassword, TILPassword2;
    private TextInputEditText EtNama, EtEmail, EtNoTelp, EtPassword, EtPassword2;
    private MaterialButton btnRegister;
    private RelativeLayout loadingPage;
    private LoginActivity loginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        // set objects
        TILNama = findViewById(R.id.TilNamaLengkap);
        TILEmail = findViewById(R.id.TilEmailRegister);
        TILNoTelp = findViewById(R.id.TilNoTelp);
        TILPassword = findViewById(R.id.TilPasswordRegistrasi);
        TILPassword2 = findViewById(R.id.TilConfirmPasswordRegistrasi);
        EtNama = findViewById(R.id.TxtNamaLengkap);
        EtEmail = findViewById(R.id.TxtEmailRegister);
        EtNoTelp = findViewById(R.id.TxtNoTelp);
        EtPassword = findViewById(R.id.TxtPasswordRegistrasi);
        EtPassword2 = findViewById(R.id.TxtConfirmPasswordRegistrasi);
        btnRegister = findViewById(R.id.btnRegistrasi);
        loadingPage = findViewById(R.id.loading_page);
        loginActivity = new LoginActivity();
        
        btnRegister.setOnClickListener(v -> {
            register();
        });

    }

    private void register() {
        loadingPage.setVisibility(View.VISIBLE);
        loginActivity.disableTouch();
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
                loginActivity.disableTouch();
                if(jsonObject.optString("error").equals("false")){
                    // success
                    // Toast.makeText(this, "Registrasi sukses!", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Akun berhasil dibuat!");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }
                    });
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Kesalahan");
                    alertDialog.setMessage(jsonObject.getString("message"));
                    alertDialog.setButton(-3, "Dimengerti", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    alertDialog.show();
                    loadingPage.setVisibility(View.GONE);
                    loginActivity.enableTouch();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            // Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Terjadi kesalahan : " + error.getMessage());
            builder.setNegativeButton("OK", (dialog, which) -> {
                dialog.dismiss();
            });
            loadingPage.setVisibility(View.GONE);
            loginActivity.enableTouch();
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