package com.example.shopmiphamapp.Bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.shopmiphamapp.Database.Bill.Bill;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.Product_Bill.Product_Bill;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    private RecyclerView rcv_bill;
    private BillAdapter billAdapter;
    private Toolbar toolbar;
    private ShopDatabase shopDatabase;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        shopDatabase = ShopDatabase.getInstance(this);
        user = HomeActivity.userPublic;

        intiUi();

        backNavigation();
        recyclerViewBill();
    }

    private void intiUi() {
        toolbar = findViewById(R.id.back_navigation);
        rcv_bill = findViewById(R.id.rcv_bill);
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

    private void recyclerViewBill() {
        billAdapter = new BillAdapter( this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_bill.setLayoutManager(linearLayout);
        billAdapter.setData(getListBill());
        rcv_bill.setAdapter(billAdapter);

        billAdapter.setOnItemClickListener(new BillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BillItem item) {
                Intent intent = new Intent(BillActivity.this, DetailBillActivity.class);
                String billId = new Gson().toJson(item.getBillId());
                intent.putExtra("billId", billId);
                startActivity(intent);
            }
        });
    }

    private List<BillItem> getListBill() {
        List<BillItem> list = new ArrayList<>();
        List<Bill> bills = shopDatabase.billDAO().getBillByUserId(user.getUserId());
        for (Bill bill : bills) {
            List<Product_Bill> productBills = shopDatabase.productBillDAO().getListProductBill(bill.getId());
            Product_Bill productBill = productBills.get(0);
            Product product = shopDatabase.productDAO().getProductById(productBill.getProductId());
            String productType = shopDatabase.productTypeDAO().getProductTypeById(product.getProductId()).getName();

            list.add(new BillItem(bill.getId(), productBill.getProductId(), product.getImgProductURL(),
                    product.getPrice(), productBill.getQuantity(), bill.getTotalMonney(),
                    product.getName(), productType, productBills.size()));
        }

        return list;
    }
}