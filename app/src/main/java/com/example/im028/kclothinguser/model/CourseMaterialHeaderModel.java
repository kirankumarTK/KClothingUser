package com.example.im028.kclothinguser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by im028 on 6/6/17.
 */

public class CourseMaterialHeaderModel {
    private String MinorCode;
    private ArrayList<CourseMaterialChileModel> courseMaterialChileModels;

    public String getMinorCode() {
        return MinorCode;
    }

    public void setMinorCode(String minorCode) {
        MinorCode = minorCode;
    }

    public List<CourseMaterialChileModel> getCourseMaterialChileModels() {
        return courseMaterialChileModels;
    }

    public void setCourseMaterialChileModels(ArrayList<CourseMaterialChileModel> courseMaterialChileModels) {
        this.courseMaterialChileModels = courseMaterialChileModels;
    }
}
