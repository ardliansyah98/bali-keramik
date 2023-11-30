package com.btikk.balikeramik.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btikk.balikeramik.EventDetailsActivity;
import com.btikk.balikeramik.PelayananDetailsActivity;
import com.btikk.balikeramik.PerajinDetailsActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.models.Pelayanan;
import com.btikk.balikeramik.models.Perajin;
import com.bumptech.glide.Glide;

import java.util.List;

public class PelayananAdapter extends RecyclerView.Adapter<PelayananAdapter.ViewHolder> {
    private Context context;
    private List<Pelayanan> pelayananList;

    public PelayananAdapter(Context context, List<Pelayanan> pelayananList) {
        this.context = context;
        this.pelayananList = pelayananList;
    }

    @NonNull
    @Override
    public PelayananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pelayanan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PelayananAdapter.ViewHolder holder, int position) {
        final Pelayanan pelayanan = this.pelayananList.get(position);
        Glide.with(this.context).load(pelayanan.getGambar()).into(holder.gambar);
        holder.judul.setText(pelayanan.getJudul());
        holder.konten.setText(pelayanan.getKonten());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PelayananDetailsActivity.class);
            intent.putExtra("id_pelayanan", pelayanan.getId_pelayanan());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pelayananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView gambar;
        TextView judul, konten;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.item_gambar_pelayanan);
            judul = itemView.findViewById(R.id.item_judul_pelayanan);
            konten = itemView.findViewById(R.id.snippet_pelayanan);
        }
    }
}
