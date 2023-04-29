package com.btikk.balikeramik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class KeramikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keramik);

        Intent intent = getIntent();
        Toast.makeText(this, "idKategori = " + intent.getIntExtra("id", 0), Toast.LENGTH_SHORT).show();
    }
}