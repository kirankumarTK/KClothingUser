package com.example.im028.kclothinguser.model;

import java.util.ArrayList;
import java.util.List;

public class DashBoardModel {

    private ArrayList<CategorylistEntity> categorylist;
    private String type = "";

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


}
