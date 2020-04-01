package com.project.tapnenjoy.Models;

public class UserOffer {
    public Integer Id;
    public Integer userId;
    public Integer sellerId;
    public Integer productId;
    public float price;
    public float newPrice;
    public Boolean status;

    public UserOffer(Integer Id,
                     Integer userId,
                     Integer sellerId,
                     Integer productId,
                     float price,
                     float newPrice,
                     Boolean status){
        this.Id = Id;
        this.userId = userId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.price = price;
        this.newPrice = newPrice;
        this.status = status;
    }
}
