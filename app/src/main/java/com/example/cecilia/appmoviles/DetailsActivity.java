package com.example.cecilia.appmoviles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cecilia.appmoviles.sqlite.Beach;
import com.example.cecilia.appmoviles.sqlite.BeachDataSource;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    final static String KEY_LAT="POSITION";
    final static String KEY_ID ="DETALLES";
    private TextView txtDescription;
    private TextView txtLocation;
    private TextView txtTempNow;
    private TextView txtTempMin;
    private TextView txtTempMax;
    private TextView txtHumidity;
    private TextView txtTempWater;
    private ImageView imgView;
    private ImageView imgView2;
    private ImageView imgAseos;
    private ImageView imgBedida;
    private ImageView imgComida;
    private ImageView imgDeporte;
    private ImageView imgHamaca;
    private ImageView imgInfantil;
    private ImageView imgLimpieza;
    private ImageView imgNautico;
    private ImageView imgNudismo;
    private ImageView imgPapelera;
    private ImageView imgSombrilla;
    private ImageView imgSubmarinismo;
    private ImageView imgSurf;
    private ImageView imgTurismo;
    private ImageView imgDuchas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_description);

        txtDescription = (TextView) findViewById(R.id.beach_description);
        txtLocation = (TextView) findViewById(R.id.beach_location);
        txtTempNow = (TextView) findViewById(R.id.beach_tempNow);
        txtTempMin = (TextView) findViewById(R.id.beach_tempMin);
        txtTempMax = (TextView) findViewById(R.id.beach_tempMax);
        txtHumidity = (TextView) findViewById(R.id.beach_humidity);
        txtTempWater = (TextView) findViewById(R.id.beach_tempWater);
        imgView = (ImageView) findViewById(R.id.imgPlaya);
        imgView2 = (ImageView) findViewById(R.id.imageViewTiempo);
        imgAseos = (ImageView) findViewById(R.id.imgAseos);
        imgDuchas = (ImageView) findViewById(R.id.imageDuchas);
        imgBedida = (ImageView) findViewById(R.id.imgbebida);
        imgComida = (ImageView) findViewById(R.id.imgcomida);
        imgDeporte = (ImageView) findViewById(R.id.imgdeporte);
        imgHamaca = (ImageView) findViewById(R.id.imghamaca);
        imgInfantil = (ImageView) findViewById(R.id.imgInfantil);
        imgLimpieza = (ImageView) findViewById(R.id.imglimpieza);
        imgNautico = (ImageView) findViewById(R.id.imgNautico);
        imgNudismo = (ImageView) findViewById(R.id.imgNudismo);
        imgPapelera = (ImageView) findViewById(R.id.imgPapelera);
        imgSombrilla = (ImageView) findViewById(R.id.imgSombrilla);
        imgSubmarinismo = (ImageView) findViewById(R.id.imgSubmarinismo);
        imgSurf = (ImageView) findViewById(R.id.imgsurf);
        imgTurismo = (ImageView) findViewById(R.id.imgTurismo);

        //Obtenemos los datos pasados por MapsActivity
        Long id = getIntent().getExtras().getLong(KEY_ID);
        LatLng lat = (LatLng)getIntent().getExtras().get(KEY_LAT);

        //Cargamos la playas
        final BeachDataSource beachSource = new BeachDataSource(getApplicationContext());
        beachSource.open();
        Beach beach = beachSource.getBeachById(id);
        beachSource.close();

        //Ponemos como titulo el nombre de la playa
        collapsingToolbar.setTitle(beach.getName());


        //Obtenemos el tiempo para las coordenadas dadas
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());
        OpenWeather op = new OpenWeather();
        Weather weather = op.getWeaher(lat);

        //Establecemos cada campo de texto
        txtDescription.setText(beach.getDescription());
        txtLocation.setText("Coordenadas: "+ beach.getLocation());
        txtTempNow.setText(weather.getDescripcion() + "\n" + "Temperatura actual: " + weather.getTemp() + " ºC");
        txtTempMin.setText("Temperatura mínima: " + weather.getTemp_min() + " ºC");
        txtTempMax.setText("Temperatura máxima: " + weather.getTemp_max() + " ºC");
        txtHumidity.setText("Humedad: " + weather.getHumidity() + "%");
        txtTempWater.setText("Temperatura del agua: " + weather.getWaterTemp_C() + " ºC");

        //Miramos que servicios tiene la playa
        getServices(beach);

        //Añadimos la imagen de la playa
        Bitmap loadedImage = downloadFotoBeach(beach.getUrlFoto());
        //Bitmap resized = Bitmap.createScaledBitmap(loadedImage,(int)(loadedImage.getWidth()*4), (int)(loadedImage.getHeight()*4), true);
        imgView.setImageBitmap(loadedImage);

        // Añadimos la imagen correspondiente al tiempo dado
        Bitmap loadedImageTiempo = op.downloadIcon(weather.getIcon());
        Bitmap resized = Bitmap.createScaledBitmap(loadedImageTiempo,(int)(loadedImageTiempo.getWidth()*4), (int)(loadedImageTiempo.getHeight()*4), true);
        imgView2.setImageBitmap(resized);
       /*
        Weather weather = op.getWeaher(marker.getPosition());
        Bitmap loadedImage = op.downloadIcon(weather.getIcon());
        Bitmap resized = Bitmap.createScaledBitmap(loadedImage, (int) (loadedImage.getWidth() * 4), (int) (loadedImage.getHeight() * 4), true);
        //Añadimos la imagen
        ImageView infoImageView = new ImageView(MapsActivity.this);
        infoImageView.setImageBitmap(resized);
        infoView.addView(infoImageView);*/

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getServices(Beach beach) {
        if (beach.getAseos().contains("S"))
            imgAseos.setImageResource(getResources().getIdentifier("aseos", "drawable",getPackageName()));
        if (beach.getDuchas().contains("S"))
            imgDuchas.setImageResource(getResources().getIdentifier("ducha", "drawable",getPackageName()));
        if (beach.getEstablBebida().contains("S"))
            imgBedida.setImageResource(getResources().getIdentifier("bebida", "drawable",getPackageName()));
        if (beach.getEstablComida().contains("S"))
            imgComida.setImageResource(getResources().getIdentifier("comida", "drawable",getPackageName()));
        if (beach.getZonaDeport().contains("S"))
            imgDeporte.setImageResource(getResources().getIdentifier("deporte", "drawable",getPackageName()));
        if (beach.getAlquHamacas().contains("S"))
            imgHamaca.setImageResource(getResources().getIdentifier("hamaca", "drawable",getPackageName()));
        if (beach.getZonaInfa().contains("S"))
            imgInfantil.setImageResource(getResources().getIdentifier("infantil", "drawable",getPackageName()));
        if (beach.getServ_limpieza().contains("S"))
            imgLimpieza.setImageResource(getResources().getIdentifier("limpieza", "drawable",getPackageName()));
        if (beach.getAlquNauticos().contains("S"))
            imgNautico.setImageResource(getResources().getIdentifier("nautico", "drawable",getPackageName()));
        if (beach.getNudismo().contains("S"))
            imgNudismo.setImageResource(getResources().getIdentifier("nudismo", "drawable",getPackageName()));
        if (beach.getPapelera().contains("S"))
            imgPapelera.setImageResource(getResources().getIdentifier("papelera", "drawable",getPackageName()));
        if (beach.getAlquSombrillas().contains("S"))
            imgSombrilla.setImageResource(getResources().getIdentifier("sombrilla", "drawable",getPackageName()));
        if (beach.getZonaSubmarin().contains("S"))
            imgSubmarinismo.setImageResource(getResources().getIdentifier("submarinismo", "drawable",getPackageName()));
        if (beach.getZonaSurf().contains("S"))
            imgSurf.setImageResource(getResources().getIdentifier("surf", "drawable",getPackageName()));
        if (beach.getOficinaTurismo().contains("S"))
            imgTurismo.setImageResource(getResources().getIdentifier("turismo", "drawable",getPackageName()));
    }


    /**
     * Dada la url de la imagen de la playa la descarga y la devuelve como objeto Bitmap
     * @param url  código de la imagen
     * @return  Bitmap con la imagen correspondiente
     */
    public Bitmap downloadFotoBeach(String url) {
        URL imageUrl = null;
        Bitmap loadedImage = null;
        try {
            imageUrl = new URL("http://servicios2.magrama.es/imagenes/DGC/GuiaPlayasv2/"+url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedImage;
    }


}
