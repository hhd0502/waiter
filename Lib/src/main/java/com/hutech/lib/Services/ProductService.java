package com.hutech.lib.Services;


import com.hutech.lib.ResultModel.ProductsResultModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {

    @GET("product/get-all")
    Observable<ProductsResultModel> getAll();

    @GET("product/get-by-cateId")
    Observable<ProductsResultModel> getProductByCategoryId(@Query("id") int categoryID);
}
