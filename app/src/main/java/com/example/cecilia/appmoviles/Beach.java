package com.example.cecilia.appmoviles;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Cecilia on 18/11/2016.
 */

public class Beach {

    private String name;
    private String description;
    private LatLng location;

    public Beach(String name, String description, LatLng location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
