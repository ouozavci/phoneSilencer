package com.example.oguzhan.silencer;

/**
 * Created by oguzhan on 1.4.2016.
 */
public class LocRecord {
    private int id = -1;
    private String name = "";

    private double lat;
    private double lon;

    public LocRecord(int id, String name, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLon() {
        return this.lon;
    }
}
