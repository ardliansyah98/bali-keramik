package com.btikk.balikeramik.configs;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyVolleySingleton {
    private static Context context;
    private static MyVolleySingleton instance;
    private RequestQueue requestQueue;

    private MyVolleySingleton(Context context){
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized MyVolleySingleton getInstance(Context context){
        MyVolleySingleton myVolleySingleton;
        synchronized (MyVolleySingleton.class){
            if(instance == null){
                instance = new MyVolleySingleton(context);
            }
            myVolleySingleton = instance;
        }
        return myVolleySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (this.requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return this.requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
