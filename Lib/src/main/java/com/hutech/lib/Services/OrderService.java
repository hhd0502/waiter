package com.hutech.lib.Services;

import com.hutech.lib.Model.OrderCreateModel;
import com.hutech.lib.ResultModel.OrderResultModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {
    @POST("order/create")
    Observable<OrderResultModel> createOrder(@Body OrderCreateModel orderCreateModel);
}
