package com.thea.itailor.armoire.model;

import com.thea.itailor.armoire.bean.Lattice;

import java.util.List;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public interface ILatticeModel {
    void add(String name, int iconId);

    void update(int id, String name);

    void delete(int id);

    int count();

    Lattice find(int id);

    int find(String name);

    List<Lattice> findAll();
}
