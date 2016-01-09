package com.thea.itailor.armoire.model;

import com.thea.itailor.armoire.bean.Cloth;

import java.util.List;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public interface IClothModel {
    void add(String name, String filename, int latticeId);

    void add(String name, String filename, String description, int latticeId);

    void update(int id, String name);

    void update(int id, int latticeId);

    void update(int id, String name, int latticeId);

    void update(int id, String name, String description, int latticeId);

    void delete(int id);

    void delete(String filename);

    void deleteLattice(int latticeId);

    int count();

    List<Cloth> find(int latticeId);

    List<Cloth> findAll();

    List<Cloth> findNotSynchronized();
}
