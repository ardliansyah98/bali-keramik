package com.btikk.balikeramik.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btikk.balikeramik.R;
import com.btikk.balikeramik.models.Balasan;
import com.bumptech.glide.Glide;

import java.util.List;

public class BalasanAdapter extends RecyclerView.Adapter<BalasanAdapter.ViewHolder> {
    private Context context;
    private List<Balasan> balasanList;

    public BalasanAdapter(Context context, List<Balasan> balasanList) {
        this.context = context;
        this.balasanList = balasanList;
    }

    @NonNull
    @Override
    public BalasanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_balasan_komentar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BalasanAdapter.ViewHolder holder, int position) {
        final Balasan balasan = this.balasanList.get(position);
        Glide.with(this.context).load(balasan.getFoto_profil()).into(holder.fotoProfil);
        holder.nama.setText(balasan.getNama());
        holder.tgl.setText(balasan.getTgl());
        if(!balasan.getNama_perajin().equals("null")){
            holder.namaPerajin.setText(balasan.getNama_perajin());
        } else {
            holder.namaPerajin.setVisibility(View.GONE);
        }
        holder.balasan.setText(balasan.getBalasan());
    }

    @Override
    public int getItemCount() {
        return balasanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fotoProfil;
        TextView balasan, nama, namaPerajin, tgl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoProfil = itemView.findViewById(R.id.foto_profil_balasan);
            balasan = itemView.findViewById(R.id.balasan_komentar_keramik);
            nama = itemView.findViewById(R.id.txt_nama_pembalas);
            namaPerajin = itemView.findViewById(R.id.txt_nama_perajin_balasan);
            tgl = itemView.findViewById(R.id.txt_date_balasan);
        }
    }
}
