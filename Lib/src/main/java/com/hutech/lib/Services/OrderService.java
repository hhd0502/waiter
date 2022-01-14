package com.hutech.lib.Services;

import android.database.Observable;

import com.hutech.lib.Model.OrderCreateModel;
import com.hutech.lib.ResultModel.OrderResultModel;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {
    @POST("order/create")
    Observable<OrderResultModel> createOrder(@Body OrderCreateModel orderCreateModel);
}
