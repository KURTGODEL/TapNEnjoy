package com.project.tapnenjoy.Models;

public class Seller {
    public Integer Id;
    public Integer userId;

    public Seller(Integer Id,
                Integer userId){
        this.Id = Id;
        this.userId = userId;
    }

    public Seller(Integer userId){
        this.userId = userId;
    }
}
