package com.example.cecilia.appmoviles.sqlite;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;


public class Beach {

    private String name;
    private String description;
    private LatLng location;

    private String aseos;
    private String lavapies;
    private String duchas;
    private String papelera;
    private String serv_limpieza;
    private String oficinaTurismo;
    private String establComida;
    private String establBebida;
    private String alquHamacas;
    private String alquSombrillas;
    private String alquNauticos;
    private String clubNautico;
    private String zonaSubmarin;
    private String zonaSurf;
    private String zonaInfa;
    private String zonaDeport;
    private String nudismo;



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

    public String getAseos() {
        return aseos;
    }

    public void setAseos(String aseos) {
        this.aseos = aseos;
    }

    public String getLavapies() {
        return lavapies;
    }

    public void setLavapies(String lavapies) {
        this.lavapies = lavapies;
    }

    public String getDuchas() {
        return duchas;
    }

    public void setDuchas(String duchas) {
        this.duchas = duchas;
    }

    public String getPapelera() {
        return papelera;
    }

    public void setPapelera(String papelera) {
        this.papelera = papelera;
    }

    public String getServ_limpieza() {
        return serv_limpieza;
    }

    public void setServ_limpieza(String serv_limpieza) {
        this.serv_limpieza = serv_limpieza;
    }

    public String getOficinaTurismo() {
        return oficinaTurismo;
    }

    public void setOficinaTurismo(String oficinaTurismo) {
        this.oficinaTurismo = oficinaTurismo;
    }

    public String getEstablComida() {
        return establComida;
    }

    public void setEstablComida(String establComida) {
        this.establComida = establComida;
    }

    public String getEstablBebida() {
        return establBebida;
    }

    public void setEstablBebida(String establBebida) {
        this.establBebida = establBebida;
    }

    public String getAlquHamacas() {
        return alquHamacas;
    }

    public void setAlquHamacas(String alquHamacas) {
        this.alquHamacas = alquHamacas;
    }

    public String getAlquSombrillas() {
        return alquSombrillas;
    }

    public void setAlquSombrillas(String alquSombrillas) {
        this.alquSombrillas = alquSombrillas;
    }

    public String getAlquNauticos() {
        return alquNauticos;
    }

    public void setAlquNauticos(String alquNauticos) {
        this.alquNauticos = alquNauticos;
    }

    public String getClubNautico() {
        return clubNautico;
    }

    public void setClubNautico(String clubNautico) {
        this.clubNautico = clubNautico;
    }

    public String getZonaSubmarin() {
        return zonaSubmarin;
    }

    public void setZonaSubmarin(String zonaSubmarin) {
        this.zonaSubmarin = zonaSubmarin;
    }

    public String getZonaSurf() {
        return zonaSurf;
    }

    public void setZonaSurf(String zonaSurf) {
        this.zonaSurf = zonaSurf;
    }

    public String getZonaInfa() {
        return zonaInfa;
    }

    public void setZonaInfa(String zonaInfa) {
        this.zonaInfa = zonaInfa;
    }

    public String getZonaDeport() {
        return zonaDeport;
    }

    public void setZonaDeport(String zonaDeport) {
        this.zonaDeport = zonaDeport;
    }

    public String getNudismo() {
        return nudismo;
    }

    public void setNudismo(String nudismo) {
        this.nudismo = nudismo;
    }

    @Override
    public String toString() {
        return "Nname='" + name +
                "\n description='" + description +
                "\n location=" + location +
                "\n aseos='" + aseos +
                "\n lavapies='" + lavapies +
                "\n duchas='" + duchas +
                "\n papelera='" + papelera + '\'' +
                "\n serv_limpieza='" + serv_limpieza + '\'' +
                "\noficinaTurismo='" + oficinaTurismo + '\'' +
                "\n establComida='" + establComida + '\'' +
                "\n establBebida='" + establBebida + '\'' +
                "\n alquHamacas='" + alquHamacas + '\'' +
                "\n alquSombrillas='" + alquSombrillas + '\'' +
                "\n alquNauticos='" + alquNauticos + '\'' +
                "\n clubNautico='" + clubNautico + '\'' +
                "\n zonaSubmarin='" + zonaSubmarin + '\'' +
                "\n zonaSurf='" + zonaSurf + '\'' +
                "\n zonaInfa='" + zonaInfa + '\'' +
                "\n zonaDeport='" + zonaDeport + '\'' +
                "\n nudismo='" + nudismo;
    }
}
