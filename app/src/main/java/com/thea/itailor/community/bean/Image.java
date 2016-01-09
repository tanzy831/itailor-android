package com.thea.itailor.community.bean;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Image {
    private int clothingImageID;
    private String source;
    private int sizeB;
    private String imageID;

    public Image(String imageID) {
        this.imageID = imageID;
    }

    public void setResourceID(int resourceID) {
        this.clothingImageID = resourceID;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSizeB(int sizeB) {
        this.sizeB = sizeB;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public int getResourceID() {
        return clothingImageID;
    }

    public String getSource() {
        return source;
    }

    public int getSizeB() {
        return sizeB;
    }

    public String getImageID() {
        return imageID;
    }
}
