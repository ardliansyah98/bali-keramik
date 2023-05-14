package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;

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
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.GambarKeramik;
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


        Intent intent = getIntent();
        int idKeramik = intent.getIntExtra("id_keramik", 0);
        String namaKeramik = intent.getStringExtra("nama_keramik");
        String dimensiKeramik = intent.getStringExtra("dimensi");
        String warnaKeramik = intent.getStringExtra("warna");
        String deskripsiKeramik = intent.getStringExtra("deskripsi");
        String namaPerajin = intent.getStringExtra("nama_perajin");
        String fotoPerajin = intent.getStringExtra("foto_perajin");
        String namaKategori = intent.getStringExtra("kategori");

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