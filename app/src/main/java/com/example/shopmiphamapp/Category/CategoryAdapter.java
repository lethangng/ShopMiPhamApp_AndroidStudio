package com.example.shopmiphamapp.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
//        Load du lieu len Adapter
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

//        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(categoryItem.getImageURL())
                .placeholder(R.drawable.layout_none) // Ảnh placeholder hiển thị trong quá trình tải
                .error(R.drawable.layout_none) // Ảnh hiển thị khi có lỗi xảy ra
                .into(holder.imgCategory, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Quá trình tải ảnh thành công, ẩn ProgressBar và hiển thị ImageView
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imgCategory.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                        holder.progressBar.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                });


//        holder.imgCategory.setImageResource(categoryItem.getImage());
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
