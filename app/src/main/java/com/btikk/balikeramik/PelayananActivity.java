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
import com.btikk.balikeramik.adapters.PelayananAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.GetDate;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.Pelayanan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PelayananActivity extends AppCompatActivity {
    RecyclerView rvPelayanan;
    AppConfig appConfig = new AppConfig();
    Toolbar toolbar;
    ArrayList<Pelayanan> pelayananArrayList;
    PelayananAdapter pelayananAdapter;
    GetDate getDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelayanan);
        toolbar = findViewById(R.id.pelayanan_toolbar);
        rvPelayanan = findViewById(R.id.rv_pelayanan);
        toolbar.setTitle("Pelayanan BTIKK");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvPelayanan.setHasFixedSize(true);
        rvPelayanan.setLayoutManager(new LinearLayoutManager(this));
        pelayananArrayList = new ArrayList<>();
        pelayananAdapter = new PelayananAdapter(this, pelayananArrayList);
        rvPelayanan.setAdapter(pelayananAdapter);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, appConfig.PelayananUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Pelayanan","Pelayanan response: " + response);
                    if(!jsonObject.getBoolean("error")){
                        JSONArray jsonArray = jsonObject.getJSONArray("pelayanan");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject itemPelayanan = jsonArray.getJSONObject(i);
                            Pelayanan pelayanan = new Pelayanan(itemPelayanan.getInt("id"),
                                                                itemPelayanan.getString("judul_pelayanan"),
                                    itemPelayanan.getString("konten"),
                                    appConfig.BaseUrl(itemPelayanan.getString("gambar")),
                                    itemPelayanan.getString("date_created"));
                            pelayananArrayList.add(pelayanan);
                        }
                        pelayananAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> error.printStackTrace());
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}