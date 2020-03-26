package com.project.tapnenjoy.Models;

public class UserOrdersAdapterToList {
    public Integer userOrderId;
    public Integer userId;
    public Integer sellerId;
    public String sellerName;
    public Integer productId;
    public String productTitle;
    public Integer quantity;
    public Boolean status;
    public byte[] image;

    public UserOrdersAdapterToList(Integer Id,
                                   Integer userId,
                                   Integer sellerId,
                                   String sellerName,
                                   Integer productId,
                                   String productTitle,
                                   Integer quantity,
                                   Boolean status,
                                   byte[] image){
        this.userOrderId = Id;
        this.userId = userId;
        this.sellerName = sellerName;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.status = status;
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

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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
}
