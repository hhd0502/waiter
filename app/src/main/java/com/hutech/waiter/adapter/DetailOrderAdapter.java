package com.hutech.waiter.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.hutech.lib.ResultModel.ProductsResultModel;
import com.hutech.lib.entity.ProductWrapper;
import com.hutech.waiter.R;

import java.util.List;

import me.imstudio.core.ui.widget.TextView;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.ViewHolder> {

    private IClickProductItem clickProductItem;
    private List<ProductsResultModel.Data> listOrder;
    public DetailOrderAdapter(List<ProductsResultModel.Data> listOrderTrans) {
        this.listOrder = listOrderTrans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new DetailOrderAdapter.ViewHolder(view);
    }
    private int stt = 1;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsResultModel.Data product = listOrder.get(position);
        holder.stt.setText(String.valueOf(stt));
        stt++;

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()) + ".000 vnđ");
        holder.note.setText("");
        holder.quantity.setText(String.valueOf(product.getQuantity()));

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curQuantity = Integer.parseInt(holder.quantity.getText().toString());
                if(curQuantity > 1){
                    curQuantity--;
                    holder.quantity.setText(String.valueOf(curQuantity));
                }
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curQuantity = Integer.parseInt(holder.quantity.getText().toString());
                if(curQuantity >= 1){
                    curQuantity++;
                    holder.quantity.setText(String.valueOf(curQuantity));
                }
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOrder.remove(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listOrder.size() != 0) {
            return listOrder.size();
        }
        return 0;
    }

    public String getProductId(ProductsResultModel.Data product){
        return product.getId();
    }
    public int getQuantityProduct(ProductsResultModel.Data product){
        return product.getQuantity();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stt, productName, productPrice, addNote,  note, quantity;
        View btnDecrease;
        View btnIncrease;
        View btnRemove;

        public ViewHolder(@NonNull View view) {
            super(view);
            stt = view.findViewById(R.id.stt);
            productName = view.findViewById(R.id.product_name_detail);
            productPrice = view.findViewById(R.id.txtPriceDetailOrder);
            addNote = view.findViewById(R.id.add_note);
            note = view.findViewById(R.id.note);
            quantity = view.findViewById(R.id.quantity);

            btnDecrease = view.findViewById(R.id.btn_decrease);
            btnIncrease = view.findViewById(R.id.btn_increase);
            btnRemove = view.findViewById(R.id.btn_remove);
        }
    }




}
