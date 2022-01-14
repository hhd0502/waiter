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
    private static Context context;
    private static Table currentTable;
    private static ProductWrapper productWrapper;
    private static Order ordered;

    public DetailOrderAdapter detailOrderAdapter;

    public static void start(View.OnClickListener onClickListener, Table table, ProductWrapper orderedItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLE, table);
        Intent intent = new Intent(context, MenuActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        currentTable = table;
        productWrapper = orderedItem;
    }
    public static void start(View.OnClickListener onClickListener, Table table, Order order, ProductWrapper orderedItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLE, table);
        Intent intent = new Intent(context, MenuActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        currentTable = table;
        productWrapper = orderedItem;
        ordered = order;
    }

    TextView tableName ;
    AppCompatImageView btnClose;
    View btnCreateOrder;
    RecyclerView listOrder;
    TextView quantity;
    FloatingActionButton fabAddProduct;

    public void getViewId(){
        tableName = findViewById(R.id.text_table_name);
        btnClose = findViewById(R.id.btn_close);
        btnCreateOrder = findViewById(R.id.btn_create_order);
        listOrder = findViewById(R.id.recycler_view);
        listOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        quantity = findViewById(R.id.text_quantity);
        fabAddProduct = findViewById(R.id.fab_add);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_activity);
        getViewId();
    }
}