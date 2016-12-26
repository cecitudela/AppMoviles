package com.example.cecilia.appmoviles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;

    private GoogleApiClient client;

    private HashMap<Marker, Integer> mRatingHash;
    private List<Beach> list;

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

        //Cargamos las playas
        ManageBeach mb = new ManageBeach();
        list = mb.getBeaches();

        mRatingHash = new HashMap<>();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Centramos la cámara en Asturias
        LatLng asturias = new LatLng(43.366980, -5.852600);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(asturias, 7.0f));



        //Creamos un marcador para cada playa
        for (Beach b : list) {
            Marker mark = mMap.addMarker(new MarkerOptions().position(b.getLocation()).title(b.getName()));
            mRatingHash.put(mark, list.indexOf(b));
            //Toast.makeText(getApplicationContext(), list.indexOf(b), Toast.LENGTH_SHORT).show();
        }


        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker arg0) {
                Intent i = new Intent(getApplicationContext(), DetailsActivity.class );
                i.putExtra(DetailsActivity.KEY_LAT, arg0.getPosition());

                int pos = mRatingHash.get(arg0);
                i.putExtra(DetailsActivity.KEY_DET, list.get(pos).toString());
                startActivity(i);
            }

        });

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

        //Descargamos la imagen             <----MEJORARLO
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

        return infoView;
    }


}
