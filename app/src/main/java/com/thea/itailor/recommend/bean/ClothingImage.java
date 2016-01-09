package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class ClothingImage {
    private int resourceID;
    private String source;
    private int sizeB;
    private String imageID;

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
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
        return resourceID;
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
