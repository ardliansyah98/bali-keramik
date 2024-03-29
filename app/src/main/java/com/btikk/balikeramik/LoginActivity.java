package com.btikk.balikeramik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.GetDate;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.configs.SharedPrefManager;
import com.btikk.balikeramik.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView tvToRegister;
    Button btnLogin;
    TextInputLayout TilEmail, TilPassword;
    TextInputEditText etEmail, etPassword;
    String email, password;
    AppConfig appConfig = new AppConfig();
    RelativeLayout rlLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TilEmail = (TextInputLayout) findViewById(R.id.TilEmailLogin);
        TilPassword = (TextInputLayout) findViewById(R.id.TilPasswordLogin);
        etEmail = (TextInputEditText) findViewById(R.id.TxtEmailLogin);
        etPassword = (TextInputEditText) findViewById(R.id.TxtPasswordLogin);
        rlLoading = findViewById(R.id.loading_page);

        rlLoading.setVisibility(View.GONE);

        // if logged in
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }

        // Linking to xml objects
        tvToRegister = (TextView) findViewById(R.id.TvToRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Go to Register Activity
        tvToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrasiActivity.class));
        });

        // Login Button
        btnLogin.setOnClickListener(v -> {
            login();
        });
    }

    private void login() {
        rlLoading.setVisibility(View.VISIBLE);
        disableTouch();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        TilEmail.clearFocus();
        TilPassword.clearFocus();

        if (TextUtils.isEmpty(email)) {
            TilEmail.setError("Email tidak boleh kosong");
            TilEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            TilPassword.setError("Password tidak boleh kosong");
            TilPassword.requestFocus();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.AuthUrl(), response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    disableTouch();
                    Log.d("Login Response", response);
                    if(jsonObject.optString("error").equals("false")){
                        // success
                        Intent intent = new Intent(this, DashboardActivity.class);
                        JSONObject userJson = jsonObject.getJSONObject("akun");
                        User user = new User(userJson.getInt("id"),
                                userJson.getInt("id_perajin"),
                                userJson.getInt("is_active"),
                                userJson.getString("nama"),
                                userJson.getString("email"),
                                userJson.getString("no_telp"),
                                userJson.getString("foto_profil"),
                                userJson.getString("date_created"));
                        SharedPrefManager.getInstance(this).userLogin(user);

                        startActivity(intent);
                        Log.d("User Account", userJson.toString());
                        finish();
                    } else {
                        // Toast.makeText(this, "Error: " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle("Kesalahan");
                        alertDialog.setMessage(jsonObject.getString("message"));
                        alertDialog.setButton(-3, "Dimengerti", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        alertDialog.show();
                        rlLoading.setVisibility(View.GONE);
                        enableTouch();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                error.printStackTrace();
                // Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Terjadi kesalahan : " + error.getMessage());
                builder.setNegativeButton("OK", (dialog, which) -> dialog.dismiss());
                builder.create();
                rlLoading.setVisibility(View.GONE);
                enableTouch();
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("login", "1");
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            //startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            //finish();
            MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    public void disableTouch(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                             WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void enableTouch(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}