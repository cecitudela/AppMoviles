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
 * Ejemplo <b>SQLite</b>. Ejemplo de uso de SQLite.
 *
 * DAO para la tabla de valoracion.
 * Se encarga de abrir y cerrar la conexion, asi como hacer las consultas relacionadas con la tabla valoracion
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
            MyDBHelper.COLUMN_DESCR, MyDBHelper.COLUMN_LAT, MyDBHelper.COLUMN_LON };
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

        // Insertamos la valoracion
        long insertId = database.insert(MyDBHelper.TABLE_PLAYAS, null, values);

        /*database.execSQL("insert into playas (name, descrp, lat, lon) values ('" + beachToInsert.getName()
                +"', '" + beachToInsert.getDescription() +"', " + beachToInsert.getLocation().latitude +
                ", " + beachToInsert.getLocation().longitude + ")");

*/
        return insertId;
    }

    /**
     * Obtiene todas las valoraciones andadidas por los usuarios.
     *
     * @return Lista de objetos de tipo Valoration
     */
    public List<Beach> getAllValorations() {
        // Lista que almacenara el resultado
        List<Beach> beachsList = new ArrayList<Beach>();
        //hacemos una query porque queremos devolver un cursor
        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng l = new LatLng(cursor.getDouble(3), cursor.getDouble(4));
            final Beach beach = new Beach(cursor.getString(1),cursor.getString(2), l);
            beachsList.add(beach);
            cursor.moveToNext();
        }

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        return beachsList;
    }


    public Beach getBeachById(Long id) {
        // Lista que almacenara el resultado
        Beach beach = null;
        //hacemos una query porque queremos devolver un cursor
        Cursor c = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "_id=" + id,
                null, null, null, null, null);
        if(c != null) {
            c.moveToFirst();
        }
        LatLng l = new LatLng(c.getDouble(3), c.getDouble(4));
        beach = new Beach(c.getString(1),c.getString(2), l);

        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // playa.
        return beach;
    }

}
