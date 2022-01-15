package com.hutech.lib.provider;

import android.content.Context;
import android.content.SharedPreferences;

import com.hutech.lib.ResultModel.UserLoginResultModel;

public class CacheUserProvider {
    private final Context context;

    public CacheUserProvider(Context context) {
        this.context = context;
    }

    public void saveUser(UserLoginResultModel.Data user){
        SharedPreferences settings = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("UserID", user.get_id());
        editor.putString("avatar_url", user.getAvatarUrl());
        editor.putString("email", user.getEmail());
        editor.putString("username", user.getUsername());
        editor.putString("fullname", user.getFullname());
        editor.putString("phone_number", user.getPhone_number());
        editor.apply();
    }
    public UserLoginResultModel.Data getUser(){
        SharedPreferences settings = this.context.getSharedPreferences("user", Context.MODE_PRIVATE);

        UserLoginResultModel.Data user = new UserLoginResultModel.Data();
        String id = settings.getString("UserID","");
        user.set_id(id);
        user.setAvatar_url(settings.getString("avatar_url",""));
        user.setEmail(settings.getString("email",""));
        user.setUsername(settings.getString("username",""));
        user.setFullname(settings.getString("fullname",""));
        user.setPhone_number(settings.getString("phone_number",""));

        return user;
    }
    public void clear(){
        SharedPreferences settings = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }
}
