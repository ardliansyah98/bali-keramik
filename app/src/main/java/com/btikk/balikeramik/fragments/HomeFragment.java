package com.btikk.balikeramik.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.adapters.EventsAdapter;
import com.btikk.balikeramik.adapters.KategoriAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.Events;
import com.btikk.balikeramik.models.Kategori;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private AppConfig appConfig = new AppConfig();
    private SliderView sliderView;
    private RecyclerView rvKategori;
    private EventsAdapter eventsAdapter;
    private KategoriAdapter kategoriAdapter;
    private RelativeLayout loadingPage;
    ArrayList<Events> eventsArrayList;
    ArrayList<Kategori> kategoriArrayList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Linking the XML resources
        sliderView = (SliderView) view.findViewById(R.id.img_slider_events);
        rvKategori = (RecyclerView) view.findViewById(R.id.rv_kategori);
        loadingPage = (RelativeLayout) view.findViewById(R.id.loading_page);

        // Loading all data from database
        loadEvents();
        loadKategori();

        // Inflate the layout for this fragment
        return view;
    }

    private void loadEvents() {
        eventsArrayList = new ArrayList<>();
        eventsAdapter = new EventsAdapter(getActivity().getApplicationContext(), eventsArrayList);
        sliderView.setSliderAdapter(eventsAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
        // Showing Loading bar
        loadingPage.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, appConfig.EventsUrl(), response -> {
            Log.d("response", "Response Event " + response + "");
            // Hiding Loading bar
            loadingPage.setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Response Object", "Event Object " + jsonObject);
                if(jsonObject.optString("error").equals("false")){
                    JSONArray eventsArray = jsonObject.getJSONArray("events");
                    // Log.d("Events object", "Event objscts" + eventsArray);
                    for(int i = 0; i < eventsArray.length(); i++){
                        JSONObject eventObject = eventsArray.getJSONObject(i);
                        Events events = new Events(eventObject.getInt("id"),
                                                   eventObject.getString("judul"),
                                                   eventObject.getString("konten"),
                                                   appConfig.BaseUrl(eventObject.getString("gambar")));
                        this.eventsArrayList.add(events);
                    }
                    // Log.d("Events List", "Event List " + eventsArrayList);
                    // Toast.makeText(getActivity().getApplicationContext(), "If you see this toast, it should've been working...", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Error  " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            eventsAdapter.notifyDataSetChanged();
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Error", "Error: " + error.getMessage() + "");
        });

        MyVolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void loadKategori(){
        kategoriArrayList = new ArrayList<>();
        kategoriAdapter = new KategoriAdapter(getActivity().getApplicationContext(), kategoriArrayList);
        rvKategori.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2, RecyclerView.HORIZONTAL, false));
        rvKategori.setAdapter(kategoriAdapter);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, appConfig.KategoriUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Kategori", "Kateogori response: " + response);
                if(jsonObject.optString("error").equals("false")){
                    JSONArray kategoriArray = jsonObject.getJSONArray("kategori");
                    for(int i = 0; i < kategoriArray.length(); i++){
                        JSONObject kategoriObject = kategoriArray.getJSONObject(i);
                        Kategori kategori = new Kategori(kategoriObject.getInt("id"),
                                                         kategoriObject.getString("nama_kategori"),
                                                         appConfig.BaseUrl(kategoriObject.getString("gambar_kategori")));
                        this.kategoriArrayList.add(kategori);
                    }
                    kategoriAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            // Hiding Loading bar
            loadingPage.setVisibility(View.GONE);
        });
        MyVolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }
}