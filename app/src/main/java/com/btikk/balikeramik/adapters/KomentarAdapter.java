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
import com.btikk.balikeramik.models.Komentar;
import com.bumptech.glide.Glide;

import java.util.List;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.ViewHolder> {

    private Context context;
    private List<Komentar> komentarList;

    public KomentarAdapter(Context context, List<Komentar> komentarList) {
        this.context = context;
        this.komentarList = komentarList;
    }

    @NonNull
    @Override
    public KomentarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_komentar_keramik, parent, false);
        return new KomentarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KomentarAdapter.ViewHolder holder, int position) {
        final Komentar komentar = this.komentarList.get(position);
        Glide.with(this.context).load(komentar.getFoto_profil()).into(holder.fotoProfil);
        holder.namaKomentator.setText(komentar.getNama());
        holder.tanggal.setText(komentar.getDate_created());
        holder.namaPerajin.setText(komentar.getNama_perajin());
        holder.komentar.setText(komentar.getKomentar());
    }

    @Override
    public int getItemCount() {
        return komentarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fotoProfil;
        TextView namaKomentator, tanggal, namaPerajin, komentar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoProfil = itemView.findViewById(R.id.foto_profil_komentar);
            namaKomentator = itemView.findViewById(R.id.txt_nama_komentator);
            tanggal = itemView.findViewById(R.id.txt_date_komentar);
            namaPerajin = itemView.findViewById(R.id.txt_nama_perajin_komentator);
            komentar = itemView.findViewById(R.id.komentar_keramik);
        }
    }
}
