package com.example.shopmiphamapp.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mContext;
    private List<CategoryItem> lCategory;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(CategoryItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CategoryItem> list) {
        this.lCategory = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem categoryItem = lCategory.get(position);
        if (categoryItem == null) {
            return;
        }

        Helper.loadImage(categoryItem.getImageURL(), holder.imgCategory, holder.progressBar);

        String name = Helper.formatString(categoryItem.getName(), 13);
        holder.tvNameCategory.setText(name);

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

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
         private ImageView imgCategory;
         private TextView tvNameCategory;
         private ProgressBar progressBar;

         public CategoryViewHolder(@NonNull View itemView) {
             super(itemView);

             imgCategory = itemView.findViewById(R.id.img_category);
             tvNameCategory = itemView.findViewById(R.id.title_category);
             progressBar = itemView.findViewById(R.id.progressBar);
         }
     }
}
