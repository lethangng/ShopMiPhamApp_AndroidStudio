package com.example.shopmiphamapp.Category;

public class CategoryItem {

    private int productTypeId;
    private String imageURL;
    private String name;

    public CategoryItem(int productTypeId, String imageURL, String name) {
        this.productTypeId = productTypeId;
        this.imageURL = imageURL;

        this.name = name;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
