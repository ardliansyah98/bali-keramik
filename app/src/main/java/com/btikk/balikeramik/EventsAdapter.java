package com.btikk.balikeramik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btikk.balikeramik.models.Events;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class EventsAdapter extends SliderViewAdapter<EventsAdapter.EventsViewHolder> {
    private List<Events> eventsList;
    private Context context;

    public EventsAdapter(List<Events> eventsList){
        this.eventsList = eventsList;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_events, parent, false);
        return new EventsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder viewHolder, int position) {
        Events events = this.eventsList.get(position);
        Glide.with(this.context).load(events.getGambar()).into(viewHolder.gambar_event);
        viewHolder.judul_event.setText(events.getJudul());
    }

    @Override
    public int getCount() {
        return eventsList.size();
    }

    public class EventsViewHolder extends SliderViewAdapter.ViewHolder{
        ImageView gambar_event;
        TextView judul_event;
        public EventsViewHolder(View itemView) {
            super(itemView);
            this.gambar_event = (ImageView) itemView.findViewById(R.id.item_gambar_events);
            this.judul_event = (TextView) itemView.findViewById(R.id.item_judul_events);
        }
    }
}
