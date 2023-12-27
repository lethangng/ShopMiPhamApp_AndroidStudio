package com.example.shopmiphamapp.Pay;

public class PayItem {
    private int payItemId;
    private int price, count;
    private String productName, productType, imgURL;

    public PayItem(String imgURL, String productName, String productType, int price, int count) {
        this.imgURL = imgURL;
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
