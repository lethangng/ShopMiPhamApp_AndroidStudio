package com.example.shopmiphamapp.Bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.example.shopmiphamapp.Product.DetailProductActivity;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailBillActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rcv_bill_detail;
    private PayAdapter payAdapter;
    private ShopDatabase shopDatabase;
    private User user;
    private TextView name, address, time, note, tv_price_total, payMethod;
    private Bill bill;

    private LinearLayout btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);

        shopDatabase = ShopDatabase.getInstance(this);
        user = HomeActivity.userPublic;

        String billId = getIntent().getStringExtra("billId");

        bill = shopDatabase.billDAO().getBillById(billId);

        intiUi();
        setUi();
        backNavigation();
        recyclerViewBill();

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBuy();
            }
        });
    }

    private void intiUi() {
        toolbar = findViewById(R.id.back_navigation);
        rcv_bill_detail = findViewById(R.id.rcv_bill_detail);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        time = findViewById(R.id.time);
        note = findViewById(R.id.note);
        tv_price_total = findViewById(R.id.tv_price_total);
        payMethod = findViewById(R.id.pay_method);
        btnBuy = findViewById(R.id.btn_buy);
    }

    private void setUi() {
        name.setText(user.getName());
        address.setText(bill.getDeliveryAddress());

        String date = Helper.formatDate(bill.getPurchaseDate());

        time.setText(date);
        note.setText("Không có");
        String totalMoney = Helper.formatPrice(bill.getTotalMoney());
        tv_price_total.setText(totalMoney);

        payMethod.setText(bill.getPayMethod());
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
        List<Product_Bill> productBills = shopDatabase.productBillDAO().getListProductBill(bill.getId());
//        Log.d("productBills", productBills.toString());
        for (Product_Bill productBill : productBills) {
            Product product = shopDatabase.productDAO().getProductById(productBill.getProductId());
            String productType = shopDatabase.productTypeDAO().getProductTypeById(product.getProductTypeId()).getName();

            list.add(new PayItem(product.getImgUrl(), product.getName(),
                    productType, product.getPrice(), productBill.getQuantity()));
        }

        return list;
    }

    private void handleBuy() {
        // Lay ra san pham dau tien trong list product_bill
        Product_Bill productBill = shopDatabase.productBillDAO().getListProductBill(bill.getId()).get(0);
        String productIdJson = new Gson().toJson(productBill.getProductId());
        Intent intent = new Intent(DetailBillActivity.this, DetailProductActivity.class);

        // Gửi dữ liệu của mục bằng cách đính kèm nó như một Extra
        intent.putExtra("productId", productIdJson);
        startActivity(intent);
    }
}