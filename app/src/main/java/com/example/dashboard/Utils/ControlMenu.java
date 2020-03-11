package com.example.dashboard.Utils;

import android.widget.ImageView;
import android.widget.LinearLayout;


public class ControlMenu {
    private LinearLayout general;
    private ImageView touch;
    private ImageView left;
    private ImageView right;
    private ImageView upp;
    private ImageView down;
    public ControlMenu(LinearLayout general, ImageView touch , ImageView left, ImageView right, ImageView upp, ImageView down){
        this.general = general;
        this.touch = touch;
        this.left = left;
        this.right = right;
        this.down = down;
    }

    public LinearLayout getGeneral() {
        return general;
    }

    public void setGeneral(LinearLayout general) {
        this.general = general;
    }

    public ImageView getTouch() {
        return touch;
    }

    public void setTouch(ImageView touch) {
        this.touch = touch;
    }

    public ImageView getLeft() {
        return left;
    }

    public void setLeft(ImageView left) {
        this.left = left;
    }

    public ImageView getRight() {
        return right;
    }

    public void setRight(ImageView right) {
        this.right = right;
    }

    public ImageView getUpp() {
        return upp;
    }

    public void setUpp(ImageView upp) {
        this.upp = upp;
    }

    public ImageView getDown() {
        return down;
    }

    public void setDown(ImageView down) {
        this.down = down;
    }
}

