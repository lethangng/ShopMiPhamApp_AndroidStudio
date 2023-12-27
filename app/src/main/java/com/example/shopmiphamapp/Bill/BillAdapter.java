package com.example.shopmiphamapp.Bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmiphamapp.Cart.CartAdapter;
import com.example.shopmiphamapp.Cart.CartItem;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.Product.ProductAdapter;
import com.example.shopmiphamapp.Product.ProductItem;
import com.example.shopmiphamapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHodel> {
    private Context mContext;

    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(BillItem item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    private List<BillItem> listBill;
    public void setData(List<BillItem> list) {
        listBill = list;
        notifyDataSetChanged();
    }

    public BillAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BillViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillAdapter.BillViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHodel holder, int position) {
        BillItem billItem = listBill.get(position);
        if (billItem == null) {
            return;
        }

        Picasso.get()
                .load(billItem.getImgURL())
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

//        holder.img_product.setImageResource(billItem.getImgId());
        holder.tv_product_name.setText(billItem.getProductName());
        holder.tv_type_product.setText(billItem.getProductType());
        String price = Helper.formatPrice(billItem.getPrice());

        holder.tv_price.setText(price);
        holder.tv_pay_count.setText("x" + String.valueOf(billItem.getCount()));
        String totalPrice = Helper.formatPrice(billItem.getTotalPrice());
        holder.total_price.setText(totalPrice);
        holder.sum_product.setText(String.valueOf(billItem.getSumProduct()) + " sản phẩm");

        // Xu ly su kien click, duoc su dung o activity cha
        // Đặt ClickListener cho mục
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(billItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listBill != null) {
            return listBill.size();
        }
        return 0;
    }

    public class BillViewHodel extends RecyclerView.ViewHolder {
        private ImageView img_product;
        private TextView tv_product_name, tv_type_product, tv_price, tv_pay_count, total_price, sum_product;
        ProgressBar progressBar;

        public BillViewHodel(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_type_product = itemView.findViewById(R.id.tv_type_product);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_pay_count = itemView.findViewById(R.id.tv_pay_count);
            total_price = itemView.findViewById(R.id.total_price);
            sum_product = itemView.findViewById(R.id.sum_product);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
