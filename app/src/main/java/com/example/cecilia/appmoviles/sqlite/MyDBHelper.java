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
    private static final int DATABASE_VERSION = 2;
    /**
     * Nombre de la tabla valorations y sus columnas
     */
    public static final String TABLE_PLAYAS = "playas";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCR = "descrp";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_ASEOS = "aseos";
    public static final String COLUMN_DUCHAS = "duchas";
    public static final String COLUMN_PAPELERA = "papelera";
    public static final String COLUMN_SERV_LIMPIEZA = "servlimpieza";
    public static final String COLUMN_OFI_TUR = "ofitur";
    public static final String COLUMN_EST_COMIDA = "estcomida";
    public static final String COLUMN_EST_BEBIDA = "estbebida";
    public static final String COLUMN_ALQ_HAM = "alquham";
    public static final String COLUMN_ALQ_SOMB = "alqusomb";
    public static final String COLUMN_ALQ_NAUT = "alqunaut";
    public static final String COLUMN_CLUB_NAUT = "clubnaut";
    public static final String COLUMN_ZONA_SUBM = "zonasubm";
    public static final String COLUMN_ZONA_SURF = "zonasurf";
    public static final String COLUMN_ZONA_INFA = "zonainfa";
    public static final String COLUMN_ZONA_DEP = "zonadep";
    public static final String COLUMN_NUDISMO = "nudismo";



    /**
     * Script para crear la base datos
     */
    private static final String DATABASE_CREATE = "create table " + TABLE_PLAYAS
            + "( " + COLUMN_ID + " " + "integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DESCR + " text not null, "
            + COLUMN_LAT + " real not null, "
            + COLUMN_LON + " real not null, "
            + COLUMN_ASEOS + " text not null, "
            + COLUMN_DUCHAS + " text not null, "
            + COLUMN_PAPELERA + " text not null, "
            + COLUMN_SERV_LIMPIEZA + " text not null, "
            + COLUMN_OFI_TUR + " text not null, "
            + COLUMN_EST_COMIDA + " text not null, "
            + COLUMN_EST_BEBIDA + " text not null, "
            + COLUMN_ALQ_HAM + " text not null, "
            + COLUMN_ALQ_SOMB + " text not null, "
            + COLUMN_ALQ_NAUT + " text not null, "
            + COLUMN_CLUB_NAUT + " text not null, "
            + COLUMN_ZONA_SUBM + " text not null, "
            + COLUMN_ZONA_SURF + " text not null, "
            + COLUMN_ZONA_INFA + " text not null, "
            + COLUMN_ZONA_DEP + " text not null, "
            + COLUMN_NUDISMO + " text not null"
            +");";

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
