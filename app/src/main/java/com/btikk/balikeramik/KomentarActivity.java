package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.adapters.KomentarAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.Komentar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);

        toolbar = findViewById(R.id.komentar_toolbar);
        rvKomentar = findViewById(R.id.rv_komentar_keramik);

        toolbar.setTitle("Semua komentar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        int idKeramik = intent.getIntExtra("id_keramik", 0);

        Toast.makeText(this, "id = " + idKeramik, Toast.LENGTH_SHORT).show();

        loadkomentar(idKeramik);
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