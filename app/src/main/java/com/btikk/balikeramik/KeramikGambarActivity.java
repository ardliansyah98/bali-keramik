package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.btikk.balikeramik.configs.AppConfig;
import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

public class KeramikGambarActivity extends AppCompatActivity {
    // ImageView ivGambar;
    ZoomageView myZoomageView;
    AppConfig appConfig = new AppConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keramik_gambar);
        myZoomageView = findViewById(R.id.myZoomageView);

        //get gambar
        Intent intent = getIntent();
        String gambar = intent.getStringExtra("gambar");

        Glide.with(this).load(gambar).into(myZoomageView);
    }
}