package com.example.shopmiphamapp.Category;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopmiphamapp.Category.CategoryItem;
import com.example.shopmiphamapp.Product.ProductAdapter;
import com.example.shopmiphamapp.Product.ProductItem;
import com.example.shopmiphamapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mContext;
    private List<CategoryItem> lCategory;
    private CategoryAdapter.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(CategoryItem item);
    }

    public void setOnItemClickListener(CategoryAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CategoryItem> list) {
        this.lCategory = list;
//        Load du lieu len Adapter
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem categoryItem = lCategory.get(position);
        if (categoryItem == null) {
            return;
        }
        holder.imgCategory.setImageResource(categoryItem.getImage());
        holder.tvNameCategory.setText(categoryItem.getName());

        // Xu ly su kien click, duoc su dung o activity cha
        // Đặt ClickListener cho mục
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(categoryItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lCategory != null) {
            return lCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends  RecyclerView.ViewHolder {
         private ImageView imgCategory;
         private TextView tvNameCategory;

         public CategoryViewHolder(@NonNull View itemView) {
             super(itemView);

             imgCategory = itemView.findViewById(R.id.img_category);
             tvNameCategory = itemView.findViewById(R.id.title_category);
         }
     }
}
