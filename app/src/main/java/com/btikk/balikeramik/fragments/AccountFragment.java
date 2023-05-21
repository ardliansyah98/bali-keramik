package com.btikk.balikeramik.fragments;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btikk.balikeramik.R;
import com.btikk.balikeramik.configs.SharedPrefManager;
import com.btikk.balikeramik.models.User;

public class AccountFragment extends Fragment {
    private NestedScrollView svAccount;
    private RelativeLayout layoutNotLoggedIn;
    private TextView tvDate;
    private TextView tvEmail1;
    private TextView tvNama;
    private TextView tvNamaLengkap;
    private TextView tvPerajin;
    private TextView tveEmail;


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

        // if user logged in
        if(SharedPrefManager.getInstance(getActivity().getApplicationContext()).isLoggedIn()){
            layoutNotLoggedIn.setVisibility(View.GONE);
            final User user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
            this.tvNama.setText(user.getNama());
            this.tveEmail.setText(user.getEmail());
            this.tvDate.setText(user.getDateCreated());
            this.tvNamaLengkap.setText(user.getNama());
            this.tvEmail1.setText(user.getEmail());

        } else if(!SharedPrefManager.getInstance(getActivity().getApplicationContext()).isLoggedIn()){
            layoutNotLoggedIn.setVisibility(View.VISIBLE);
            svAccount.setVisibility(View.GONE);
        }

        return view;
    }
}