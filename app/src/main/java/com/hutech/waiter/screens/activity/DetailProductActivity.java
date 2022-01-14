package com.hutech.waiter.screens.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hutech.lib.ResultModel.ProductsResultModel;
import com.hutech.waiter.R;

import me.imstudio.core.ui.widget.TextView;

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
        ititView();
        transData();
    }
    TextView txtTitle, txtPrice, txtDescription, txtAmount;
    ImageView btnMinus, btnPlus;
    TextView btnAddToCard;
    private void ititView() {
        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtAmount = findViewById(R.id.txtAmount);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnAddToCard = findViewById(R.id.btnAddToCart);
    }

    private void transData() {
        txtTitle.setText(currentProduct.getName());
        txtPrice.setText(currentProduct.getPrice() * 1000 + "vnđ");
        txtDescription.setText(currentProduct.getDescription());
        //handle button
        btnMinus.setOnClickListener(new View.OnClickListener() {
            int curAmount = Integer.parseInt(txtAmount.getText().toString());
            @Override
            public void onClick(View v) {
                if(curAmount > 1){
                    curAmount--;
                    txtAmount.setText(curAmount);
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            int curAmount = Integer.parseInt(txtAmount.getText().toString());
            @Override
            public void onClick(View v) {
                if(curAmount > 1){
                    curAmount++;
                    txtAmount.setText(curAmount);
                }
            }
        });
        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}