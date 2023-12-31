package com.example.shopmiphamapp.Database;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shopmiphamapp.Database.Bill.Bill;
import com.example.shopmiphamapp.Database.Bill.BillDAO;
import com.example.shopmiphamapp.Database.Carousel.Carousel;
import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.Cart.CartDAO;
import com.example.shopmiphamapp.Database.Carousel.CarouselDAO;
import com.example.shopmiphamapp.Database.FavoriteProduct.FavoriteProduct;
import com.example.shopmiphamapp.Database.FavoriteProduct.FavoriteProductDAO;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.Product.ProductDAO;
import com.example.shopmiphamapp.Database.ProductType.ProductType;
import com.example.shopmiphamapp.Database.ProductType.ProductTypeDAO;
import com.example.shopmiphamapp.Database.Product_Bill.Product_Bill;
import com.example.shopmiphamapp.Database.Product_Bill.Product_BillDAO;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Database.User.UserDAO;


@Database(entities = {User.class, Product.class, Cart.class,
        ProductType.class, Product_Bill.class,
        Bill.class, Carousel.class, FavoriteProduct.class}, version = 1)
public abstract class ShopDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "shop.db";
    private static volatile ShopDatabase instance;
    MutableLiveData<Boolean> isDatabaseInitialized = new MutableLiveData<>();
    public LiveData<Boolean> isDatabaseInitialized() {
        return isDatabaseInitialized;
    }

    public static synchronized ShopDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ShopDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new InitShopDatabase(instance).loadData();
                }
            }).start();
        }

        return instance;
    }
    public static synchronized void destroyInstance() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }
    public abstract UserDAO userDAO();
    public abstract ProductDAO productDAO();
    public abstract CartDAO cartDAO();
    public abstract ProductTypeDAO productTypeDAO();
    public abstract BillDAO billDAO();
    public abstract Product_BillDAO productBillDAO();
    public abstract CarouselDAO carouselDAO();
    public abstract FavoriteProductDAO favoriteProductDAO();
}
