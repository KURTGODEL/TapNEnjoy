package com.project.tapnenjoy.Models;

public class UserOffersAdapterToList {
    public Integer userOfferId;
    public Integer userId;
    public Integer sellerId;
    public Integer productId;
    public String productTitle;
    public Double productPrice;
    public Integer productStock;
    public byte[] image;

    public UserOffersAdapterToList(Integer Id,
                                   Integer userId,
                                   Integer sellerId,
                                   Integer productId,
                                   String productTitle,
                                   Double productPrice,
                                   Integer productStock,
                                   byte[] image) {
        this.userOfferId = Id;
        this.userId = userId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.image = image;
    }

    public Integer getUserOfferId() {
        return userOfferId;
    }

    public void setUserOfferId(Integer userOfferId) {
        this.userOfferId = userOfferId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }
}