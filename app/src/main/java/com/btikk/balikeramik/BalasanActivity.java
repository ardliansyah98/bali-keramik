package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.adapters.BalasanAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.GetDate;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.Balasan;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BalasanActivity extends AppCompatActivity {
    ImageView fotoProfil;
    TextView txtNamaKomentator, txtTanggal, txtNamaPerajin, txtKomentar;
    RecyclerView rvBalasan;
    GetDate getDate = new GetDate();
    AppConfig appConfig = new AppConfig();
    BalasanAdapter balasanAdapter;
    ArrayList<Balasan> balasanArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balasan);
        fotoProfil = findViewById(R.id.foto_profil_komentar);
        txtNamaKomentator = findViewById(R.id.txt_nama_komentator);
        txtTanggal = findViewById(R.id.txt_date_komentar);
        txtNamaPerajin = findViewById(R.id.txt_nama_perajin_komentator);
        txtKomentar = findViewById(R.id.komentar_keramik);
        rvBalasan = findViewById(R.id.rv_balasan_komentar);

        // get intent data
        Intent intent = getIntent();
        int id = intent.getIntExtra("id_komentar", 0);
        String namaKomentator = intent.getStringExtra("nama_komentator");
        String tgl = intent.getStringExtra("tgl");
        String namaPerajin = intent.getStringExtra("namaPerajin");
        String komentar = intent.getStringExtra("komentar");
        String foto = intent.getStringExtra("foto");
        // Toast.makeText(this, "id komentar " + id,  Toast.LENGTH_SHORT).show();
        txtNamaKomentator.setText(namaKomentator);
        txtTanggal.setText(getDate.returnDate(tgl));
        if(namaPerajin.equals("null")){
            txtNamaPerajin.setVisibility(View.GONE);
        } else {
            txtNamaPerajin.setText(namaPerajin);
        }
        txtKomentar.setText(komentar);
        Glide.with(this).load(foto).into(fotoProfil);

        loadBalasan(id);
    }

    private void loadBalasan(int id) {
        rvBalasan.setHasFixedSize(true);
        balasanArrayList = new ArrayList<>();
        rvBalasan.setLayoutManager(new LinearLayoutManager(this));
        balasanAdapter = new BalasanAdapter(this, balasanArrayList);
        rvBalasan.setAdapter(balasanAdapter);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.BalasanUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Response balasan", response);
                if(jsonObject.optString("error").equals("false")){
                    JSONArray balasanArray = jsonObject.getJSONArray("balasan");
                    for(int i = 0; i < balasanArray.length(); i++){
                        JSONObject balasanObject = balasanArray.getJSONObject(i);
                        Balasan balasan = new Balasan(balasanObject.getInt("id"),
                                                      balasanObject.getInt("id_komentar"),
                                                      balasanObject.getInt("id_akun"),
                                                      balasanObject.getString("nama"),
                                                      balasanObject.getString("nama_perajin"),
                                                      appConfig.BaseUrl(balasanObject.getString("foto_profil")),
                                                      getDate.returnDate(balasanObject.getString("date_created")),
                                                      balasanObject.getString("balasan")
                        );
                        this.balasanArrayList.add(balasan);
                    }
                    balasanAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id_komentar", String.valueOf(id));
                return params;
            }
        };
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}