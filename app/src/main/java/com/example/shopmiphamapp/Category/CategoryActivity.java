package com.example.shopmiphamapp.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.shopmiphamapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    int image[] = {R.drawable.mainbkg, R.drawable.signimg, R.drawable.loginimg};
    String name[] = {"Nước hoa hồng", "Kem dưỡng", "Mặt nạ"};

    ArrayList<CategoryItem> listCategory;
    CategoryAdapter categoryAdapter;
    RecyclerView rcvCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_product);

        // ListView Category
        rcvCategory = findViewById(R.id.rcv_categpry);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);
//        listCategory = new ArrayList<>();
//        for (int i=0; i<name.length; i++) {
//            listCategory.add(new CategoryItem(image[i], name[i]));
//        }
        categoryAdapter = new CategoryAdapter(this);
        categoryAdapter.setData(getCategory());
        rcvCategory.setAdapter(categoryAdapter);
    }
    private List<CategoryItem> getCategory() {
        List<CategoryItem> listCategory = new ArrayList<>();
//        listCategory.add(new CategoryItem(R.drawable.signimg, "Category 1"));
//        listCategory.add(new CategoryItem(R.drawable.loginimg, "Category 2"));
//        listCategory.add(new CategoryItem(R.drawable.mainbkg, "Category 3"));

        return listCategory;
    }
}