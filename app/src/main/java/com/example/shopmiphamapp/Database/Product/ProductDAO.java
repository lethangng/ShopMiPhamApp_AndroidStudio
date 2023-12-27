package com.example.shopmiphamapp.Database.Product;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopmiphamapp.Database.FavoriteProduct.FavoriteProduct;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM product")
    List<Product> getListProduct();

    @Query("SELECT * FROM product where name = :name")
    List<Product> checkProductByName(String name);

    @Query("SELECT * FROM product where productId = :id")
    Product getProductById(int id);

    @Query("SELECT * FROM product where productTypeId = :productTypeId")
    List<Product> getProductByProductTypeId(int productTypeId);

    @Query("SELECT * FROM product where name LIKE :name")
    List<Product> getProductByName(String name);

    @Query("SELECT product_type.name FROM product_type INNER JOIN product " +
            "where product.productTypeId = product_type.productTypeId AND productId = :productId LIMIT 1")
    String getProductType(int productId);

    @Query("DELETE FROM product")
    void deleteAllProducts();
    @Query("DELETE FROM sqlite_sequence WHERE name='product'")
    void resetProductId();
}
