package com.example.im028.kclothinguser.model;

/**
 * Created by karth on 7/13/2017.
 */

public class StandardSize {
    private String size;
    private boolean selected;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public interface OnItemClickListener {
        void onItemClick(String size);

    }
}
