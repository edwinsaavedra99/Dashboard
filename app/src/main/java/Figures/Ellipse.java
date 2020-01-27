package Figures;
//Import
import android.graphics.Paint;
/**
 * This class define the ellipse geometrical figure
 * @author Edwin Saavedra
 * @version 3
 */
public class Ellipse extends Figure{
    //Class Attributes
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float xCenter;
    private float yCenter;
    private float aPow2;
    private float bPow2;
    /**
     * Class Constructor
     * @param _left Define the position of the ellipse from the left
     * @param _top Define the position of the ellipse from above
     * @param _right Define the position of the ellipse from the right
     * @param _bottom Define the position of the ellipse from the below
     * @param _colour Define colour of the figure
     * @param _paint Define the style and plotted to draw the figure*/
    public Ellipse(float _left, float _top, float _right, float _bottom , Paint _paint, int[] _colour){
        super(_paint,_colour);
        left = _left;
        top = _top;
        right = _right;
        bottom = _bottom;
        xCenter = (this.right+this.left)/2;
        yCenter = (this.top+this.bottom)/2;
        aPow2 = (float) Math.pow(xCenter-this.right,2);
        bPow2 = (float) Math.pow(yCenter-this.bottom,2);
    }//Closing the class constructor
    // Methods Getters and Setters
    public float getLeft() {
        return left;
    }
    public void setLeft(float left) {
        this.left = left;
    }
    public float getTop() {
        return top;
    }
    public void setTop(float top) {
        this.top = top;
    }
    public float getRight() {
        return right;
    }
    public void setRight(float right) {
        this.right = right;
    }
    public float getBottom() {
        return bottom;
    }
    public void setBottom(float bottom) {
        this.bottom = bottom;
    }//Closing the methods getters and setters
    /**
     * Method updateParameters : The equation of the ellipse changes according to its parameters
     * @param temp Ellipse update*/
    public void updateParameters(Ellipse temp){
        xCenter = (temp.right+temp.left)/2;
        yCenter = (temp.top+temp.bottom)/2;
        aPow2 = (float) Math.pow(temp.xCenter-temp.right,2);
        bPow2 = (float) Math.pow(temp.yCenter-temp.bottom,2);
    }//End Method
    /**
     * Method pointEquationX1: Find the smallest point of intersection of a line parallel to the X-Axis with the ellipse
     * @return X coordinate of point small*/
    public float pointEquationX1(float pointY){
        return (float) (Math.sqrt((1-Math.pow(pointY-yCenter,2)/bPow2)*aPow2))+xCenter;
    }
    /**
     * Method pointEquationX2: Find the major point of intersection of a line parallel to the X-Axis with the ellipse
     * @return X coordinate of point major*/
    public float pointEquationX2(float pointY){
        return (float) (-Math.sqrt((1-Math.pow(pointY-yCenter,2)/bPow2)*aPow2))+xCenter;
    }
    /**
     * Method pointEquationY1: Find the major point of intersection of a line parallel to the Y-Axis with the ellipse
     * @return Y coordinate of point major*/
    public float pointEquationY1(float pointX){
        return (float) (Math.sqrt((1-Math.pow(pointX-xCenter,2)/aPow2)*bPow2))+yCenter;
    }
    /**
     * Method pointEquationY2: Find the smallest point of intersection of a line parallel to the Y-Axis with the ellipse
     * @return Y coordinate of point small*/
    public float pointEquationY2(float pointX){
        return (float) (-Math.sqrt((1-Math.pow(pointX-xCenter,2)/aPow2)*bPow2))+yCenter;
    }
    /**
     * Method toString show class content
     * @param format Define the writing format
     * @return content of the class*/
    public String toString(String format){
        switch (format){
            case "json":
                return "{\"id\":2,\"l\"="+this.getLeft()+",\"t\"="+this.getTop()+",\"r\"="+this.getRight()+",\"b\"="+this.getBottom()+"}";
            case "xml":
                return "Implement Format XML";
            default:
                return "Format no exits";
        }
    }//End Method toString
}//Close Class