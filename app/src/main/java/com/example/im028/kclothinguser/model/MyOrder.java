package com.example.im028.kclothinguser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrder {

    @Expose
    @SerializedName("order_item_count")
    private int order_item_count;
    @Expose
    @SerializedName("order_total")
    private String order_total;
    @Expose
    @SerializedName("currency")
    private String currency;
    @Expose
    @SerializedName("order_status")
    private String order_status;
    @Expose
    @SerializedName("order_date")
    private String order_date;
    @Expose
    @SerializedName("order_number")
    private int order_number;

    public int getOrder_item_count() {
        return order_item_count;
    }

    public String getOrder_total() {
        return order_total;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public int getOrder_number() {
        return order_number;
    }
}
