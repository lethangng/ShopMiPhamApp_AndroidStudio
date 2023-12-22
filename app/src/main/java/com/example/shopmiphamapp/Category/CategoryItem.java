package com.example.shopmiphamapp.Category;

public class CategoryItem {

    private int productTypeId;
    private int image;
    private String name;

    public CategoryItem(int productTypeId, int image, String name) {
        this.productTypeId = productTypeId;
        this.image = image;
        this.name = name;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
