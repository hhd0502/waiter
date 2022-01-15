package com.hutech.lib.Services;


import com.hutech.lib.Model.UserLoginModel;
import com.hutech.lib.ResultModel.UserLoginResultModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("login")
    Observable<UserLoginResultModel> logIn(@Query("UserName") String userName,
                                           @Query("PassWord") String password);

    @GET("token")
    Observable<UserLoginResultModel> getToken(@Body UserLoginModel userLoginModel);
}
