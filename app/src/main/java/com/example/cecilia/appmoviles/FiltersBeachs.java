package com.example.cecilia.appmoviles;

import android.content.Context;

import com.example.cecilia.appmoviles.sqlite.Beach;
import com.example.cecilia.appmoviles.sqlite.BeachDataSource;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Cecilia on 27/12/2016.
 */

public class FiltersBeachs {


    private BeachDataSource beachSource;
    public FiltersBeachs(Context context){
        beachSource = new BeachDataSource(context);
    }


    public List<Beach> getBeachsByName(String name){
        beachSource.open();
        List<Beach> list = beachSource.getBeachByName(name);
        beachSource.close();
        return list;
    }

}
