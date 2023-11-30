package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.configs.SharedPrefManager;
import com.btikk.balikeramik.models.User;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KeramikAddActivity extends AppCompatActivity {
    SearchableSpinner spinnerKategori;
    ArrayAdapter<String> adapterKategori;
    AppConfig appConfig = new AppConfig();
    User user = SharedPrefManager.getInstance(this).getUser();
    List<Integer> kategoriIdList;
    List<String> kategoriList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keramik_add);
        spinnerKategori = findViewById(R.id.spinner_kategori);
        toolbar = findViewById(R.id.add_keramik_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnerKategori.setTitle("Pilih Kategori");
        spinnerKategori.setPositiveButton("Pilih");
        kategoriIdList = new ArrayList<>();
        kategoriList = new ArrayList<>();
        adapterKategori = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, kategoriList);
        adapterKategori.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerKategori.setAdapter((SpinnerAdapter) adapterKategori);
        addKategoritoSpinner();
    }

    private void addKategoritoSpinner() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, appConfig.KategoriUrl(), response -> {
            try {
                Log.d("Kategori Spinner", response);
                JSONObject jsonObject = new JSONObject(response);
                if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("kategori");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject kategori = jsonArray.getJSONObject(i);
                        kategoriIdList.add(Integer.valueOf(kategori.getInt("id")));
                        kategoriList.add(kategori.getString("nama_kategori"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapterKategori.notifyDataSetChanged();
        }, error -> {
            error.printStackTrace();
        });
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}