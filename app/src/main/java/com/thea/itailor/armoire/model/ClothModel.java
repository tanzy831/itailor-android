package com.thea.itailor.armoire.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thea.itailor.armoire.bean.Cloth;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public class ClothModel implements IClothModel {
    private ClothSQLiteOpenHelper helper;

    public ClothModel(ClothSQLiteOpenHelper helper) {
        this.helper = helper;
    }

    @Override
    public void add(String name, String filename, int latticeId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into Cloth (clothname,filename,lattice) values (?,?,?)",
                new Object[]{name,filename,latticeId});
        db.close();
    }

    @Override
    public void add(String name, String filename, String description, int latticeId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into Cloth (clothname,filename,description,lattice) values (?,?,?,?)",
                new Object[]{name,filename,description,latticeId});
        db.close();
    }

    @Override
    public void update(int id, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update Cloth set clothname = ? where id = ?", new Object[]{name, id});
        db.close();
    }

    @Override
    public void update(int id, int latticeId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update Cloth set lattice = ? where id = ?", new Object[]{latticeId, id});
        db.close();
    }

    @Override
    public void update(int id, String name, int latticeId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update Cloth set clothname = ?, lattice = ? where id = ?",
                new Object[]{name, latticeId, id});
        db.close();
    }

    @Override
    public void update(int id, String name, String description, int latticeId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update Cloth set clothname = ?, description = ?, lattice = ? where id = ?",
                new Object[]{name, description, latticeId, id});
        db.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from Cloth where id = ?", new Object[]{id});
        db.close();
    }

    @Override
    public void delete(String filename) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from Cloth where filename = ?", new Object[]{filename});
        db.close();
    }

    @Override
    public void deleteLattice(int latticeId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from Cloth where lattice = ?", new Object[]{latticeId});
        db.close();
    }

    @Override
    public int count() {
        SQLiteDatabase db = helper.getReadableDatabase();
        int count = 0;
        Cursor cursor = db.rawQuery("select count(id) from Cloth", null);
        if (cursor.moveToNext())
            count = cursor.getInt(0);

        cursor.close();
        db.close();
        return count;
    }

    @Override
    public List<Cloth> find(int latticeId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Cloth> clothes = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id, clothname, filename, createdTime, description from Cloth where lattice = ? " +
                "order by createdTime desc", new String[]{latticeId+""});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String filename = cursor.getString(2);
            Timestamp createdTime = Timestamp.valueOf(cursor.getString(3));
            String description = cursor.getString(4);
            clothes.add(new Cloth(id, name, filename, createdTime, description));
        }

        cursor.close();
        db.close();
        return clothes;
    }

    @Override
    public List<Cloth> findAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Cloth> clothes = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id, clothname, filename, createdTime, description from Cloth " +
                "order by createdTime desc", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String filename = cursor.getString(2);
            Timestamp createdTime = Timestamp.valueOf(cursor.getString(3));
            String description = cursor.getString(4);
            clothes.add(new Cloth(id, name, filename, createdTime, description));
        }

        cursor.close();
        db.close();
        return clothes;
    }

    @Override
    public List<Cloth> findNotSynchronized() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Cloth> clothes = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id, clothname, filename, createdTime, description " +
                "from Cloth where isSynchronized = ?", new String[]{false+""});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String filename = cursor.getString(2);
            Timestamp createdTime = Timestamp.valueOf(cursor.getString(3));
            String description = cursor.getString(4);
            clothes.add(new Cloth(id, name, filename, createdTime, description));
        }

        cursor.close();
        db.close();
        return clothes;
    }
}
