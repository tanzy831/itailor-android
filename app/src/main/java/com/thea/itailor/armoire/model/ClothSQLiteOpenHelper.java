package com.thea.itailor.armoire.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hunter on 2015/6/16.
 */
public class ClothSQLiteOpenHelper extends SQLiteOpenHelper {

    public ClothSQLiteOpenHelper(Context context) {
        super(context, "Cloth.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Cloth(" +
                "id INTEGER primary key autoincrement, " +
                "clothname VARCHAR(40), filename VARCHAR(40)," +
                "createdTime TIMESTAMP DEFAULT (datetime('now','localtime')), " +
                "description TEXT, isSynchronized BOOLEAN default false, " +
                "lattice INTEGER, " +
                "FOREIGN KEY (lattice) REFERENCES Lattice(id))");
        /*db.execSQL("create table Cloth(" +
                "id INTEGER primary key autoincrement, " +
                "clothname VARCHAR(40), filename VARCHAR(40), " +
                "createdTime TIMESTAMP, " +
                "description TEXT, isSynchronized BOOLEAN default false, " +
                "lattice INTEGER， unique(clothname, lattice))");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
