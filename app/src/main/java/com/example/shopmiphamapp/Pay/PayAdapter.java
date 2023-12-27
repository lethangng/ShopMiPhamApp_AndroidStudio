package com.example.shopmiphamapp.Pay;

import android.content.Context;
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

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayViewHodel> {
    private Context mContext;
    private List<PayItem> mListPay;
    public void setData(List<PayItem> list) {
        this.mListPay = list;
    }

    @NonNull
    @Override
    public PayViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay, parent, false);
        return new PayAdapter.PayViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayViewHodel holder, int position) {
        PayItem payItem = mListPay.get(position);
        if (payItem == null) {
            return;
        }
        Picasso.get()
                .load(payItem.getImgURL())
                .placeholder(R.drawable.layout_none) // Ảnh placeholder hiển thị trong quá trình tải
                .error(R.drawable.layout_none) // Ảnh hiển thị khi có lỗi xảy ra
                .into(holder.img_product, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Quá trình tải ảnh thành công, ẩn ProgressBar và hiển thị ImageView
                        holder.progressBar.setVisibility(View.GONE);
                        holder.img_product.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                        holder.progressBar.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                });
//        holder.img_product.setImageResource(payItem.getImgId());
        holder.tv_product_name.setText(payItem.getProductName());
        holder.tv_type_product.setText(payItem.getProductType());
        String price = Helper.formatPrice(payItem.getPrice());
        holder.tv_price.setText(price);
        holder.tv_pay_count.setText("x" + String.valueOf(payItem.getCount()));

    }

    @Override
    public int getItemCount() {
        if (mListPay != null) {
            return mListPay.size();
        }
        return 0;
    }

    public class PayViewHodel extends RecyclerView.ViewHolder {
        private ImageView img_product;
        private TextView tv_product_name, tv_type_product, tv_price, tv_pay_count;
        private ProgressBar progressBar;

        public PayViewHodel(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_type_product = itemView.findViewById(R.id.tv_type_product);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_pay_count = itemView.findViewById(R.id.tv_pay_count);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
