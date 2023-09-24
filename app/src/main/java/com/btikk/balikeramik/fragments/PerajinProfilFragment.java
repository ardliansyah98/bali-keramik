package com.btikk.balikeramik.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.btikk.balikeramik.PerajinDetailsActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.configs.AppConfig;
import com.bumptech.glide.Glide;

public class PerajinProfilFragment extends Fragment {
    ImageView fotoPerajin;
    TextView tvProfilPerajin;
    AppConfig appConfig = new AppConfig();
    public PerajinProfilFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perajin_profil, container, false);
        PerajinDetailsActivity activity = (PerajinDetailsActivity) getActivity();
        fotoPerajin = view.findViewById(R.id.profil_gambar_perajin);
        tvProfilPerajin = view.findViewById(R.id.txt_profil);

        String profil = activity.getProfilPerajin();
        String foto = activity.getGambarPerajin();

        Toast.makeText(getActivity().getApplicationContext(), "Foto: " + foto, Toast.LENGTH_SHORT).show();

        tvProfilPerajin.setText(profil);
        Glide.with(getActivity().getApplicationContext()).load(foto).into(fotoPerajin);
        return view;
    }
}