package com.example.dashboard.Models;

import org.json.JSONException;
import org.json.JSONObject;

import ListFigures.ListFigure;
import ListFigures.ListSegmentation;

public class FigureModel {
    private float imageX;
    private float imagey;
    private ProfileItem profileItem;
    private int landmarksNumber;


    public JSONObject setJsonObjectSegmentation(ListSegmentation myListSegmentation) throws JSONException {
        JSONObject landMark = new JSONObject();
        landMark.put("imageX",myListSegmentation.getGeneralWidth());
        landMark.put("imagey",myListSegmentation.getGeneralHeight());
        landMark.put("profileItems",null);
        landMark.put("landmarksNumber",0);
        landMark.put("landmarks",myListSegmentation.dataSegments());
        landMark.put("landmarksCreated",myListSegmentation.getSegmentation().size());
        landMark.put("profileName","Edwin");
        landMark.put("imageName","image_rx_jpg");
        return landMark;
    }

    public JSONObject setJsonObjectFigures(ListFigure myListFigure) throws JSONException {
        JSONObject landMark = new JSONObject();
        landMark.put("imageX", myListFigure.getGeneralWidth());
        landMark.put("imagey", myListFigure.getGeneralHeight());
        landMark.put("profileItems",null);
        landMark.put("figures", myListFigure.dataFigures());
        landMark.put("figuresCreated",myListFigure.getMyFigures().size());
        landMark.put("profileName","Edwin");
        landMark.put("imageName","image_rx_jpg");
        return landMark;
    }

}
