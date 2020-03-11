package com.example.dashboard.ListFigures;

//Imports
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;
import com.example.dashboard.Figures.Circle;
import com.example.dashboard.Figures.Figure;

/**
 * This class Canvas defines a list of figures in a view zoom
 * @author Edwin Saavedra
 * @version 3
 */
public class ListZoomSegmentation extends View {

    //Class Attributes
    protected ArrayList<Figure> segmentation;
    private ArrayList<Figure> segmentationMin;
    private int[] color = {183, 149, 11};
    protected int figureSelected;
    protected float acceptDistance = 30;
    protected float generalHeight = 0;
    protected float generalWidth = 0;
    protected float touchX = 0;
    protected float touchY = 0;
    private float mPositionX;
    private float mPositionY;
    protected int modeTouch = 0;
    private Bitmap mImage;
    private  int mImageWidth;
    private int mImageHeight;
    private float alto=0,ancho=0;
    private Drawable mBoard;
    protected boolean flagPreview = false;
    private float mScaleFactor = 1.0f;
    private boolean touchEvent = false;

    public boolean isTouchEvent() {
        return touchEvent;
    }

    public void setTouchEvent(boolean touchEvent) {
        this.touchEvent = touchEvent;
    }

    public float getmScaleFactor() {
        return mScaleFactor;
    }

    public void setmScaleFactor(float mScaleFactor) {
        this.mScaleFactor = mScaleFactor;
    }

    public float getmPositionX() {
        return mPositionX;
    }

    public float getmPositionY() {
        return mPositionY;
    }

    public void setmPositionX(float mPositionX) {
        this.mPositionX = mPositionX;
    }

    public void setmPositionY(float mPositionY) {
        this.mPositionY = mPositionY;
    }

    /**
     * Class Constructor
     * @param context The View*/
    public ListZoomSegmentation (Context context){
        super(context);
        segmentation = new ArrayList<>();
        segmentationMin = new ArrayList<>();
        invalidate();
    }//Closing the class constructor

    public float getGeneralHeight() {
        return generalHeight;
    }

    public float getGeneralWidth() {
        return generalWidth;
    }

    /**
     * Method addCircleSegmentation add a circle in the list of segments Zoom
     * @param _startX Define the position of the segment
     * @param _startY Define the position of the segment
     * @param _radius Define the radius od the segment*/
    public void addCircleSegmentation(float _startX, float _startY, float _radius, Paint pencil,int index,String description,int colour[]) {
        pencil.setStrokeWidth(1);
        Circle aux = new Circle(_startX, _startY, _radius, pencil,colour);
        aux.setDescription(description);
        if(index == -1 ) {
            this.segmentation.add(aux);
            figureSelected = this.segmentation.size() - 1;
        }else if(index == 0) {
            this.segmentation.add(0,aux);
        }else{
            this.segmentation.add(index,aux);

        }
        invalidate();
    }//End Method

    public ArrayList<Figure> getSegmentation() {
        return segmentation;
    }

    public int getFigureSelected() {
        return figureSelected;
    }

    /**
     * Method after : remove one segment : deleteFigure the selected figure is deleted*/
    public void after(){
        if(figureSelected == segmentation.size()-1) {
            if (segmentation.size() > 0) {
                segmentation.remove(segmentation.size() - 1);
                figureSelected = segmentation.size() - 1;
                invalidate();
            }
        }else{
            if (segmentation.size()>0 && this.figureSelected != -1 ) {
                segmentation.remove(this.figureSelected);
                figureSelected = -1;
                invalidate(); //Redraw
            }
        }
    }
    public void clearList(){
        segmentation.clear();
        invalidate();
    }
    /**
     * Method changeColour of the list of segments zoom
     * */
    public boolean changeColour(int [] colour) {
        if(figureSelected == segmentation.size()-1) { //si el seleccionado es el ultimo -- cambiar el color a partir del ultimo
            if(segmentation.size()==0){
                return false;
            }else {
                Circle temp = (Circle) segmentation.get(figureSelected);
                temp.getPaint().setARGB(255, colour[0], colour[1], colour[2]);
                temp.setColour(colour);
                color = colour;
                invalidate(); //Redraw
                return true;
            }
        }else{
            if (segmentation.size() >0 && this.figureSelected != -1) {
                segmentation.get(this.figureSelected).setColour(colour);
                segmentation.get(this.figureSelected).getPaint().setARGB(255,colour[0],colour[1],colour[2]);
                invalidate(); //Redraw
                return true;
            }else {
                return false;
            }
        }

    }//End Method
    public void loadImage(Bitmap mImage,float alto,float ancho){
        this.mImage = mImage;
        mBoard = new BitmapDrawable(getResources(),mImage);
        generalWidth =ancho;
        generalHeight = alto;
        invalidate();
    }
    /**
     * Method onDraw draw the segment
     * @param canvas area of draw*/
    protected void onDraw(Canvas canvas) {
        int alto,ancho;
        alto = (int) generalHeight;
        ancho = (int) generalWidth;
        if(mBoard !=null){
            canvas.save();
            mBoard.setBounds(0,0,(int)ancho,(int)alto);
            mImageHeight =(int) alto;
            mImageWidth = (int)ancho;
            canvas.translate(mPositionX,mPositionY);
            canvas.scale(mScaleFactor,mScaleFactor);
            mBoard.draw(canvas);
        }
        for(int i=0;i<segmentation.size();i++){
            if (segmentation.get(i) instanceof Circle) {
                Circle temp = (Circle) segmentation.get(i);
                if(i==segmentation.size()-1 || i == 0){
                    temp.getPaint().setStyle(Paint.Style.STROKE);
                    temp.getPaint().setStrokeWidth(2);
                }else{
                    temp.getPaint().setStyle(Paint.Style.FILL);
                }
                canvas.drawCircle(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getPaint());
                if(i == figureSelected){
                    Paint p = temp.getPaint();
                    p.setStyle(Paint.Style.STROKE);
                    p.setStrokeWidth(2);
                    canvas.drawCircle(temp.getCenterX(),temp.getCenterY(),acceptDistance,p);
                }
            }
        }
        if(modeTouch ==2 && touchEvent){
            float[] intervals = new float[]{0.0f, 0.0f};
            float phase = 0;
            DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
            Paint pencil;
            pencil = new Paint();
            pencil.setAntiAlias(true);
            pencil.setARGB(250, 255,250,240);
            pencil.setStrokeWidth(1);
            pencil.setStyle(Paint.Style.STROKE);
            pencil.setPathEffect(dashPathEffect);
            canvas.drawCircle(touchX,touchY,acceptDistance/2,pencil);
            canvas.drawCircle(touchX,touchY,acceptDistance*2,pencil);
        }
        if(flagPreview && !segmentation.isEmpty()){
            segmentationMin = segmentation;
            for(int i=0;i< segmentationMin.size();i++){
                if ( segmentationMin.get(i) instanceof Circle) {
                    Circle temp = (Circle)  segmentationMin.get(i);
                    if(i+1!= segmentationMin.size()) {
                        Circle temp02 = (Circle)  segmentationMin.get(i+1);
                        Paint p = temp.getPaint();
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(2);
                        canvas.drawLine( temp.getCenterX(),  temp.getCenterY(), temp02.getCenterX(),  temp02.getCenterY(),p);
                    }

                }
            }
        }
        canvas.restore();
    }
}