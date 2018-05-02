package com.example.im028.kclothinguser.model;

import java.util.ArrayList;

/**
 * Created by im028 on 12/6/17.
 */

public class CommonNavignationHeader {
    private ArrayList<CommonNavignationChild> commonNavignationChildren=new ArrayList<>();
    private String menuTitle;
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList<CommonNavignationChild> getCommonNavignationChildren() {
        return commonNavignationChildren;
    }

    public void setCommonNavignationChildren(ArrayList<CommonNavignationChild> commonNavignationChildren) {
        this.commonNavignationChildren = commonNavignationChildren;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }
}
