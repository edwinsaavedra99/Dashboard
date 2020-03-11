package com.example.dashboard.Figures;

import android.graphics.Paint;

public class Mark extends Figure{
    //Class Attributes
    private float centerX;
    private float centerY;
    private Mark next;
    private Mark after;

    public Mark(float _centerX, float _centerY, Paint _paint, int[] _colour){
        super(_paint,_colour);
        centerX = _centerX;
        centerY = _centerY;
        next = null;
        after = null;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public Mark getNext() {
        return next;
    }

    public void setNext(Mark next) {
        this.next = next;
    }

    public Mark getAfter() {
        return after;
    }

    public void setAfter(Mark after) {
        this.after = after;
    }
}
