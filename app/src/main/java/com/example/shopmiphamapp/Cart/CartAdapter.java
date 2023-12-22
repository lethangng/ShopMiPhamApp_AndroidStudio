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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.R;

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
    public boolean isCheckAll;

    // Xet moi truong de tham chieu den cha
    public CartActivity cartActivity;
    public CartAdapter(Context mContext, CartActivity cartActivity, boolean isCheckAll) {
        this.mContext = mContext;
        this.cartActivity = cartActivity;
        this.isCheckAll = isCheckAll;
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
        holder.img_cart.setImageResource(cartItem.getImgId());
        holder.tv_product_name.setText(cartItem.getProductName());
        holder.tv_type_product.setText(cartItem.getProductType());

        String price = Helper.formatPrice(cartItem.getPrice());
        holder.tv_price.setText(price);

        int[] count = {listCart.get(position).getCount()};
        holder.tv_count.setText(String.valueOf(count[0]));
//        Log.d(">>>>>", String.valueOf(count[0]));

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count[0] == 1) {
                    return;
                }
                count[0]--;
                String sCount = String.valueOf(count[0]);
                // Set lai tv hien thi
                holder.tv_count.setText(sCount);

                // Set lai count cua cartItem => Ty nua chuyen no sang Activity ThanhToan
                cartItem.setCount(count[0]);

                // Kiem tra xem co trong list khong, co thi moi tru tien
                if (selectedItems.contains(cartItem)) {
                    totalPrice -= cartItem.getPrice();
                    cartActivity.updateTotalPrice();

                    CartItem selectedCartItem = findSelectedItem(cartItem);
                    selectedCartItem.setCount(count[0]);

                }
            }
        });

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]++;
                holder.tv_count.setText(String.valueOf(count[0]));

                // Nếu cartItem này có trong selectedItems thì xử lý khối này
                if (selectedItems.contains(cartItem)) {
                    totalPrice += cartItem.getPrice();
                    cartActivity.updateTotalPrice();

                    CartItem selectedCartItem = findSelectedItem(cartItem);
                    selectedCartItem.setCount(count[0]);
                }
            }
        });

        // Thiết lập trạng thái chọn cho CheckBox
        holder.cb_select.setChecked(itemStateArray.get(position,false));
//        Log.d("countItem", itemStateArray.toString());
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
//                    Log.d("countBuy1", selectedItems.toString());
                    cartActivity.updateTotalPrice();
                } else {
//                    isCheckAll = false;
                    totalPrice -= cartItem.getCount() * cartItem.getPrice();

                    // Xoa khoi list
                    selectedItems.remove(cartItem);
//                    Log.d("countBuy1", selectedItems.toString());
                    cartActivity.updateTotalPrice();
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(cartActivity);
                builder.setMessage("Bạn có chắc chắn muốn xóa không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Xử lý xóa ở đây
//                                Log.d("delete", String.valueOf(listCart.get(position).getCartId()));
                                shopDatabase.cartDAO().deleteCart(listCart.get(position).getCartId());
                                listCart.remove(position);
                                notifyDataSetChanged();
                                cartActivity.updateTotalPrice();
                                Toast.makeText(cartActivity, "Đã xóa sản phẩm khỏi giỏ hàng!", Toast.LENGTH_LONG).show();
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
        });
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
