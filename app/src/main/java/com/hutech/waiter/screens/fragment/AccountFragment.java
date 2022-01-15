package com.hutech.waiter.screens.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hutech.lib.ResultModel.UserLoginResultModel;
import com.hutech.lib.presentation.ProgressDialog;
import com.hutech.lib.provider.CacheUserProvider;
import com.hutech.lib.provider.TokenProvider;
import com.hutech.waiter.R;
import com.hutech.waiter.screens.activity.LoginActivity;
import com.bumptech.glide.Glide;
import me.imstudio.core.ui.widget.Button;

public class AccountFragment extends Fragment implements View.OnClickListener {

    Button btnLogOut ;
    TextView userName, email;
    AppCompatImageView avatar, btnBack;
    ProgressDialog progressDialog ;

    private final TokenProvider tokenProvider;
    private final CacheUserProvider userProvider;

    public AccountFragment(Context context) {
        this.userProvider = new CacheUserProvider(context);
        this.tokenProvider = new TokenProvider(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        userName = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.txtEmail);
        avatar = view.findViewById(R.id.ic_avatar);
        btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> getActivity().onBackPressed());
        btnLogOut = view.findViewById(R.id.btn_log_out);
        btnLogOut.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        writeDataUser();
    }

    public void writeDataUser(){

        UserLoginResultModel.Data user = userProvider.getUser();

        String username = user.getFullname();
        String avatarURL = user.getAvatarUrl();
        String emailStr = user.getEmail();
        Log.v("user_name:",username);
        Log.v("avatar_URL:",avatarURL);
        userName.setText(username);
        email.setText(emailStr);
        Glide.with(this)
                .load(avatarURL)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                .placeholder(R.drawable.ic_avatar_holder)
                .optionalCircleCrop()
                .error(R.drawable.ic_avatar_holder)
                .into(avatar);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_log_out) {
            LogOut();
        }
    }

    public void LogOut() {
        AlertDialog.Builder ab = createAlertDialog();
        ab.show();
    }

    public AlertDialog.Builder createAlertDialog(){
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setTitle("Chú ý");
        ab.setMessage("Bạn có chắc chắn muốn đăng xuất ?");
        ab.setPositiveButton("Đồng ý", (dialog, which) -> {
            userProvider.clear();
            tokenProvider.clear();
            LoginActivity.start(getContext());
        });
        ab.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        ab.create();
        return ab;
    }



}