package com.example.cecilia.appmoviles;

import android.os.StrictMode;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cecilia on 18/11/2016.
 */

public class ManageBeach {

    List<Beach> beaches;

    public ManageBeach(){
        beaches = new ArrayList<>();
        initializeBeaches();
        //loadBeachs();
    }

    /**
     * Método PROVISIONAL para tener ejemplos de playas
     */
    private void initializeBeaches(){
        Beach b = new Beach("Playa Cueva / La Arena", "En la desembocadura del Esva se abre esta " +
                "playa formada por arena oscura y piedra y que está limitada en su parte más " +
                "oriental por las estribaciones de Cabo Busto. Está dispuesta en forma de concha, " +
                "es ancha y presenta una alta ocupación. No es accesible.",
                new LatLng(+43.549917,-6.47275));
        beaches.add(b);

        Beach b2 = new Beach("Playa El Puntal", "El Puntal es un pequeño arenal situado en el margen" +
                " derecho de la ría de Villaviciosa, un tramo de litoral que ha sido declarado " +
                "Reserva Natural. Cuenta con una pequeña zona arbolada, una acogedora playa de " +
                "arena dorada y un puerto deportivo a apenas 100 metros de distancia.",
                new LatLng(+43.529405,-5.387507));
        beaches.add(b2);
    }

    public List<Beach> getBeaches() {
        return beaches;
    }




    /*public void loadBeachs() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());
        String json = null;
        try {
            json = getJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        convertJsonToBeach(json);
    }

    private String getJson() throws IOException {
        String resultado = "";

        URL url = new URL("http://opendata.esri.es/datasets/84ddbc8cf4104a579d579f6441fcaa8a_0.geojson");

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            resultado = convertStreamToString(in);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return resultado;
    }


    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void convertJsonToBeach(String in){
        JSONObject reader = null;
        Weather weather=null;
        try {
            JSONArray json_array = reader.getJSONArray("features");
            JSONObject prop = null;
            for (int i = 0; i < json_array.length(); i++) {
                prop = reader.getJSONObject("properties");
                String nombre = prop.getString("Nombre");
                String desc = prop.getString("Nombre");
                double x = prop.getDouble("Coordenada_4");
                double y = prop.getDouble("Coordenada_5");
                LatLng location = new LatLng(x,y);
                Beach b = new Beach(nombre, desc, location);
                beaches.add(b);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/


}
