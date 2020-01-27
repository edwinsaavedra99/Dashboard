package Figures;
//Imports
import android.graphics.Paint;
/**
 * This class define the geometric circle figure
 * @author Edwin Saavedra
 * @version 3
 */
public class Circle extends Figure{
    //Class Attributes
    private float radius;
    private float centerX;
    private float centerY;
    /**
     * Class Constructor
     * @param _centerX Define the position of the circle
     * @param _centerY Define the position of the circle
     * @param _radius Define the radius od the circle
     * @param _colour Define colour of the figure
     * @param _paint Define the style and plotted to draw the figure*/
    public Circle(float _centerX, float _centerY, float _radius , Paint _paint, int[] _colour){
        super(_paint,_colour);
        centerX = _centerX;
        centerY = _centerY;
        radius = _radius;
    }//Closing the class constructor

    //Methods Getters and Setters
    public void setRadius(float _radius){
        radius = _radius;
    }
    public float getRadius(){
        return radius;
    }
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
                return "{\"id\":1,\"cx\"="+this.getCenterX()+",\"cy\"="+this.getCenterY()+",\"r\"="+this.getRadius()+"}" ;
            case "xml":
                return "Implement Format XML";
            default:
                return "Format no exits";
        }
    }//End Method toString
}//Close Class
