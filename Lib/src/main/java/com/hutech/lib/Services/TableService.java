package com.hutech.lib.Services;

import com.hutech.lib.ResultModel.ChangeStatusModel;
import com.hutech.lib.ResultModel.TableResultModel;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TableService {
    @GET("table/get-all-table")
    Observable<TableResultModel> getAllTable();

    @GET("table/get-serving-table")
    Observable<TableResultModel> getServingTable();

    @POST("table/change-status")
    Observable<ChangeStatusModel> changeStatus(@Query("id") String tableId, @Query("status") int status);
}
