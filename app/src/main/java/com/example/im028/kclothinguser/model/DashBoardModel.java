package com.example.im028.kclothinguser.model;

import java.util.ArrayList;

public class DashBoardModel {

    private ArrayList<CategorylistEntity> categorylist;
    private ArrayList<FeaturedProducts> featuredProductsArrayList;
    private String type = "";

    public ArrayList<FeaturedProducts> getFeaturedProductsArrayList() {
        return featuredProductsArrayList;
    }

    public void setFeaturedProductsArrayList(ArrayList<FeaturedProducts> featuredProductsArrayList) {
        this.featuredProductsArrayList = featuredProductsArrayList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<CategorylistEntity> getCategorylist() {
        return categorylist;
    }

    public void setCategorylist(ArrayList<CategorylistEntity> categorylist) {
        this.categorylist = categorylist;
    }

    public static class CategorylistEntity {

        private String thumbnail;
        private String name;
        private int id;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


    public static class FeaturedProducts {
        private String product_name, price, stock_status, image;
        private int id;

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStock_status() {
            return stock_status;
        }

        public void setStock_status(String stock_status) {
            this.stock_status = stock_status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
