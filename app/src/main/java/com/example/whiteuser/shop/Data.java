package com.example.whiteuser.shop;


public class Data
{
    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    Data(String name, int price)
    {
        this.name = name;
        this.price = price;
    }

}
