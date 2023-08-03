package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.adapters.PerajinAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.GetDate;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.Perajin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PerajinActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvPerajin;
    AppConfig appConfig = new AppConfig();
    ArrayList<Perajin> perajinArrayList;
    PerajinAdapter perajinAdapter;
    GetDate getDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perajin);
        toolbar = (Toolbar) findViewById(R.id.perajin_toolbar);
        rvPerajin = findViewById(R.id.rv_perajin);
        toolbar.setTitle("Daftar Perajin");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvPerajin.setHasFixedSize(true);
        rvPerajin.setLayoutManager(new LinearLayoutManager(this));
        perajinArrayList = new ArrayList<>();
        perajinAdapter = new PerajinAdapter(this, perajinArrayList);
        rvPerajin.setAdapter(perajinAdapter);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, appConfig.PerajinUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Perajin", "Perajin response: " + response);
                if (!jsonObject.getBoolean("error")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("perajin");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject itemPerajin = jsonArray.getJSONObject(i);
                        Perajin perajin = new Perajin(itemPerajin.getInt("id"),
                                                      itemPerajin.getInt("id_akun"),
                                                      itemPerajin.getString("nama_perajin"),
                                                      itemPerajin.getString("deskripsi_perajin"),
                                                      itemPerajin.getString("alamat"),
                                                      itemPerajin.getString("nama"),
                                                      itemPerajin.getString("email"),
                                                      appConfig.BaseUrl(itemPerajin.getString("foto_profil")),
                                                      itemPerajin.getString("date_created"));
                        perajinArrayList.add(perajin);
                    }
                    perajinAdapter.notifyDataSetChanged();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> error.printStackTrace());
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}