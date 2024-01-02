package com.example.shopmiphamapp.Bill;

public class BillItem {
    private String billId;
    private int productId, price, count, totalPrice, sumProduct;
    private String productName, productType, imgURL;

    public BillItem(String billId, int productId, String imgURL, int price, int count, int totalPrice, String productName, String productType, int sumProduct) {
        this.billId = billId;
        this.productId = productId;
        this.imgURL = imgURL;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
        this.productName = productName;
        this.productType = productType;
        this.sumProduct = sumProduct;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public int getSumProduct() {
        return sumProduct;
    }

    public void setSumProduct(int sumProduct) {
        this.sumProduct = sumProduct;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

//    public int getImgId() {
//        return imgId;
//    }
//
//    public void setImgId(int imgId) {
//        this.imgId = imgId;
//    }


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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
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
