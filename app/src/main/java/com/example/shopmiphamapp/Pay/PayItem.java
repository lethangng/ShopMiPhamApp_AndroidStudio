package com.example.shopmiphamapp.Pay;

public class PayItem {
    private int payItemId;
    private int imgId, price, count;
    private String productName, productType;

    public PayItem(int imgId, String productName, String productType, int price, int count) {
        this.imgId = imgId;
        this.price = price;
        this.count = count;
        this.productName = productName;
        this.productType = productType;
    }

    public int getPayItemId() {
        return payItemId;
    }

    public void setPayItemId(int payItemId) {
        this.payItemId = payItemId;
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
