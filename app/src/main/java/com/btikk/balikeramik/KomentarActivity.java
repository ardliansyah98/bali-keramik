package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class KomentarActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvKomentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.komentar_toolbar);
        rvKomentar = findViewById(R.id.rv_komentar_keramik);

        toolbar.setTitle("Semua komentar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);
    }
}