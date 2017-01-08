package com.example.cecilia.appmoviles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cecilia.appmoviles.sqlite.Beach;
import com.example.cecilia.appmoviles.sqlite.BeachDataSource;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class  MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;

    private GoogleApiClient client;

    private HashMap<Marker, Long> mRatingHash;
    private List<Beach> list;

    private EditText editName;
    private Button btnBuscar;
    private Spinner spinerBuscar;
    private int seleccionSpinner=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        editName = (EditText) findViewById(R.id.txtName);
        spinerBuscar = (Spinner) findViewById(R.id.spinner);
        spinerBuscar.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        seleccionSpinner = position;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        //Cargamos las playas
        ManageBeach mb = new ManageBeach();
        list = mb.getBeaches();
        mRatingHash = new HashMap<>();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Centramos la cámara en Asturias
        LatLng asturias = new LatLng(43.366980, -5.852600);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(asturias, 7.0f));


        /**
         * Cargamos todas las playas en la base de datos y marcamos cada una de ellas en el mapa
         */
        final BeachDataSource beachSource = new BeachDataSource(getApplicationContext());
        beachSource.open();
        for (Beach b : list) {
            Long id = beachSource.createBeach(b);
            Marker mark = mMap.addMarker(new MarkerOptions().position(b.getLocation()).title(b.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador_playa6)));
            mRatingHash.put(mark, id);
        }
        beachSource.close();


        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker arg0) {
                Intent i = new Intent(getApplicationContext(), DetailsActivity.class );
                i.putExtra(DetailsActivity.KEY_LAT, arg0.getPosition());

                Long id = mRatingHash.get(arg0);
                i.putExtra(DetailsActivity.KEY_ID, id);
                startActivity(i);
            }

        });
    }

    /**
     * Máetodo que muestra las playas que contienen el nombre escrito por el usuario en el
     * campo de texto.
     * @param view
     */
    public void filtrarNombre(View view) {
        String name = editName.getText().toString();
        mMap.clear();
        mRatingHash = new HashMap<>();
        FiltersBeachs fb = new FiltersBeachs(getApplicationContext());
        list =fb.getBeachsByName(name, seleccionSpinner);
        for (Beach b : list) {
            switch(seleccionSpinner) {
                case 0: //Todas
                    Marker mark = mMap.addMarker(new MarkerOptions().position(b.getLocation()).title(b.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador_playa6)));
                    mRatingHash.put(mark, b.getId());
                    break;
                case 1: //Zona surf
                    Marker mark2 = mMap.addMarker(new MarkerOptions().position(b.getLocation()).title(b.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador_playa8)));
                    mRatingHash.put(mark2, b.getId());
                    break;
                default:
                    Marker mark3 = mMap.addMarker(new MarkerOptions().position(b.getLocation()).title(b.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador_playa6)));
                    mRatingHash.put(mark3, b.getId());
                    break;
            }
        }


        editName.setVisibility(View.INVISIBLE);
        editName.setEnabled(false);
        btnBuscar.setVisibility(View.INVISIBLE);
        btnBuscar.setEnabled(false);
        spinerBuscar.setVisibility(View.INVISIBLE);
        spinerBuscar.setEnabled(false);
    }

    public void mostrarBusador(View view){
        editName.setVisibility(View.VISIBLE);
        editName.setEnabled(true);
        btnBuscar.setVisibility(View.VISIBLE);
        btnBuscar.setEnabled(true);
        spinerBuscar.setVisibility(View.VISIBLE);
        spinerBuscar.setEnabled(true);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return prepareInfoView(marker);
    }


    private View prepareInfoView(Marker marker) {
        //prepare InfoView programmatically
        LinearLayout infoView = new LinearLayout(MapsActivity.this);
        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        infoView.setOrientation(LinearLayout.HORIZONTAL);
        infoView.setLayoutParams(infoViewParams);

        //Descargamos la imagen
        OpenWeather op = new OpenWeather();
        Weather weather = op.getWeaher(marker.getPosition());
        Bitmap loadedImage = op.downloadIcon(weather.getIcon());
        Bitmap resized = Bitmap.createScaledBitmap(loadedImage, (int) (loadedImage.getWidth() * 4), (int) (loadedImage.getHeight() * 4), true);
        //Añadimos la imagen
        ImageView infoImageView = new ImageView(MapsActivity.this);
        infoImageView.setImageBitmap(resized);
        infoView.addView(infoImageView);


        //Añadimos el nombre de la playa
        LinearLayout subInfoView = new LinearLayout(MapsActivity.this);
        LinearLayout.LayoutParams subInfoViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        subInfoView.setOrientation(LinearLayout.VERTICAL);
        subInfoView.setLayoutParams(subInfoViewParams);

        TextView subInfoName = new TextView(MapsActivity.this);
        subInfoName.setText(marker.getTitle());
        subInfoView.addView(subInfoName);
        infoView.addView(subInfoView);

        //Añadir informacion del clima por escrito
      /*  TextView subInfoClima = new TextView(MapsActivity.this);
        subInfoClima.setText(marker.getTitle().);
        subInfoView.addView(subInfoClima);
        infoView.addView(subInfoView);
        */
        return infoView;
    }
}




