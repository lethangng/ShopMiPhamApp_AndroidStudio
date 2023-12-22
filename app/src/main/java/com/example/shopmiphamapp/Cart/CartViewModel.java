package com.example.shopmiphamapp.Cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.Cart.CartDAO;

import java.util.List;

public class CartViewModel extends ViewModel {

    private CartDAO cartDAO;
    private LiveData<List<Cart>> listCartLiveData;

    public CartViewModel() {
        // Constructor không đối số
    }

    public void init(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
        listCartLiveData = cartDAO.getListCartLiveData();
    }

    public LiveData<List<Cart>> getListCartLiveData() {
        return listCartLiveData;
    }
}
