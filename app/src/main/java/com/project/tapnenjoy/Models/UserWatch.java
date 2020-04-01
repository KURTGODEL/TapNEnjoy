package com.project.tapnenjoy.Models;

public class UserWatch {
    public Integer Id;
    public Integer userId;
    public Integer productId;
    public Boolean status;

    public UserWatch(Integer Id,
                     Integer userId,
                     Integer productId,
                     Boolean status){
        this.Id = Id;
        this.userId = userId;
        this.productId = productId;
        this.status = status;
    }
}
