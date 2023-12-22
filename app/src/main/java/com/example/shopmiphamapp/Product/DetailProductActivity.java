package com.example.shopmiphamapp.Product;
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
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;

import com.example.shopmiphamapp.Carousel.CarouselAdapter;
import com.example.shopmiphamapp.Carousel.CarouselItem;
import com.example.shopmiphamapp.Cart.CartActivity;
import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.FavoriteProduct.FavoriteProduct;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.FavoriteProduct.FavoriteProductActivity;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.core.text.HtmlCompat;

import me.relex.circleindicator.CircleIndicator3;

public class DetailProductActivity extends AppCompatActivity {
    private ShopDatabase shopDatabase;
    private Toolbar toolbar;
    private boolean isHeartSelected = false;
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private TextView product_desc, product_sold, product_price, product_name, count_cart, product_type;
    private ImageButton btn_add_cart, btn_cart, btn_heart;
    private User user;
    private Product product;
    private int productId;
    private List<CarouselItem> mListCarousel;
    // Auto slider
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == mListCarousel.size() -1) {
                mViewPager2.setCurrentItem(0);
            } else {
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        shopDatabase  = ShopDatabase.getInstance(this);

        initUi();
        backNavigation();
        carosel();

        String productIdJson = getIntent().getStringExtra("productId");
        productId = new Gson().fromJson(productIdJson, Integer.class);
        product = shopDatabase.productDAO().getProductById(productId);

        user = HomeActivity.userPublic;

        if (productIdJson != null) {
            setUi(productId);
        }

        updateCount(user);
        initListener();

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
    }

    private void setUi(int productId) {
        Product product = shopDatabase.productDAO().getProductById(productId);
        product_desc.setText(HtmlCompat.fromHtml(product.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        product_price.setText(String.valueOf(product.getPrice()) + "đ");
        product_name.setText(product.getName());

        float soldProductNumber = product.getSold();
        String stringSold;

        if (soldProductNumber >= 1000) {
            soldProductNumber /= 1000;
            DecimalFormat decimalFormat = new DecimalFormat("#.#"); // Định dạng số sau dấu phẩy
            stringSold = decimalFormat.format(soldProductNumber) + "k";
        } else {
            stringSold = String.valueOf((int) soldProductNumber);
        }

        product_sold.setText("Đã bán " + stringSold + " sp.");
        String productType = shopDatabase.productDAO().getProductType(product.getProductId());
        product_type.setText(productType);

        boolean check = shopDatabase.favoriteProductDAO().checkFavoriteProductExist(product.getProductId());
        if (check) {
            btn_heart.setImageResource(R.drawable.ic_heart_selected);
            isHeartSelected = true;
        }
    }

    private void initListener() {
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
                Cart cart = shopDatabase.cartDAO().checkProductExist(productId);
                if (cart != null) {
                    Toast.makeText(DetailProductActivity.this,
                            "Sản phẩm đã tồn tại trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    shopDatabase.cartDAO().insertCart(new Cart(productId, user.getUserId()));
                    updateCount(user);
                    Toast.makeText(DetailProductActivity.this,
                            "Thêm sản phẩm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHeartSelected = !isHeartSelected;
                boolean check = shopDatabase.favoriteProductDAO().checkFavoriteProductExist(product.getProductId());

                if (isHeartSelected) {
                    if (!check) {
                        btn_heart.setImageResource(R.drawable.ic_heart_selected);
                        FavoriteProduct favoriteProduct = new FavoriteProduct(user.getUserId(), productId);
                        shopDatabase.favoriteProductDAO().insertFavoriteProduct(favoriteProduct);
                        Toast.makeText(DetailProductActivity.this,
                                "Thêm sản phẩm yêu thích thành công!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    btn_heart.setImageResource(R.drawable.ic_heart);
                    shopDatabase.favoriteProductDAO().deleteFavoriteProduct(user.getUserId(), productId);
                    Toast.makeText(DetailProductActivity.this,
                            "Xóa sản phẩm yêu thích thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Update lại số lượng trong cart khi btn_add_cart được nhấn
    private void updateCount(User user) {
        String countCart = String.valueOf(shopDatabase.cartDAO().getListCartUser(user.getUserId()).size());
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

    private void carosel() {
        mListCarousel = getListCarousel();
        CarouselAdapter adapter = new CarouselAdapter(mListCarousel);
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);
//        Lang nghe su kien chuyen page
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
        list.add(new CarouselItem(R.drawable.loginimg));
        list.add(new CarouselItem(R.drawable.signimg));
        list.add(new CarouselItem(R.drawable.mainbkg));
        list.add(new CarouselItem(R.drawable.product_1));

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
