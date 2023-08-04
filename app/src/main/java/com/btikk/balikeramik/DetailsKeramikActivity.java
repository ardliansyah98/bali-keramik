package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.btikk.balikeramik.adapters.GambarKeramikAdapter;
import com.btikk.balikeramik.adapters.KomentarAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.GambarKeramik;
import com.btikk.balikeramik.models.Komentar;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsKeramikActivity extends AppCompatActivity {
    private SliderView svGambar;
    private ArrayList<GambarKeramik> gambarKeramikList;
    private GambarKeramikAdapter gambarKeramikAdapter;
    private AppConfig appConfig = new AppConfig();
    TextView txtDeskripsi, txtDimensi, txtKategori, txtNama, txtPerajin, txtPerajin2, txtkategori1, txtWarna;
    ImageView gambarPerajin;
    CardView cvToPerajin, cvToKomentar;
    RecyclerView rvKomentar;
    ArrayList<Komentar> komentarArrayList;
    KomentarAdapter komentarAdapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_keramik);

        svGambar = (SliderView) findViewById(R.id.sv_gambar_keramik);
        this.txtNama = (TextView) findViewById(R.id.text_nama_keramik);
        this.txtPerajin = (TextView) findViewById(R.id.text_perajin);
        this.txtPerajin2 = (TextView) findViewById(R.id.txt_perajin);
        this.txtKategori = (TextView) findViewById(R.id.text_kategori);
        this.txtkategori1 = (TextView) findViewById(R.id.kategori_keramik);
        this.txtDimensi = (TextView) findViewById(R.id.dimensi_keramik);
        this.txtWarna = (TextView) findViewById(R.id.warna_keramik);
        this.txtDeskripsi = (TextView) findViewById(R.id.deskripsi_keramik);
        this.gambarPerajin = (ImageView) findViewById(R.id.gambar_perajin_keramik_details);
        this.cvToPerajin = findViewById(R.id.cv_to_perajin);
        this.cvToKomentar = findViewById(R.id.cv_to_komentar);
        this.toolbar = (Toolbar) findViewById(R.id.ceramic_details_toolbar);
        rvKomentar = findViewById(R.id.rv_komentar);
        toolbar.setTitle("Detail Keramik");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        int idKeramik = intent.getIntExtra("id_keramik", 0);
        int idPerajin = intent.getIntExtra("id_perajin", 0);
        String namaKeramik = intent.getStringExtra("nama_keramik");
        String dimensiKeramik = intent.getStringExtra("dimensi");
        String warnaKeramik = intent.getStringExtra("warna");
        String deskripsiKeramik = intent.getStringExtra("deskripsi");
        String namaPerajin = intent.getStringExtra("nama_perajin");
        String fotoPerajin = intent.getStringExtra("foto_perajin");
        String namaKategori = intent.getStringExtra("kategori");

        cvToPerajin.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, PerajinDetailsActivity.class);
            intent1.putExtra("id_perajin", idPerajin);
            startActivity(intent1);
        });

        cvToKomentar.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, KomentarActivity.class);
            intent2.putExtra("id_keramik", idKeramik);
            startActivity(intent2);
        });

        txtNama.setText(namaKeramik);
        txtPerajin.setText("Oleh: " + namaPerajin);
        txtPerajin2.setText(namaPerajin);
        txtDimensi.setText(dimensiKeramik);
        txtWarna.setText(warnaKeramik);
        txtDeskripsi.setText(deskripsiKeramik);
        txtKategori.setText("Posted Under " + namaKategori);
        txtkategori1.setText(namaKategori);
        Glide.with(this).load(appConfig.BaseUrl(fotoPerajin)).into(gambarPerajin);

        loadGambar(idKeramik);
        loadKomentar(idKeramik);
    }

    private void loadKomentar(int idKeramik) {
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
                    for (int i = 0; i < 1; i++) {
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

    private void loadGambar(int idKeramik) {
        gambarKeramikList = new ArrayList<>();
        gambarKeramikAdapter = new GambarKeramikAdapter(this, gambarKeramikList);
        svGambar.setSliderAdapter(gambarKeramikAdapter);
        svGambar.setIndicatorAnimation(IndicatorAnimationType.WORM);
        svGambar.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.GambarKeramikUrl(), response -> {
            Log.d("response", "Response Event " + response + "");
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Response Object", "Event Object " + jsonObject);
                if(jsonObject.optString("error").equals("false")){
                    JSONArray gambarArray = jsonObject.getJSONArray("gambar");
                    Log.d("gambar object", "gambar objscts" + gambarArray);
                    for(int i = 0; i < gambarArray.length(); i++){
                        JSONObject gambarObject = gambarArray.getJSONObject(i);
                        GambarKeramik gambarKeramik = new GambarKeramik(gambarObject.getInt("id"),
                                                                        gambarObject.getInt("id_keramik"),
                                                                        appConfig.BaseUrl(gambarObject.getString("gambar")));
                        gambarKeramikList.add(gambarKeramik);
                    }
                    gambarKeramikAdapter.notifyDataSetChanged();
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
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}