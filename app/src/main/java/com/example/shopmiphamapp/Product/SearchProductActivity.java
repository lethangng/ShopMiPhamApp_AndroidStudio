package com.example.shopmiphamapp.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shopmiphamapp.Category.CategoryProductActivity;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edtSearch;
    private ProductAdapter productAdapter;
    private RecyclerView rcvProduct;
    private ImageButton btnSearch;
    private ShopDatabase shopDatabase;

    private String productKeyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        shopDatabase = ShopDatabase.getInstance(this);

        productKeyRequest = getIntent().getStringExtra("productKey");

        initUi();
        setUi(productKeyRequest);
        backNavigation();
        initListener();

        recyclerViewProduct();
    }

    private void initUi() {
        toolbar = findViewById(R.id.toolbar);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        rcvProduct = findViewById(R.id.rcv_product);
    }

    private void setUi(String productKeyRequest) {
        edtSearch.setText(productKeyRequest);
    }

    private void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue = edtSearch.getText().toString().trim();
                if (searchValue.isEmpty()) {
                    Toast.makeText(SearchProductActivity.this, "Vui lòng nhập từ khóa.", Toast.LENGTH_SHORT).show();
                    return;
                }

                productKeyRequest = searchValue;

                recyclerViewProduct();
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
                Intent intent = new Intent(SearchProductActivity.this, DetailProductActivity.class);
                String productIdJson = new Gson().toJson(item.getProductId());
                // Gửi dữ liệu của mục bằng cách đính kèm nó như một Extra
                intent.putExtra("productId", productIdJson);
                startActivity(intent);
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
    private List<ProductItem> getListProduct() {
        List<ProductItem> listProduct = new ArrayList<>();

        List<Product> products = shopDatabase.productDAO().getProductByName(productKeyRequest);
//        Log.d("products", String.valueOf(products));
        for(Product product: products) {
            String productType = shopDatabase.productDAO().getProductType(product.getId());
            listProduct.add(new ProductItem(product.getId(), product.getImgUrl(),
                    product.getName(), productType, product.getPrice(), product.getSold()));
        }
        return listProduct;
    }
}