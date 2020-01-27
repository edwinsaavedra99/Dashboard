package ListFigures;
//Imports
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
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
    private ListZoomSegmentation zoomList;
    /**
     * Class Constructor
     * @param context The View
     * @param viewZoom : layout zoom Image RX*/
    public ListSegmentation (Context context, LinearLayout viewZoom){
        super(context);
        this.viewZoom = viewZoom;
        this.viewZoom.setVisibility(INVISIBLE);
        segmentation = new ArrayList<>();
        zoomList = new ListZoomSegmentation(context);
        this.viewZoom.addView(zoomList);
    }//Closing the class constructor

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
        zoomList.addCircleSegmentation(_startX*this.viewZoom.getWidth()/this.getWidth(),_startY*this.viewZoom.getHeight()/this.getHeight(), _radius*this.viewZoom.getWidth()/this.getWidth(),pencil);
        zoomList.invalidate();
    }
    /**
     * Method after : remove the last segment*/
    public void after(){
        if(segmentation.size() > 0){
            segmentation.remove(segmentation.size()-1);
            zoomList.after();
            zoomList.invalidate();
            invalidate();
        }
    }

    /**
     * Method changeColour of the list of segments
     * */
    public boolean changeColour(int [] colour) {
        if(segmentation.size()==0){
            return false;
        }else {
            zoomList.changeColour(colour);
            zoomList.invalidate();
            for (int i = 0; i < segmentation.size(); i++) {
                Circle temp = (Circle) segmentation.get(i);
                temp.getPaint().setARGB(255, colour[0], colour[1], colour[2]);
            }
            invalidate(); //Redraw
            return true;
        }
    }//End Method
    /**
     * Method onDraw draw the segment
     * @param canvas area of draw*/
    protected void onDraw(Canvas canvas) {
        for(int i=0;i<segmentation.size();i++){
            if (segmentation.get(i) instanceof Circle) {
                Circle temp = (Circle) segmentation.get(i);
                if(i==segmentation.size()-1){
                    temp.getPaint().setStyle(Paint.Style.STROKE);
                }else{
                    temp.getPaint().setStyle(Paint.Style.FILL);
                }
                canvas.drawCircle(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getPaint());
            }
        }
    }
    /**
     * Method onTouchEvent
     * @param event Events of touch*/
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float getX = event.getX();
        float getY = event.getY();
        this.viewZoom.setPivotX(getX*this.viewZoom.getWidth()/this.getWidth());
        this.viewZoom.setPivotY(getY*this.viewZoom.getHeight()/this.getHeight());
        this.viewZoom.setScaleX(3);
        this.viewZoom.setScaleY(3);
        this.viewZoom.setTranslationY(15);
        this.viewZoom.setTranslationX(15);
        this.viewZoom.invalidate();
        invalidate();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if (! segmentation.isEmpty()) {
                Circle aux = (Circle) segmentation.get(segmentation.size() - 1);
                float centerX = aux.getCenterX() - event.getX();
                float centerY = aux.getCenterY() - event.getY();
                if (Math.sqrt(centerX  * centerX  + centerY * centerY) <= 40)
                    this.viewZoom.setVisibility(View.VISIBLE);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (! segmentation.isEmpty()) {
                boolean drawS = false;
                Circle aux = (Circle) segmentation.get(segmentation.size() - 1);
                float centerX = aux.getCenterX() - event.getX();
                float centerY = aux.getCenterY() - event.getY();
                //50 y 60 is the interval of segmentation
                if (Math.sqrt(centerX * centerX + centerY*centerY) > 50 && Math.sqrt(centerX * centerX + centerY * centerY) < 60) {
                    drawS = true;
                    this.viewZoom.setVisibility(View.VISIBLE);
                }
                if (drawS)
                    addCircleSegmentation(getX,getY,9); //9 is radius acceptable
            } else {
                addCircleSegmentation(getX,getY,9);  //9 is radius acceptable
                this.viewZoom.setVisibility(View.VISIBLE);
            }
            invalidate();
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            this.viewZoom.setVisibility(View.INVISIBLE);
        }
        return true;
    }//End Method
}//Close Class