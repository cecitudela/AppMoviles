package com.example.cecilia.appmoviles;

import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cecilia.appmoviles.sqlite.Beach;
import com.example.cecilia.appmoviles.sqlite.BeachDataSource;
import com.google.android.gms.maps.model.LatLng;

public class DetailsActivity extends AppCompatActivity {

    final static String KEY_LAT="POSITION";
    final static String KEY_ID ="DETALLES";
    private TextView txtDetails;
    private ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtDetails = (TextView) findViewById(R.id.txtDetails);
        imgView = (ImageView) findViewById(R.id.imgWeather);

        //Obtenemos los datos pasados por MapsActivity
        Long id = getIntent().getExtras().getLong(KEY_ID);
        LatLng lat = (LatLng)getIntent().getExtras().get(KEY_LAT);

        //Cargamos la playas
        final BeachDataSource beachSource = new BeachDataSource(getApplicationContext());
        beachSource.open();
        Beach beach = beachSource.getBeachById(id);
        beachSource.close();

        //Obtenemos el tiempo para las coordenadas dadas
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());
        OpenWeather op = new OpenWeather();
        Weather weather = op.getWeaher(lat);
        txtDetails.setText(beach.toString() + " \n\n " + weather.toString());

        // Añadimos la imagen correspondiente al tiempo dado
        Bitmap loadedImage = op.downloadIcon(weather.getIcon());
        Bitmap resized = Bitmap.createScaledBitmap(loadedImage,(int)(loadedImage.getWidth()*4), (int)(loadedImage.getHeight()*4), true);
        imgView.setImageBitmap(resized);

    }


}
