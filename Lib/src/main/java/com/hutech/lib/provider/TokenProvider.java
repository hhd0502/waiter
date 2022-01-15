package com.hutech.lib.provider;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenProvider {
    private final Context context;

    public TokenProvider(Context context) {
        this.context = context;
    }

    public void saveToken(String token) {
        SharedPreferences settings = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", "Bearer " + token);
        editor.putLong("expires", System.currentTimeMillis() + 86400000); // 24/3600000
        editor.apply();
    }

    public String getToken() {
        SharedPreferences settings = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        long expires = settings.getLong("expires", 0);
        if (expires < System.currentTimeMillis()) {
            return "";
        }
        return settings.getString("token", "");
    }
    public void clear(){
        SharedPreferences settings = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }
}
