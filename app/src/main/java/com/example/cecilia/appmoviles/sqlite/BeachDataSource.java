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
            , MyDBHelper.COLUMN_URL_FOTO
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
        values.put(MyDBHelper.COLUMN_URL_FOTO, beachToInsert.getUrlFoto());
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

            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));


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
        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "_id=" + id,
                null, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
        beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
        beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
        beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
        beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
        beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
        beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
        beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
        beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
        beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
        beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
        beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
        beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
        beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
        beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
        beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
        beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
        beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
        beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
        beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

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
            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

    /**
     * Obtiene una lista de todas las playas cuyo nombre contiene la cadena pasada por parámetro
     * y además tienen zona de surf
     * @param nombre    Cadena que debe contener el nombre de las playas que se buscan
     * @return  Lista de objetos Beach cuyo nombre se corresponde con la cadena dada por parámetro
     */
    public List<Beach> getBeachByNameAndZonaSurf(String nombre) {
        List<Beach> beachsList = new ArrayList<Beach>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "name LIKE '%" + nombre
                + "%' AND "+ MyDBHelper.COLUMN_ZONA_SURF+" = 'Sí'",
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

    public List<Beach> getBeachByNameAndDuchas(String name) {
        List<Beach> beachsList = new ArrayList<Beach>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "name LIKE '%" + name
                        + "%' AND "+ MyDBHelper.COLUMN_DUCHAS+" = 'Sí'",
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

    public List<Beach> getBeachByNameAndEstComida(String name) {
        List<Beach> beachsList = new ArrayList<Beach>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "name LIKE '%" + name
                        + "%' AND "+ MyDBHelper.COLUMN_EST_COMIDA+" = 'Sí'",
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

    public List<Beach> getBeachByNameAndAlquHam(String name) {
        List<Beach> beachsList = new ArrayList<Beach>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "name LIKE '%" + name
                        + "%' AND "+ MyDBHelper.COLUMN_ALQ_HAM+" = 'Sí'",
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

    public List<Beach> getBeachByNameAndNudista(String name) {
        List<Beach> beachsList = new ArrayList<Beach>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "name LIKE '%" + name
                        + "%' AND "+ MyDBHelper.COLUMN_NUDISMO+" = 'Sí'",
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

    public List<Beach> getBeachByNameAndServLimp(String name) {
        List<Beach> beachsList = new ArrayList<Beach>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "name LIKE '%" + name
                        + "%' AND "+ MyDBHelper.COLUMN_SERV_LIMPIEZA+" = 'Sí'",
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

    public List<Beach> getBeachByNameAndOfTur(String name) {
        List<Beach> beachsList = new ArrayList<Beach>();

        Cursor cursor = database.query(MyDBHelper.TABLE_PLAYAS, allColumns, "name LIKE '%" + name
                        + "%' AND "+ MyDBHelper.COLUMN_OFI_TUR+" = 'Sí'",
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LatLng lat = new LatLng(cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(MyDBHelper.COLUMN_LON)));
            final Beach beach = new Beach(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME))
                    ,cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DESCR)), lat);
            beach.setId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.COLUMN_ID)));
            beach.setUrlFoto(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_URL_FOTO)));
            beach.setAseos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ASEOS)));
            beach.setDuchas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DUCHAS)));
            beach.setPapelera(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_PAPELERA)));
            beach.setServ_limpieza(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_SERV_LIMPIEZA)));
            beach.setOficinaTurismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_OFI_TUR)));
            beach.setEstablComida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_COMIDA)));
            beach.setEstablBebida(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_EST_BEBIDA)));
            beach.setAlquHamacas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_HAM)));
            beach.setAlquSombrillas(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_SOMB)));
            beach.setAlquNauticos(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ALQ_NAUT)));
            beach.setClubNautico(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CLUB_NAUT)));
            beach.setZonaSubmarin(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SUBM)));
            beach.setZonaSurf(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_SURF)));
            beach.setZonaInfa(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_INFA)));
            beach.setZonaDeport(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ZONA_DEP)));
            beach.setNudismo(cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NUDISMO)));

            beachsList.add(beach);
            cursor.moveToNext();
        }
        cursor.close();
        return beachsList;
    }

}
