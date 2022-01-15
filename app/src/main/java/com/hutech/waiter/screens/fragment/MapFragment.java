package com.hutech.waiter.screens.fragment;

import static com.hutech.lib.RetrofitClient.getRetrofit;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hutech.lib.ResultModel.TableResultModel;
import com.hutech.lib.Services.TableService;
import com.hutech.lib.entity.Table;
import com.hutech.waiter.R;
import com.hutech.waiter.adapter.MapAdapter;
import com.hutech.waiter.adapter.iClickMapItem;
import com.hutech.waiter.dialog.ChangeTableStatusDialog;
import com.hutech.waiter.screens.activity.MenuActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MapFragment extends Fragment {

    public MapFragment() {

    }

    RecyclerView listTable;
    private MapAdapter mapAdapter;
    ChangeTableStatusDialog dialog;

    AppCompatImageView btnBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        listTable = (RecyclerView) view.findViewById(R.id.listTable);
        listTable.setLayoutManager(new GridLayoutManager(listTable.getContext(), 3));
        btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        dialog = new ChangeTableStatusDialog();
        getData();
        return view;
    }

    public void getData(){
        TableService tableServices = getRetrofit().create(TableService.class);
        new CompositeDisposable().add(tableServices.getAllTable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(@NonNull TableResultModel tableResultModel) {
        Log.v("map_call-all-table","Access to server");
        List<TableResultModel.Data> data = tableResultModel.getData();
        mapAdapter = new MapAdapter(data, getActivity(), new iClickMapItem() {
            @Override
            public void onClick(TableResultModel.Data table, int position) {
                int status = table.getStatus();
                if(status == 1){
                    gotoDetailEmpty(table, position);
                } else if(status == 2){
                    gotoDetailServing(table);
                } else {
                    gotoChangeOrderedSitting(table);
                }
            }
        });
        listTable.setAdapter(mapAdapter);
    }

    private void handleError(Throwable throwable) {
        Log.v("map_call-all-table","Cannot access to server");
    }

    private void gotoChangeOrderedSitting(TableResultModel.Data table) {
        int status = table.getStatus();
        dialog.setTableId(table.get_id());
        dialog.setStatus(status);
        FragmentManager fm = getFragmentManager();
        fm.executePendingTransactions();
        dialog.setDissmissListener(new ChangeTableStatusDialog.OnDismissListener() {
            @Override
            public void onDismiss(ChangeTableStatusDialog myDialogFragment) {
                listTable.setAdapter(null);
                getData();
            }
        });
        dialog.show(getFragmentManager(),dialog.TAG);
    }

    Table selectedTable;
    private void gotoDetailServing(TableResultModel.Data table) {
        Log.v("map_go_to_serving_with_table_name: ",table.getName());
        Table transTable = new Table();
        transTable.setId(table.get_id());
        transTable.setStatus(table.getStatus());
        transTable.setName(table.getName());
        selectedTable = transTable;
        MenuActivity.start(requireContext(), selectedTable);

    }

    private void gotoDetailEmpty(TableResultModel.Data table, int position) {
        Log.v("map_gotoEmpty_with_table_name: ",table.getName());

        int status = table.getStatus();
        dialog.setTableId(table.get_id());
        dialog.setStatus(status);
        FragmentManager fm = getFragmentManager();
        fm.executePendingTransactions();
        dialog.setDissmissListener(new ChangeTableStatusDialog.OnDismissListener() {
            @Override
            public void onDismiss(ChangeTableStatusDialog myDialogFragment) {
                listTable.setAdapter(null);
                getData();

            }
        });
        dialog.show(getFragmentManager(),dialog.TAG);
        if(table.getStatus()!= status){
            Table transTable = new Table();
            transTable.setId(table.get_id());
            transTable.setStatus(table.getStatus());
            transTable.setName(table.getName());
            selectedTable = transTable;
            MenuActivity.start(requireContext(), selectedTable);
        }
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}