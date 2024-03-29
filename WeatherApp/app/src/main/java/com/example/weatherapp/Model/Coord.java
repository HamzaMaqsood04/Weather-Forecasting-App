package com.example.weatherapp.Model;

public class Coord {
    private float lon ;
    private float lat ;

    public Coord() {
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;

    }

    @Override
    public String toString(){
        return new StringBuilder("[").append(this.lat).append(',').append(this.lon).append("]").toString();
    }
}
