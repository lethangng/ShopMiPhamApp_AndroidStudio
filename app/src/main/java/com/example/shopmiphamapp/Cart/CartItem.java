package com.example.shopmiphamapp.Cart;

public class CartItem {
    private String cartId;
    private int price, count, productId;
    private String productName, productType, imgURL;

    public CartItem(String cartId,int productId, String imgURL, String productName, String productType, int price) {
        this.cartId = cartId;
        this.productId = productId;
        this.imgURL = imgURL;
        this.price = price;
        this.productName = productName;
        this.productType = productType;
        this.count = 1;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
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
