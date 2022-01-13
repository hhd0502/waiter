package com.hutech.waiter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hutech.lib.ResultModel.TableResultModel;
import com.hutech.waiter.R;
import com.hutech.waiter.dialog.ChangeTableStatusDialog;


import java.util.List;
import android.view.View.OnClickListener;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.TableMapHolder> {

    List<TableResultModel.Data> listTable;

    private final Context context;

    private static iClickMapItem clickListener;

    public MapAdapter(List<TableResultModel.Data> listTable, Context context,iClickMapItem listener  ){
        this.listTable = listTable;
        this.context = context;
        this.clickListener = listener;
    }
    @NonNull
    @Override
    public TableMapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new TableMapHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableMapHolder holder, int position) {
        TableResultModel.Data table = listTable.get(position);
        holder.tableName.setText(table.getName());

        switch (table.getStatus())
        {
            case 1:
                holder.tableName.setBackground(context.getDrawable(R.drawable.ic_empty_table));
                break;
            case 2:
                holder.tableName.setBackground(context.getDrawable(R.drawable.ic_serving_table));
                break;
            case 3:
                holder.tableName.setBackground(context.getDrawable(R.drawable.ic_reserved_table));
                break;
        }

        holder.item_table.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(table, holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        if(listTable.size() != 0)
        {
            return listTable.size();
        }
        return 0;
    }

    public class TableMapHolder extends RecyclerView.ViewHolder
    {
        TextView tableName;
        View item_table;
        public TableMapHolder(@NonNull View view)
        {
            super(view);
            item_table = view.findViewById(R.id.item_table);
            tableName = view.findViewById(R.id.text_table_name);
        }

    }

    public void clear() {
        if(listTable != null && !listTable.isEmpty()){
            int size = listTable.size();
            listTable.clear();
            notifyItemRangeRemoved(0, size);
        }
    }
}
