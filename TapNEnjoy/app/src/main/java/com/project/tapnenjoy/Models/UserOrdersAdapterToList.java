package com.project.tapnenjoy.Models;

public class UserOrdersAdapterToList {
    public Integer userOrderId;
    public Integer userId;
    public Integer productId;
    public String productTitle;
    public Double productPrice;
    public byte[] image;

    public UserOrdersAdapterToList(Integer Id,
                                   Integer userId,
                                   Integer productId,
                                   String productTitle,
                                   Double productPrice,
                                   byte[] image){
        this.userOrderId = Id;
        this.userId = userId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.image = image;
    }

    public Integer getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(Integer userOrderId) {
        this.userOrderId = userOrderId;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
