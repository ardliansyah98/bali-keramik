package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.btikk.balikeramik.adapters.KeramikAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.models.Keramik;

import java.util.ArrayList;

public class SearchKeramikActivity extends AppCompatActivity {
    RecyclerView rvKeramik;
    AppConfig appConfig = new AppConfig();
    ArrayList<Keramik> keramikArrayList;
    KeramikAdapter keramikAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvKeramik = findViewById(R.id.rv_keramik);

        Intent intent = getIntent();
        String namaKeramik = intent.getStringExtra("nama_keramik");
        Toast.makeText(this, "Keramik = " + namaKeramik, Toast.LENGTH_SHORT).show();
    }
}