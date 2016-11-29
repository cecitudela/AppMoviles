package com.example.cecilia.appmoviles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    final static String KEY_LAT="POSITION";
    private TextView txtDetails;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtDetails = (TextView) findViewById(R.id.txtDetails);
        imgView = (ImageView) findViewById(R.id.imgWeather);

        LatLng lat = (LatLng)getIntent().getExtras().get(KEY_LAT);

        //Obtenemos el tiempo para las coordenadas dadas
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());
        OpenWeather op = new OpenWeather();
        Weather weather = op.getWeaher(lat);
        txtDetails.setText(weather.toString());

        // Añadimos la imagen correspondiente al tiempo dado
        Bitmap loadedImage = op.downloadIcon(weather.getIcon());
        Bitmap resized = Bitmap.createScaledBitmap(loadedImage,(int)(loadedImage.getWidth()*4), (int)(loadedImage.getHeight()*4), true);
        imgView.setImageBitmap(resized);

    }


}
