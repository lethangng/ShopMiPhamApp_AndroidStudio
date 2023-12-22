package com.example.shopmiphamapp.Cart;

public class CartItem {
    private int cartId;
    private int imgId, price, count;
    private String productName, productType;

    public CartItem(int cartId, int imgId, String productName, String productType, int price) {
        this.cartId = cartId;
        this.imgId = imgId;
        this.price = price;
        this.productName = productName;
        this.productType = productType;
        this.count = 1;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
