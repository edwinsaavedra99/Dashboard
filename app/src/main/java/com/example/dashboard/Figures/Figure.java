package com.example.dashboard.Figures;
//Imports
import android.graphics.Paint;
/**
 * This abstract class defines geometric figures
 * @author Edwin Saavedra
 * @version 3
 */
public abstract class Figure {
    //Class Attributes
    private Paint paint;
    private int[] colour;
    private String description;
    /**
     * Class Constructor
     * @param _paint Define the style and plotted to draw the figure
     * @param _colour Define colour of the figure*/
    Figure(Paint _paint, int[] _colour){
        paint = _paint;
        colour = _colour;
        description = "";
    }//Closing the class constructor
    // Methods Getters and Setters
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int[] getColour() {
        return colour;
    }
    public void setColour(int[] _colour) {
        colour = _colour;
    }
    public Paint getPaint(){
        return paint;
    }
    public void setPaint(Paint _paint){
        paint = _paint;
    }//Closing the methods getters and setters
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
}//Class Closure