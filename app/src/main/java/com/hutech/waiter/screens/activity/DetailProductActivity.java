package com.hutech.waiter.screens.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hutech.lib.ResultModel.ProductsResultModel;
import com.hutech.waiter.R;

public class DetailProductActivity extends AppCompatActivity {

    private static ProductsResultModel.Data currentProduct;

    public static void start(Context context, ProductsResultModel.Data product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("product",product);
        Intent intent = new Intent(context,DetailProductActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        currentProduct = product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
    }
}