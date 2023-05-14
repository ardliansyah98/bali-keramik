package com.btikk.balikeramik.configs;

import android.content.Context;
import android.content.SharedPreferences;

import com.btikk.balikeramik.models.User;

public class SharedPrefManager {
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IDPERAJIN = "keyidperajin";
    private static final String KEY_ISACTIVE = "keyisactive";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_PROFILE_IMAGE = "keygambar";
    // private static final String KEY_ROLE = "keyrole";
    private static final String SHARED_PREF_NAME = "login";
    private static final String KEY_DATE_CREATED = "keydatecreated";
    private static Context ctx;
    private static SharedPrefManager mInstance;

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        SharedPrefManager sharedPrefManager;
        synchronized (SharedPrefManager.class) {
            if (mInstance == null) {
                mInstance = new SharedPrefManager(context);
            }
            sharedPrefManager = mInstance;
        }
        return sharedPrefManager;
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putInt(KEY_IDPERAJIN, user.getId_perajin());
        // editor.putInt(KEY_ROLE, user.getRole());
        editor.putInt(KEY_ISACTIVE, user.getIs_active());
        editor.putString(KEY_NAME, user.getNama());
        editor.putString(KEY_PHONE, user.getNo_telp());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PROFILE_IMAGE, user.getFotoProfil());
        editor.putString(KEY_DATE_CREATED, user.getDateCreated());
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, 0);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, 0);
        return new User(sharedPreferences.getInt(KEY_ID, -1),
                        sharedPreferences.getInt(KEY_IDPERAJIN, -1),
                        sharedPreferences.getInt(KEY_ISACTIVE, -1),
                        sharedPreferences.getString(KEY_NAME, null),
                        sharedPreferences.getString(KEY_EMAIL, null),
                        sharedPreferences.getString(KEY_PHONE, null),
                        sharedPreferences.getString(KEY_PROFILE_IMAGE, null),
                        sharedPreferences.getString(KEY_DATE_CREATED, null));
    }

    public void updateUser(String email, String nama, String phone) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, nama);
        editor.putString(KEY_PHONE, phone);
        editor.apply();
    }

    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
