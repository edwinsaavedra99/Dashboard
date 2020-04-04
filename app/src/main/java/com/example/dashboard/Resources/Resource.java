package com.example.dashboard.Resources;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import org.json.JSONObject;

public class Resource {
    public static String uriImageResource;
    public static String emailUserLogin;
    public static String nameUserLogin;
    public static String urlImageUserLogin;
    public static int role;
    public static String idCarpeta;
    public static int idFile;
    public static int idPacient;
    public static boolean openFile=false;
    public static boolean openShareFile = false;
    public static String privilegeFile;
    public static String emailSharedFrom;
    public static String nameFile;
    public static String descriptionFile;
    public static String dateFile;
    public static String residecyPatient;
    public static int agePatient;
    public static int genderPatient;
    public static JSONObject infoStudy;
    public static JSONObject infoMedicine;
    public static GoogleSignInClient SignInClient;
    public static boolean changeSaveFigures = false;
}
