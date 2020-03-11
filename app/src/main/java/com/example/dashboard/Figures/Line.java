package com.example.dashboard.Figures;
//Import
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class define the geometric line figure
 * @author Edwin Saavedra
 * @version 3
 */
public class Line extends Figure {
    //Class Attributes
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    /**
     * Class Constructor
     * @param _startX Define the start Coordinate X
     * @param _startY Define the start Coordinate Y
     * @param _stopX Define the stop Coordinate X
     * @param _stopY Define the stop Coordinate Y
     * @param _colour Define colour of the figure
     * @param _paint Define the style and plotted to draw the figure*/
    public Line(float _startX , float _startY, float _stopX, float _stopY, Paint _paint, int[] _colour){
        super(_paint,_colour);
        startX = _startX;
        startY = _startY;
        stopX = _stopX;
        stopY = _stopY;
    }//Closing the class constructor
    /**
     * Method distancePoint to Line define tha distance between one point and one line
     * @param valX X Coordinate of the point
     * @param valY X Coordinate of the point
     * @return distance*/
    public float distancePoint_to_Line(float valX, float valY){
        float m = (stopY - startY) / (stopX - startX); //slope of the straight
        //Formula of the distance
        float B = -1;
        float C = - m * startX + startY;
        double distance = Math.abs(m * valX + B * valY + C)/Math.sqrt(m * m + B * B);
        //End Formula
        return (float) distance;
    }//End Method

    // Methods Getters and Setters
    public float getStartX() {
        return startX;
    }
    public void setStartX(float startX) {
        this.startX = startX;
    }
    public float getStartY() {
        return startY;
    }
    public void setStartY(float startY) {
        this.startY = startY;
    }
    public float getStopX() {
        return stopX;
    }
    public void setStopX(float stopX) {
        this.stopX = stopX;
    }
    public float getStopY() {
        return stopY;
    }
    public void setStopY(float stopY) {
        this.stopY = stopY;
    } //Closing the methods getters and setters
    /**
     * Method toString show class content
     * @param format Define the writing format
     * @return content of the class*/
    public String toString(String format){
        switch (format){
            case "json":
                return "{\"id\":3,\"startX\"="+this.startX+",\"startY\"="+this.startY+",\"stopX\"="+this.stopX+",\"stopY\"="+this.stopY+"}";
            case "xml":
                return "Implement Format XML";
            default:
                return "Format no exits";
        }
    }//End Method toString

    public JSONObject getJSONFigure(int id) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id",id);
        object.put("type",4);
        object.put("startX",this.startX);
        object.put("startY",this.startY);
        object.put("stopX",this.stopX);
        object.put("stopY",this.stopY);
        object.put("comment",this.getDescription());
        object.put("r",this.getColour()[0]);
        object.put("g",this.getColour()[1]);
        object.put("b",this.getColour()[2]);
        object.put("colorName",null);
        return object;
    }
}