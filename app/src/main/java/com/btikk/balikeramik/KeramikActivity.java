package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.adapters.KeramikAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.Keramik;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeramikActivity extends AppCompatActivity {
    private ImageView gbrKategori;
    private TextView namaKategori, jumlahKategori;
    private RecyclerView rvKeramik;
    private ArrayList<Keramik> keramikArrayList;
    private KeramikAdapter keramikAdapter;
    private AppConfig appConfig = new AppConfig();
    String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keramik);

        gbrKategori = (ImageView) findViewById(R.id.gbr_kategori);
        namaKategori = (TextView) findViewById(R.id.nama_kategori);
        jumlahKategori = (TextView) findViewById(R.id.jml_kategori);
        rvKeramik = (RecyclerView) findViewById(R.id.rv_keramik);

        Intent intent = getIntent();
        Toast.makeText(this, "idKategori = " + intent.getIntExtra("id", 0), Toast.LENGTH_SHORT).show();
        String gambar = intent.getStringExtra("gambar_kategori");
        nama = intent.getStringExtra("nama_kategori");
        int idKategori = intent.getIntExtra("id", 0);

        Glide.with(this).load(gambar).into(gbrKategori);
        namaKategori.setText(nama);

        loadKeramik(idKategori);
    }
    private void loadKeramik(int id) {
        keramikArrayList = new ArrayList<>();
        keramikAdapter = new KeramikAdapter(this, keramikArrayList);
        rvKeramik.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        rvKeramik.setAdapter(keramikAdapter);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.KeramikUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Response Object", "Keramik Object " + jsonObject);
                jumlahKategori.setText("Terdapat " + jsonObject.optString("count") + " data di bawah kategori berjudul " + nama);
                if(jsonObject.optString("error").equals("false")){
                    JSONArray keramikArray = jsonObject.getJSONArray("keramik");
                    for(int i = 0; i < keramikArray.length(); i++){
                        JSONObject keramikObject = keramikArray.getJSONObject(i);
                        Keramik keramik = new Keramik(keramikObject.getInt("id"),
                                                      keramikObject.getInt("id_perajin"),
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
            Toast.makeText(KeramikActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("sb_kategori", "1");
                params.put("id_kategori", String.valueOf(id));
                return params;
            }
        };
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}