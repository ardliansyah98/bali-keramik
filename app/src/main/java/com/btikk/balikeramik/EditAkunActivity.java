package com.btikk.balikeramik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.configs.SharedPrefManager;
import com.btikk.balikeramik.models.User;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    ImageView gbrfotoProfil;
    Toolbar toolbar;
    User user;
    String namaAkun, emailAkun, noTelpAkun;

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
        this.gbrfotoProfil = findViewById(R.id.gambar_profil);

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
        Glide.with(this).load(appConfig.BaseUrl(user.getFotoProfil())).placeholder(R.drawable.ic_account).error(R.drawable.ic_account).into(gbrfotoProfil);

        Toast.makeText(this, "Id akun = " + user.getId(), Toast.LENGTH_SHORT).show();

        btnSimpan.setOnClickListener(v -> {
            updateUser(user.getId(), etNama.getText().toString(), etEmail.getText().toString(), etPhone.getText().toString());
        });
        
        btnGantiPassword.setOnClickListener(v -> {
            updatePassword(user.getId());
        });
    }

    private void updatePassword(int id) {
    }

    private void updateUser(int id, String nama, String email, String phone) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.AuthUrl(), response -> {
            Log.d("UpdateUser", "updateUser: " + response.toString());
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Update", "updateUser: " + response);
                if(jsonObject.optString("error").equals("false")){
                    // Success
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Berhasil");
                    alertDialog.setMessage("Perubahan akun berhasil! Silahkan login ulang!");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Dimengerti", (dialog, which) -> {
                        dialog.dismiss();
                        this.finish();
                        SharedPrefManager.getInstance(this).logout();
                        this.startActivity(new Intent(this, DashboardActivity.class));
                    });
                    alertDialog.show();
                    Toast.makeText(this, "Success! " + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                } if(jsonObject.optString("error").equals("true")){
                    Toast.makeText(this, "Error: " + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(EditAkunActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(EditAkunActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("update_user", "1");
                params.put("id_akun", String.valueOf(id));
                params.put("nama_lengkap", nama);
                params.put("email", email);
                params.put("no_telp", phone);
                return params;
            }
        };
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}