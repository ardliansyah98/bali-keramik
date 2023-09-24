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

import com.btikk.balikeramik.PerajinDetailsActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.models.Perajin;
import com.bumptech.glide.Glide;

import java.util.List;

public class PerajinAdapter extends RecyclerView.Adapter<PerajinAdapter.ViewHolder>{
    private Context context;
    private List<Perajin> perajinList;

    public PerajinAdapter(Context context, List<Perajin> perajinList) {
        this.context = context;
        this.perajinList = perajinList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_perajin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Perajin perajin = this.perajinList.get(position);
        Glide.with(context).load(perajin.getFoto_profil()).into(holder.gambar);
        holder.nama.setText(perajin.getNama_perajin());
        holder.perajin.setText(perajin.getNama_pemilik());
        holder.profil.setText(perajin.getDeskripsi_perajin());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PerajinDetailsActivity.class);
            intent.putExtra("id_perajin", perajin.getId());
            intent.putExtra("profil", perajin.getDeskripsi_perajin());
            intent.putExtra("foto", perajin.getFoto_profil());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return perajinList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView gambar;
        TextView nama;
        TextView perajin;
        TextView profil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.gambar = (ImageView) itemView.findViewById(R.id.item_gambar_perajin);
            this.nama = (TextView) itemView.findViewById(R.id.nama_perusahaan);
            this.perajin = (TextView) itemView.findViewById(R.id.nama_pemilik);
            this.profil = (TextView) itemView.findViewById(R.id.profil);
        }
    }
}
