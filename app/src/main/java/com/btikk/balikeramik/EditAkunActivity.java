package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.SharedPrefManager;
import com.btikk.balikeramik.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditAkunActivity extends AppCompatActivity {
    TextInputLayout TIL1;
    TextInputLayout TIl2;
    AppConfig appConfig = new AppConfig();
    Button btnGantiPassword;
    Button btnSimpan;
    String email;
    TextInputEditText etEmail;
    TextInputEditText etNama;
    TextInputEditText etPhone;
    // CircleImageView fotoProfil;
    RelativeLayout loading;
    String nama;
    String noHp;
    Toolbar toolbar;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);

        this.toolbar = (Toolbar) findViewById(R.id.akun_toolbar);
        this.TIL1 = (TextInputLayout) findViewById(R.id.TIl1);
        this.TIl2 = (TextInputLayout) findViewById(R.id.TIl2);
        this.etNama = (TextInputEditText) findViewById(R.id.et_nama);
        this.etEmail = (TextInputEditText) findViewById(R.id.et_email);
        this.etPhone = (TextInputEditText) findViewById(R.id.et_no_hp);
        this.btnSimpan = (Button) findViewById(R.id.btn_save);
        this.btnGantiPassword = (Button) findViewById(R.id.btn_ganti_password);

        this.toolbar.setTitle("Edit Akun");

        // If user is not logged in
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }

        user = SharedPrefManager.getInstance(this).getUser();
        etNama.setText(user.getNama());
        etEmail.setText(user.getEmail());
        etPhone.setText(user.getNo_telp());
    }
}