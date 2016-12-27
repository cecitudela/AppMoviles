package com.example.cecilia.appmoviles.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    private final String[] allColumns = { MyDBHelper.COLUMN_ID, MyDBHelper.COLUMN_ASIGNATURA,
            MyDBHelper.COLUMN_COMMENT, MyDBHelper.COLUMN_RATING };
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
       /* ContentValues values = new ContentValues();
        values.put(MyDBHelper.COLUMN_COMMENT, valorationToInsert.getComment());
        values.put(MyDBHelper.COLUMN_ASIGNATURA, valorationToInsert.getCourse());
        values.put(MyDBHelper.COLUMN_RATING, valorationToInsert.getRating());

        // Insertamos la valoracion
        long insertId = database.insert(MyDBHelper.TABLE_VALORATIONS, null, values);

        return insertId;*/
        return 0;
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
        Cursor cursor = database.query(MyDBHelper.TABLE_VALORATIONS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            /*final Beach beach = new Beach();
            valoration.setCourse(cursor.getString(1));
            valoration.setComment(cursor.getString(2));
            valoration.setRating(cursor.getInt(3));

            valorationList.add(beach);*/
            cursor.moveToNext();
        }

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        return beachsList;
    }

}
