package com.hutech.lib.Services;


import com.hutech.lib.ResultModel.ProductsResultModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ProductService {

    @GET("product/get-all")
    Observable<ProductsResultModel> getAll();
}
