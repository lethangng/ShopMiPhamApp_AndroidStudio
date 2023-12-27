package com.example.shopmiphamapp.FavoriteProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shopmiphamapp.Category.CategoryProductActivity;
import com.example.shopmiphamapp.Database.FavoriteProduct.FavoriteProduct;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.Product.DetailProductActivity;
import com.example.shopmiphamapp.Product.ProductAdapter;
import com.example.shopmiphamapp.Product.ProductItem;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FavoriteProductActivity extends AppCompatActivity {
    private TextView tvProductType;

    private ProductAdapter productAdapter;
    private RecyclerView rcvProduct;

    private ShopDatabase shopDatabase;

    private FavoriteProductViewModel favoriteProductViewModel;

    private User user;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);
        shopDatabase  = ShopDatabase.getInstance(this);

        user = HomeActivity.userPublic;

        initUi();
        setUi();
        backNavigation();

        recyclerViewProduct();
    }

    private void initUi() {
        tvProductType = findViewById(R.id.tv_product_type);
        rcvProduct = findViewById(R.id.rcv_product);
        toolbar = findViewById(R.id.back_navigation);
    }

    private void setUi() {
        tvProductType.setText("Sản phẩm yêu thích");
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

    private void recyclerViewProduct() {
        productAdapter = new ProductAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        productAdapter.setData(getListProduct());

        rcvProduct.setAdapter(productAdapter);

        // Đặt ClickListener cho mục trong Adapter
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductItem item) {
                Intent intent = new Intent(FavoriteProductActivity.this, DetailProductActivity.class);
                String productIdJson = new Gson().toJson(item.getProductId());
                // Gửi dữ liệu của mục bằng cách đính kèm nó như một Extra
                intent.putExtra("productId", productIdJson);
                startActivity(intent);
            }
        });

        // Xử lý khi favorite_product thay đổi thì recycView cũng phải thay đổi theo
        favoriteProductViewModel = new ViewModelProvider(this).get(FavoriteProductViewModel.class);
        favoriteProductViewModel.getListProductLiveData().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                productAdapter.setData(productItems);
                productAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<ProductItem> getListProduct() {
        List<ProductItem> listProduct = new ArrayList<>();
        if (user != null) {
            List<FavoriteProduct> favoriteProducts = shopDatabase.favoriteProductDAO().getFavoriteProduct(user.getUserId());
            for (FavoriteProduct favoriteProduct : favoriteProducts) {
                int productId = favoriteProduct.getFavoriteProductId();
                Product product = shopDatabase.productDAO().getProductById(productId);
                String productType = shopDatabase.productDAO().getProductType(product.getProductId());
                listProduct.add(new ProductItem(product.getProductId(), product.getImgProductURL(), product.getName(), productType, product.getPrice(), product.getSold()));
            }
        }
        return listProduct;
    }
}