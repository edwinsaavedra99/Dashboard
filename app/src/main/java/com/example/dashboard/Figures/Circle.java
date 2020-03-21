package com.example.dashboard.Figures;
//Imports
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

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
     * @return content of the class*/
    public String toString(int id) throws JSONException {
        return  getJSONFigure(id).toString();

    }//End Method toString

    public JSONObject getJSON(int id) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id",id);
        object.put("x",this.centerX);
        object.put("y",this.centerY);
        object.put("radius",this.radius);
        object.put("comment",this.getDescription());
        object.put("r",this.getColour()[0]);
        object.put("g",this.getColour()[1]);
        object.put("b",this.getColour()[2]);
        object.put("colorName",null);
        return object;
    }

    public JSONObject getJSONFigure(int id) throws JSONException {
        JSONObject object = getJSON(id);
        object.put("type",1);
        return object;
    }
}//Close Class
