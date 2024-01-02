package com.example.shopmiphamapp.Pay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmiphamapp.Cart.CartItem;
import com.example.shopmiphamapp.Database.Bill.Bill;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.Product_Bill.Product_Bill;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.Product.DetailProductActivity;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class PayActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView rcv_pay;
    private PayAdapter payAdapter;
    private TextView tv_buy, tv_price_total, count_cart, tv_price_total_2;
    private EditText pay_note, pay_address;
    private Toolbar toolbar;

    private FrameLayout progressLayout;
    private ProgressBar progressBarLayout;

    private String payMethod = "Thanh toán khi nhận hàng";
    private ShopDatabase shopDatabase;

    public List<CartItem> cartItemSelects;
    private int totalPrice;

    private Product product;

    private User user;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        shopDatabase = ShopDatabase.getInstance(this);
        user = HomeActivity.userPublic;

        // Lấy các item từ bên CartActivity chuyển sang
        String selectItemsJson = getIntent().getStringExtra("selectItemsJson");
        if (selectItemsJson != null) {
            Type cartItemType = new TypeToken<List<CartItem>>(){}.getType();
            cartItemSelects = new Gson().fromJson(selectItemsJson, cartItemType);
        }

        // Lấy các item từ bên DetalProductActivity chuyển sang
        String productJson = getIntent().getStringExtra("productJson");
        if (productJson != null) {
            product = new Gson().fromJson(productJson, Product.class);
        }

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
        progressLayout = findViewById(R.id.progressLayout);
        progressBarLayout = findViewById(R.id.progressBarLayout);
    }

    private void setUi() {
        tv_price_total.setText(totalPrice + "đ");
        tv_price_total_2.setText(totalPrice + "đ");

        String[] item = {
                "Thanh toán khi nhận hàng",
                "Thanh toán qua ví Momo",
                "Thanh toán qua ngân hàng"
        };

        AutoCompleteTextView autoCompleteTextView;
        ArrayAdapter<String> adapterItem;

        autoCompleteTextView = findViewById(R.id.auto_compelete_txt);
        adapterItem = new ArrayAdapter<String>(this, R.layout.item_pay_methods, item);

        autoCompleteTextView.setAdapter(adapterItem);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                payMethod = item;
            }
        });
    }

    private void initListener() {
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = pay_address.getText().toString().trim();
                String note = pay_note.getText().toString().trim();

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(PayActivity.this, "Vui lòng nhập địa chỉ mua hàng!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                long dateNow = currentDate.getTime();

                Bill bill = new Bill(dateNow, address, totalPrice, user.getId(), payMethod, note);
                Helper.handleLoading(true, progressLayout, progressBarLayout);
                // Thêm vào bill trên firebase
                db.collection("bill").add(bill)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String documentId = documentReference.getId();
                                Bill bill = new Bill(documentId, dateNow, address, totalPrice, user.getId(), payMethod, note);
                                shopDatabase.billDAO().insertBill(bill);
                                handleAddProductBill(documentId);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Helper.handleLoading(false, progressLayout, progressBarLayout);
                                Toast.makeText(PayActivity.this, "Mua hàng thất bại!", Toast.LENGTH_LONG).show();
                            }
                        });;
            }
        });
    }

    private void handleAddProductBill(String billId) {
        // Xử lý từ CartActivity truyền sang
        if (cartItemSelects != null && cartItemSelects.size() > 0) {
            int countSelect = cartItemSelects.size();
            AtomicInteger count = new AtomicInteger(0);

            for (CartItem cartItem : cartItemSelects) {
                Product_Bill productBill = new Product_Bill(billId, cartItem.getProductId(), cartItem.getCount());
                db.collection("productBill").add(productBill)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                count.incrementAndGet();
                                String documentId = documentReference.getId();
                                Product_Bill productBill = new Product_Bill(documentId, billId, cartItem.getProductId(), cartItem.getCount());
                                shopDatabase.productBillDAO().insertProductBill(productBill);

                                if (count.get() == countSelect) {
                                    handleDeleteCart();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Helper.handleLoading(false, progressLayout, progressBarLayout);
                                Toast.makeText(PayActivity.this, "Mua hàng thất bại!", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        } else {
            // Xử lý DetailProduct truyền sang
            Product_Bill productBill = new Product_Bill(billId, product.getId(), DetailProductActivity.count);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("productBill").add(productBill)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String documentId = documentReference.getId();
                            Product_Bill productBill = new Product_Bill(documentId, billId, product.getId(), DetailProductActivity.count);
                            shopDatabase.productBillDAO().insertProductBill(productBill);

                            Toast.makeText(PayActivity.this,
                                    "Mua hàng thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PayActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Helper.handleLoading(false, progressLayout, progressBarLayout);
                            Toast.makeText(PayActivity.this, "Mua hàng thất bại!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void handleDeleteCart() {
        int countSelect = cartItemSelects.size();
        AtomicInteger count = new AtomicInteger(0);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (CartItem cartItem : cartItemSelects) {
            String cartId = cartItem.getCartId();
            db.collection("cart").document(cartId).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            count.incrementAndGet();
                            shopDatabase.cartDAO().deleteCart(cartId);
                            if (count.get() == countSelect) {
                                Toast.makeText(PayActivity.this,
                                        "Mua hàng thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PayActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Helper.handleLoading(false, progressLayout, progressBarLayout);
                            Toast.makeText(PayActivity.this,
                                    "Mua hàng thất bại!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }

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
        payAdapter = new PayAdapter();
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_pay.setLayoutManager(linearLayout);
        payAdapter.setData(getListPay());

        rcv_pay.setAdapter(payAdapter);
    }

    private List<PayItem> getListPay() {
        List<PayItem> list = new ArrayList<>();
        if (cartItemSelects != null && cartItemSelects.size() > 0) {
            for (CartItem cartItem : cartItemSelects) {
                PayItem payItem = new PayItem(cartItem.getImgURL(), cartItem.getProductName(),
                        cartItem.getProductType(), cartItem.getPrice(), cartItem.getCount());
                list.add(payItem);
            }
        } else {
            String productType = shopDatabase.productTypeDAO().getProductTypeById(product.getProductTypeId()).getName();
            PayItem payItem = new PayItem(product.getImgUrl(), product.getName(),
                    productType, product.getPrice(), DetailProductActivity.count);
            list.add(payItem);
        }

        return list;
    }
}