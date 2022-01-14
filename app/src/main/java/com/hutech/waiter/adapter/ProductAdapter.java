package com.hutech.waiter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hutech.lib.ResultModel.ProductsResultModel;
import com.hutech.waiter.R;

import java.util.List;

import butterknife.BindView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<ProductsResultModel.Data> listProduct;
    private IClickProductItem ClickProductItem;
    public ProductAdapter(Context context, List<ProductsResultModel.Data> listProduct, IClickProductItem listener) {
        this.context  = context;
        this.listProduct = listProduct;
        this.ClickProductItem = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_product, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }
    private static final String BASE_URL_IMAGE = "http://192.168.1.111:8083/images/product/";
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsResultModel.Data product = listProduct.get(position);
        Glide.with(this.context)
                .load(BASE_URL_IMAGE+product.getImage())
                .into(holder.productImg);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()*1000));
        holder.productUnit.setText("Phần");

        holder.transitionsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickProductItem.onClick(product,holder.getAdapterPosition());
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickProductItem.onClickToCart(product,holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        if(listProduct.size() != 0)
        {
            return listProduct.size();
        }
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView productImg;
        TextView productName;
        TextView productPrice;
        TextView productUnit;
        View transitionsContainer;
        View btnAdd;

        public ViewHolder(@NonNull View view) {
            super(view);
            productImg = view.findViewById(R.id.img_product);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.products_price);
            productUnit = view.findViewById(R.id.product_unit);
            transitionsContainer = view.findViewById(R.id.layout_holder);
            btnAdd = view.findViewById(R.id.btn_add_option);
        }
    }

}
