package ListFigures;
//Imports
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import Figures.*;
/**
 * This class Canvas defines a list of segments in a view
 * @author Edwin Saavedra
 * @version 3
 */
@SuppressLint("ViewConstructor")
public class ListSegmentation extends View {
    //Class Attributes
    private ArrayList<Figure> segmentation;
    private int[] color = {183, 149, 11};
    private LinearLayout viewZoom;
    private LinearLayout content;
    private CardView cardView;
    private int figureSelected;
    private boolean flagFilter = false;
    protected float touchX = 0;
    protected float touchY = 0;
    private float getPastX = 0;
    private float getPastY = 0;
    private float acceptDistance = 30;
    private float generalHeight = 0;
    private float generalWidth = 0;
    private float scaleZoomLayout = 3.0f;
    //Mode Touch
    private int modeTouch = 0; //Mode 0 is Normal ... Mode 1 es zoomMode ... //Mode 2 es eraser
    private  LinearLayout layout;
    private boolean upMode = false;
    private Drawable d;
    //PicToZoom
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ListZoomSegmentation zoomList;
    /**
     * Class Constructor
     * @param context The View
     * @param viewZoom : layout zoom Image RX*/
    public ListSegmentation (Context context, LinearLayout viewZoom,CardView cardView,LinearLayout content){
        super(context);
        this.content = content;
        this.viewZoom = viewZoom;
        layout = content;
        scaleGestureDetector = new ScaleGestureDetector(context, new ListSegmentation.ScaleListener());
        this.viewZoom.setVisibility(INVISIBLE);
        this.cardView = cardView;
        this.cardView.setVisibility(GONE);
        segmentation = new ArrayList<>();
        zoomList = new ListZoomSegmentation(context);
        this.viewZoom.addView(zoomList);
        invalidate();
    }//Closing the class constructor
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            float m_MIN_ZOOM = 1.0f;
            float m_MAX_ZOOM = 3.0f;
            mScaleFactor = Math.max(m_MIN_ZOOM,Math.min(mScaleFactor, m_MAX_ZOOM));
            invalidate();
            layout.setScaleX(mScaleFactor);
            layout.setScaleY(mScaleFactor);
            return true;
        }
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector){
            return true;
        }
    }
    /**
     * Method addCircleSegmentation add a circle in the list of segments
     * @param _startX Define the position of the segment
     * @param _startY Define the position of the segment
     * @param _radius Define the radius od the segment*/
    public void addCircleSegmentation(float _startX, float _startY, float _radius) {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        Paint pencil;
        pencil = new Paint();
        pencil.setAntiAlias(true);
        pencil.setARGB(250, color[0],color[1],color[2]);
        pencil.setStrokeWidth(4);
        pencil.setStyle(Paint.Style.FILL);
        pencil.setPathEffect(dashPathEffect);
        Circle aux = new Circle(_startX, _startY,  _radius, pencil,color);
        this.segmentation.add(aux);
        figureSelected = this.segmentation.size()-1;
        zoomList.addCircleSegmentation(_startX*this.viewZoom.getWidth()/this.getWidth(),_startY*this.viewZoom.getHeight()/this.getHeight(), _radius*this.viewZoom.getWidth()/this.getWidth(),pencil);
        zoomList.invalidate();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void changeFlagFilter(Drawable d){
        //flagFilter = !flagFilter;
        viewZoom.setBackground(d);
        content.setBackground(d);
        this.d = d;
    }

    /**
     * Method after : remove one segment : deleteFigure the selected figure is deleted*/
    public void after(){
        zoomList.after();
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

    /**
     * Method clearList deleted the list
     * */
    public void clearList(){
        zoomList.clearList();
        zoomList.invalidate();
        segmentation.clear();
        invalidate();
    }
    /**
     * Method changeColour of the list of segments
     * */
    public boolean changeColour(int [] colour) {
        zoomList.changeColour(colour);
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

    /**
     * Method distancePoint to Point define tha distance between two points
     * @param pointX X Coordinate of the first point
     * @param pointX1 X Coordinate of the second point
     * @param pointY Y Coordinate of the first point
     * @param pointY1 Y Coordinate of the second point
     * @return distance*/
    public float distancePoint_to_Point(float pointX, float pointY, float pointX1, float pointY1){
        float valX = pointX - pointX1;
        float valY = pointY - pointY1;
        double distance = Math.sqrt(valX * valX + valY * valY);
        return (float) distance;
    }//Closing the Method

    /**
     * Method onDraw draw the segment
     * @param canvas area of draw*/
    protected void onDraw(Canvas canvas) {
        generalWidth = canvas.getWidth();
        generalHeight = canvas.getHeight();
        for(int i=0;i<segmentation.size();i++){
            if (segmentation.get(i) instanceof Circle) {
                Circle temp = (Circle) segmentation.get(i);
                if(i==segmentation.size()-1){
                    temp.getPaint().setStyle(Paint.Style.STROKE);
                    temp.getPaint().setStrokeWidth(6);
                }else{
                    temp.getPaint().setStyle(Paint.Style.FILL);
                }
                canvas.drawCircle(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getPaint());
                if(i == figureSelected){
                    canvas.drawCircle(temp.getCenterX(),temp.getCenterY(),acceptDistance,Util.Circle(temp.getColour()));
                }
            }
        }
        if(modeTouch ==2&&upMode){
            float[] intervals = new float[]{0.0f, 0.0f};
            float phase = 0;
            DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
            Paint pencil;
            pencil = new Paint();
            pencil.setAntiAlias(true);
            pencil.setARGB(250, 255,250,240);
            pencil.setStrokeWidth(4);
            pencil.setStyle(Paint.Style.STROKE);
            pencil.setPathEffect(dashPathEffect);
            canvas.drawCircle(touchX,touchY,acceptDistance/2,pencil);
            canvas.drawCircle(touchX,touchY,acceptDistance*2,pencil);
        }
    }

    public void changeModeTouch( int mode){
        modeTouch = mode;
        zoomList.modeTouch = mode;
        zoomList.invalidate();
    }
    public int getModeTouch(){
        return modeTouch;
    }

    /**
     * Method check check the dimensions of the figure Point in its container
     * @param x of point
     * @param y of point */
    public boolean check(float x,float y){
        return x< generalWidth &&
                x>0 &&
                y<generalHeight &&
                y>0;
    }//End Method

    /**
     * Method checkCircle check the dimensions of the figure Circle in its container
     * @param temp Circle figure */
    public boolean checkCircle(Circle temp){
        return temp.getCenterX()+temp.getRadius() < generalWidth &&
                temp.getCenterX()-temp.getRadius()>0 &&
                temp.getCenterY()+temp.getRadius()<generalHeight &&
                temp.getCenterY()-temp.getRadius()>0;
    }//End Method

    /**
     * Method adaptCircle change and adapt the dimensions of the figure Circle in its container
     * @param temp Circle figure */
    public void adaptCircle(Circle temp){
        if(temp.getCenterX()+temp.getRadius() >= generalWidth ){
            if(generalWidth-temp.getRadius()-1-temp.getRadius()<=0){
                temp.setRadius(temp.getRadius()-1);
            }else {
                temp.setCenterX(generalWidth - temp.getRadius() - 1);
            }
        }else if(temp.getCenterX()-temp.getRadius()<=0){
            temp.setCenterX(temp.getRadius()+1);
        }
        if(temp.getCenterY()+temp.getRadius()>=generalHeight){
            if(generalHeight-temp.getRadius()-1-temp.getRadius()<=0){
                temp.setRadius(temp.getRadius()-1);
            }else {
                temp.setCenterY(generalHeight - temp.getRadius() - 1);
            }
        }else if(temp.getCenterY()-temp.getRadius()<=0){
            temp.setCenterY(temp.getRadius()+1);
        }
    }//End Method
    /**
     * Method onTouchEvent
     * @param event Events of touch*/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        upMode = true;
        float getX = event.getX();
        float getY = event.getY();
        int acct = event.getAction();
        float d, d_1;
        if (layout.getTranslationX() >= 0)
            d = (layout.getScaleX() * layout.getWidth() - layout.getWidth()) - layout.getTranslationX();
        else
            d = (layout.getScaleX() * layout.getWidth() - layout.getWidth()) + layout.getTranslationX();
        if (layout.getTranslationY() >= 0)
            d_1 = (layout.getScaleY() * layout.getHeight() - layout.getHeight()) - layout.getTranslationY();
        else
            d_1 = (layout.getScaleY() * layout.getHeight() - layout.getHeight()) + layout.getTranslationY();
        //Mode Zoom
        if(modeTouch == 1) {
            scaleGestureDetector.onTouchEvent(event);
            invalidate();
            if(acct == MotionEvent.ACTION_DOWN) {
                if(!scaleGestureDetector.isInProgress()) {
                    getPastX = getX;
                    getPastY = getY;
                }
            }else if(acct == MotionEvent.ACTION_MOVE){
                if(!scaleGestureDetector.isInProgress()&&layout.getScaleX()>1f&&layout.getScaleY()>1f) {
                    if((getX - (getPastX - layout.getTranslationX()))<d &&(getX - (getPastX - layout.getTranslationX()))>-d)
                        layout.setTranslationX(getX - (getPastX - layout.getTranslationX()));
                    if((getY - (getPastY - layout.getTranslationY()))<d_1 &&(getY - (getPastY - layout.getTranslationY()))>-d_1)
                        layout.setTranslationY(getY - (getPastY - layout.getTranslationY()));
                }
            }else if(acct == MotionEvent.ACTION_UP){
                getPastX = getX;
                getPastY = getY;
                if(layout.getScaleX()<=1f&&layout.getScaleY()<=1f){
                    layout.animate().translationX(0).translationY(0);
                }else{
                    if(layout.getTranslationX()>d)
                        layout.animate().translationX(d);
                    if(layout.getTranslationY()>d_1)
                        layout.animate().translationY(d_1);
                    if(layout.getTranslationX()<-d)
                        layout.animate().translationX(-d);
                    if(layout.getTranslationY()<-d_1)
                        layout.animate().translationY(-d_1);
                }
            }
            return true;
        }
        zoomList.acceptDistance = acceptDistance*this.viewZoom.getWidth()/this.getWidth();
        zoomList.touchX = event.getX()*this.viewZoom.getWidth()/this.getWidth();
        zoomList.touchY = event.getY()*this.viewZoom.getHeight()/this.getHeight();
        this.viewZoom.setPivotX(getX*this.viewZoom.getWidth()/this.getWidth());
        this.viewZoom.setPivotY(getY*this.viewZoom.getHeight()/this.getHeight());
        this.viewZoom.setScaleX(scaleZoomLayout);
        this.viewZoom.setScaleY(scaleZoomLayout);
        this.viewZoom.setTranslationY(15);
        this.viewZoom.setTranslationX(15);
        this.viewZoom.invalidate();
        invalidate();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            zoomList.touchX = event.getX()*this.viewZoom.getWidth()/this.getWidth();
            zoomList.touchY = event.getY()*this.viewZoom.getHeight()/this.getHeight();
            if(modeTouch == 0) { //Touch segments
                this.figureSelected = -1;
                zoomList.figureSelected = -1;
                zoomList.invalidate();
                invalidate();
                getPastX = getX;
                getPastY = getY;
                //zoomList.touchPastX = getX();
                //zoomList.touchPastY = getY();
                if (!segmentation.isEmpty()) {
                    Circle aux = (Circle) segmentation.get(segmentation.size() - 1);
                    float centerX = aux.getCenterX() - event.getX();
                    float centerY = aux.getCenterY() - event.getY();
                    if (Math.sqrt(centerX * centerX + centerY * centerY) <= 40) {
                        this.viewZoom.setVisibility(View.VISIBLE);
                        this.cardView.setVisibility(VISIBLE);
                    }
                    for (int i = 0; i < segmentation.size(); i++) {
                        if (segmentation.get(i) instanceof Circle) {
                            Circle temp = (Circle) segmentation.get(i);
                            //distance with the origin
                            float distance = distancePoint_to_Point(getX, getY, temp.getCenterX(), temp.getCenterY());
                            if (distance <= acceptDistance) { //Move
                                zoomList.figureSelected = i;
                                this.figureSelected = i;
                            }
                        }
                    }
                }
            }else if(modeTouch == 2){ //touch eraser
                this.viewZoom.setVisibility(View.VISIBLE);
                this.cardView.setVisibility(VISIBLE);
                zoomList.invalidate();
            }
        }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            touchX = event.getX();
            touchY = event.getY();
            zoomList.touchX = event.getX()*this.viewZoom.getWidth()/this.getWidth();
            zoomList.touchY = event.getY()*this.viewZoom.getHeight()/this.getHeight();
            if(modeTouch == 0) { //Touch segments
                if (figureSelected == segmentation.size() - 1) {
                    if (!segmentation.isEmpty()) {
                        boolean drawS = false;
                        Circle aux = (Circle) segmentation.get(segmentation.size() - 1);
                        float centerX = aux.getCenterX() - event.getX();
                        float centerY = aux.getCenterY() - event.getY();
                        //60 y 70 is the interval of segmentation acceptDistance = 30
                        if (Math.sqrt(centerX * centerX + centerY * centerY) > acceptDistance * 2 && Math.sqrt(centerX * centerX + centerY * centerY) < acceptDistance * 2 + 10) {
                            drawS = true;
                            this.viewZoom.setVisibility(View.VISIBLE);
                            this.cardView.setVisibility(VISIBLE);
                        }
                        if (drawS)
                            addCircleSegmentation(getX, getY, 9); //9 is radius acceptable
                    } else {
                        addCircleSegmentation(getX, getY, 9);  //9 is radius acceptable
                    }
                } else {
                    if (this.figureSelected > -1) {
                        if (segmentation.get(figureSelected) instanceof Circle) {
                            Circle temp = (Circle) segmentation.get(figureSelected);
                            Circle temp_z = (Circle) zoomList.segmentation.get(figureSelected);
                            //checkCircle check dimensions of the circle
                            if (checkCircle(temp)) {
                                temp.setCenterX(temp.getCenterX() - (getPastX - getX));
                                temp.setCenterY(temp.getCenterY() - (getPastY - getY));
                                invalidate();
                                temp_z.setCenterX(temp.getCenterX() * this.viewZoom.getWidth() / this.getWidth());
                                temp_z.setCenterY(temp.getCenterY() * this.viewZoom.getHeight() / this.getHeight());
                                zoomList.invalidate();
                            } else {
                                adaptCircle(temp);
                            }
                        }
                    }
                    getPastX = getX;
                    getPastY = getY;
                }
            }else if(modeTouch == 2){ //touch eraser
                for (int i = 0; i < segmentation.size(); i++) {
                    if (segmentation.get(i) instanceof Circle){
                        Circle temp = (Circle) segmentation.get(i);
                        //distance with the origin
                        float distance = distancePoint_to_Point(getX, getY, temp.getCenterX(), temp.getCenterY());
                        if(distance<=temp.getRadius()){
                            segmentation.remove(i);
                            zoomList.segmentation.remove(i);
                            figureSelected = segmentation.size()-1;
                            zoomList.figureSelected = zoomList.segmentation.size()-1;
                            zoomList.invalidate();
                        }
                    }
                }
            }
            this.viewZoom.setVisibility(View.VISIBLE);
            this.cardView.setVisibility(VISIBLE);
            invalidate();
            zoomList.invalidate();
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            upMode = false;
            invalidate();
            zoomList.invalidate();
            this.viewZoom.setVisibility(View.INVISIBLE);
            this.cardView.setVisibility(GONE);
            for (int i = 0; i < segmentation.size(); i++) {
                if (segmentation.get(i) instanceof Circle){
                    Circle temp = (Circle) segmentation.get(i);
                    if(!checkCircle(temp))
                        adaptCircle(temp);
                }
            }
            if(layout.getScaleX()<=1f&&layout.getScaleY()<=1f){
                layout.animate().translationX(0).translationY(0);
            }else{
                if(layout.getTranslationX()>d)
                    layout.animate().translationX(d);
                if(layout.getTranslationY()>d_1)
                    layout.animate().translationY(d_1);
                if(layout.getTranslationX()<-d)
                    layout.animate().translationX(-d);
                if(layout.getTranslationY()<-d_1)
                    layout.animate().translationY(-d_1);
            }
        }
        return true;
    }//End Method
}//Close Class