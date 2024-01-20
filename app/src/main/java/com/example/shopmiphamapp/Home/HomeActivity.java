package com.example.shopmiphamapp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopmiphamapp.Bill.BillActivity;
import com.example.shopmiphamapp.Carousel.CarouselAdapter;
import com.example.shopmiphamapp.Carousel.CarouselItem;
import com.example.shopmiphamapp.Cart.CartActivity;
import com.example.shopmiphamapp.Category.CategoryAdapter;
import com.example.shopmiphamapp.Category.CategoryItem;
import com.example.shopmiphamapp.Category.CategoryProductActivity;
import com.example.shopmiphamapp.Database.Carousel.Carousel;
import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ProductType.ProductType;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.FavoriteProduct.FavoriteProductActivity;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.Login.LoginActivity;
import com.example.shopmiphamapp.Product.DetailProductActivity;
import com.example.shopmiphamapp.Product.ProductItem;
import com.example.shopmiphamapp.Product.ProductAdapter;
import com.example.shopmiphamapp.Product.SearchProductActivity;
import com.example.shopmiphamapp.R;
import com.example.shopmiphamapp.User.InfoUserActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ShopDatabase shopDatabase;
    private DrawerLayout drawerLayout;
    public static NavigationView navigationView;
    private Toolbar toolbar;
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private LiveData<List<Cart>> cartLiveData;
    private RecyclerView rcvProduct, rcvCategory;
    private ImageButton btn_cart, btn_chat, btnSearch;
    private EditText edtSearch;
    public static TextView count_cart;
    private ProgressBar progressBar;
    public static User userPublic;
    private FirebaseUser userFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userFirebase = FirebaseAuth.getInstance().getCurrentUser();
        if (userFirebase == null) {
            return;
        }
        shopDatabase = ShopDatabase.getInstance(this);

        initUi();

        // Hiển thị màn hình xám với ProgressBar
        FrameLayout progressLayout = findViewById(R.id.progress_layout);
        Helper.handleLoading(true, progressLayout, progressBar);

        shopDatabase.isDatabaseInitialized().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isInitialized) {
                if (isInitialized) {
                    // Ẩn màn hình xám
                    Helper.handleLoading(false, progressLayout, progressBar);

                    userPublic = shopDatabase.userDAO().getUser(userFirebase.getUid());

                    // Truong hop admin xoa nguoi dung tren sever nhung local van luu nguoi dung => Văng bug
                    if (userPublic == null) {
                        Toast.makeText(HomeActivity.this, "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }

                    setUi();

                    navigateLeft();

                    category();
                    recyclerViewProduct();
                    carousel();

                    initListener();

                    // Bat su thay doi cua cart trong database, neu thay doi thi sua count_cart
                    cartLiveData = shopDatabase.cartDAO().getListCartLiveData();
                    cartLiveData.observe(HomeActivity.this, new Observer<List<Cart>>() {
                        @Override
                        public void onChanged(List<Cart> carts) {
                            String userId = userPublic.getId();
                            String countCart = String.valueOf(shopDatabase.cartDAO().getListCartUser(userId).size());
                            count_cart.setText(countCart);
                        }
                    });
                }
            }
        });

    }

    private void initUi() {
        btn_cart = findViewById(R.id.btn_cart);
        btn_chat = findViewById(R.id.btn_chat);
        mViewPager2 = findViewById(R.id.view_page2_carousel);
        mCircleIndicator3 = findViewById(R.id.cricle_indicator_3);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        rcvCategory = findViewById(R.id.rcv_categpry);
        rcvProduct = findViewById(R.id.rcv_product);
        count_cart = findViewById(R.id.count_cart);
        progressBar = findViewById(R.id.progressBar);
        btnSearch = findViewById(R.id.btnSearch);
        edtSearch = findViewById(R.id.edtSearch);
    }

    private void setUi() {
        String userId = userPublic.getId();
        String countCart = String.valueOf(shopDatabase.cartDAO().getListCartUser(userId).size());
        count_cart.setText(countCart);
    }

    private void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue = edtSearch.getText().toString().trim();
                if (searchValue.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Vui lòng nhập từ khóa.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(HomeActivity.this, SearchProductActivity.class);
                intent.putExtra("productKey", searchValue);
                startActivity(intent);
            }
        });
        // Cart Btn
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.messenger.com/t/100054196028101"));
                startActivity(intent);
            }
        });
    }

    // Navigation
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Xu ly navigation right
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            // Handle nav_home
        } else if (itemId == R.id.nav_cart) {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            ShopDatabase.destroyInstance();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        } else if (itemId == R.id.nav_info) {
            Intent intent = new Intent(HomeActivity.this, InfoUserActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_like_product) {
            Intent intent = new Intent(HomeActivity.this, FavoriteProductActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_bill) {
            Intent intent = new Intent(HomeActivity.this, BillActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void navigateLeft() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        // Xet title cho header của navigation
        View headerView = navigationView.getHeaderView(0);
        TextView tv_name = headerView.findViewById(R.id.tv_name);
        TextView tv_email = headerView.findViewById(R.id.tv_email);
        RoundedImageView imageUser = headerView.findViewById(R.id.imageUser);
        ProgressBar progressBarHeader = headerView.findViewById(R.id.progressBarHeader);


        tv_name.setText(userPublic.getName());
        tv_email.setText(userFirebase.getEmail());

        if (userFirebase.getPhotoUrl() == null) {
            progressBarHeader.setVisibility(View.GONE);
            imageUser.setImageResource(R.drawable.img_avatar);
        } else {
//            Log.d("uriOK", String.valueOf(userFirebase.getPhotoUrl()));
            Helper.loadImageUri(userFirebase.getPhotoUrl(), imageUser, progressBarHeader);
        }
    }

    private void carousel() {
        CarouselAdapter adapter = new CarouselAdapter(HomeActivity.this);
        adapter.setData(getListCarousel());
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);

        // Auto slider
        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mViewPager2.getCurrentItem() == adapter.getItemCount() - 1) {
                    mViewPager2.setCurrentItem(0);
                } else {
                    mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
                }
            }
        };

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

    private void recyclerViewProduct() {
        ProductAdapter productAdapter = new ProductAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        productAdapter.setData(getListProduct());
        rcvProduct.setAdapter(productAdapter);

        // Đặt ClickListener cho mục trong Adapter
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductItem item) {
                Intent intent = new Intent(HomeActivity.this, DetailProductActivity.class);
                String productIdJson = new Gson().toJson(item.getProductId());

                // Gửi dữ liệu của mục bằng cách đính kèm nó như một Extra
                intent.putExtra("productId", productIdJson);
                startActivity(intent);
            }
        });
    }

    private void category() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this);
        categoryAdapter.setData(getCategory());
        rcvCategory.setAdapter(categoryAdapter);

        // Đặt ClickListener cho mục trong Adapter
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryItem item) {
                Intent intent = new Intent(HomeActivity.this, CategoryProductActivity.class);
                String productTypeIdJson = new Gson().toJson(item.getProductTypeId());
                // Gửi dữ liệu của mục bằng cách đính kèm nó như một Extra
                intent.putExtra("productTypeId", productTypeIdJson);
                startActivity(intent);
            }
        });
    }
    private List<CarouselItem> getListCarousel() {
        List<CarouselItem> list = new ArrayList<>();
        List<Carousel> carousels = shopDatabase.carouselDAO().getListCarousel();
        for(Carousel carousel : carousels) {
            list.add(new CarouselItem(carousel.getImgUrl()));
        }
        return list;
    }

    private List<ProductItem> getListProduct() {
        List<ProductItem> listProduct = new ArrayList<>();
        List<Product> products = ShopDatabase.getInstance(this).productDAO().getListProduct();
        for(Product product: products) {
            String productType = shopDatabase.productDAO().getProductType(product.getId());
            listProduct.add(new ProductItem(product.getId(), product.getImgUrl(),
                    product.getName(), productType, product.getPrice(), product.getSold()));
        }

        return listProduct;
    }
    private List<CategoryItem> getCategory() {
        List<CategoryItem> listCategory = new ArrayList<>();

        List<ProductType> productTypeList = shopDatabase.productTypeDAO().getListProductType();
        for (ProductType productType : productTypeList) {
            listCategory.add(new CategoryItem(productType.getId(),
                    productType.getImgUrl(), productType.getName()));
        }

        return listCategory;
    }
}