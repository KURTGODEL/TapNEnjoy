package com.project.tapnenjoy.Models;

public class User {
    public Integer Id;
    public String name;
    public String userName;
    public String password;
    public String address;
    public float latitude;
    public float longitude;
    public Boolean status;
    public Boolean isSeller;

    public User(Integer Id,
                String name,
                String userName,
                String password,
                String address,
                float latitude,
                float longitude,
                Boolean status){
        this.Id = Id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public void setUserType(Boolean isSeller){
        this.isSeller = isSeller;
    }
}
