package com.example.shopmiphamapp.Bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shopmiphamapp.Database.Bill.Bill;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.Product_Bill.Product_Bill;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.Pay.PayAdapter;
import com.example.shopmiphamapp.Pay.PayItem;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailBillActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rcv_bill_detail;
    private PayAdapter payAdapter;
    private ShopDatabase shopDatabase;
    private User user;
    private int billId;

    private TextView name, address, time, note, tv_price_total;
    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);

        shopDatabase = ShopDatabase.getInstance(this);
        user = HomeActivity.userPublic;

        String billIdJson = getIntent().getStringExtra("billId");
        billId = new Gson().fromJson(billIdJson, Integer.class);

        bill = shopDatabase.billDAO().getBillById(billId);

        intiUi();
        setUi();
        backNavigation();
        recyclerViewBill();
    }

    private void intiUi() {
        toolbar = findViewById(R.id.back_navigation);
        rcv_bill_detail = findViewById(R.id.rcv_bill_detail);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        time = findViewById(R.id.time);
        note = findViewById(R.id.note);
        tv_price_total = findViewById(R.id.tv_price_total);
    }

    private void setUi() {
        name.setText(user.getName());
        address.setText(bill.getDeliveryAddress());

        String date = Helper.formatDate(bill.getPurchaseDate());

        time.setText(date);
        note.setText("Không có");
        String totalMoney = Helper.formatPrice(bill.getTotalMonney());
        tv_price_total.setText(totalMoney + "đ");
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

    private void recyclerViewBill() {
        payAdapter = new PayAdapter();
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_bill_detail.setLayoutManager(linearLayout);
        payAdapter.setData(getListBill());
        rcv_bill_detail.setAdapter(payAdapter);
    }

    private List<PayItem> getListBill() {
        List<PayItem> list = new ArrayList<>();
        List<Product_Bill> productBills = shopDatabase.productBillDAO().getListProductBill(billId);
        for (Product_Bill productBill : productBills) {
            Product product = shopDatabase.productDAO().getProductById(productBill.getProductId());
            String productType = shopDatabase.productTypeDAO().getProductTypeById(product.getProductId()).getName();

            list.add(new PayItem(product.getImgProductURL(), product.getName(),
                    productType, product.getPrice(), productBill.getQuantity()));
        }

        return list;
    }
}