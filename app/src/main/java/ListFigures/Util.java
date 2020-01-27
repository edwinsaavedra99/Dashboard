package ListFigures;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

import java.util.ArrayList;

public class Util {
    public static int[] color1 = {183, 149, 11};
    public static int[] color2 = {99, 57, 116};
    public static int[] color3 = {31, 97, 141};
    public static int[] color4 = {40, 116, 166};
    public static int[] color5 = {20, 143, 119};
    public static int[] color6 = {183, 149, 11};
    public static int[] color7 = {186, 74, 0};
    public static int[] color8 = {95, 106, 106};
    public static int[] color9 = {46, 64, 83};
    public static int[] color10 = {136, 78, 160};
    public static int[][] collections = new int[10][];

    public static int[][] getCollections(){
        collections[0] = color1;
        collections[1]=color2;
        collections[2]=color3;
        collections[3]=color4;
        collections[4]=color5;
        collections[5]=color6;
        collections[6]=color7;
        collections[7]=color8;
        collections[8]=color9;
        collections[9]=color10;
        return collections;
    }
    public static Paint Circle(int[] colour){
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

    public static Paint CircleTransparent(int[] colour){
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
    public static Paint CircleSmall(int[] colour){
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
