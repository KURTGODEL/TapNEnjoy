package com.project.tapnenjoy.Models;

public class UserWatchsAdapterToList {
    public Integer user_watchs_id;
    public Integer userId;
    public Integer product_id;
    public String title;
    public Double price;
    public Integer stock;
    public byte[] image;

    public UserWatchsAdapterToList(Integer Id,
                            Integer userId,
                            Integer product_id,
                            String title,
                            Double price,
                            Integer stock,
                            byte[] image){
        this.user_watchs_id = Id;
        this.userId = userId;
        this.product_id = product_id;
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public Integer getUser_watchs_id() {
        return user_watchs_id;
    }

    public void setUser_watchs_id(Integer user_watchs_id) {
        this.user_watchs_id = user_watchs_id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
