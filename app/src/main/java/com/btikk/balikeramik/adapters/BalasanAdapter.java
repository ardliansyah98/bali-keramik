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
        }
    }
}
