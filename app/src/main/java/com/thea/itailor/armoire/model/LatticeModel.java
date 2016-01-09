package com.thea.itailor.armoire.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thea.itailor.armoire.bean.Lattice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public class LatticeModel implements ILatticeModel {
    private LatticeSQLiteOpenHelper helper;

    public LatticeModel(LatticeSQLiteOpenHelper helper) {
        this.helper = helper;
    }

    @Override
    public void add(String name, int iconId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into Lattice(latticename, iconId) values (?, ?)",
                new Object[]{name,iconId});
        db.close();
    }

    @Override
    public void update(int id, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update Lattice set latticename = ? where id = ?", new Object[]{name, id});
        db.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from Lattice where id = ?", new Object[]{id});
        db.close();
    }

    @Override
    public int count() {
        SQLiteDatabase db = helper.getReadableDatabase();
        int count = 0;
        Cursor cursor = db.rawQuery("select count(id) from Lattice", null);
        if (cursor.moveToNext())
            count = cursor.getInt(0);

        cursor.close();
        db.close();
        return count;
    }

    @Override
    public Lattice find(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Lattice lattice = null;
        Cursor cursor = db.rawQuery("select id, latticename, iconId from Lattice where id = ? " +
                "order by createdTime desc", new String[]{id + ""});
        if (cursor.moveToNext()) {
            int latticeId = cursor.getInt(0);
            String name = cursor.getString(1);
            int iconId = cursor.getInt(2);
            lattice = new Lattice(latticeId, name, iconId);
        }

        cursor.close();
        db.close();
        return lattice;
    }

    @Override
    public int find(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int id = -1;
        Cursor cursor = db.rawQuery("select id from Lattice where latticename = ?",
                new String[]{name + ""});
        if (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return id;
    }

    @Override
    public List<Lattice> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Lattice> lattices = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id, latticename, iconId from Lattice " +
                "order by createdTime asc", null);
        while (cursor.moveToNext()) {
            int latticeId = cursor.getInt(0);
            String name = cursor.getString(1);
            int iconId = cursor.getInt(2);
            lattices.add(new Lattice(latticeId, name, iconId));
        }

        cursor.close();
        db.close();
        return lattices;
    }
}
