package com.example.shopmiphamapp.Product;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;

import com.example.shopmiphamapp.Carousel.CarouselAdapter;
import com.example.shopmiphamapp.Carousel.CarouselItem;
import com.example.shopmiphamapp.Cart.CartActivity;
import com.example.shopmiphamapp.Cart.CartItem;
import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.FavoriteProduct.FavoriteProduct;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.FavoriteProduct.FavoriteProductActivity;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.Login.RegiterActivity;
import com.example.shopmiphamapp.Pay.PayActivity;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.core.text.HtmlCompat;

import org.checkerframework.checker.units.qual.C;

import me.relex.circleindicator.CircleIndicator3;

public class DetailProductActivity extends AppCompatActivity {
    private ShopDatabase shopDatabase;
    private Toolbar toolbar;
    private boolean isHeartSelected = false;
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private TextView product_desc, product_sold, product_price, product_name, count_cart, product_type, btnBuy;
    private ImageButton btn_add_cart, btn_cart, btn_heart;
    private User user;
    private Product product;
    private int productId;

    public static int count = 1;

    private LinearLayout layoutPayBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        shopDatabase  = ShopDatabase.getInstance(this);

        initUi();
        backNavigation();


        String productIdJson = getIntent().getStringExtra("productId");
        productId = new Gson().fromJson(productIdJson, Integer.class);
        product = shopDatabase.productDAO().getProductById(productId);
        user = HomeActivity.userPublic;

        setUi();

        carousel();

        updateCount();
        initListener();
        handleLayoutPayBottom();
    }

    private void initUi() {
        mViewPager2 = findViewById(R.id.view_page2_carousel);
        mCircleIndicator3 = findViewById(R.id.cricle_indicator_3);
        toolbar = findViewById(R.id.back_navigation);
        btn_heart = findViewById(R.id.btn_heart);
        product_desc = findViewById(R.id.product_desc);
        product_sold = findViewById(R.id.product_sold);
        product_price = findViewById(R.id.product_price);
        product_name = findViewById(R.id.product_name);
        btn_add_cart = findViewById(R.id.btn_add_cart);
        count_cart = findViewById(R.id.count_cart);
        btn_cart = findViewById(R.id.btn_cart);
        product_type = findViewById(R.id.product_type);
        btnBuy = findViewById(R.id.btn_buy);
        layoutPayBottom = findViewById(R.id.layout_pay_bottom);
    }

    private void setUi() {
        layoutPayBottom.setVisibility(View.GONE);
        product_desc.setText(HtmlCompat.fromHtml(product.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        String price = Helper.formatPrice(product.getPrice());
        product_price.setText(price);
        product_name.setText(product.getName());

        String sold = Helper.formatSold(product.getSold());

        product_sold.setText(sold);
        String productType = shopDatabase.productDAO().getProductType(product.getId());
        product_type.setText(productType);

        boolean check = shopDatabase.favoriteProductDAO().checkFavoriteProductExist(product.getId(), user.getId());
        if (check) {
            btn_heart.setImageResource(R.drawable.ic_heart_selected);
            isHeartSelected = true;
        }
    }

    private void initListener() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layoutPayBottom = findViewById(R.id.layout_pay_bottom);
                layoutPayBottom.setVisibility(View.VISIBLE);
            }
        });
        // Cart Btn
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkCart = shopDatabase.cartDAO().checkProductExist(productId, user.getId());
                if (checkCart) {
                    Toast.makeText(DetailProductActivity.this,
                            "Sản phẩm đã tồn tại trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Cart cart = new Cart(productId, user.getId());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("cart").add(cart)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    String documentId = documentReference.getId();
                                    Cart cart = new Cart(documentId, productId, user.getId());
                                    shopDatabase.cartDAO().insertCart(cart);

                                    updateCount();
                                    Toast.makeText(DetailProductActivity.this,
                                            "Thêm sản phẩm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xử lý khi thêm thất bại
                                    Toast.makeText(DetailProductActivity.this,
                                            "Thêm sản phẩm vào giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHeartSelected = !isHeartSelected;
                boolean check = shopDatabase.favoriteProductDAO().checkFavoriteProductExist(product.getId(), user.getId());

                if (isHeartSelected) {
                    if (!check) {
                        FavoriteProduct favoriteProduct = new FavoriteProduct(user.getId(), productId);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("favoriteProduct").add(favoriteProduct)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        btn_heart.setImageResource(R.drawable.ic_heart_selected);
                                        String documentId = documentReference.getId();
                                        FavoriteProduct favoriteProduct = new FavoriteProduct(documentId, user.getId(), productId);
                                        shopDatabase.favoriteProductDAO().insertFavoriteProduct(favoriteProduct);

                                        Toast.makeText(DetailProductActivity.this,
                                                "Thêm sản phẩm yêu thích thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DetailProductActivity.this,
                                                "Thêm sản phẩm yêu thích thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    // Xử lý xóa ở đây
                    String favoriteProductId = shopDatabase.favoriteProductDAO().getFavoriteProductId(user.getId(), product.getId());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("favoriteProduct").document(favoriteProductId).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    btn_heart.setImageResource(R.drawable.ic_heart);
                                    shopDatabase.favoriteProductDAO().deleteFavoriteProduct(user.getId(), productId);
                                    Toast.makeText(DetailProductActivity.this,
                                            "Xóa sản phẩm yêu thích thành công!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailProductActivity.this, "Xóa sản phẩm yêu thích thất bại!", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    private void handleLayoutPayBottom() {
        ImageButton btnMinus = findViewById(R.id.btn_minus);
        ImageButton btnAdd= findViewById(R.id.btn_add);
        TextView tvCount = findViewById(R.id.tv_count);
        ImageView imgProduct =findViewById(R.id.img_product);
        TextView tvPrice2 = findViewById(R.id.tv_price_2);
        TextView tvSold = findViewById(R.id.tv_sold);
        ImageButton btnClose = findViewById(R.id.btn_close);
        TextView btnPay2 = findViewById(R.id.btn_pay_2);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Helper.loadImage(product.getImgUrl(), imgProduct, progressBar);

        String price = Helper.formatPrice(product.getPrice());
        tvPrice2.setText(price);
        tvSold.setText("Kho: " + product.getSold());
        tvCount.setText(String.valueOf(count));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutPayBottom.setVisibility(View.GONE);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 1) {
                    return;
                }
                count--;
                tvCount.setText(String.valueOf(count));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == product.getSold()) {
                    return;
                }
                count++;
                tvCount.setText(String.valueOf(count));
            }
        });

        btnPay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailProductActivity.this, PayActivity.class);
                String productJson = new Gson().toJson(product);
                intent.putExtra("productJson", productJson);
                intent.putExtra("totalPrice", count * product.getPrice());
                startActivity(intent);
            }
        });
    }

    // Update lại số lượng trong cart khi btn_add_cart được nhấn
    private void updateCount() {
        String countCart = String.valueOf(shopDatabase.cartDAO().getListCartUser(user.getId()).size());
        count_cart.setText(countCart);
    }

    private void backNavigation() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void carousel() {
        CarouselAdapter adapter = new CarouselAdapter(DetailProductActivity.this);
        adapter.setData(getListCarousel());
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);

        // Auto slider
        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mViewPager2.getCurrentItem() == adapter.getItemCount() -1) {
                    mViewPager2.setCurrentItem(0);
                } else {
                    mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
                }
            }
        };

        // Lang nghe su kien chuyen page
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 3000);
            }
        });
    }

    private List<CarouselItem> getListCarousel() {
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(product.getImgUrl()));

        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Xử lý sự kiện khi nút back được nhấn
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
