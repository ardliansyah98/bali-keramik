package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.GetDate;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PelayananDetailsActivity extends AppCompatActivity {
    int id;
    ImageView gbrPelayanan;
    TextView jdlPelayanan, penulisPelayanan, tglPelayanan, deskripsiPelayanan;
    AppConfig appConfig = new AppConfig();
    GetDate getDate = new GetDate();
    Toolbar toolbar;
    LinearLayout llToCP, llToFormulir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelayanan_details);
        gbrPelayanan = findViewById(R.id.gbr_pelayanan);
        jdlPelayanan = findViewById(R.id.judul_pelayanan);
        penulisPelayanan = findViewById(R.id.penulis_pelayanan);
        tglPelayanan = findViewById(R.id.tgl_pelayanan);
        deskripsiPelayanan = findViewById(R.id.deskripsi_pelayanan);
        toolbar = findViewById(R.id.pelayanan_details_toolbar);
        llToCP = findViewById(R.id.cv_to_cp1);
        llToFormulir = findViewById(R.id.cv_to_formulir);

        toolbar.setTitle("Detail Pelayanan");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        id = intent.getIntExtra("id_pelayanan", 0);
        String uri = "https://api.whatsapp.com/send?phone=6281338178869&text=Selamat%20pagi%2C%20Bu%20Yuli.%20Saya%20ingin%20bertanya%20seputar%20pelayanan%20di%20BTIKK.%20";
        String uriForm = "https://docs.google.com/forms/d/e/1FAIpQLSf4zmJwvV4vLnYON9urP3sw4b0k6KCtKrTrB77c9XYjMVe2mQ/viewform";

        loadPelayanan(id);

        llToCP.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent1.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
            startActivity(intent1);
        });

        llToFormulir.setOnClickListener(v -> {
            Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(uriForm));
            intent2.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
            startActivity(intent2);
        });

    }

    private void loadPelayanan(int id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.PelayananUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Pelayanan", "Pelayanan response : " + response);
                if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("pelayanan");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject itemPelayanan = jsonArray.getJSONObject(i);
                        jdlPelayanan.setText(itemPelayanan.getString("judul_pelayanan"));
                        Glide.with(PelayananDetailsActivity.this).load(appConfig.BaseUrl(itemPelayanan.getString("gambar"))).into(gbrPelayanan);
                        penulisPelayanan.setText("Diposting oleh: Admin BTIKK");
                        tglPelayanan.setText("Diposting pada: " + getDate.returnDate(itemPelayanan.getString("date_created")));
                        deskripsiPelayanan.setText(itemPelayanan.getString("konten"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(PelayananDetailsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(PelayananDetailsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id_pelayanan", String.valueOf(id));
                return params;
            }
        };
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}