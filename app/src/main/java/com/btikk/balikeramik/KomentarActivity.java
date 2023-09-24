package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.adapters.KomentarAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.configs.SharedPrefManager;
import com.btikk.balikeramik.models.Komentar;
import com.btikk.balikeramik.models.User;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KomentarActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvKomentar;
    AppConfig appConfig = new AppConfig();
    ArrayList<Komentar> komentarArrayList;
    KomentarAdapter komentarAdapter;
    Button btnKirimKomentar;
    TextInputEditText etKomentar;
    LinearLayout llKomentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);

        toolbar = findViewById(R.id.komentar_toolbar);
        rvKomentar = findViewById(R.id.rv_komentar_keramik);
        etKomentar = findViewById(R.id.et_komentar);
        btnKirimKomentar = findViewById(R.id.btn_input_komentar);
        llKomentar = findViewById(R.id.ll_komentar);

        llKomentar.setVisibility(View.GONE);

        toolbar.setTitle("Semua komentar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        int idKeramik = intent.getIntExtra("id_keramik", 0);

        Toast.makeText(this, "id = " + idKeramik, Toast.LENGTH_SHORT).show();

        loadkomentar(idKeramik);

        User user = SharedPrefManager.getInstance(this).getUser();
        int idAKun = user.getId();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            llKomentar.setVisibility(View.VISIBLE);
        }

        btnKirimKomentar.setOnClickListener(view -> {
            String komentar = etKomentar.getText().toString().trim();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.KomentarUrl(), response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.optString("error").equals("false")){
                        // success
                        Toast.makeText(KomentarActivity.this, "Input Komentar Sukses", Toast.LENGTH_SHORT).show();
                        etKomentar.setText("");
                        komentarArrayList.clear();
                        komentarAdapter.notifyDataSetChanged();
                        loadkomentar(idKeramik);
                    } else {
                        Toast.makeText(this, "Error ", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error = " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, error -> error.getMessage()){
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();
                    params.put("insert_komentar", "1");
                    params.put("id_akun", String.valueOf(idAKun));
                    params.put("id_keramik", String.valueOf(idKeramik));
                    params.put("komentar", komentar);
                    return params;
                }
            };
            MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        });
    }

    private void loadkomentar(int idKeramik) {
        rvKomentar.setHasFixedSize(true);
        rvKomentar.setLayoutManager(new LinearLayoutManager(this));
        komentarArrayList = new ArrayList<>();
        komentarAdapter = new KomentarAdapter(this, komentarArrayList);
        rvKomentar.setAdapter(komentarAdapter);
        StringRequest komentarRequest = new StringRequest(Request.Method.POST, appConfig.KomentarUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Komentar", "Komentar response: " + response);
                if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("komentar");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject itemKomentar = jsonArray.getJSONObject(i);
                        Komentar komentar = new Komentar(itemKomentar.getInt("id"),
                                itemKomentar.getInt("id_akun"),
                                itemKomentar.getInt("id_keramik"),
                                itemKomentar.getString("komentar"),
                                itemKomentar.getString("date_created"),
                                itemKomentar.getString("nama"),
                                appConfig.BaseUrl(itemKomentar.getString("foto_profil")),
                                itemKomentar.getString("nama_perajin")
                        );
                        komentarArrayList.add(komentar);
                    }
                    komentarAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            error.getMessage();
            error.printStackTrace();
            Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id_keramik", String.valueOf(idKeramik));
                return params;
            }
        };
        MyVolleySingleton.getInstance(this).addToRequestQueue(komentarRequest);
    }
}