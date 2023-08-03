package com.btikk.balikeramik.adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btikk.balikeramik.DetailsKeramikActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.models.Keramik;
import com.bumptech.glide.Glide;

import java.util.List;

public class KeramikAdapter extends RecyclerView.Adapter<KeramikAdapter.ViewHolder> {
    private Context context;
    private List<Keramik> keramikList;

    public KeramikAdapter(Context context, List<Keramik> keramikList){
        this.context = context;
        this.keramikList = keramikList;
    }

    @NonNull
    @Override
    public KeramikAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_keramik, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeramikAdapter.ViewHolder holder, int position) {
        final Keramik keramik = this.keramikList.get(position);
        Glide.with(this.context).load(keramik.getGambarKeramik()).into(holder.gambarKeramik);
        holder.namaKeramik.setText(keramik.getNamaKeramik());
        holder.perajinKeramik.setText("Oleh: " + keramik.getNamaPerajin());
        holder.kategoriKeramik.setText("Diposting pada kategori " + keramik.getKategoriKeramik());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailsKeramikActivity.class);
            intent.putExtra("id_keramik", keramik.getId());
            intent.putExtra("id_perajin", keramik.getId_perajin());
            intent.putExtra("nama_keramik", keramik.getNamaKeramik());
            intent.putExtra("dimensi", keramik.getDimensiKeramik());
            intent.putExtra("warna", keramik.getWarnaKeramik());
            intent.putExtra("deskripsi", keramik.getDeskripsiKeramik());
            intent.putExtra("nama_perajin", keramik.getNamaPerajin());
            intent.putExtra("foto_perajin", keramik.getFotoPerajin());
            intent.putExtra("kategori", keramik.getKategoriKeramik());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return keramikList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarKeramik;
        TextView namaKeramik, kategoriKeramik, perajinKeramik;

        public ViewHolder(View itemView) {
            super(itemView);
            this.gambarKeramik = (ImageView) itemView.findViewById(R.id.item_gambar_keramik);
            this.namaKeramik = (TextView) itemView.findViewById(R.id.item_nama_keramik);
            this.kategoriKeramik = (TextView) itemView.findViewById(R.id.item_kategori_keramik);
            this.perajinKeramik = (TextView) itemView.findViewById(R.id.item_nama_perajin_keramik);
        }
    }
}
