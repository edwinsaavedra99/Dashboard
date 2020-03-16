package com.example.dashboard.Filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.graphics.Bitmap.Config.ARGB_8888;

public class GroupFilters {

    public static Bitmap makeTransparent(Bitmap src, int value, String description){
        int width = src.getWidth();
        int height =  src.getHeight();
        Bitmap transBitmap = Bitmap.createBitmap(width,height, ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0,0,0,0);
        final Paint paint = new Paint();
        paint.setAlpha(value);
        final Paint pencil = new Paint();
        Typeface tipoFuente = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL);
        pencil.setTypeface(tipoFuente);
        pencil.setTextSize(40*width/height);
        pencil.setTextAlign(Paint.Align.CENTER);
        pencil.setColor(Color.WHITE);
        canvas.drawBitmap(src,0,0,paint);
        canvas.drawText(description,src.getWidth()/2,src.getHeight()/2,pencil);
        return  transBitmap;
    }

    public static Bitmap makeText(Bitmap src,String description){
        int width = src.getWidth();
        int height =  src.getHeight();
        Bitmap textBitmap = Bitmap.createBitmap(width,height, ARGB_8888);
        Canvas canvas = new Canvas(textBitmap);
        canvas.drawARGB(0,0,0,0);

        final Paint paint = new Paint();
        final Paint pencil2 = new Paint();
        Typeface tipoFuente = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL);
        pencil2.setTypeface(tipoFuente);
        pencil2.setTextSize(40*width/height);
        int sizeDimen =  (int) (height/2+200*width/height);
        Rect areRect = new Rect(0,sizeDimen-65*width/height,width,sizeDimen);
        pencil2.setColor(Color.BLACK);

        canvas.drawBitmap(src,0,0,paint);
        canvas.drawRect(areRect,pencil2);

        RectF bounds = new RectF(areRect);
        bounds.right = pencil2.measureText(description,0,description.length());
        bounds.bottom = pencil2.descent()-pencil2.ascent();
        bounds.left += (areRect.width()-bounds.right)/2.0f;
        bounds.top += (areRect.height()-bounds.bottom)/2.0f;
        pencil2.setColor(Color.WHITE);
        canvas.drawText(description,bounds.left,bounds.top-pencil2.ascent(),pencil2);
        return  textBitmap;
    }

    public static Bitmap scaleDown(Bitmap realImage,float maxImageSize , boolean filter){
        float ratio = Math.min((float) maxImageSize/realImage.getWidth(),(float) maxImageSize/realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());
        Bitmap newImage = Bitmap.createScaledBitmap(realImage,width,height,filter);
        return newImage;
    }
}
