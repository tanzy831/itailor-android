package com.thea.itailor.armoire.bean;

import java.sql.Timestamp;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Cloth {
    private int id = -1;
    private String name;
    private String filename;
    private Timestamp createdTime = new Timestamp(System.currentTimeMillis());
    private String description;

    public Cloth(String name) {
        this.name = name;
    }

    public Cloth(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    public Cloth(int id, String name, String filename) {
        this.id = id;
        this.name = name;
        this.filename = filename;
    }

    public Cloth(int id, String name, String filename, Timestamp createdTime, String description) {
        this.id = id;
        this.name = name;
        this.filename = filename;
        this.createdTime = createdTime;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cloth cloth = (Cloth) o;

        return name.equals(cloth.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Cloth{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", filename='" + filename + '\'' +
                ", createdTime=" + createdTime +
                ", description='" + description + '\'' +
                '}';
    }
}
