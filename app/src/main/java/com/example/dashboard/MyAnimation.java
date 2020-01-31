package com.example.dashboard;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyAnimation {

    private ImageView img;

    public MyAnimation(){
        img = null;
    }
    public MyAnimation(ImageView img){
        this.img = img;
    }

    public boolean animationScale(ImageView img_,int time, float x , float y){
        deleteAnimation();
        this.img = img_;
        if(this.img == null){
            return false;
        }else {
            this.img.animate().setDuration(time).scaleX(x).scaleY(y);
            //this.img.setColorFilter(Color.rgb(255,127,80));
            return true;
        }
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
    private void deleteAnimation(){
        if(this.img != null) {
            this.img.animate().scaleX(1).scaleY(1);
            //this.img.setColorFilter(Color.rgb(255,255,255));
        }
    }
}
