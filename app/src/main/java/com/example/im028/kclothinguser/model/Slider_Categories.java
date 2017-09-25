package com.example.im028.kclothinguser.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karth on 7/12/2017.
 */

public class Slider_Categories {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("thumbnail")
    public String thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

