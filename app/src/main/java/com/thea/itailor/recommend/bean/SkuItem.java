package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class SkuItem {
    private String skuid;
    private String size;
    private String color;
    private int stock;
    private int saleQuantity;
    private double price;

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setSaleQuantity(int saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSkuid() {
        return skuid;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public int getStock() {
        return stock;
    }

    public int getSaleQuantity() {
        return saleQuantity;
    }

    public double getPrice() {
        return price;
    }
}
