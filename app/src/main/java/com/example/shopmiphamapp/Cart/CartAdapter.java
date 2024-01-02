package com.example.shopmiphamapp.Cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHodel> {
    private Context mContext;
    private List<CartItem> listCart;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();
    private List<CartItem> selectedItems = new ArrayList<>();
    private int totalPrice = 0;
    public List<CartItem> getSelectedItems() {
        return selectedItems;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public int getCountListCart() {
        return listCart.size();
    }

    // Xet moi truong de tham chieu den cha
    public CartActivity cartActivity;

    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(CartItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CartAdapter(Context mContext, CartActivity cartActivity) {
        this.mContext = mContext;
        this.cartActivity = cartActivity;
    }
    private ShopDatabase shopDatabase = ShopDatabase.getInstance(cartActivity);

    public void setData(List<CartItem> list) {
        listCart = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.CartViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHodel holder, int position) {
        CartItem cartItem = listCart.get(position);
        if (cartItem == null) {
            return;
        }

        Helper.loadImage(cartItem.getImgURL(), holder.img_cart, holder.progressBar);

        String name = Helper.formatString(cartItem.getProductName(), 25);
        holder.tv_product_name.setText(name);
        holder.tv_type_product.setText(cartItem.getProductType());

        String price = Helper.formatPrice(cartItem.getPrice());
        holder.tv_price.setText(price);

        holder.tv_count.setText(String.valueOf(cartItem.getCount()));

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleMinusCount(cartItem, holder.tv_count);
            }
        });

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddCount(cartItem, holder.tv_count);
            }
        });

        // Thiết lập trạng thái chọn cho CheckBox
        // itemStateArray là cái cái array quản lý trạng thái check/uncheck của các checkbox
        // Đại khái là set trạng thái ban đầu cho các checkbox = false
        holder.cb_select.setChecked(itemStateArray.get(position,false));
        holder.cb_select.setChecked(cartActivity.isCheckAll);

        // Xử lý sự kiện khi người dùng chọn hoặc hủy chọn CheckBox
        holder.cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                itemStateArray.put(position, isChecked);
                // Lưu item đã chọn vào danh sách selectedItems
                if (isChecked) {
                    totalPrice += cartItem.getCount() * cartItem.getPrice();

                    // Them vao list
                    selectedItems.add(cartItem);
                    cartActivity.updateTotalPrice();
                } else {
                    totalPrice -= cartItem.getCount() * cartItem.getPrice();

                    // Xoa khoi list
                    selectedItems.remove(cartItem);
                    cartActivity.updateTotalPrice();
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickDelete(position);
            }
        });
        // Xu ly su kien click, duoc su dung o activity cha
        // Đặt ClickListener cho mục
        holder.img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(cartItem);
                }
            }
        });
    }

    private void handleAddCount(CartItem cartItem, TextView tvCount) {
        int mCount = cartItem.getCount();

        String sCount = String.valueOf(mCount + 1);
        // Set lai tv hien thi
        tvCount.setText(sCount);
        cartItem.setCount(mCount + 1);

        // Nếu cartItem này có trong selectedItems thì xử lý khối này
        if (selectedItems.contains(cartItem)) {
            totalPrice += cartItem.getPrice();
            cartActivity.updateTotalPrice();

            CartItem selectedCartItem = findSelectedItem(cartItem);
            selectedCartItem.setCount(mCount + 1);
        }
    }

    private void handleMinusCount(CartItem cartItem, TextView tvCount) {
        int mCount = cartItem.getCount();
        if (mCount == 1) {
            return;
        }
        String sCount = String.valueOf(mCount - 1);
        // Set lai tv hien thi
        tvCount.setText(sCount);

        // Set lai count cua cartItem => Ty nua chuyen no sang Activity ThanhToan
        cartItem.setCount(mCount - 1);

        // Kiem tra xem co trong list được chọn khong, co thi moi tru tien
        if (selectedItems.contains(cartItem)) {
            totalPrice -= cartItem.getPrice();
            cartActivity.updateTotalPrice();

            CartItem selectedCartItem = findSelectedItem(cartItem);
            selectedCartItem.setCount(mCount - 1);
        }
    }

    private void handleClickDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cartActivity);
        builder.setMessage("Bạn có chắc chắn muốn xóa không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý xóa ở đây
                        String cartId = listCart.get(position).getCartId();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("cart").document(cartId).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        shopDatabase.cartDAO().deleteCart(cartId);
                                        listCart.remove(position);
                                        notifyDataSetChanged();
                                        cartActivity.updateTotalPrice();
                                        Toast.makeText(cartActivity, "Đã xóa sản phẩm khỏi giỏ hàng!", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(cartActivity, "Xóa sản phẩm khỏi giỏ hàng thất bại!", Toast.LENGTH_LONG).show();
                                    }
                                });

                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Hủy bỏ xóa
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if (listCart != null) {
            return listCart.size();
        }
        return 0;
    }

    public class CartViewHodel extends RecyclerView.ViewHolder {
        private ImageView img_cart;
        private TextView tv_product_name, tv_type_product, tv_price, tv_count, btnDelete;
        private CheckBox cb_select;
        private ImageButton btn_minus, btn_add;

        private ProgressBar progressBar;

        public CartViewHodel(@NonNull View itemView) {
            super(itemView);

            img_cart = itemView.findViewById(R.id.img_cart);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_type_product = itemView.findViewById(R.id.tv_type_product);
            tv_price = itemView.findViewById(R.id.tv_price);
            cb_select = itemView.findViewById(R.id.cb_select);
            tv_count = itemView.findViewById(R.id.tv_count);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            btn_add = itemView.findViewById(R.id.btn_add);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private CartItem findSelectedItem(CartItem cartItem) {
        for (CartItem selectedItem : selectedItems) {
            if (selectedItem.getCartId() == (cartItem.getCartId())) {
                // Tìm thấy CartItem tương ứng trong selectedItems
                return selectedItem;
            }
        }
        // Không tìm thấy CartItem trong selectedItems
        return null;
    }

}
