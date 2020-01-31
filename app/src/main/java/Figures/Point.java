package Figures;
//Imports
import android.graphics.Paint;
/**
 * This class define the geometric point figure
 * @author Edwin Saavedra
 * @version 3
 */
public class Point extends Figure{
    //Class Attributes
    private float centerX;
    private float centerY;
    /**
     * Class Constructor
     * @param _centerX Define the position of the point
     * @param _centerY Define the position of the point
     * @param _colour Define colour of the figure
     * @param _paint Define the style and plotted to draw the figure*/
    public Point(float _centerX,float _centerY,Paint _paint, int[] _colour){
        super(_paint,_colour);
        centerX = _centerX;
        centerY = _centerY;
    }
    //Methods Getters and Setters
    public void setCenterX(float _centerX){
        centerX = _centerX;
    }
    public float getCenterX(){
        return centerX;
    }
    public void setCenterY(float _centerY){
        centerY = _centerY;
    }
    public float getCenterY(){
        return centerY;
    }//Closing the methods getters and setters
    /**
     * Method toString show class content
     * @param format Define the writing format
     * @return content of the class*/
    public String toString(String format){
        switch (format){
            case "json":
                return "{\"id\":5,\"cx\"="+this.getCenterX()+",\"cy\"="+this.getCenterY()+"}" ;
            case "xml":
                return "Implement Format XML";
            default:
                return "Format no exits";
        }
    }//End Method toString
}//Close Class
