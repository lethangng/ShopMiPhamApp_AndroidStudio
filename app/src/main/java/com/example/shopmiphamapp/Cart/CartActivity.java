package com.example.shopmiphamapp.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.InitShopDatabase;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.Pay.PayActivity;
import com.example.shopmiphamapp.Product.DetailProductActivity;
import com.example.shopmiphamapp.Product.ProductAdapter;
import com.example.shopmiphamapp.Product.ProductItem;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView rcv_cart;
    private CartAdapter cartAdapter;

    private List<CartItem> selectedItems;
    private TextView tv_buy, tv_price_total, count_cart, tvCartNull;
    private CheckBox cb_select_all;

    public boolean isCheckAll = false;

    private Toolbar toolbar;
    private ShopDatabase shopDatabase;
    private User user;

    private HomeActivity homeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        shopDatabase = ShopDatabase.getInstance(this);
        user = HomeActivity.userPublic;

        initUi();
        setUi();

        recyclerViewCart();
        backNavigation();

        initListener();
    }

    public void updateTotalPrice() {
        String tatalPrice = String.valueOf(cartAdapter.getTotalPrice());
        tv_price_total.setText(tatalPrice + "đ");

        String countBuy = String.valueOf(selectedItems.size());
        tv_buy.setText("Mua hàng(" + countBuy + ")");

        String count = String.valueOf(cartAdapter.getCountListCart());
        count_cart.setText("(" + count + ")");

        HomeActivity.count_cart.setText(count);
    }

    private void initUi() {
        rcv_cart = findViewById(R.id.rcv_cart);
        tv_buy = findViewById(R.id.tv_buy);
        tv_price_total = findViewById(R.id.tv_price_total);
        cb_select_all = findViewById(R.id.cb_select_all);
        toolbar = findViewById(R.id.back_navigation);
        count_cart = findViewById(R.id.count_cart);
        tvCartNull = findViewById(R.id.tv_cart_null);
        tvCartNull.setVisibility(View.GONE);
    }

    private void setUi() {
        int countCart = shopDatabase.cartDAO().getListCartUser(user.getId()).size();
        count_cart.setText("(" + countCart + ")");
        if (countCart == 0) {
            tvCartNull.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        selectedItems = cartAdapter.getSelectedItems();
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedItems.size() == 0) {
                    Toast.makeText(CartActivity.this, "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(CartActivity.this, PayActivity.class);
                String selectItemsJson = new Gson().toJson(selectedItems);
                intent.putExtra("selectItemsJson", selectItemsJson);
                intent.putExtra("totalPrice", cartAdapter.getTotalPrice());
                startActivity(intent);
            }
        });

        cb_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheckAll = !isCheckAll;
                cartAdapter.notifyDataSetChanged();
            }
        });
    }

    private void backNavigation() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                HomeActivity.navigationView.setCheckedItem(R.id.nav_home);
            }
        });
    }

    private void recyclerViewCart() {
//        Log.d(">>>", String.valueOf(isCheckAll));
        cartAdapter = new CartAdapter( mContext,this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_cart.setLayoutManager(linearLayout);
        cartAdapter.setData(getListCart());

        rcv_cart.setAdapter(cartAdapter);

        // Đặt ClickListener cho mục trong Adapter
        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CartItem item) {
                Intent intent = new Intent(CartActivity.this, DetailProductActivity.class);
                String productIdJson = new Gson().toJson(item.getProductId());

                // Gửi dữ liệu của mục bằng cách đính kèm nó như một Extra
                intent.putExtra("productId", productIdJson);
                startActivity(intent);
                finish();
            }
        });
    }

    private List<CartItem> getListCart() {
        List<CartItem> list = new ArrayList<>();

        List<Cart> carts = shopDatabase.cartDAO().getListCartUser(user.getId());
        for (Cart cart : carts) {
            Product product = shopDatabase.cartDAO().getListProductCartById(cart.getProductId());
//            Log.d(">>> check", product.getName());
            String productType = shopDatabase.productDAO().getProductType(product.getId());
            list.add(new CartItem(cart.getId(), product.getId(), product.getImgUrl(),
                    product.getName(), productType, product.getPrice()));
        }

        return list;
    }
}