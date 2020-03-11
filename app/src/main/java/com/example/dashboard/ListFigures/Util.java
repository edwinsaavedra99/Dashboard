package com.example.dashboard.ListFigures;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class Util {
    private static int[] color1 = {183, 149, 11};
    private static int[] color2 = {99, 57, 116};
    private static int[] color3 = {31, 97, 141};
    private static int[] color4 = {40, 116, 166};
    private static int[] color5 = {20, 143, 119};
    private static int[] color6 = {183, 149, 11};
    private static int[] color7 = {186, 74, 0};
    private static int[] color8 = {95, 106, 106};
    private static int[] color9 = {46, 64, 83};
    private static int[] color10 = {0, 0, 255};
    private static int[] color11 = {255, 0, 90};
    private static int[] color12 = {138, 43, 226};
    private static int[] color13 = {127, 255, 0};
    private static int[] color14 = {95, 158, 160};
    private static int[] color15 = {255, 127, 80};
    private static int[] color16 = {220, 20, 60};
    private static int[] color17 = {0, 0, 139};
    private static int[] color18 = {0, 100, 0};
    private static int[] color19 = {139, 0, 0};
    private static int[] color20 = {255, 20, 147};
    private static int[] color21 = {255, 250, 240};
    private static int[] color22 = {72, 209, 204};
    private static int[] color23 = {65, 105, 225};
    private static int[] color24 = {255, 255, 0};
    private static int[] color25 = {154, 205, 50};
    private static int[][] collections = new int[25][];

    public static int[][] getCollections(){
        collections[0]=color1;
        collections[1]=color2;
        collections[2]=color3;
        collections[3]=color4;
        collections[4]=color5;
        collections[5]=color6;
        collections[6]=color7;
        collections[7]=color8;
        collections[8]=color9;
        collections[9]=color10;
        collections[10]=color11;
        collections[11]=color12;
        collections[12]=color13;
        collections[13]=color14;
        collections[14]=color15;
        collections[15]=color16;
        collections[16]=color17;
        collections[17]=color18;
        collections[18]=color19;
        collections[19]=color20;
        collections[20]=color21;
        collections[21]=color22;
        collections[22]=color23;
        collections[23]=color24;
        collections[24]=color25;
        return collections;
    }
    static Paint Circle(int[] colour){
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        Paint paint;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setARGB(250, colour[0], colour[1], colour[2]);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(dashPathEffect);
        return paint;
    }
    static Paint CircleZoom(int[] colour){
        Paint paint = Circle(colour);
        paint.setStrokeWidth(2);
        return paint;
    }

    static Paint CircleTransparent(int[] colour){
        float[] intervals = new float[]{5.0f, 5.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        Paint paint;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setARGB(150, colour[0], colour[1], colour[2]);
        paint.setStrokeWidth(44);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(dashPathEffect);
        return paint;
    }
    static Paint CircleSmall(int[] colour){
        float[] intervals = new float[]{5.0f, 5.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        Paint paint;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setARGB(255, colour[0], colour[1], colour[2]);
        paint.setStrokeWidth(24);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(dashPathEffect);
        return paint;
    }
}
