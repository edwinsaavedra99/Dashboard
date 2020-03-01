package ListFigures;

//Imports
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import Figures.Circle;
import Figures.Figure;

import static android.view.MotionEvent.INVALID_POINTER_ID;

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
    protected int modeTouch = 0;
    private Bitmap mImage;
    private  int mImageWidth;
    private int mImageHeight;
    protected boolean flagPreview = false;
    /**
     * Class Constructor
     * @param context The View*/
    public ListZoomSegmentation (Context context){
        super(context);
        segmentation = new ArrayList<>();
        segmentationMin = new ArrayList<>();
        invalidate();
    }//Closing the class constructor

    /**
     * Method addCircleSegmentation add a circle in the list of segments Zoom
     * @param _startX Define the position of the segment
     * @param _startY Define the position of the segment
     * @param _radius Define the radius od the segment*/
    public void addCircleSegmentation(float _startX, float _startY, float _radius, Paint pencil,int index) {
        pencil.setStrokeWidth(1);
        Circle aux = new Circle(_startX, _startY, _radius, pencil,color);
        if (index == 1) {
            this.segmentation.add(aux);
            invalidate();
            figureSelected = this.segmentation.size() - 1;
        }else{
            this.segmentation.add(0,aux);
            invalidate();
            figureSelected = 0;
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
    public void loadImage(Bitmap mImage){
        //mImage = img;
        float aspectRatio = (float) mImage.getHeight()/mImage.getWidth();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mImageWidth = displayMetrics.widthPixels;
        mImageHeight = Math.round(mImageWidth*aspectRatio);
        this.mImage =  Bitmap.createScaledBitmap(mImage,mImageWidth,mImageHeight,false);
        invalidate();
        //requestLayout();
    }
    /**
     * Method onDraw draw the segment
     * @param canvas area of draw*/
    protected void onDraw(Canvas canvas) {
        generalWidth = canvas.getWidth();
        generalHeight = canvas.getHeight();

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
        if(modeTouch ==2){
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
        if(flagPreview && !segmentation.isEmpty()){ //DEMO TRAZADO SEGMENTATION
            //segmentationMin = distanceMin(segmentation);
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
    }
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {

        final int acct = event.getActionMasked();
        switch (acct) {

            case MotionEvent.ACTION_MOVE:{
                //System.out.println(event.getX()+" : "+event.getY());
                break;
            }
        }
        return true;
    }
}