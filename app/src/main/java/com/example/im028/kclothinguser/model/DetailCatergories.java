package com.example.im028.kclothinguser.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by im028 on 28/6/17.
 */

public class DetailCatergories {
    @SerializedName("product_id")
    public int product_id=0;
    @SerializedName("product_name")
    public String product_name="";
    @SerializedName("product_content")
    public String product_content="";
    @SerializedName("currency")
    public String currency="";
    @SerializedName("price")
    public String price="";
    @SerializedName("medium_image")
    public String medium_image="";
    @SerializedName("original_image")
    public String original_image="";
    @SerializedName("thumb_image")
    public String thumb_image="";
    @SerializedName("stock_status")
    public String stock_status="";

    public String getMedium_image() {
        return medium_image;
    }

    public void setMedium_image(String medium_image) {
        this.medium_image = medium_image;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_content() {
        return product_content;
    }

    public void setProduct_content(String product_content) {
        this.product_content = product_content;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginal_image() {
        return original_image;
    }

    public void setOriginal_image(String original_image) {
        this.original_image = original_image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getStock_status() {
        return stock_status;
    }

    public void setStock_status(String stock_status) {
        this.stock_status = stock_status;
    }
}
