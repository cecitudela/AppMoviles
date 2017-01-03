package com.example.cecilia.appmoviles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Cecilia on 18/11/2016.
 */

public class OpenWeather {

    private Weather weather;

    private String APPID_TIEMPO="f04cfb2ae2c0de6170c93642e492db63";
    private String APPID_MAREA="a61073923b5e4809bb2191414162712";
    

    public Weather getWeaher(LatLng lat) {
        try {
            URL urlTiempo = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+lat.latitude+"&lon="+lat.longitude+
                    "&APPID="+APPID_TIEMPO+"&units=metric&lang=es");
            getTiempo(urlTiempo);

            URL urlMareas = new URL("http://api.worldweatheronline.com/premium/v1/marine.ashx?key=" + APPID_MAREA +
                    "&format=json&q=" + lat.latitude + "," + lat.longitude);
            getMareas(urlMareas);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return weather;
    }


    /**
     * Método que devuelve los datos del tiempo en unas coordenadas concretas.
     * @param url   Coordenadas en las que se desea saber el tiempo
     * @return Objeto Weather que contiene los datos del tiempo para dichas coordenadas.
     */
    public Weather getTiempo(URL url) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());
        String json = null;
        try {
            json = getJson(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertJsonToWeather(json);
    }

    private void getMareas(URL url) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());
        String json = null;
        try {
            json = getJson(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        convertJsonToWeather2(json);
    }

    /**
     * Método que dadas unas coordenadas, accede a la API OpenWeatherMap para obtener los datos del tiempo en ese lugar.
     * @param url   Objeto Location con las coordenadas.
     * @return  Devuelve un JSON con los datos del tiempo.
     * @throws IOException
     */
    private String getJson(URL url) throws IOException {
        String resultado = "";
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
     * Métoo que dado un objeto InputStream lo convierte en String.
     * @param is    Objeto ImputStream
     * @return  String con el contenido del ImputStream pasado por parámetro.
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
     * Dado un archivo JSON en String devuelve el objeto Weather.
     * @param in
     * @return  objeto Weather que contiene todos los detalles del tiempo en unas coordenadas concretas.
     */
    private Weather convertJsonToWeather(String in){
        JSONObject reader = null;
        try {
            reader = new JSONObject(in);
            JSONArray w = reader.getJSONArray("weather");
            JSONObject wobj = (JSONObject) w.get(0);
            String descripcion = wobj.getString("description");
            String icon=wobj.getString("icon");

            JSONObject main = reader.getJSONObject("main");
            int temp = main.getInt("temp");
            int temp_min = main.getInt("temp_min");
            int temp_max = main.getInt("temp_max");
            int humidity = main.getInt("humidity");
            int pressure = main.getInt("pressure");

            weather = new Weather(descripcion,icon,
                    temp,temp_min,temp_max,humidity,pressure);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }


    private void convertJsonToWeather2(String json) {
        JSONObject reader = null;
        try {
            reader = new JSONObject(json);
            JSONObject data = reader.getJSONObject("data");
            JSONArray w = data.getJSONArray("weather");
            JSONObject wobj = (JSONObject) w.get(0);

            JSONArray hourly = wobj.getJSONArray("hourly");
            JSONObject whourly = (JSONObject) hourly.get(0);

            double swellHeight_m = whourly.getDouble("swellHeight_m");
            int waterTemp_C = whourly.getInt("waterTemp_C");
            weather.setSwellHeight_m(swellHeight_m);
            weather.setWaterTemp_C(waterTemp_C);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dado el código del icono, descarga la imagen correspondiente de la API ofrecida por OpenWeatherMap
     * @param icon  código del icono
     * @return  Bitmap con la imagen correspondiente
     */
    public Bitmap downloadIcon(String icon) {
        URL imageUrl = null;
        Bitmap loadedImage = null;
        try {
            imageUrl = new URL("http://openweathermap.org/img/w/"+icon+".png");
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedImage;
    }



}
