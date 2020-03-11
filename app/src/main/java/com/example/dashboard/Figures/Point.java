package com.example.dashboard.Figures;
//Imports
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject getJSONFigure(int id) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id",id);
        object.put("type",5);
        object.put("x",this.centerX);
        object.put("y",this.centerY);
        object.put("comment",this.getDescription());
        object.put("r",this.getColour()[0]);
        object.put("g",this.getColour()[1]);
        object.put("b",this.getColour()[2]);
        object.put("colorName",null);
        return object;
    }

}//Close Class
