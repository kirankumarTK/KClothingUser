package com.example.im028.kclothinguser.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by im028 on 28/6/17.
 */

public class Events implements Serializable {

    @SerializedName("event_id")
    public int event_id;
    @SerializedName("event_name")
    public String event_name;
    @SerializedName("event_startdate")
    public String event_startdate;
    @SerializedName("exhibition_enddate")
    public String exhibition_enddate;
    @SerializedName("event_city")
    public String event_city;
    @SerializedName("event_venue")
    public String event_venue;
    @SerializedName("event_location")
    public String event_location;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_startdate() {
        return event_startdate;
    }

    public void setEvent_startdate(String event_startdate) {
        this.event_startdate = event_startdate;
    }

    public String getExhibition_enddate() {
        return exhibition_enddate;
    }

    public void setExhibition_enddate(String exhibition_enddate) {
        this.exhibition_enddate = exhibition_enddate;
    }

    public String getEvent_city() {
        return event_city;
    }

    public void setEvent_city(String event_city) {
        this.event_city = event_city;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }
}
