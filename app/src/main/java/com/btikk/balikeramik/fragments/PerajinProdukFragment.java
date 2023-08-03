package com.btikk.balikeramik.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.btikk.balikeramik.KeramikActivity;
import com.btikk.balikeramik.PerajinActivity;
import com.btikk.balikeramik.PerajinDetailsActivity;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.adapters.KeramikAdapter;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;
import com.btikk.balikeramik.models.Keramik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerajinProdukFragment extends Fragment {
    KeramikAdapter keramikAdapter;
    private RecyclerView rvProdukPerajin;
    List<Keramik> keramikList = new ArrayList();
    private AppConfig appConfig = new AppConfig();

    public PerajinProdukFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perajin_produk, container, false);
        this.rvProdukPerajin = (RecyclerView) view.findViewById(R.id.rv_keramik_perajin);
        PerajinDetailsActivity activity = (PerajinDetailsActivity) getActivity();

        int idPerajin = activity.getIdPerajin();
        rvProdukPerajin.setHasFixedSize(true);
        rvProdukPerajin.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2, RecyclerView.VERTICAL, false));
        keramikAdapter = new KeramikAdapter(getActivity().getApplicationContext(), keramikList);
        rvProdukPerajin.setAdapter(keramikAdapter);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.KeramikUrl(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Response Object", "Keramik Perajin Object " + jsonObject);
                if(jsonObject.optString("error").equals("false")){
                    JSONArray keramikArray = jsonObject.getJSONArray("keramik");
                    for(int i = 0; i < keramikArray.length(); i++){
                        JSONObject keramikObject = keramikArray.getJSONObject(i);
                        Keramik keramik = new Keramik(keramikObject.getInt("id"),
                                keramikObject.getInt("id_kategori"),
                                keramikObject.getString("nama_keramik"),
                                keramikObject.getString("dimensi"),
                                keramikObject.getString("warna"),
                                keramikObject.getString("deskripsi_keramik"),
                                appConfig.BaseUrl(keramikObject.getString("gambar")),
                                keramikObject.getString("nama_perajin"),
                                keramikObject.getString("foto_profil"),
                                keramikObject.getString("nama_kategori"));
                        this.keramikList.add(keramik);
                    }
                    keramikAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("sb_perajin", "1");
                params.put("id_perajin", String.valueOf(idPerajin));
                return params;
            }
        };
        MyVolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

        return view;
    }
}