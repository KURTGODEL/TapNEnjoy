package com.project.tapnenjoy.Models;

public class UserWatchsAdapterToList {
    public Integer user_watchs_id;
    public Integer userId;
    public Integer productId;
    public String productTitle;
    public Double price;
    public Integer quantity;
    public Boolean status;
    public Integer stock;
    public byte[] image;

    public UserWatchsAdapterToList(Integer Id,
                            Integer userId,
                            Integer productId,
                            String productTitle,
                            Double price,
                            Integer quantity,
                            Boolean status,
                            Integer stock,
                            byte[] image){
        this.user_watchs_id = Id;
        this.userId = userId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
