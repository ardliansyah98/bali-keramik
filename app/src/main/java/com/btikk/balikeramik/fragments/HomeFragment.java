package com.btikk.balikeramik.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.btikk.balikeramik.R;
import com.btikk.balikeramik.configs.AppConfig;
import com.btikk.balikeramik.configs.MyVolleySingleton;

import org.json.JSONObject;

public class HomeFragment extends Fragment {
    AppConfig appConfig = new AppConfig();
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, appConfig.EventsUrl(), response -> {
            Log.d("response", "Response Event " + response + "");
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Response Object", "Event Object " + jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Error  " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Error", "Error: " + error.getMessage() + "");
        });

        MyVolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}