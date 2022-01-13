package com.hutech.waiter.screens.fragment;

import static com.hutech.lib.RetrofitClient.getRetrofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hutech.lib.ResultModel.TableResultModel;
import com.hutech.lib.Services.TableService;
import com.hutech.waiter.R;
import com.hutech.waiter.adapter.MapAdapter;
import com.hutech.waiter.adapter.iClickMapItem;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ServingFragment extends Fragment {

    RecyclerView listServing;
    MapAdapter mapAdapter;
    LinearLayout layoutEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_serving,container,false);
        listServing = (RecyclerView) view.findViewById(R.id.listServing);
        layoutEmpty = view.findViewById(R.id.layout_empty_1);
        listServing.setLayoutManager(new GridLayoutManager(listServing.getContext(), 3));
        getServingTable();
        return view;
    }

    private void getServingTable() {
        TableService tableServices = getRetrofit().create(TableService.class);
        new CompositeDisposable().add(tableServices.getServingTable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(TableResultModel tableResultModel) {
        Log.v("serving_error: ","get serving table success");
        List<TableResultModel.Data> data = tableResultModel.getData();
        mapAdapter = new MapAdapter(data, getActivity(), new iClickMapItem() {
            @Override
            public void onClick(TableResultModel.Data table, int position) {
                gotoDetailServing(table);
            }
        });
        if(mapAdapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }
        listServing.setAdapter(mapAdapter);
    }

    private void gotoDetailServing(TableResultModel.Data table) {
        Log.v("go_to_serving_with_table_name:",table.getName());
    }

    private void handleError(Throwable throwable) {
        Log.v("call_api_error: ",throwable.getMessage());
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}