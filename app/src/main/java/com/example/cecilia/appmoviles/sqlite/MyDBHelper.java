package com.example.cecilia.appmoviles.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Android on 21/10/15.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    /**
     * Nombre y version de la base de datos
     */
    private static final String DATABASE_NAME = "playas.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * Nombre de la tabla valorations y sus columnas
     */
    public static final String TABLE_PLAYAS = "playas";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCR = "descrp";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";



    /**
     * Script para crear la base datos
     */
    private static final String DATABASE_CREATE = "create table " + TABLE_PLAYAS
            + "( " + COLUMN_ID + " " +
            "integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_DESCR + " text not null, " + COLUMN_LAT +
            " real not null, " + COLUMN_LON + " real not null" +");";

    /**
     * Script para borrar la base de datos
     */
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + TABLE_PLAYAS;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  invocacamos execSQL pq no devuelve ningún tipo de dataset
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP);
        this.onCreate(db);

    }
}
