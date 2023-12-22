package com.example.shopmiphamapp.FavoriteProduct;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopmiphamapp.Product.ProductItem;

import java.util.List;

public class FavoriteProductViewModel extends ViewModel {
    private MutableLiveData<List<ProductItem>> listProductLiveData;

    public FavoriteProductViewModel() {
        listProductLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<ProductItem>> getListProductLiveData() {
        return listProductLiveData;
    }
}