package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.GetDate;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.configs.SharedPrefManager;
import com.btikk.balikeramik.fragments.PerajinProdukFragment;
import com.btikk.balikeramik.fragments.PerajinProfilFragment;
import com.btikk.balikeramik.models.User;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerajinDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private AppConfig appConfig = new AppConfig();
    private Fragment productFragment = new PerajinProdukFragment();
    private Fragment profilFragment = new PerajinProfilFragment();
    private FragmentManager fm = getSupportFragmentManager();
    Fragment active = this.productFragment;
    private BottomNavigationView navPerajin;
    TextView txtNamaPerajin, txtLokasi, txtPemilik, txtNoTelp, txtDate;
    ImageView fotoProfil;
    int idPerajin = 0;
    String profilPerajin, fotoPerajin;
    GetDate getDate = new GetDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perajin_details);
        navPerajin = findViewById(R.id.navigation_details_perajin);
        toolbar = findViewById(R.id.perajin_details_toolbar);
        toolbar.setTitle("Detail Perajin");
        txtNamaPerajin = findViewById(R.id.txt_nama_perajin_komentator);
        txtLokasi = findViewById(R.id.txt_lokasi_perajin);
        txtPemilik = findViewById(R.id.txt_nama_pemilik);
        txtNoTelp = findViewById(R.id.txt_no_telp);
        fotoProfil = findViewById(R.id.gambar_perajin);
        txtDate = findViewById(R.id.txt_waktu_bergabung);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        idPerajin = intent.getIntExtra("id_perajin", 0);
        profilPerajin = intent.getStringExtra("profil");
        fotoPerajin = intent.getStringExtra("foto");

        // Populate data
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.PerajinUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Response Object", "Perajin Object " + jsonObject);
                if(jsonObject.optString("error").equals("false")){
                    JSONArray perajinArray = jsonObject.getJSONArray("perajin");
                    JSONObject perajinObject = perajinArray.getJSONObject(0);
                    txtNamaPerajin.setText(perajinObject.getString("nama_perajin"));
                    Glide.with(this).load(appConfig.BaseUrl(perajinObject.getString("foto_profil"))).into(fotoProfil);
                    txtPemilik.setText("Pemilik: " + perajinObject.getString("nama"));
                    txtLokasi.setText(perajinObject.getString("alamat"));
                    txtNoTelp.setText("No Telp: " + perajinObject.getString("no_telp"));
                    txtDate.setText("Bergabung sejak " + getDate.returnDate(perajinObject.getString("date_created")));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id_perajin", String.valueOf(idPerajin));
                return params;
            }
        };
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        fm.beginTransaction().add(R.id.frame_perajin, productFragment, "1").commit();
        fm.beginTransaction().add(R.id.frame_perajin, profilFragment, "2").hide(profilFragment).commit();
        navPerajin.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId){
                case R.id.nav_profil:
                    fm.beginTransaction().hide(active).show(profilFragment).commit();
                    active = profilFragment;
                    break;
                case R.id.nav_products:
                    fm.beginTransaction().hide(active).show(productFragment).commit();
                    active = productFragment;
                    break;
            }
            return true;
        });
    }

    public int getIdPerajin(){
        return idPerajin;
    }

    public String getProfilPerajin(){
        return profilPerajin;
    }

    public String getGambarPerajin(){
        return fotoPerajin;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        User user = SharedPrefManager.getInstance(this).getUser();
        if (SharedPrefManager.getInstance(this).isLoggedIn() && user.getId_perajin() != 0 && user.getId_perajin() == this.idPerajin){
            getMenuInflater().inflate(R.menu.menu_toolbar_account, menu);
            toolbar.setOnMenuItemClickListener(item -> PerajinDetailsActivity.this.onCreateOptionsMenu(item));
            return true;
        }
        return true;
    }

    private boolean onCreateOptionsMenu(MenuItem item) {
        if(item.getItemId() == R.id.nav_edit){
            Intent intentPerajin = new Intent(this, PerajinEditActivity.class);
            intentPerajin.putExtra("id", idPerajin);
            startActivity(intentPerajin);
            return true;
        }
        return true;
    }
}