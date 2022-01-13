package com.hutech.waiter.dialog;

import static com.hutech.lib.RetrofitClient.getRetrofit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import com.hutech.lib.CallBack;
import com.hutech.lib.ResultModel.ChangeStatusModel;
import com.hutech.lib.ResultModel.TableResultModel;
import com.hutech.lib.Services.TableService;
import com.hutech.lib.entity.Table;
import com.hutech.waiter.R;
import com.tuyenmonkey.mkloader.MKLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ChangeTableStatusDialog extends DialogFragment {

    public static final String TAG = ChangeTableStatusDialog.class.getSimpleName();

    private CallBack.With<Integer> callBack;
    private int selectedStatus;
    private int originStatus;
    private String tableId;

    @BindView(R.id.layout_holder_empty_status)
    LinearLayout layoutEmptyStatus;
    @BindView(R.id.layout_holder_serving_status)
    LinearLayout layoutServingStatus;
    @BindView(R.id.layout_holder_reserved_status)
    LinearLayout layoutReservedStatus;

    @BindView(R.id.check_box_empty_status)
    AppCompatImageView checkBoxEmptyStatus;
    @BindView(R.id.check_box_serving_status)
    AppCompatImageView checkBoxServingStatus;
    @BindView(R.id.check_box_reserved_status)
    AppCompatImageView checkBoxReservedStatus;

    @BindView(R.id.text_empty_status)
    TextView textEmptyStatus;
    @BindView(R.id.text_serving_status)
    TextView textServingStatus;
    @BindView(R.id.text_reserved_status)
    TextView textReservedStatus;
    @BindView(R.id.ic_loader)
    MKLoader icLoader;

    @Override
    public void onStart() {
        super.onStart();
        try {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_table_status, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        ButterKnife.bind(this, view);

        if (originStatus == 2)
        {
            layoutEmptyStatus.setClickable(false);
            layoutReservedStatus.setClickable(false);
        }

        setSelectedStatusView();
        return dialog;
    }

    @OnClick({R.id.layout_holder_empty_status, R.id.layout_holder_serving_status, R.id.layout_holder_reserved_status})
    public void onChooseStatus(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.layout_holder_empty_status:
                selectedStatus = 1;
                break;
            case R.id.layout_holder_serving_status:
                selectedStatus = 2;
                break;
            case R.id.layout_holder_reserved_status:
                selectedStatus = 3;
                break;
        }
        setSelectedStatusView();
    }

    @OnClick(R.id.btn_dismiss)
    @Override
    public void dismiss()
    {
        super.dismiss();
    }

    @OnClick(R.id.btn_confirm)
    public void onConfirm()
    {
        icLoader.setVisibility(View.VISIBLE);
        (new Handler()).postDelayed(() -> {

            if (originStatus == selectedStatus)
            {
                dismiss();
            }
            else
                changeStatus(this.tableId,selectedStatus);
        }, 500);

    }

    public void setStatus(int status)
    {
        if (status>=1 && status <=3)
            originStatus = status;
        selectedStatus = originStatus;
    }
    public void setTableId(String tableId)
    {
        this.tableId = tableId;
    }
    private void onSyncViews()
    {
        layoutEmptyStatus.setBackground(requireContext().getDrawable(R.drawable.box_background));
        layoutServingStatus.setBackground(requireContext().getDrawable(R.drawable.box_background));
        layoutReservedStatus.setBackground(requireContext().getDrawable(R.drawable.box_background));

        checkBoxEmptyStatus.setImageResource(R.drawable.all_style_checkbox_normal);
        checkBoxServingStatus.setImageResource(R.drawable.all_style_checkbox_normal);
        checkBoxReservedStatus.setImageResource(R.drawable.all_style_checkbox_normal);

        textEmptyStatus.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        textServingStatus.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        textReservedStatus.setTextColor(getResources().getColor(R.color.colorTextPrimary));
    }

    private void setSelectedStatusView()
    {
        onSyncViews();
//
        switch (selectedStatus)
        {
            case 1:
                layoutEmptyStatus.setBackground(requireContext().getDrawable(R.drawable.selected_box_background));
                checkBoxEmptyStatus.setImageResource(R.drawable.all_style_checkbox_selected);
                textEmptyStatus.setTextColor(getResources().getColor(R.color.colorJade));
                break;
            case 2:
                layoutServingStatus.setBackground(requireContext().getDrawable(R.drawable.selected_box_background));
                checkBoxServingStatus.setImageResource(R.drawable.all_style_checkbox_selected);
                textServingStatus.setTextColor(getResources().getColor(R.color.colorJade));
                break;
            case 3:
                layoutReservedStatus.setBackground(requireContext().getDrawable(R.drawable.selected_box_background));
                checkBoxReservedStatus.setImageResource(R.drawable.all_style_checkbox_selected);
                textReservedStatus.setTextColor(getResources().getColor(R.color.colorJade));
                break;
        }
    }

    private void changeStatus(String tableId, int status){
        TableService tableServices = getRetrofit().create(TableService.class);
        new CompositeDisposable().add(tableServices.changeStatus(tableId,status)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(ChangeStatusModel changeStatusModel) {
        String status = changeStatusModel.getStatus();
        if(status.equals("true")){
            dismiss();
        }
    }

    private void handleError(Throwable throwable) {
    }
    public interface OnDismissListener {
        void onDismiss(ChangeTableStatusDialog myDialogFragment);
    }

    private OnDismissListener onDismissListener;
    public void setDissmissListener(OnDismissListener dissmissListener) {
        this.onDismissListener = dissmissListener;
    }
    //Call it on the dialogFragment onDissmiss
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }
    }


    public void addOnDoneListener(CallBack.With<Integer> callBack)
    {
        this.callBack = callBack;
    }
}
