package com.example.shopmiphamapp.Product;

public class ProductItem {
    private int productId;
    private String image;
    private String name, productType;
    private int price, sold;

    public ProductItem(int productId, String image, String name, String productType, int price, int sold) {
        this.productId = productId;
        this.image = image;
        this.name = name;
        this.price = price;
        this.sold = sold;
        this.productType = productType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
