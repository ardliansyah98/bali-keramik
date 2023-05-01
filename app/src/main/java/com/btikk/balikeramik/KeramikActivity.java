package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class KeramikActivity extends AppCompatActivity {
    private ImageView gbrKategori;
    private TextView namaKategori, jumlahKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keramik);

        gbrKategori = (ImageView) findViewById(R.id.gbr_kategori);
        namaKategori = (TextView) findViewById(R.id.nama_kategori);
        jumlahKategori = (TextView) findViewById(R.id.jml_kategori);

        Intent intent = getIntent();
        Toast.makeText(this, "idKategori = " + intent.getIntExtra("id", 0), Toast.LENGTH_SHORT).show();
        String gambar = intent.getStringExtra("gambar_kategori");
        String nama = intent.getStringExtra("nama_kategori");

        Glide.with(this).load(gambar).into(gbrKategori);
        namaKategori.setText(nama);
        jumlahKategori.setText("Terdapat 50 data di bawah kategori berjudul " + nama);

    }
}