package com.example.shopmiphamapp.Pay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmiphamapp.Cart.CartItem;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView rcv_pay;
    private PayAdapter payAdapter;
    private TextView tv_buy, tv_price_total, count_cart, tv_price_total_2;
    private EditText pay_note, pay_address;
    private Toolbar toolbar;
    private ShopDatabase shopDatabase;

    List<CartItem> cartItemSelects;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        shopDatabase = ShopDatabase.getInstance(this);

        // Lấy các item từ bên CartActivity chuyển sang
        String selectItemsJson = getIntent().getStringExtra("selectItemsJson");
        Type cartItemType = new TypeToken<List<CartItem>>(){}.getType();
        cartItemSelects = new Gson().fromJson(selectItemsJson, cartItemType);

        totalPrice = getIntent().getIntExtra("totalPrice", 0);

//        Log.d("totalPrice", String.valueOf(totalPrice));

        initUi();
        backNavigation();

        recyclerViewCart();
        setUi();
        initListener();

    }

    private void initUi() {
        rcv_pay = findViewById(R.id.rcv_pay);
        tv_buy = findViewById(R.id.tv_buy);
        tv_price_total = findViewById(R.id.tv_price_total);
        tv_price_total_2 = findViewById(R.id.tv_price_total_2);
        pay_address = findViewById(R.id.pay_address);
        pay_note = findViewById(R.id.pay_note);
        toolbar = findViewById(R.id.back_navigation);
    }

    private void setUi() {
        tv_price_total.setText(String.valueOf(totalPrice) + "đ");
        tv_price_total_2.setText(String.valueOf(totalPrice) + "đ");
    }

    private void initListener() {
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = pay_address.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(PayActivity.this, "Vui lòng nhập địa chỉ mua hàng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (CartItem cartItem : cartItemSelects) {
                    shopDatabase.cartDAO().deleteCart(cartItem.getCartId());
                }

                Toast.makeText(PayActivity.this, "Mua hàng thành công!", Toast.LENGTH_LONG).show();

                // Chờ 1 giây trước khi chuyển hướng
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(PayActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }, 1000);
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
            }
        });
    }

    private void recyclerViewCart() {
        payAdapter = new PayAdapter();
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_pay.setLayoutManager(linearLayout);
        payAdapter.setData(getListPay());

        rcv_pay.setAdapter(payAdapter);
    }

    private List<PayItem> getListPay() {
        List<PayItem> list = new ArrayList<>();
        for (CartItem cartItem : cartItemSelects) {
            PayItem payItem = new PayItem(cartItem.getImgId(), cartItem.getProductName(),
                    cartItem.getProductType(), cartItem.getPrice(), cartItem.getCount());
            list.add(payItem);
        }

        return list;
    }
}