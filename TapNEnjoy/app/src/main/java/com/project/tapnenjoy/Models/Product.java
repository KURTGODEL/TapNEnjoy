package com.project.tapnenjoy.Models;

public class Product {
    public Integer Id;
    public String title;
    public String price;
    public String description;
    public byte[] image;
    public float stock;
    public float sellerId;
    public Boolean status;

    public Product(Integer Id,
                String title,
                String price,
                String description,
                byte[] image,
                Integer stock,
                Integer sellerId,
                Boolean status){
        this.Id = Id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.stock = stock;
        this.sellerId = sellerId;
        this.status = status;
    }
}
