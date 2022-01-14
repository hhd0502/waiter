package com.hutech.waiter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hutech.lib.ResultModel.CategoryResultModel;
import com.hutech.waiter.R;

import java.util.List;

import me.imstudio.core.ui.widget.TextView;

public class TabCategoryAdapter extends RecyclerView.Adapter<TabCategoryAdapter.ViewHolder> {

    private List<CategoryResultModel.Data> listCategory;
    private Context context;
    private IClickCategoryItem clickCategoryItem;

    public TabCategoryAdapter(List<CategoryResultModel.Data> listCategory, Context context, IClickCategoryItem listener)
    {
        this.listCategory = listCategory;
        this.context = context;
        this.clickCategoryItem = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryResultModel.Data category = listCategory.get(position);
        holder.text_category_name.setText(String.valueOf(category.getCategoryName()));
        holder.text_num_category_count.setText("("+String.valueOf(category.getProductCount())+")");
        holder.layout_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCategoryItem.onClick(category, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listCategory.size() > 0 )
            return listCategory.size();
        return 0;
    }

    public void release(){
        this.context = null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_category_name, text_num_category_count;
        LinearLayout layout_holder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_category_name = itemView.findViewById(R.id.text_department_name);
            text_num_category_count = itemView.findViewById(R.id.text_num_table);
            layout_holder = itemView.findViewById(R.id.layout_holder);
        }
    }

}