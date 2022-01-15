package com.hutech.waiter.screens.activity;

import static com.hutech.lib.RetrofitClient.getRetrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hutech.lib.Model.OrderCreateModel;
import com.hutech.lib.ResultModel.OrderResultModel;
import com.hutech.lib.ResultModel.ProductsResultModel;
import com.hutech.lib.Services.OrderService;
import com.hutech.lib.entity.Table;
import com.hutech.waiter.R;
import com.hutech.waiter.adapter.DetailOrderAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.imstudio.core.ui.widget.TextView;

public class DetailTemporaryOrderActivity extends AppCompatActivity {

    private static Table currentTable;
    private static List<ProductsResultModel.Data> listOrderProduct;

    public DetailOrderAdapter detailOrderAdapter;

    TextView tableName;
    AppCompatImageView btnClose;
    View btnCreateOrder;
    RecyclerView listOrder;
    TextView quantity;
    FloatingActionButton fabAddProduct;

    public DetailTemporaryOrderActivity() {

    }

    public static void start(Context context, Table table, List<ProductsResultModel.Data> listOrderProductTrans) {

        Intent intent = new Intent(context, DetailTemporaryOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        currentTable = table;
        listOrderProduct = listOrderProductTrans;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_activity);
        getViewId();
        transData();
    }

    public void getViewId() {
        tableName = findViewById(R.id.text_table_name);
        btnClose = findViewById(R.id.btn_close);
        btnCreateOrder = findViewById(R.id.btn_create_order);
        listOrder = findViewById(R.id.recycler_view);
        listOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        quantity = findViewById(R.id.text_quantity);
        fabAddProduct = findViewById(R.id.fab_add);

    }

    private void transData() {

        tableName.setText(currentTable.getName());
        detailOrderAdapter = new DetailOrderAdapter(listOrderProduct);
        quantity.setText(String.valueOf(listOrderProduct.size()));
        listOrder.setAdapter(detailOrderAdapter);

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder(listOrderProduct);
            }
        });
    }

    private static final String STAFF_ID = "59f31522-f0f4-4bae-8e85-9083eadf1aa0";

    private void CreateOrder(List<ProductsResultModel.Data> listOrder) {
        Log.v("create_Order_check: ", String.valueOf(listOrder.size()));
        for (ProductsResultModel.Data item : listOrder) {
            Log.v("create_Order_check: ", String.valueOf(item.toShortString()));
        }
        OrderCreateModel orderCreateModel = new OrderCreateModel();
        orderCreateModel.setTableOrderID(currentTable.getId());
        orderCreateModel.setStaffID(STAFF_ID);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        Log.v("create_Order_check_currentDateTime", currentDateAndTime);
        orderCreateModel.setCheckinTime(currentDateAndTime);

        List<OrderCreateModel.ListOrder> listOrderToDatabase = new ArrayList<>();
        for (ProductsResultModel.Data product : listOrder) {
            OrderCreateModel.ListOrder order = new OrderCreateModel.ListOrder();
            order.setProductID(product.getId());
            order.setAmount(product.getQuantity());
            listOrderToDatabase.add(order);
        }
        orderCreateModel.setListOrder(listOrderToDatabase);
        for( OrderCreateModel.ListOrder item : listOrderToDatabase){
            Log.v("create_Order_check_listOrderToDatabase", item.toString());
        }

        sendOrder(orderCreateModel);
    }

    private void sendOrder(OrderCreateModel orderCreateModel) {
        OrderService orderService = getRetrofit().create(OrderService.class);
        new CompositeDisposable().add(orderService.createOrder(orderCreateModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }


    private void handleResponse(OrderResultModel orderResultModel) {
        Log.v("create_Order_check_create: ", "success");
        String message = orderResultModel.getMessage();
        Log.v("create_Order_check_message: ", message);

        OrderResultModel.Data data = orderResultModel.getData();
        Toast result = Toast.makeText(this, message, Toast.LENGTH_LONG);
        result.show();
        send_KOT_Id_To_KOD(data); // save kot_id for current table
        finish();
    }

    private void send_KOT_Id_To_KOD(OrderResultModel.Data data) {
        String kot_id = String.valueOf(data.getKOT_Id());
        String kot_statusID = String.valueOf(data.getKoT_StatusID());
        Log.v("create_Order_check_kot_id: ", kot_id);
        Log.v("create_Order_check_kot_id: ", kot_statusID);
    }

    private void handleError(Throwable throwable) {
        Log.v("create_Order_check_create_order: ", "failed");
        Toast result = Toast.makeText(this, throwable.toString(), Toast.LENGTH_LONG);
        result.show();
    }
}