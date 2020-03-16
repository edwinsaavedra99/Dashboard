package com.example.dashboard.Animations;

import android.widget.ImageView;

public class MyAnimation {

    private ImageView img;

    public MyAnimation() {
        this.img=null;
    }

    public void animationScale(ImageView img_, int time, float x, float y){
        deleteAnimation();
        this.img = img_;
        if(this.img == null){
        }else {
            this.img.animate().setDuration(time).scaleX(x).scaleY(y);
            //this.img.setColorFilter(Color.rgb(255,127,80));
        }
    }

    /*public void setImg(ImageView img) {
        this.img = img;
    }*/
    private void deleteAnimation(){
        if(this.img != null) {
            this.img.animate().scaleX(1).scaleY(1);
            //this.img.setColorFilter(Color.rgb(255,255,255));
        }
    }
}
