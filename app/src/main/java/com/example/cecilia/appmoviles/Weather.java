package com.example.cecilia.appmoviles;


public class Weather {
    private String descripcion;
    private String icon;
    private int temp;
    private int temp_min;
    private int temp_max;
    private int humidity;
    private int pressure;

    private double swellHeight_m = -1.1;
    private int waterTemp_C = -3;

    /* swellHeight_m
    * waterTemp_C
    * tideTime
    * tideHeight_mt
    * tide_type
    * */

    public Weather(String descripcion, String icon, int temp, int temp_min, int temp_max, int humidity, int pressure) {
        this.descripcion = descripcion;
        this.icon = icon;
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getSwellHeight_m() {
        return swellHeight_m;
    }

    public void setSwellHeight_m(double swellHeight_m) {
        this.swellHeight_m = swellHeight_m;
    }

    public int getWaterTemp_C() {
        return waterTemp_C;
    }

    public void setWaterTemp_C(int waterTemp_C) {
        this.waterTemp_C = waterTemp_C;
    }

    @Override
    public String toString() {
        return "Descripcion: " + descripcion +
                "\n temp: " + temp +
                "\n temp_min: " + temp_min +
                "\n temp_max: " + temp_max +
                "\n humidity: " + humidity +
                "\n pressure: " + pressure +
                "\n swellHeight_m: " + swellHeight_m +
                "\n waterTemp_C: " + waterTemp_C ;
    }
}
