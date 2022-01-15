package com.hutech.waiter.screens.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hutech.lib.ResultModel.UserLoginResultModel;
import com.hutech.lib.presentation.ProgressDialog;
import com.hutech.lib.provider.CacheUserProvider;
import com.hutech.lib.provider.TokenProvider;
import com.hutech.waiter.R;
import com.hutech.waiter.screens.activity.LoginActivity;
import com.bumptech.glide.Glide;
import me.imstudio.core.ui.widget.Button;
import me.imstudio.core.ui.widget.TextView;

public class AccountFragment extends Fragment implements View.OnClickListener {

    Button btnLogOut ;
    TextView userName;
    AppCompatImageView avatar;
    ProgressDialog progressDialog ;

    private TokenProvider tokenProvider;
    private CacheUserProvider userProvider;

    public AccountFragment(Context context) {
        this.userProvider = new CacheUserProvider(context);
        this.tokenProvider = new TokenProvider(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        userName = view.findViewById(R.id.user_name);
        avatar = view.findViewById(R.id.ic_avatar);

//        Bundle bundle = getActivity().getIntent().getExtras();
//        if(bundle == null){
//            Log.v("bundle_user","Null bundle ");
//        }
//        UserLoginResultModel.Data userInfo = (UserLoginResultModel.Data) bundle.get("user");


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
        Log.v("user_name:",username);
        Log.v("avatar_URL:",avatarURL);
        userName.setText(username);
        Glide.with(this)
                .load(avatarURL)
                .into(avatar);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_log_out:
                LogOut();
                break;
        }
    }

    public void LogOut() {
        AlertDialog.Builder ab = createAlertDiaglog();
        ab.show();
    }

    public AlertDialog.Builder createAlertDiaglog(){
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setTitle("Chú ý");
        ab.setMessage("Bạn có chắc chắn muốn đăng xuất ?");
        ab.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userProvider.clear();
                tokenProvider.clear();
                LoginActivity.start(getContext());
            }
        });
        ab.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ab.create();
        return ab;
    }



}