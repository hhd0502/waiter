package com.hutech.lib.Services;

import com.hutech.lib.ResultModel.CategoryResultModel;
import com.hutech.lib.ResultModel.OrderResultModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("cate/get-all")
    Observable<CategoryResultModel> getAllCategory();
}
