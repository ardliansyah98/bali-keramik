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

import com.btikk.balikeramik.KeramikActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.models.Kategori;
import com.bumptech.glide.Glide;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {
    private Context context;
    private List<Kategori> kategoriList;

    public KategoriAdapter(Context context, List<Kategori> kategoriList){
        this.context = context;
        this.kategoriList = kategoriList;
    }

    @NonNull
    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_kategori, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Kategori kategori = this.kategoriList.get(position);
        Glide.with(this.context).load(kategori.getGambar()).into(holder.gambarKategori);
        holder.judulKategori.setText(kategori.getNama());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), KeramikActivity.class);
            intent.putExtra("id", kategori.getId());
            intent.putExtra("nama_kategori", kategori.getNama());
            intent.putExtra("gambar_kategori", kategori.getGambar());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarKategori;
        TextView judulKategori;

        public ViewHolder(View itemView) {
            super(itemView);
            this.gambarKategori = (ImageView) itemView.findViewById(R.id.item_gambar_kategori);
            this.judulKategori = (TextView) itemView.findViewById(R.id.item_nama_kategori);
        }
    }

}
