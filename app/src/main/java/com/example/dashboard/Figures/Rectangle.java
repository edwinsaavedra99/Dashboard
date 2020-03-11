package com.example.dashboard.Figures;
//Import
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class define the rectangular geometrical figure
 * @author Edwin Saavedra
 * @version 3
 */
public class Rectangle extends Figure{
    //Class Attributes
    private float left;
    private float top;
    private float right;
    private float bottom;
    /**
     * Class Constructor
     * @param _left Define the position of the rectangle from the left
     * @param _top Define the position of the rectangle from above
     * @param _right Define the position of the rectangle from the right
     * @param _bottom Define the position of the rectangle from the below
     * @param _colour Define colour of the figure
     * @param _paint Define the style and plotted to draw the figure*/
    public Rectangle(float _left, float _top, float _right, float _bottom , Paint _paint, int[] _colour){
        super(_paint,_colour);
        left = _left;
        top = _top;
        right = _right;
        bottom = _bottom;
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
     * Method toString show class content
     * @param format Define the writing format*
     * @return content of the class*/
    public String toString(String format){
        switch (format){
            case "json":
                return "{\"id\":4,\"l\"="+this.getLeft()+",\"t\"="+this.getTop()+",\"r\"="+this.getRight()+",\"b\"="+this.getBottom()+"}";
            case "xml":
                return "Implement Format XML";
            default:
                return "Format no exits";
        }
    }//End Method toString

    public JSONObject getJSONFigure(int id) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id",id);
        object.put("type",3);
        object.put("left",this.left);
        object.put("top",this.top);
        object.put("right",this.right);
        object.put("bottom",this.bottom);
        object.put("comment",this.getDescription());
        object.put("r",this.getColour()[0]);
        object.put("g",this.getColour()[1]);
        object.put("b",this.getColour()[2]);
        object.put("colorName",null);
        return object;
    }
}