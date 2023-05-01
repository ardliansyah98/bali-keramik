package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.btikk.balikeramik.configs.GetDate;

public class LoginActivity extends AppCompatActivity {

    TextView tvToRegister;
    Button btnLogin;
    private GetDate getDate = new GetDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        //finish();
    }
}