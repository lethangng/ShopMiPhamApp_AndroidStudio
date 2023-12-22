package com.example.shopmiphamapp.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Product.DetailProductActivity;
import com.example.shopmiphamapp.Product.ProductAdapter;
import com.example.shopmiphamapp.Product.ProductItem;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductActivity extends AppCompatActivity {
    private TextView tvProductType;

    private ProductAdapter productAdapter;
    private RecyclerView rcvProduct;

    private ShopDatabase shopDatabase = ShopDatabase.getInstance(this);

    private int productTypeId;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        String productTypeIdJson = getIntent().getStringExtra("productTypeId");
        String userJson = getIntent().getStringExtra("user");
        productTypeId = new Gson().fromJson(productTypeIdJson, Integer.class);

        initUi();
        setUi();
        backNavigation();

        recyclerViewProduct(userJson);
    }

    private void initUi() {
        tvProductType = findViewById(R.id.tv_product_type);
        rcvProduct = findViewById(R.id.rcv_product);
        toolbar = findViewById(R.id.back_navigation);
    }

    private void setUi() {
        if (productTypeId != -1) {
            String productTypeName = shopDatabase.productTypeDAO().getProductTypeById(productTypeId).getName();
//            Log.d("productType", productTypeName);
            tvProductType.setText(productTypeName);
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
            }
        });
    }

    private void recyclerViewProduct(String userJson) {
        productAdapter = new ProductAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        productAdapter.setData(getListProduct());

        rcvProduct.setAdapter(productAdapter);

        // Đặt ClickListener cho mục trong Adapter
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductItem item) {
                Intent intent = new Intent(CategoryProductActivity.this, DetailProductActivity.class);
                String productIdJson = new Gson().toJson(item.getProductId());
                // Gửi dữ liệu của mục bằng cách đính kèm nó như một Extra
                intent.putExtra("productId", productIdJson);
                intent.putExtra("user", userJson);
                startActivity(intent);
            }
        });
    }

    private List<ProductItem> getListProduct() {
        List<ProductItem> listProduct = new ArrayList<>();
        if (productTypeId != -1) {
            List<Product> products = ShopDatabase.getInstance(this).productDAO().getProductByProductTypeId(productTypeId);
            for(Product product: products) {
                String productType = shopDatabase.productDAO().getProductType(product.getProductId());
                listProduct.add(new ProductItem(product.getProductId(), product.getImgProduct(), product.getName(), productType, product.getPrice(), product.getSold()));
            }
        }

        return listProduct;
    }
}