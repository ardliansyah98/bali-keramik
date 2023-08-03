package com.btikk.balikeramik.adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btikk.balikeramik.EventDetailsActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.models.Events;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.List;

/* loaded from: classes.dex */
public class EventsAdapter extends SliderViewAdapter<EventsAdapter.EventsViewHolder> {
    private Context context;
    private List<Events> events;

    public EventsAdapter(Context context, List<Events> events) {
        this.context = context;
        this.events = events;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.smarteist.autoimageslider.SliderViewAdapter
    public EventsViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_events, parent, false);
        return new EventsViewHolder(v);
    }

    @Override // com.smarteist.autoimageslider.SliderViewAdapter
    public void onBindViewHolder(EventsViewHolder viewHolder, int position) {
        final Events event = this.events.get(position);
        Glide.with(this.context).load(event.getGambar()).into(viewHolder.gambar_events);
        viewHolder.judul_events.setText(event.getJudul());
        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EventDetailsActivity.class);
            intent.putExtra("id_event", event.getId_event());
            intent.putExtra("judul", event.getJudul());
            intent.putExtra("konten", event.getKonten());
            intent.putExtra("tgl", event.getDate());
            intent.putExtra("gambar", event.getGambar());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        });
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.events.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class EventsViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView gambar_events;
        TextView judul_events;

        public EventsViewHolder(View itemView) {
            super(itemView);
            this.gambar_events = (ImageView) itemView.findViewById(R.id.item_gambar_events);
            this.judul_events = (TextView) itemView.findViewById(R.id.item_judul_events);
        }
    }
}