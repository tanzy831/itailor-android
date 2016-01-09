package com.thea.itailor.armoire.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thea.itailor.R;

/**
 * Created by hunter on 2015/6/16.
 */
public class LatticeSQLiteOpenHelper extends SQLiteOpenHelper {

    public LatticeSQLiteOpenHelper(Context context) {
        super(context, "lattice.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Lattice(" +
                "id integer primary key autoincrement, " +
                "latticename varchar(40), iconId int, " +
                "createdTime timestamp DEFAULT (datetime('now','localtime')))");
        db.execSQL("insert into Lattice(latticename, iconId) values ('最新', ?)", new Object[]{R.mipmap.head_icon});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
