package com.thea.itailor.armoire.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Lattice {
    private int id;
    private String name;
    private int iconId;
    private Timestamp createdTime;
    private List<Cloth> clothes = new ArrayList<>();

    public Lattice() {
    }

    public Lattice(String name, int iconId) {
        this.name = name;
        this.iconId = iconId;
    }

    public Lattice(int id, String name, int iconId) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
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

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public List<Cloth> getClothes() {
        return clothes;
    }

    public void setClothes(List<Cloth> clothes) {
        this.clothes = clothes;
    }

    public void addCloth(Cloth cloth) {
        clothes.add(cloth);
    }

    public void removeCloth(Cloth cloth) {
        clothes.remove(cloth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lattice lattice = (Lattice) o;

        return name.equals(lattice.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public Cloth getCloth(int index) {
        return clothes.get(index);
    }

    public int size() {
        return clothes.size();
    }

    public Cloth removeCloth(int index) {
        return clothes.remove(index);
    }

    public void addCloth(int index, Cloth cloth) {
        clothes.add(index, cloth);
    }

    public boolean contains(Cloth cloth) {
        return clothes.contains(cloth);
    }
}
