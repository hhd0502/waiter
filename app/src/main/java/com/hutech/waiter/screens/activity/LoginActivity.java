package com.hutech.waiter.screens.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import static com.hutech.lib.RetrofitClient.getRetrofit;

import com.hutech.lib.ResultModel.UserLoginResultModel;
import com.hutech.lib.Services.UserService;

import com.hutech.lib.provider.CacheUserProvider;
import com.hutech.lib.provider.TokenProvider;
import com.hutech.waiter.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {


    EditText editUserName;
    EditText editPassword;

    private TokenProvider tokenProvider;
    private CacheUserProvider cacheUserProvider;

    public LoginActivity() {
        tokenProvider = new TokenProvider(this);
        cacheUserProvider = new CacheUserProvider(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_activity);
        initView();
    }

    private void initView() {
        editUserName = findViewById(R.id.edit_user_name);
        editPassword = findViewById(R.id.edit_password);
        HideReturnsTransformationMethod showPassword = HideReturnsTransformationMethod.getInstance();
        PasswordTransformationMethod hidePassword = PasswordTransformationMethod.getInstance();

        editPassword.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    editPassword.setTransformationMethod(showPassword);
                    return true;
                case MotionEvent.ACTION_UP:
                    editPassword.setTransformationMethod(hidePassword);
                    return true;
            }
            return false;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (tokenProvider != null && cacheUserProvider != null) {
            if (isLoggedIn()) {
                startMain();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tokenProvider != null && cacheUserProvider != null) {
            if (isLoggedIn()) {
                startMain();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (tokenProvider != null && cacheUserProvider != null) {
            if (isLoggedIn()) {
                startMain();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tokenProvider = null;
        cacheUserProvider = null;
    }

    public boolean isLoggedIn() {
        return !tokenProvider.getToken().equals("");
    }

    public void Login(View view) {
        editUserName = findViewById(R.id.edit_user_name);
        editPassword = findViewById(R.id.edit_password);
        String userName = editUserName.getText().toString();
        String password = editPassword.getText().toString();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            UserService userServices = getRetrofit().create(UserService.class);
            new CompositeDisposable().add(userServices.logIn(userName, password)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        }
    }

    private void handleResponse(UserLoginResultModel user) {

        if (tokenProvider != null && cacheUserProvider != null) {
            String token = user.getData().getToken();
            tokenProvider.saveToken(token);
            cacheUserProvider.saveUser(user.getData());
            startMain();
        } else {
            Log.v("login_error", "Can't start main activity - cacheUserProvider is NULL");
            Log.v("login_error", "Can't start main activity - tokenProvider is NULL");
            finish();
        }
    }

    public void startMain() {
        MainActivity.start(this, cacheUserProvider);
        finish();
    }

    private void handleError(Throwable error) {
        Log.v("login_error", "Can't start main activity:" + error);
    }
}