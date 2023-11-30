package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.adapters.KeramikAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.Keramik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchKeramikActivity extends AppCompatActivity {
    RecyclerView rvKeramik;
    AppConfig appConfig = new AppConfig();
    ArrayList<Keramik> keramikArrayList;
    KeramikAdapter keramikAdapter;
    TextView jdlPencarian;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvKeramik = findViewById(R.id.rv_keramik);
        jdlPencarian = findViewById(R.id.jdl_pencarian);
        toolbar = findViewById(R.id.toolbar_search);
        toolbar.setTitle("Hasil Pencarian");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String namaKeramik = intent.getStringExtra("namaKeramik");
        Toast.makeText(this, "Keramik = " + namaKeramik, Toast.LENGTH_SHORT).show();

        jdlPencarian.setText("Berikut ini merupakan hasil pencarian dari " + "\"" + namaKeramik + "\"");
        loadKeramik(namaKeramik);
    }

    private void loadKeramik(String namaKeramik) {
        keramikArrayList = new ArrayList<>();
        keramikAdapter = new KeramikAdapter(this, keramikArrayList);
        rvKeramik.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        rvKeramik.setAdapter(keramikAdapter);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.KeramikUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Response Object", "Keramik Object " + jsonObject);
                if(jsonObject.optString("error").equals("false")){
                    JSONArray keramikArray = jsonObject.getJSONArray("keramik");
                    for(int i = 0; i < keramikArray.length(); i++){
                        JSONObject keramikObject = keramikArray.getJSONObject(i);
                        Keramik keramik = new Keramik(keramikObject.getInt("id"),
                                keramikObject.getInt("id_kategori"),
                                keramikObject.getString("nama_keramik"),
                                keramikObject.getString("dimensi"),
                                keramikObject.getString("warna"),
                                keramikObject.getString("deskripsi_keramik"),
                                appConfig.BaseUrl(keramikObject.getString("gambar")),
                                keramikObject.getString("nama_perajin"),
                                keramikObject.getString("foto_profil"),
                                keramikObject.getString("nama_kategori"));
                        this.keramikArrayList.add(keramik);
                    }
                    keramikAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(SearchKeramikActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("nama_keramik", namaKeramik);
                return params;
            }
        };
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}