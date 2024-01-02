package com.example.shopmiphamapp.Product;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>  {
    private Context mContext;
    private List<ProductItem> listProduct;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(ProductItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ProductAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<ProductItem> list) {
        listProduct = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductItem productItem = listProduct.get(position);
        if (productItem == null) {
            return;
        }

        Picasso.get()
                .load(productItem.getImage())
                .placeholder(R.drawable.layout_none) // Ảnh placeholder hiển thị trong quá trình tải
                .error(R.drawable.layout_none) // Ảnh hiển thị khi có lỗi xảy ra
                .into(holder.imgProduct, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Quá trình tải ảnh thành công, ẩn ProgressBar và hiển thị ImageView
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imgProduct.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                        holder.progressBar.setVisibility(View.GONE);
                        Log.d("errors", e.toString());
                        throw new RuntimeException(e);
                    }
                });

//        holder.imgProduct.setImageResource(R.drawable.product_1);
        String name = Helper.formatString(productItem.getName(), 25);
        holder.nameProduct.setText(name);
        String price = Helper.formatPrice(productItem.getPrice());
        holder.priceProduct.setText(price);

        String sold = Helper.formatSold(productItem.getSold());

        holder.soldProduct.setText(sold);
        holder.productType.setText(productItem.getProductType());

        // Xu ly su kien click, duoc su dung o activity cha
        // Đặt ClickListener cho mục
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(productItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listProduct != null) {
            return listProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView nameProduct, priceProduct, soldProduct, productType;
        private ProgressBar progressBar;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            nameProduct = itemView.findViewById(R.id.name_product);
            priceProduct = itemView.findViewById(R.id.price_product);
            soldProduct = itemView.findViewById(R.id.sold_product);
            productType = itemView.findViewById(R.id.product_type);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
