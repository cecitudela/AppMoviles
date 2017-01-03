package com.example.cecilia.appmoviles.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>SQLite</b>
 *
 * DAO para la tabla de playas.
 * Se encarga de abrir y cerrar la conexion, asi como hacer las consultas relacionadas con la tabla playas
 *

 */
public class BeachDataSource {
    /**
     * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
     * y nos proporciona metodos para hacer operaciones
     * CRUD (create, read, update and delete)
     */
    private SQLiteDatabase database;
    /**
     * Referencia al helper que se encarga de crear y actualizar la base de datos.
     */
    private MyDBHelper dbHelper;
    /**
     * Columnas de la tabla
     */
    private final String[] allColumns = { MyDBHelper.COLUMN_ID, MyDBHelper.COLUMN_NAME,
            MyDBHelper.COLUMN_DESCR,
            MyDBHelper.COLUMN_LAT, MyDBHelper.COLUMN_LON
            , MyDBHelper.COLUMN_ASEOS
            , MyDBHelper.COLUMN_DUCHAS
            , MyDBHelper.COLUMN_PAPELERA
            , MyDBHelper.COLUMN_SERV_LIMPIEZA
            , MyDBHelper.COLUMN_OFI_TUR
            , MyDBHelper.COLUMN_EST_COMIDA
            , MyDBHelper.COLUMN_EST_BEBIDA
            , MyDBHelper.COLUMN_ALQ_HAM
            , MyDBHelper.COLUMN_ALQ_SOMB
            , MyDBHelper.COLUMN_ALQ_NAUT
            , MyDBHelper.COLUMN_CLUB_NAUT
            , MyDBHelper.COLUMN_ZONA_SUBM
            , MyDBHelper.COLUMN_ZONA_SURF
            , MyDBHelper.COLUMN_ZONA_INFA
            , MyDBHelper.COLUMN_ZONA_DEP
            , MyDBHelper.COLUMN_NUDISMO
    };
    /**
     * Constructor.
     *
     * @param context
     */
    public BeachDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    /**
     * Abre una conexion para escritura con la base de datos.
     * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
     * datos no esta creada, el helper se encargara de llamar a onCreate
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Cierra la conexion con la base de datos
     */
    public void close() {
        dbHelper.close();
    }


    public long createBeach( Beach beachToInsert) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COLUMN_NAME, beachToInsert.getName());
        values.put(MyDBHelper.COLUMN_DESCR, beachToInsert.getDescription());
        values.put(MyDBHelper.COLUMN_LAT, beachToInsert.getLocation().latitude);
        values.put(MyDBHelper.COLUMN_LON, beachToInsert.getLocation().longitude);
        values.put(MyDBHelper.COLUMN_ASEOS, beachToInsert.getAseos());
        values.put(MyDBHelper.COLUMN_DUCHAS, beachToInsert.getDuchas());
        values.put(MyDBHelper.COLUMN_PAPELERA, beachToInsert.getPapelera());
        values.put(MyDBHelper.COLUMN_SERV_LIMPIEZA, beachToInsert.getServ_limpieza());
        values.put(MyDBHelper.COLUMN_OFI_TUR, beachToInsert.getOficinaTurismo());
        values.put(MyDBHelper.COLUMN_EST_COMIDA, beachToInsert.getEstablComida());
        values.put(MyDBHelper.COLUMN_EST_BEBIDA, beachToInsert.getEstablBebida());
        values.put(MyDBHelper.COLUMN_ALQ_HAM, beachToInsert.getAlquHamacas());
        values.put(MyDBHelper.COLUMN_ALQ_SOMB, beachToInsert.getAlquSombrillas());
        values.put(MyDBHelper.COLUMN_ALQ_NAUT, beachToInsert.getAlquNauticos());
        values.put(MyDBHelper.COLUMN_CLUB_NAUT, beachToInsert.getClubNautico());
        values.put(MyDBHelper.COLUMN_ZONA_SUBM, beachToInsert.getZonaSubmarin());
        values.put(MyDBHelper.COLUMN_ZONA_SURF, beachToInsert.getZonaSurf());
        values.put(MyDBHelper.COLUMN_ZONA_INFA, beachToInsert.getZonaInfa());
        values.put(MyDBHelper.COLUMN_ZONA_DEP, beachToInsert.getZonaDeport());
        values.put(MyDBHelper.COLUMN_NUDISMO, beachToInsert.getNudismo());

        // Insertamos la playa
        long insertId = database.insert(MyDBHelper.TABLE_PLAYAS, null, values);

        return insertId;
    }

    /**
     * Obtiene todas las playas añadidas.
     *
     * @return Lista de objetos de tipo Beach
     */
    public List<Beach> getAllValorations() {
        List<Beach> beachsList = new ArrayList<Beach>();
        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));

            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(6));
            beach.setPapelera(cursor.getString(7));
            beach.setServ_limpieza(cursor.getString(8));
            beach.setOficinaTurismo(cursor.getString(9));
            beach.setEstablComida(cursor.getString(10));
            beach.setEstablBebida(cursor.getString(11));
            beach.setAlquHamacas(cursor.getString(12));
            beach.setAlquSombrillas(cursor.getString(13));
            beach.setAlquNauticos(cursor.getString(14));
            beach.setClubNautico(cursor.getString(15));
            beach.setZonaSubmarin(cursor.getString(16));
            beach.setZonaSurf(cursor.getString(17));
            beach.setZonaInfa(cursor.getString(18));
            beach.setZonaDeport(cursor.getString(19));
            beach.setNudismo(cursor.getString(20));


            beachsList.add(beach);
            cursor.moveToNext();
        }

        cursor.close();
        return beachsList;
    }

    /**
     * Obtiene la playa cuyo id es el introducido por parámetro
     * @param id    ID de la playa que se busca
     * @return Objeto de la clase Beach cuyo id se corresponde con el dado por parámetro
     */
    public Beach getBeachById(Long id) {
        Beach beach = null;
        Cursor c = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "_id=" + id,
                null, null, null, null, null);
        if(c != null) {
            c.moveToFirst();
        }
        LatLng lat = new LatLng(c.getDouble(c.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                c.getDouble(c.getColumnIndex(MyDBHelper.COLUMN_LON)));
        beach = new Beach(c.getString(c.getColumnIndex(MyDBHelper.COLUMN_NAME))
                ,c.getString(c.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
        beach.setId(c.getLong(c.getColumnIndex(MyDBHelper.COLUMN_ID)));
       beach.setAseos(c.getString(c.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
        beach.setDuchas(c.getString(6));
        beach.setPapelera(c.getString(7));
        beach.setServ_limpieza(c.getString(8));
        beach.setOficinaTurismo(c.getString(9));
        beach.setEstablComida(c.getString(10));
        beach.setEstablBebida(c.getString(11));
        beach.setAlquHamacas(c.getString(12));
        beach.setAlquSombrillas(c.getString(13));
        beach.setAlquNauticos(c.getString(14));
        beach.setClubNautico(c.getString(15));
        beach.setZonaSubmarin(c.getString(16));
        beach.setZonaSurf(c.getString(17));
        beach.setZonaInfa(c.getString(18));
        beach.setZonaDeport(c.getString(19));
        beach.setNudismo(c.getString(20));

        return beach;
    }

    /**
     * Obtiene una lista de todas las playas cuyo nombre contiene la cadena pasada por parámetro
     * @param nombre    Cadena que debe contener el nombre de las playas que se buscan
     * @return  Lista de objetos Beach cuyo nombre se corresponde con la cadena dada por parámetro
     */
    public List<Beach> getBeachByName(String nombre) {
        List<Beach> beachsList = new ArrayList<Beach>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "name LIKE '%" + nombre + "%'",
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));

            beach.setDuchas(cursor.getString(6));
            beach.setPapelera(cursor.getString(7));
            beach.setServ_limpieza(cursor.getString(8));
            beach.setOficinaTurismo(cursor.getString(9));
            beach.setEstablComida(cursor.getString(10));
            beach.setEstablBebida(cursor.getString(11));
            beach.setAlquHamacas(cursor.getString(12));
            beach.setAlquSombrillas(cursor.getString(13));
            beach.setAlquNauticos(cursor.getString(14));
            beach.setClubNautico(cursor.getString(15));
            beach.setZonaSubmarin(cursor.getString(16));
            beach.setZonaSurf(cursor.getString(17));
            beach.setZonaInfa(cursor.getString(18));
            beach.setZonaDeport(cursor.getString(19));
            beach.setNudismo(cursor.getString(20));


            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

}
