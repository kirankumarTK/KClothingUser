package com.example.im028.kclothinguser.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karth on 7/14/2017.
 */

public class ProductDetails {

    @SerializedName("ID")
    public int ID;
    @SerializedName("product_id")
    public int product_id;
    @SerializedName("product_name")
    public String product_name;
    @SerializedName("product_content")
    public String product_content;
    @SerializedName("customer_name")
    public String customer_name;
    @SerializedName("order_id")
    public String order_id;
    @SerializedName("style")
    public String style;
    @SerializedName("category_id")
    public String category_id;
    @SerializedName("currency")
    public String currency;
    @SerializedName("price")
    public String price;
    @SerializedName("original_image")
    public String original_image;
    @SerializedName("thumb_image")
    public String thumb_image;
    @SerializedName("fabric")
    public String fabric;
    @SerializedName("Colour")
    public String Colour;
    @SerializedName("Others")
    public String Others;
    @SerializedName("Others1")
    public String Others1;
    @SerializedName("Others2")
    public String Others2;
    @SerializedName("Others3")
    public String Others3;
    @SerializedName("Wash_Care")
    public String Wash_Care;
    @SerializedName("Ships In")
    public String Ships_In;
    @SerializedName("Transit Time")
    public String Transit_Time;
    @SerializedName("stock_status")
    public String stock_status;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }

    public String getOthers() {
        return Others;
    }

    public void setOthers(String others) {
        Others = others;
    }

    public String getOthers1() {
        return Others1;
    }

    public void setOthers1(String others1) {
        Others1 = others1;
    }

    public String getOthers2() {
        return Others2;
    }

    public void setOthers2(String others2) {
        Others2 = others2;
    }

    public String getOthers3() {
        return Others3;
    }

    public void setOthers3(String others3) {
        Others3 = others3;
    }

    public String getWash_Care() {
        return Wash_Care;
    }

    public void setWash_Care(String wash_Care) {
        Wash_Care = wash_Care;
    }

    public String getShips_In() {
        return Ships_In;
    }

    public void setShips_In(String ships_In) {
        Ships_In = ships_In;
    }

    public String getTransit_Time() {
        return Transit_Time;
    }

    public void setTransit_Time(String transit_Time) {
        Transit_Time = transit_Time;
    }

    public String getStock_status() {
        return stock_status;
    }

    public void setStock_status(String stock_status) {
        this.stock_status = stock_status;
    }

}
