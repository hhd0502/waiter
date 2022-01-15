package com.hutech.waiter.screens.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
    ImageView btnMinus, btnPlus, imgProduct;
    TextView btnAddToCard;
    private void ititView() {
        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtAmount = findViewById(R.id.txtAmount);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        imgProduct = findViewById(R.id.img_product);
        btnAddToCard = findViewById(R.id.btnAddToCart);
    }
    private static final String BASE_URL_IMG = "http://192.168.1.111:8083/images/product/";
    private void transData() {
        txtTitle.setText(currentProduct.getName());
        txtPrice.setText(currentProduct.getPrice() * 1000 + "vnđ");
        txtDescription.setText(currentProduct.getDescription());
        String imgURL = BASE_URL_IMG +currentProduct.getImage();
        Glide.with(this)
                .load(imgURL)
                .into(imgProduct);
        //handle button
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curAmount = Integer.parseInt(txtAmount.getText().toString());
                if(curAmount > 1){
                    curAmount--;
                    txtAmount.setText(String.valueOf(curAmount));
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            int curAmount = Integer.parseInt(txtAmount.getText().toString());
            @Override
            public void onClick(View v) {
                if(curAmount > 0){
                    curAmount++;
                    txtAmount.setText(String.valueOf(curAmount));
                }
            }
        });
        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curAmount = Integer.parseInt(txtAmount.getText().toString());
            }
        });
    }



}