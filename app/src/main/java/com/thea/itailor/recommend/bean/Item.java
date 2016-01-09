package com.thea.itailor.recommend.bean;

import java.util.List;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Item {
    private int itemServerID;
    private String itemId;
    private String itemName;
    private String keyName;
    private String shopName;
    private int saleQuantityInAMonth;
    private List<Property> properties;
    private List<SkuItem> skuItems;
    private List<ClothingImage> clothingImages;

    public void setItemServerID(int itemServerID) {
        this.itemServerID = itemServerID;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setSaleQuantityInAMonth(int saleQuantityInAMonth) {
        this.saleQuantityInAMonth = saleQuantityInAMonth;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void setSkuItems(List<SkuItem> skuItems) {
        this.skuItems = skuItems;
    }

    public void setClothingImages(List<ClothingImage> clothingImages) {
        this.clothingImages = clothingImages;
    }

    public int getItemServerID() {
        return itemServerID;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getKeyName() {
        return keyName;
    }

    public Object getShopName() {
        return shopName;
    }

    public int getSaleQuantityInAMonth() {
        return saleQuantityInAMonth;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<SkuItem> getSkuItems() {
        return skuItems;
    }

    public List<ClothingImage> getClothingImages() {
        return clothingImages;
    }
    /*private String id;
    private String name;
    private String keyName;
    private int[] imageID;
    private int score;
    private Timestamp createdTime;*/
}
