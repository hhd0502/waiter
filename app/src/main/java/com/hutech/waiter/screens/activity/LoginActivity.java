package com.hutech.waiter.screens.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import  static com.hutech.lib.RetrofitClient.getRetrofit;

import com.hutech.lib.ResultModel.UserLoginResultModel;
import com.hutech.lib.Services.UserService;

import com.hutech.waiter.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {


    EditText editUserName;

    EditText editPassword;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_activity);

    }

    public void Login(View view) {
        editUserName = findViewById(R.id.edit_user_name);
        editPassword = findViewById(R.id.edit_password);
        String userName = editUserName.getText().toString();
        String password = editPassword.getText().toString();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            UserService userServices = getRetrofit().create(UserService.class);
            new CompositeDisposable().add(userServices.logIn(userName,password)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
    }
    private void handleResponse(UserLoginResultModel userLoginResultModel) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle userInfo = new Bundle();
        userInfo.putSerializable("user",userLoginResultModel.getData());
        intent.putExtras(userInfo);
        startActivity(intent);
        Log.v("login_error","start main activity");
        finish();
    }
    private void handleError(Throwable error) {
        Log.v("login_error","Can't start main activity:"+ error);
    }
}