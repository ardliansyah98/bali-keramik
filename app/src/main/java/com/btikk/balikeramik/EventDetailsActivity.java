package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class EventDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView gbrEvent;
    TextView txtJudul, txtKonten, txtTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        gbrEvent = findViewById(R.id.gbr_event);
        txtJudul = findViewById(R.id.judul_event);
        txtKonten = findViewById(R.id.deskripsi_event);
        txtTanggal = findViewById(R.id.tgl_event);
        toolbar = findViewById(R.id.event_details_toolbar);

        toolbar.setTitle("Detail Event");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        txtJudul.setText(intent.getStringExtra("judul"));
        txtKonten.setText(intent.getStringExtra("konten"));
        Glide.with(this).load(intent.getStringExtra("gambar")).into(gbrEvent);
    }
}