package com.example.im028.kclothinguser.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by karth on 7/14/2017.
 */

public class Custom_Size implements Serializable {
    private String name;
    private String label;
    private ArrayList<Integer> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }
}
