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
import androidx.appcompat.widget.Toolbar;

import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.Pay.PayActivity;
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
    private TextView tv_buy, tv_price_total, count_cart;
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

//        homeActivity = (HomeActivity) getIntent().getSerializableExtra("home_activity");
//        if (homeActivity != null) {
//            TextView count_cart = homeActivity.count_cart;
//            count_cart.setText(count);
//        }

        HomeActivity.count_cart.setText(count);
    }

    private void initUi() {
        rcv_cart = findViewById(R.id.rcv_cart);
        tv_buy = findViewById(R.id.tv_buy);
        tv_price_total = findViewById(R.id.tv_price_total);
        cb_select_all = findViewById(R.id.cb_select_all);
        toolbar = findViewById(R.id.back_navigation);
        count_cart = findViewById(R.id.count_cart);
    }

    private void setUi() {
        String countCart = String.valueOf(shopDatabase.cartDAO().getListCartUser(user.getUserId()).size());
        count_cart.setText("("+ countCart+")");
    }

    private void initListener() {
        selectedItems = cartAdapter.getSelectedItems();
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        cartAdapter = new CartAdapter( mContext,this, isCheckAll);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_cart.setLayoutManager(linearLayout);
        cartAdapter.setData(getListCart());

        rcv_cart.setAdapter(cartAdapter);
    }

    private List<CartItem> getListCart() {
        List<CartItem> list = new ArrayList<>();

        List<Cart> carts = shopDatabase.cartDAO().getListCartUser(user.getUserId());
        for (int i = 0; i < carts.size(); i++) {
            Product product = shopDatabase.cartDAO().getListProductCartById(carts.get(i).getProductId());
//            Log.d(">>> check", product.getName());
            String productType = shopDatabase.productDAO().getProductType(product.getProductId());
            list.add(new CartItem(carts.get(i).getCartId(), product.getProductId(), product.getImgProductURL(),
                    product.getName(), productType, product.getPrice()));
        }

        return list;
    }
}