package com.hutech.waiter.screens.fragment;

import static com.hutech.lib.RetrofitClient.getRetrofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hutech.lib.ResultModel.TableResultModel;
import com.hutech.lib.Services.TableService;
import com.hutech.lib.entity.Table;
import com.hutech.waiter.R;
import com.hutech.waiter.adapter.MapAdapter;
import com.hutech.waiter.adapter.iClickMapItem;
import com.hutech.waiter.screens.activity.MenuActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ServingFragment extends Fragment {

    RecyclerView listServing;
    MapAdapter mapAdapter;
    LinearLayout layoutEmpty;
    View btnAddSmall, btnAddLarge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_serving, container, false);
        listServing = (RecyclerView) view.findViewById(R.id.listServing);
        layoutEmpty = view.findViewById(R.id.layout_empty_1);
        btnAddLarge = view.findViewById(R.id.btn_add_table_large);
        btnAddSmall = view.findViewById(R.id.btn_add_table_small);
        listServing.setLayoutManager(new GridLayoutManager(listServing.getContext(), 3));
        getServingTable();
        onClickAddTable();
        return view;
    }

    private Fragment fragment;

    public void onClickAddTable() {
        btnAddSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new MapFragment();
                loadFragment(fragment);
            }
        });
        btnAddLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new MapFragment();
                loadFragment(fragment);
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getServingTable() {
        TableService tableServices = getRetrofit().create(TableService.class);
        new CompositeDisposable().add(tableServices.getServingTable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(TableResultModel tableResultModel) {
        Log.v("serving_error: ", "get serving table success");
        List<TableResultModel.Data> data = tableResultModel.getData();
        mapAdapter = new MapAdapter(data, getActivity(), new iClickMapItem() {
            @Override
            public void onClick(TableResultModel.Data table, int position) {
                gotoDetailServing(table);
            }
        });
        if (mapAdapter.getItemCount() > 0) {
            layoutEmpty.setVisibility(View.GONE);
        } else layoutEmpty.setVisibility(View.VISIBLE);
        listServing.setAdapter(mapAdapter);
    }

    Table selectedTable;

    private void gotoDetailServing(TableResultModel.Data table) {
        Log.v("go_to_serving_with_table_name:", table.getName());
        Table transTable = new Table();
        transTable.setId(table.get_id());
        transTable.setStatus(table.getStatus());
        transTable.setName(table.getName());
        selectedTable = transTable;
        MenuActivity.start(requireContext(), selectedTable);
    }

    private void handleError(Throwable throwable) {
        Log.v("call_api_error: ", throwable.getMessage());
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}