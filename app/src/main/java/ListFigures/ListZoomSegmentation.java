package ListFigures;
//Imports
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import java.util.ArrayList;
import Figures.Circle;
import Figures.Figure;
/**
 * This class Canvas defines a list of figures in a view zoom
 * @author Edwin Saavedra
 * @version 3
 */
public class ListZoomSegmentation extends View {
    //Class Attributes
    private ArrayList<Figure> segmentation;
    private int[] color = {183, 149, 11};
    /**
     * Class Constructor
     * @param context The View*/
    public ListZoomSegmentation (Context context){
        super(context);
        segmentation = new ArrayList<>();
    }//Closing the class constructor

    /**
     * Method addCircleSegmentation add a circle in the list of segments Zoom
     * @param _startX Define the position of the segment
     * @param _startY Define the position of the segment
     * @param _radius Define the radius od the segment*/
    public void addCircleSegmentation(float _startX, float _startY, float _radius, Paint pencil) {
        pencil.setStrokeWidth(1);
        Circle aux = new Circle(_startX, _startY, _radius, pencil,color);
        this.segmentation.add(aux);
        invalidate();
    }//End Method

    /**
     * Method after : remove the last segment*/
    public void after(){
        if(segmentation.size() > 0){
            segmentation.remove(segmentation.size()-1);
            invalidate();
        }
    }

    /**
     * Method changeColour of the list of segments zoom
     * */
    public void changeColour(int [] colour) {
        for(int i=0;i<segmentation.size();i++){
            Circle temp = (Circle) segmentation.get(i);
            temp.getPaint().setARGB(255,colour[0],colour[1],colour[2]);
        }
        invalidate(); //Redraw

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
}