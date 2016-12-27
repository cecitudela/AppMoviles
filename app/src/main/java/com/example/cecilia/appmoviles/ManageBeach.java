package com.example.cecilia.appmoviles;

import android.os.StrictMode;

import com.example.cecilia.appmoviles.sqlite.Beach;
import com.example.cecilia.appmoviles.sqlite.BeachDataSource;
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



public class ManageBeach {

    List<Beach> beaches;

    public ManageBeach(){
        beaches = new ArrayList<>();
        loadBeachs();
    }

    public List<Beach> getBeaches() {
        return beaches;
    }


    /**
     * Carga todas las playas que se almacenarán en la lista beaches.
     */
    public void loadBeachs() {
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

    /**
     * Descarga el fichero json de la API ofrecida por opendata.esri.es que contiene todas las
     * playas de España.
     *
     * @return      fichero que contiene el json con todos los datos de las playas
     * @throws IOException
     */
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

    /**
     * Método que recibe un InputStream y lo convierte en String
     * @param is    objeto InputStream
     * @return  Objeto String que contiene los datos del is.
     */
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

    /**
     * Método que crea todos los objetos Beach a partir del json que se pasa por parámetro
     * @param in    String que contiene los datos del json obtenido de la API
     */
    private void convertJsonToBeach(String in){
        JSONObject reader = null;
        try {
            reader = new JSONObject(in);
            JSONArray feat = reader.getJSONArray("features");
            for(int i = 0; i<feat.length(); i++){
                JSONObject fobj = (JSONObject) feat.get(i);
                JSONObject prop = fobj.getJSONObject("properties");
                /* Tenemos en cuenta solo las playas de ASTURIAS */
                if(prop.getString("Provincia").equals("Asturias")) {
                    if (!prop.getString("Nombre").equals("L'Airín")){ //Esta playa se evita puesto
                        // que su nombre nos da problemas al añadirla a la base de datos.

                        Beach b = new Beach(prop.getString("Nombre"), prop.getString("Descripció"),
                                new LatLng(prop.getDouble("Coordena_5"), prop.getDouble("Coordena_4")));
                        b.setAlquHamacas(prop.getString("Alquiler_h"));
                        b.setAlquNauticos(prop.getString("Alquiler_n"));
                        b.setAlquSombrillas(prop.getString("Alquiler_s"));
                        b.setClubNautico(prop.getString("Club_naúti"));
                        b.setAseos(prop.getString("Aseos"));
                        b.setDuchas(prop.getString("Duchas"));
                        b.setPapelera(prop.getString("Papelera"));
                        b.setServ_limpieza(prop.getString("Servicio_l"));
                        b.setOficinaTurismo(prop.getString("Oficina_tu"));
                        b.setEstablBebida(prop.getString("Establec_1"));
                        b.setEstablComida(prop.getString("Establecim"));
                        b.setZonaDeport(prop.getString("Zona_depor"));
                        b.setZonaInfa(prop.getString("Zona_infan"));
                        b.setZonaSubmarin(prop.getString("Submarinis"));
                        b.setZonaSurf(prop.getString("Zona_Surf"));
                        b.setNudismo(prop.getString("Nudismo"));
                        beaches.add(b);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
