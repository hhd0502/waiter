package com.hutech.waiter.screens.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hutech.lib.entity.Order;
import com.hutech.lib.entity.ProductWrapper;
import com.hutech.lib.entity.Table;
import com.hutech.waiter.R;
import com.hutech.waiter.adapter.DetailOrderAdapter;

import me.imstudio.core.ui.widget.TextView;

public class DetailTemporaryOrderActivity extends AppCompatActivity {

    private static final String TABLE = "table";
    private static Table currentTable;
    private static ProductWrapper productWrapper;
    private static Order ordered;

    public DetailOrderAdapter detailOrderAdapter;
    public DetailTemporaryOrderActivity (){

    }

    TextView tableName ;
    AppCompatImageView btnClose;
    View btnCreateOrder;
    RecyclerView listOrder;
    TextView quantity;
    FloatingActionButton fabAddProduct;

    public static void start(Context context, Table table) {

        Intent intent = new Intent(context, DetailTemporaryOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        currentTable = table;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_activity);
        getViewId();
        transData();
    }
    public void getViewId(){
        tableName = findViewById(R.id.text_table_name);
        btnClose = findViewById(R.id.btn_close);
        btnCreateOrder = findViewById(R.id.btn_create_order);
        listOrder = findViewById(R.id.recycler_view);
        listOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        quantity = findViewById(R.id.text_quantity);
        fabAddProduct = findViewById(R.id.fab_add);

    }
    private void transData() {
        tableName.setText(currentTable.getName());
    }
}