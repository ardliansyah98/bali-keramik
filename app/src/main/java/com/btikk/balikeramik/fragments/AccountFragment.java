package com.btikk.balikeramik.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btikk.balikeramik.DashboardActivity;
import com.btikk.balikeramik.EditAkunActivity;
import com.btikk.balikeramik.LoginActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.GetDate;
import com.btikk.balikeramik.configs.SharedPrefManager;
import com.btikk.balikeramik.models.User;
import com.bumptech.glide.Glide;

public class AccountFragment extends Fragment {
    private NestedScrollView svAccount;
    private RelativeLayout layoutNotLoggedIn;
    private TextView tvDate;
    private TextView tvEmail1;
    private TextView tvNama;
    private TextView tvNamaLengkap;
    private TextView tvPerajin;
    private TextView tveEmail;
    private Button btnToLogin, btnLogout;
    private CardView cvPerajin;
    private Toolbar toolbar;
    private ImageView gambarProfil;
    private AppConfig appConfig = new AppConfig();
    GetDate getDate = new GetDate();

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        svAccount = (NestedScrollView) view.findViewById(R.id.sv_account);
        layoutNotLoggedIn = (RelativeLayout) view.findViewById(R.id.not_logged_in);
        this.tvNama = (TextView) view.findViewById(R.id.txt_nama);
        this.tveEmail = (TextView) view.findViewById(R.id.txt_email);
        this.tvDate = (TextView) view.findViewById(R.id.txt_tanggal_daftar);
        this.tvNamaLengkap = (TextView) view.findViewById(R.id.txt_nama_lengkap);
        this.tvEmail1 = (TextView) view.findViewById(R.id.txt_email_1);
        this.btnToLogin = (Button) view.findViewById(R.id.btn_to_login);
        this.btnLogout = (Button) view.findViewById(R.id.btn_logout);
        this.cvPerajin = (CardView) view.findViewById(R.id.cv_to_perajin);
        this.toolbar = (Toolbar) view.findViewById(R.id.account_toolbar);
        this.toolbar.setTitle("Akun Saya");
        this.gambarProfil = (ImageView) view.findViewById(R.id.gambar_profil);

        btnToLogin.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
            startActivity(new Intent(getActivity().getApplicationContext(), DashboardActivity.class));
            getActivity().finish();
        });

        // if user logged in
        if(SharedPrefManager.getInstance(getActivity().getApplicationContext()).isLoggedIn()){
            layoutNotLoggedIn.setVisibility(View.GONE);
            final User user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
            this.tvNama.setText(user.getNama());
            this.tveEmail.setText(user.getEmail());
            this.tvDate.setText(getDate.returnDate(user.getDateCreated()));
            this.tvNamaLengkap.setText(user.getNama());
            this.tvEmail1.setText(user.getEmail());

            Glide.with(getActivity().getApplicationContext()).load(appConfig.BaseUrl(user.getFotoProfil())).into(gambarProfil);

            Toast.makeText(getActivity().getApplicationContext(), "foto: " + user.getFotoProfil(), Toast.LENGTH_SHORT).show();

            if(user.getId_perajin() == 0){
                cvPerajin.setVisibility(View.GONE);
            }

            toolbar.inflateMenu(R.menu.menu_toolbar_account);
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.nav_edit) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), EditAkunActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            });

        } else if(!SharedPrefManager.getInstance(getActivity().getApplicationContext()).isLoggedIn()){
            layoutNotLoggedIn.setVisibility(View.VISIBLE);
            svAccount.setVisibility(View.GONE);
        }

        return view;
    }
}