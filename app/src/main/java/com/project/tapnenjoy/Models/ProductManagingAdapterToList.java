package com.project.tapnenjoy.Models;

public class ProductManagingAdapterToList {
    public Integer product_id;
    public String title;
    public Double price;
    public byte[] image;

    public ProductManagingAdapterToList(Integer product_id,
                                        String title,
                                        Double price,
                                        byte[] image){
        this.product_id = product_id;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public Integer getId() {
        return product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
