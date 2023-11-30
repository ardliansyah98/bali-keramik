package com.btikk.balikeramik.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btikk.balikeramik.KeramikGambarActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.models.GambarKeramik;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class GambarKeramikAdapter extends SliderViewAdapter<GambarKeramikAdapter.GambarViewHolder> {

    private Context context;
    private List<GambarKeramik> gambarKeramikList;

    public GambarKeramikAdapter(Context context, List<GambarKeramik> gambarKeramikList) {
        this.context = context;
        this.gambarKeramikList = gambarKeramikList;
    }

    @Override
    public GambarKeramikAdapter.GambarViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gambar_keramik, parent, false);
        return new GambarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GambarKeramikAdapter.GambarViewHolder viewHolder, int position) {
        final GambarKeramik gambarKeramik = gambarKeramikList.get(position);
        Glide.with(context).load(gambarKeramik.getGambar()).into(viewHolder.gambarKeramikImage);
        viewHolder.gambarKeramikImage.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), KeramikGambarActivity.class);
            intent.putExtra("gambar", gambarKeramik.getGambar());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getCount() {
        return gambarKeramikList.size();
    }

    public class GambarViewHolder extends SliderViewAdapter.ViewHolder{
        ImageView gambarKeramikImage;
        public GambarViewHolder(View itemView) {
            super(itemView);
            gambarKeramikImage = (ImageView) itemView.findViewById(R.id.item_gbr_keramik);
        }
    }
}
