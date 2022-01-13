package com.hutech.lib;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static Retrofit retrofit;
    private  static  String Base_Url = "http://192.168.1.111:8083/api/";

    public static String getBase_Url() {
        return Base_Url;
    }

    public static void setBase_Url(String base_Url) {
        Base_Url = base_Url;
    }
    public  static  Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return  retrofit;
    }

}
