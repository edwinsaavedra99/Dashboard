package com.example.dashboard.Resources;

import org.json.JSONObject;

public class Resource {

    public static String uriImageResource = null;   // para comunicar activity

    public static String emailUserLogin;
    public static String nameUserLogin;
    public static String urlImageUserLogin;

    public static int role;


    public static String idCarpeta = "unsa";
    public static int idFile = 0;
    public static int idPacient;

    public static boolean openFile=false;
    public static boolean openShareFile = false;
    public static String emailSharedFrom ;
    public static String nameFile;
    public static String descriptionFile;
    public static String dateFile;

    public static JSONObject infoStudy;
    public static JSONObject infoMedicine;
}
