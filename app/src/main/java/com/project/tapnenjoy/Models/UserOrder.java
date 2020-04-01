package com.project.tapnenjoy.Models;

public class UserOrder {
    public Integer Id;
    public Integer userId;
    public Integer sellerId;
    public Integer productId;
    public Integer quantity;
    public Boolean status;

    public UserOrder(Integer Id,
                       Integer userId,
                       Integer sellerId,
                       Integer productId,
                       Integer quantity,
                       Boolean status){
        this.Id = Id;
        this.userId = userId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }
    public UserOrder(
                     Integer userId,
                     Integer sellerId,
                     Integer productId,
                     Integer quantity,
                     Boolean status){

        this.userId = userId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }
}
