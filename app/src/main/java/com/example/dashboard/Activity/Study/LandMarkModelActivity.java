package com.example.dashboard.Activity.Study;
//Imports
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashboard.Activity.Doctor.FiguresModelActivity;
import com.example.dashboard.Animations.MyAnimation;
import com.example.dashboard.Filters.GroupFilters;
import com.example.dashboard.Filters.MyFilters;
import com.example.dashboard.ListFigures.MemoryFigure;
import com.example.dashboard.Resources.Resource;
import com.example.dashboard.Utils.ControlMenu;
import com.example.dashboard.R;
import com.example.dashboard.Utils.StringUtil;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.example.dashboard.Figures.Circle;
import com.example.dashboard.ListFigures.ListFigure;
import com.example.dashboard.ListFigures.ListSegmentation;
import com.example.dashboard.ListFigures.Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class LandMarkModelActivity extends AppCompatActivity {
    public static int TIME_ANIMATION = 100;
    public static float SCALE_ANIMATION = 1.1f;
    //Class Attributes--
    private Uri imageurl;
    private ImageView galery;
    private String currentPhotoPath;
    //Load and Save data
    //Insert com.example.dashboard.Figures
    private ImageView saveData;
    private ImageView syncData;
    private ImageView infoFigures;
    private ImageView sendData;
    private int[] colour = {183, 149, 11};
    //Zoom Image
    private ImageView extendsImage;
    //Filters
    private ImageView filterImage;
    private ImageView addPointSegment;
    private ImageView pencilSegment;
    //Layout for Image RX
    private LinearLayout layoutImageRx1;
    //View
    private ListSegmentation myListSegmentation;
    //Animation
    private MyAnimation Animation;
    //Flags
    private boolean flagAnimation;
    private boolean flagAnimationColors;
    private boolean flagSegmentation;
    private boolean flagEraserSegmentation;
    private boolean flagPointSegmentation;
    private boolean flagPencilSegmentation;
    private boolean flagPreviewSegmentation;
    private boolean flagHttp;
    private boolean flagVariant;
    private boolean flagBorders;
    private boolean flagCalor;
    private boolean flagColors;
    private boolean flagControl;
    private boolean flagAristas;
    //Scroll
    private LinearLayout menu_left;
    private LinearLayout menu_right;
    private LinearLayout contenedor_description;
    private LinearLayout getFigures;
    private LinearLayout backFilters;
    private ScrollView scrollLeft2;
    private ScrollView scrollLeft3;
    private ScrollView scrollRight1;
    private ScrollView scrollRight2;
    //Back and Checks
    private ImageView backMenuRight; //back menu right
    //Segments
    private ImageView deleteSegments;
    private ImageView changeColorSegments;
    private LinearLayout zoomImageLayout;
    private ImageView test1;
    private ImageView clearSegments;
    private ImageView eraserSegments;
    private ImageView preview;
    private ImageView openSegments;
    private ImageView controlMenu;
    private CardView cardView;
    //Colors
    private ArrayList<ImageView> listColors;
    private int colorIndex;
    private ImageView color1;
    private ImageView color2;
    private ImageView color3;
    private ImageView color4;
    private ImageView color5;
    private ImageView color6;
    private ImageView color7;
    private ImageView color8;
    private ImageView color9;
    private ImageView color10;
    private ImageView color11;
    private ImageView color12;
    private ImageView color13;
    private ImageView color14;
    private ImageView color15;
    private ImageView color16;
    private ImageView color17;
    private ImageView color18;
    private ImageView color19;
    private ImageView color20;
    private ImageView color21;
    private ImageView color22;
    private ImageView color23;
    private ImageView color24;
    private ImageView color25;
    private ImageView iconColors;
    //Filters
    private MyFilters myFilters;
    private ImageView openCv;
    private ImageView openCv1;
    private ImageView openCv2;
    private ImageView openCv3;
    private ImageView openCv4;
    private ImageView openCv5;
    private ImageView openCv6;
    private ImageView openCv7;
    private ImageView openCv8;
    private ImageView openCv9;
    private ImageView openCv10;
    private ImageView openCv11;
    private ImageView openCv12;
    private ImageView openCv13;
    private ImageView openCv14;
    private ImageView openCv15;
    private ImageView openCv16;
    private ImageView openCv17;
    private ImageView openCv18;
    private ImageView openCv19;
    private ImageView openCv20;
    private ImageView openCv21;
    private ImageView openCv22;
    private ImageView openCv23;
    private ImageView openCv24;
    private ImageView openCv25;
    private ImageView openCv26;
    private ImageView openCv27;
    private ImageView openCv28;
    private ImageView getOpenCvHttp1;
    private ImageView getOpenCvHttp2;
    private ImageView getOpenCvHttp3;
    private ImageView getOpenCvHttp4;
    private ImageView openCvHttp;
    private ImageView groupVariantOpenCv;
    private ImageView groupBordersOpenCv;
    private ImageView groupCalorOpenCv;
    private ImageView groupColorsOpenCv;
    private ImageView normalOpencv;
    private ImageView normalOpencv1;
    private ImageView normalOpencv2;
    private ImageView normalOpencv3;
    //Control
    private LinearLayout control;
    private ImageView sortUp;
    private ImageView sortDown;
    private ImageView sortLeft;
    private ImageView sortRight;
    private ImageView touchControl;
    private ImageView allSortUp;
    private ImageView allSortDown;
    private ImageView allSortLeft;
    private ImageView allSortRight;
    private ImageView controlZ;
    private ImageView controlY;
    private ImageView closeControl;
    private ImageView aristas;
    //img original format Bitmap
    private int codHttpFilter = 0;
    private Bitmap original;
    private Bitmap auxOriginal = null;
    private Bitmap auxOriginal1 = null;
    private Bitmap auxOriginal2 = null;
    private Bitmap auxOriginal3 = null;
    private Mat img;
    private int mActivePointerId = INVALID_POINTER_ID;
    private AlertDialog dialog;
    private String nameFileGlobal="";
    private String descriptionFileGlobal="";
    private TextView textSave;
    private TextView txtsyncData;
    private boolean changeSave = false;


    private String archivo = "test";
    private String carpeta = "/dataAFD/";
    String contenido;
    File file;
    String file_path = "";
    EditText texto;
    String name = "";
    File localFile;
    //--End Attributes of class
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_mark_model);

        //Load OpenCV
        OpenCVLoader.initDebug();
        //Initializing Properties
        initialProperties();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.file_path = (Environment.getExternalStorageDirectory()+carpeta);
        localFile = new File(this.file_path);
        if(!localFile.exists()){
            localFile.mkdirs();
        }
        this.name = (this.archivo+".json");
        this.file = new File(localFile,this.name);
        try{
            this.file.createNewFile();

        }catch (IOException e){
            e.printStackTrace();
        }
        if(file.exists()){
            //leer
        }
        infoFigures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myListSegmentation.isSelectedFigure())
                    showInfoDialog(false);
                else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "Please selected figure ", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        closeControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListSegmentation.deleteMemory();
            }
        });
        controlZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListSegmentation.controlZ();
            }
        });
        controlY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListSegmentation.controlY();
            }
        });
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        aristas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagAristas){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Select two point, Please",Toast.LENGTH_SHORT);
                    toast.show();
                    Animation.animationScale(aristas,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                    aristas.setColorFilter(Color.rgb(255, 127, 80));
                    eraserSegments.setColorFilter(Color.rgb(255, 255, 255));
                    addPointSegment.setColorFilter(Color.rgb(255, 255, 255));
                    extendsImage.setColorFilter(Color.rgb(255, 255, 255));
                    pencilSegment.setColorFilter(Color.rgb(255, 255, 255));
                    myListSegmentation.changeModeTouch(5);
                    flagAristas = true;
                }else{
                    aristas.setColorFilter(Color.rgb(255, 255, 255));
                    myListSegmentation.changeModeTouch(0);
                    flagAristas = false;
                }
                myListSegmentation.newEdge();
            }
        });
        controlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editorlecture
                if(Resource.privilegeFile.equals("edit")){
                    if(!flagControl){
                        Animation.animationScale(controlMenu, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                        controlMenu.setColorFilter(Color.rgb(255, 127, 80));
                        control.setVisibility(View.VISIBLE);
                        flagControl = true;
                    }else{
                        control.setVisibility(View.GONE);
                        controlMenu.setColorFilter(Color.rgb(255, 255, 255));
                        flagControl = false;
                    }
                    myListSegmentation.invalidate();
                    myListSegmentation.getZoomList().invalidate();
                }else{
                    Toast.makeText(getApplicationContext(), "Read Only", Toast.LENGTH_SHORT).show();

                }
            }
        });
        allSortUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.sortMoveControl(5)){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        allSortLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.sortMoveControl(6)){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        allSortRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.sortMoveControl(7)){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        allSortDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.sortMoveControl(8)){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        sortUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.sortMoveControl(1)){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        sortDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.sortMoveControl(4)){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        sortRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.sortMoveControl(3)){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        sortLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.sortMoveControl(2)){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        touchControl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                float getX = event.getX();
                float getY = event.getY();
                float getPastX=0;
                float getPastY=0;
                final int acct = event.getActionMasked();
                switch (acct) {
                    case MotionEvent.ACTION_DOWN:{
                        final int pointerIndex = event.getActionIndex();
                        mActivePointerId = event.getPointerId(0);
                        getPastX = getX;
                        getPastY = getY;
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        final int pointerIndex = event.findPointerIndex(mActivePointerId);
                        getX = event.getX(pointerIndex);
                        getY = event.getY(pointerIndex);
                        if((control.getTranslationX()-(getPastX-getX))-40>=0 && (control.getTranslationX()-(getPastX-getX))+((int)(getResources().getDimension(R.dimen.translate_control)))<=metrics.widthPixels){
                            control.setTranslationX((control.getTranslationX()-(getPastX-getX))-40);
                        }
                        if((control.getTranslationY()-(getPastY-getY))+((int)(getResources().getDimension(R.dimen.translate_control)))<= metrics.heightPixels && (control.getTranslationY()-(getPastY-getY))-40>=0){
                            control.setTranslationY((control.getTranslationY()-(getPastY-getY))-40);
                        }
                        getPastX = getX;
                        getPastY = getY;

                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        mActivePointerId = INVALID_POINTER_ID;
                        break;
                    }
                    case MotionEvent.ACTION_POINTER_UP: {
                        final int pointerIndex = event.getActionIndex();
                        final int pointerId = event.getPointerId(pointerIndex);
                        if (pointerId == mActivePointerId) {
                            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                            getPastX = event.getX(newPointerIndex);
                            getPastY = event.getY( newPointerIndex);
                            mActivePointerId = event.getPointerId(newPointerIndex);
                        }
                        break;
                    }
                }
                return true;
            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(saveData,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                showSaveDialog();
            }
        });
        preview=findViewById(R.id.preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagPreviewSegmentation) {
                    Animation.animationScale(preview, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                    preview.setColorFilter(Color.rgb(255, 127, 80));
                    myListSegmentation.getFlagPreview();
                    myListSegmentation.invalidate();
                    flagPreviewSegmentation = true;
                }else{
                    preview.setColorFilter(Color.rgb(255, 255, 255));
                    myListSegmentation.getFlagPreview();
                    myListSegmentation.invalidate();
                    flagPreviewSegmentation = false;
                }

            }
        });


        clearSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Resource.privilegeFile.equals("edit")){
                    myListSegmentation.clearList();
                    myListSegmentation.getCardView().setVisibility(View.GONE);
                }else{
                    Toast.makeText(getApplicationContext(), "Read Only", Toast.LENGTH_SHORT).show();
                }
                Animation.animationScale(clearSegments,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);

            }
        });
        eraserSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Resource.privilegeFile.equals("edit")){
                    if(!flagEraserSegmentation) {
                        Animation.animationScale(eraserSegments, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                        eraserSegments.setColorFilter(Color.rgb(255, 127, 80));
                        addPointSegment.setColorFilter(Color.rgb(255, 255, 255));
                        extendsImage.setColorFilter(Color.rgb(255, 255, 255));
                        pencilSegment.setColorFilter(Color.rgb(255, 255, 255));
                        aristas.setColorFilter(Color.rgb(255, 255, 255));
                        myListSegmentation.changeModeTouch(2);
                        flagEraserSegmentation = true;
                    }else{
                        eraserSegments.setColorFilter(Color.rgb(255, 255, 255));
                        myListSegmentation.changeModeTouch(0);
                        flagEraserSegmentation = false;
                    }
                }else{
                    Animation.animationScale(eraserSegments, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                    Toast.makeText(getApplicationContext(), "Read Only", Toast.LENGTH_SHORT).show();
                }

            }
        });
        addPointSegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Resource.privilegeFile.equals("edit")){
                    int modeTouch = myListSegmentation.getModeTouch();
                    if(!flagPointSegmentation) {
                        Animation.animationScale(addPointSegment, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                        eraserSegments.setColorFilter(Color.rgb(255, 255, 255));
                        pencilSegment.setColorFilter(Color.rgb(255, 255, 255));
                        extendsImage.setColorFilter(Color.rgb(255, 255, 255));
                        addPointSegment.setColorFilter(Color.rgb(255, 127, 80));
                        aristas.setColorFilter(Color.rgb(255, 255, 255));
                        myListSegmentation.changeModeTouch(3);
                        flagPointSegmentation = true;
                    }else{
                        addPointSegment.setColorFilter(Color.rgb(255, 255, 255));
                        myListSegmentation.changeModeTouch(0);
                        flagPointSegmentation = false;
                    }
                }else{
                    Animation.animationScale(addPointSegment, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                    Toast.makeText(getApplicationContext(), "Read Only", Toast.LENGTH_SHORT).show();
                }

            }
        });

        pencilSegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Resource.privilegeFile.equals("edit")){
                    int modeTouch = myListSegmentation.getModeTouch();
                    if(!flagPencilSegmentation) {
                        Animation.animationScale(pencilSegment, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                        eraserSegments.setColorFilter(Color.rgb(255, 255, 255));
                        addPointSegment.setColorFilter(Color.rgb(255, 255, 255));
                        extendsImage.setColorFilter(Color.rgb(255, 255, 255));
                        pencilSegment.setColorFilter(Color.rgb(255, 127, 80));
                        aristas.setColorFilter(Color.rgb(255, 255, 255));
                        myListSegmentation.changeModeTouch(4);
                        flagPencilSegmentation = true;
                    }else{
                        pencilSegment.setColorFilter(Color.rgb(255, 255, 255));
                        myListSegmentation.changeModeTouch(0);
                        flagPencilSegmentation = false;
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Read Only", Toast.LENGTH_SHORT).show();
                    Animation.animationScale(pencilSegment, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                }

            }
        });
        //back menu right
        backFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(backMenuRight,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                filterImage.setColorFilter(Color.rgb(255,255,255));
                scrollRight2.setVisibility(View.GONE);
                backFilters.setVisibility(View.GONE);
                scrollRight1.setVisibility(View.VISIBLE);
                test1.setVisibility(View.VISIBLE);
                notViewFilters();
                flagVariant = false;
                flagBorders = false;
                flagHttp = false;
                flagCalor = false;
                flagColors = false;
            }
        });
        //Add Filter Image
        filterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1.setVisibility(View.GONE);
                Animation.animationScale(filterImage,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                filterImage.setColorFilter(Color.rgb(255,127,80));
                scrollRight1.setVisibility(View.GONE);
                scrollRight2.setVisibility(View.VISIBLE);
                backFilters.setVisibility(View.VISIBLE);
                notViewFilters();
                flagVariant = false;
                flagHttp = false;
                flagBorders = false;
                flagCalor = false;
                flagColors = false;
            }
        });
        //Zoom Image RX
        extendsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(extendsImage,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                if(myListSegmentation.getModeTouch()==0 || myListSegmentation.getModeTouch()==2
                        || myListSegmentation.getModeTouch()==3 || myListSegmentation.getModeTouch()==4
                        ||myListSegmentation.getModeTouch() ==5) {
                    eraserSegments.setColorFilter(Color.rgb(255, 255, 255)); //white
                    pencilSegment.setColorFilter(Color.rgb(255, 255, 255)); //white
                    addPointSegment.setColorFilter(Color.rgb(255, 255, 255)); //white
                    aristas.setColorFilter(Color.rgb(255, 255, 255)); //white
                    extendsImage.setColorFilter(Color.rgb(255, 127, 80)); //orange
                    myListSegmentation.changeModeTouch(1);
                }else{
                    extendsImage.setColorFilter(Color.rgb(255, 255, 255)); //white
                    myListSegmentation.changeModeTouch(0);
                }
            }
        });

        //Ocultar menu de Change colors
        iconColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f);
                animation1.setDuration(750);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(android.view.animation.Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(android.view.animation.Animation animation) {
                        scrollLeft3.setVisibility(View.GONE);
                        scrollLeft2.setVisibility(View.VISIBLE);
                        getFigures.setVisibility(View.GONE);
                        Animation animation2 = new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
                        animation2.setDuration(550);
                        scrollLeft2.startAnimation(animation2);
                    }
                    @Override
                    public void onAnimationRepeat(android.view.animation.Animation animation) {
                    }
                });
                scrollLeft3.startAnimation(animation1);
                flagAnimationColors=true;
            }
        });
        //change color all segments
        changeColorSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(changeColorSegments,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                Animation animation1 = new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f);
                animation1.setDuration(550);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(android.view.animation.Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(android.view.animation.Animation animation) {
                        Animation animation2 = new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
                        animation2.setDuration(550);
                        scrollLeft3.startAnimation(animation2);
                        scrollLeft2.setVisibility(View.GONE);
                        scrollLeft3.setVisibility(View.VISIBLE);
                        getFigures.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationRepeat(android.view.animation.Animation animation) {
                    }
                });
                flagAnimationColors=false;
                scrollLeft2.startAnimation(animation1);
                Toast toast;

            }
        });

        openSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openService();
            }
        });

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(0);
            }
        });
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(1);
            }
        });
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(2);
            }
        });
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(3);
            }
        });
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(4);
            }
        });
        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(5);
            }
        });
        color7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(6);
            }
        });
        color8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(7);
            }
        });
        color9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(8);
            }
        });
        color10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(9);
            }
        });
        color11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(10);
            }
        });
        color12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(11);
            }
        });
        color13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(12);
            }
        });
        color14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(13);
            }
        });
        color15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(14);
            }
        });
        color16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(15);
            }
        });
        color17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(16);
            }
        });
        color18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(17);
            }
        });
        color19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(18);
            }
        });
        color20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(19);
            }
        });
        color21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(20);
            }
        });
        color22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(21);
            }
        });
        color23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(22);
            }
        });
        color24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(23);
            }
        });
        color25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(24);
            }
        });

        galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent gallery = new Intent();
                    gallery.setType("image/*");
                    gallery.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gallery, "Select picture"), 4);
                    Toast.makeText(getApplicationContext(), "image from galery", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    Toast.makeText(getApplicationContext(),"Ups :)",Toast.LENGTH_SHORT).show();

                }

            }
        });
        //Delete the after segment
        deleteSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Resource.privilegeFile.equals("edit")){
                    Animation.animationScale(deleteSegments,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                    Toast toast;
                    if(myListSegmentation.after()) {
                        toast = Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT);
                        if(myListSegmentation.getFigureSelected()>-1 && !myListSegmentation.getSegmentation().isEmpty()) {
                            Circle a = (Circle) myListSegmentation.getSegmentation().get(myListSegmentation.getFigureSelected());
                            Circle a1 = (Circle) myListSegmentation.getZoomList().getSegmentation().get(myListSegmentation.getZoomList().getFigureSelected());
                            myListSegmentation.getViewZoom().setPivotY((a.getCenterY()-myListSegmentation.getmPositionY()) / myListSegmentation.getmScaleFactor() * myListSegmentation.getViewZoom().getHeight() / myListSegmentation.getHeight());
                            myListSegmentation.getViewZoom().setPivotX((a.getCenterX()-myListSegmentation.getmPositionX()) / myListSegmentation.getmScaleFactor() * myListSegmentation.getViewZoom().getWidth() / myListSegmentation.getWidth());
                        }else{
                            myListSegmentation.getCardView().setVisibility(View.GONE);
                        }
                    }else {
                        toast = Toast.makeText(getApplicationContext(), "Isn't Selected Figure", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }else{
                    Toast.makeText(getApplicationContext(), "Read Only", Toast.LENGTH_SHORT).show();
                    Animation.animationScale(deleteSegments,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                }

            }
        });

        //Layout forget touch
        zoomImageLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //Filter canny borders
        openCv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterCanny());
            }
        });
        //Filter RGB is image original
        openCv3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                addFilter(myFilters.filterRGB());
            }
        });
        normalOpencv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                addFilter(myFilters.filterRGB());
            }
        });
        normalOpencv1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                addFilter(myFilters.filterRGB());
            }
        });
        normalOpencv2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                addFilter(myFilters.filterRGB());
            }
        });
        normalOpencv3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                addFilter(myFilters.filterRGB());
            }
        });
        //Filter morph
        openCv2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterMorph());
            }
        });
        //Filter SEPIA
        openCv1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filerSepia());
            }
        });
        //filter summer
        openCv4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(6));
            }
        });
        //filter pink
        openCv5.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(10));
            }
        });
        //filter reduce colors gray
        openCv6.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterReduceColorsGray(5));
            }
        });
        //filters reduce Colors
        openCv7.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterReduceColors(80,15,10));
            }
        });
        //filter Pencil
        openCv8.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterPencil());
            }
        });
        //filter Carton
        openCv9.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterCarton(80,15,10));
            }
        });
        //filter R
        openCv10.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(0));
            }
        });
        openCv11.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(1));
            }
        });
        openCv12.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(2));
            }
        });
        openCv13.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(3));
            }
        });
        openCv14.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(4));
            }
        });
        openCv15.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(5));
            }
        });
        openCv16.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(7));
            }
        });
        openCv17.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(8));
            }
        });
        openCv18.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(9));
            }
        });
        openCv19.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(11));
            }
        });
        openCv20.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(12));
            }
        });
        openCv21.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(13));
            }
        });
        openCv22.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(14));
            }
        });
        openCv23.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(15));
            }
        });
        openCv24.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(16));
            }
        });
        openCv25.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(17));
            }
        });
        openCv26.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(18));
            }
        });
        openCv27.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(19));
            }
        });
        openCv28.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                addFilter(myFilters.filterColor(20));
            }
        });
        getOpenCvHttp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auxOriginal == null){
                    getFilterService(myListSegmentation.getBase64String(),"clahe",1);
                }else{
                    addFilter(auxOriginal);
                }
            }
        });
        getOpenCvHttp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auxOriginal1 == null) {
                    getFilterService(myListSegmentation.getBase64String(), "clahegh",2);
                }else{
                    addFilter(auxOriginal1);
                }
            }
        });
        getOpenCvHttp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auxOriginal2 == null) {
                    getFilterService(myListSegmentation.getBase64String(), "guidedfilter",3);
                }else{
                    addFilter(auxOriginal2);
                }
            }
        });
        getOpenCvHttp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auxOriginal3 == null) {
                    getFilterService(myListSegmentation.getBase64String(), "wiener",4);
                }else{
                    addFilter(auxOriginal3);
                }
            }
        });
        openCvHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addFilter(auxOriginal);
                notViewFilters();
                if(!flagHttp) {
                    getOpenCvHttp1.setVisibility(View.VISIBLE);
                    getOpenCvHttp2.setVisibility(View.VISIBLE);
                    getOpenCvHttp3.setVisibility(View.VISIBLE);
                    getOpenCvHttp4.setVisibility(View.VISIBLE);
                    normalOpencv3.setVisibility(View.VISIBLE);
                    flagBorders = false;
                    flagCalor = false;
                    flagColors = false;
                    flagVariant = false;
                }
                flagHttp = !flagHttp;
            }
        });
        groupVariantOpenCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notViewFilters();
                if(!flagVariant) {
                    openCv1.setVisibility(View.VISIBLE);
                    openCv3.setVisibility(View.VISIBLE);
                    openCv6.setVisibility(View.VISIBLE);
                    openCv7.setVisibility(View.VISIBLE);
                    openCv11.setVisibility(View.VISIBLE);
                    openCv15.setVisibility(View.VISIBLE);
                    flagBorders = false;
                    flagCalor = false;
                    flagColors = false;
                }
                flagVariant = !flagVariant;

            }
        });

        groupBordersOpenCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notViewFilters();
                if(!flagBorders) {
                    openCv.setVisibility(View.VISIBLE);
                    openCv2.setVisibility(View.VISIBLE);
                    openCv8.setVisibility(View.VISIBLE);
                    openCv9.setVisibility(View.VISIBLE);
                    normalOpencv.setVisibility(View.VISIBLE);
                    flagVariant = false;
                    flagCalor = false;
                    flagColors = false;
                }
                flagBorders = !flagBorders;
            }
        });

        groupCalorOpenCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notViewFilters();
                if(!flagCalor) {
                    openCv12.setVisibility(View.VISIBLE);
                    openCv14.setVisibility(View.VISIBLE);
                    openCv18.setVisibility(View.VISIBLE);
                    openCv28.setVisibility(View.VISIBLE);
                    normalOpencv1.setVisibility(View.VISIBLE);
                    flagVariant = false;
                    flagBorders = false;
                    flagColors = false;
                }
                flagCalor = !flagCalor;
            }
        });
        groupColorsOpenCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notViewFilters();
                if(!flagColors) {
                    openCv4.setVisibility(View.VISIBLE);
                    openCv5.setVisibility(View.VISIBLE);
                    openCv10.setVisibility(View.VISIBLE);
                    openCv13.setVisibility(View.VISIBLE);
                    openCv16.setVisibility(View.VISIBLE);
                    openCv17.setVisibility(View.VISIBLE);
                    openCv19.setVisibility(View.VISIBLE);
                    openCv20.setVisibility(View.VISIBLE);
                    openCv21.setVisibility(View.VISIBLE);
                    openCv22.setVisibility(View.VISIBLE);
                    openCv23.setVisibility(View.VISIBLE);
                    openCv24.setVisibility(View.VISIBLE);
                    openCv25.setVisibility(View.VISIBLE);
                    openCv26.setVisibility(View.VISIBLE);
                    openCv27.setVisibility(View.VISIBLE);
                    normalOpencv2.setVisibility(View.VISIBLE);
                    flagVariant = false;
                    flagBorders = false;
                    flagCalor = false;
                }
                flagColors = !flagColors;
            }
        });
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagAnimation) {
                    test1.animate().setDuration(200).scaleX(1.2f).scaleY(1.2f);
                    test1.setColorFilter(Color.rgb(255, 127, 80)); //ORANGE
                    Animation animation1 = new TranslateAnimation(2, 0.0f, 2, 1.0f, 2, 0.0f, 2, 0.0f);
                    animation1.setDuration(450);
                    menu_right.startAnimation(animation1);
                    Animation animation2 = new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f);
                    animation2.setDuration(450);
                    menu_left.startAnimation(animation2);
                    if (scrollLeft3.getVisibility() == View.VISIBLE){
                        getFigures.startAnimation(animation2);
                        getFigures.setVisibility(View.GONE);
                    }
                    menu_right.setVisibility(View.GONE);
                    menu_left.setVisibility(View.GONE);
                    flagAnimation = false;
                }else{
                    test1.animate().setDuration(200).scaleX(1.0f).scaleY(1.0f);
                    test1.setColorFilter(Color.rgb(255, 255, 255)); //WHITE
                    Animation animation1 = new TranslateAnimation(2, 10.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
                    animation1.setDuration(350);
                    menu_right.startAnimation(animation1);
                    Animation animation2 = new TranslateAnimation(2, -10.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
                    animation2.setDuration(350);
                    if (scrollLeft3.getVisibility() == View.VISIBLE){
                        getFigures.startAnimation(animation2);
                        getFigures.setVisibility(View.VISIBLE);
                    }
                    menu_left.startAnimation(animation2);
                    menu_right.setVisibility(View.VISIBLE);
                    menu_left.setVisibility(View.VISIBLE);
                    flagAnimation = true;
                }
            }
        });
      /*  share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });*/
        syncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openService();
            }
        });
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLandMarks();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addFilter(Bitmap d){
        Drawable drawable = new BitmapDrawable(getResources(),d);
        myListSegmentation.loadImage(d);
    }
    @SuppressLint("ShowToast")
    private void changeColor(int _color){
        if(Resource.privilegeFile.equals("edit")){
            Toast toast=Toast.makeText(getApplicationContext(),"Isn't Selected Figure",Toast.LENGTH_SHORT);
            if( myListSegmentation.changeColour(Util.getCollections()[_color]))
                toast = Toast.makeText(getApplicationContext(),"Change Successfully in Segments",Toast.LENGTH_SHORT);
            toast.show();
        }else{
            Toast.makeText(getApplicationContext(), "Read Only", Toast.LENGTH_SHORT).show();
        }
    }

    public void notViewFilters(){
        openCv.setVisibility(View.GONE);
        openCv1.setVisibility(View.GONE);
        openCv2.setVisibility(View.GONE);
        openCv3.setVisibility(View.GONE);
        openCv4.setVisibility(View.GONE);
        openCv5.setVisibility(View.GONE);
        openCv6.setVisibility(View.GONE);
        openCv7.setVisibility(View.GONE);
        openCv8.setVisibility(View.GONE);
        openCv9.setVisibility(View.GONE);
        openCv10.setVisibility(View.GONE);
        openCv11.setVisibility(View.GONE);
        openCv12.setVisibility(View.GONE);
        openCv13.setVisibility(View.GONE);
        openCv14.setVisibility(View.GONE);
        openCv15.setVisibility(View.GONE);
        openCv16.setVisibility(View.GONE);
        openCv17.setVisibility(View.GONE);
        openCv18.setVisibility(View.GONE);
        openCv19.setVisibility(View.GONE);
        openCv20.setVisibility(View.GONE);
        openCv21.setVisibility(View.GONE);
        openCv22.setVisibility(View.GONE);
        openCv23.setVisibility(View.GONE);
        openCv24.setVisibility(View.GONE);
        openCv25.setVisibility(View.GONE);
        openCv26.setVisibility(View.GONE);
        openCv27.setVisibility(View.GONE);
        openCv28.setVisibility(View.GONE);
        normalOpencv.setVisibility(View.GONE);
        normalOpencv1.setVisibility(View.GONE);
        normalOpencv2.setVisibility(View.GONE);
        normalOpencv3.setVisibility(View.GONE);
        getOpenCvHttp1.setVisibility(View.GONE);
        getOpenCvHttp2.setVisibility(View.GONE);
        getOpenCvHttp3.setVisibility(View.GONE);
        getOpenCvHttp4.setVisibility(View.GONE);
    }

    private void showSaveDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SAVE: ");
        dialog.setMessage("Please insert Name and Description");
        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") View login_layout = inflater.inflate(R.layout.layout_save_figure,null);
        final TextInputEditText nameDescription = login_layout.findViewById(R.id.txt_nameProject);
        final TextInputEditText editDescription = login_layout.findViewById(R.id.txt_descriptionProject);
        final RadioGroup gGroup = login_layout.findViewById(R.id.methodSave);
        nameDescription.setText(nameFileGlobal);
        editDescription.setText(descriptionFileGlobal);
        dialog.setView(login_layout);
        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameDescription.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                if(name.length() == 0 || name.contains("@")){
                    nameDescription.setError("Error ...");
                    nameDescription.requestFocus();
                }else {

                    if(gGroup.getCheckedRadioButtonId() == R.id.radioInternet){
                        saveLandMarks(name,description);
                        nameFileGlobal = name;
                        descriptionFileGlobal = description;
                    }else if(gGroup.getCheckedRadioButtonId() == R.id.radioLocal){
                        saveLocal(description,name);
                        nameFileGlobal = name;
                        descriptionFileGlobal = description;
                    }
                }
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showInfoDialog(final boolean flagSegmentation) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("INFO FIGURE: ");
        dialog.setMessage("Please description figure");
        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_info_figure,null);
        final TextInputEditText editDescription = login_layout.findViewById(R.id.txt_description);
        editDescription.setText(myListSegmentation.getDescriptionFigure());
        dialog.setView(login_layout);
        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(Resource.privilegeFile.equals("edit")){
                    Toast toast;
                    if (myListSegmentation.setDescriptionFigure(editDescription.getText().toString()))
                        toast = Toast.makeText(getApplicationContext(), "Save Successfully", Toast.LENGTH_SHORT);
                    else
                        toast = Toast.makeText(getApplicationContext(), "No Save ", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast.makeText(getApplicationContext(), "Read Only", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void sendLandMarks(){
        filtersWithProgressBar();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            String infoDate="";
            postdata.put("image", myListSegmentation.getBase64String());
            postdata.put("email",Resource.emailSharedFrom);
            postdata.put("project",Resource.idCarpeta);
            JSONObject posdate123 = new JSONObject();
            posdate123.put("imageX",myListSegmentation.getGeneralWidth());
            posdate123.put("imagey",myListSegmentation.getGeneralHeight());
            posdate123.put("profileItems",null);
            posdate123.put("landmarks",myListSegmentation.dataSegments());
            postdata.put("name",Resource.nameFile);
            postdata.put("description",Resource.descriptionFile);
            postdata.put("date",Resource.dateFile);
            postdata.put("information",posdate123);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"landmark") /*URL ... INDEX Pc DE WILMER*/
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Wilmer ERROR :" + e);
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("**************RESPUESTA ****************");
                    System.out.println(responseData);
                    System.out.println("**************RESPUESTA ****************");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(responseData.equals("error")){
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                            }else{
                                MemoryFigure.changeSave = true;
                                Toast.makeText(getApplicationContext(),"Send Successfully",Toast.LENGTH_SHORT).show();
                                if(Resource.openFile){
                                    System.out.println("openFile");
                                }else if(Resource.openShareFile){
                                    System.out.println("openshareFile");
                                }else{
                                    //txtsyncData.setVisibility(View.VISIBLE);
                                    //syncData.setVisibility(View.VISIBLE);
                                }
                            }
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
    }
    public void saveLocal(final String description,final String name){
        JSONObject postdata = new JSONObject();
        try {
            this.archivo = name;
            this.name = (this.archivo+".json");
            this.file = new File(localFile,this.name);
            try{
                this.file.createNewFile();

            }catch (IOException e){
                e.printStackTrace();
            }
            if(file.exists()){
                //leer
            }
            String infoDate="";
            if(!Resource.openFile) {
                Date date = new Date();
                DateFormat hourdateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                infoDate = hourdateFormat.format(date);
            }else{
                infoDate = Resource.dateFile;
            }
            postdata.put("image", myListSegmentation.getBase64String());
            postdata.put("email",Resource.emailUserLogin);
            postdata.put("project",Resource.idCarpeta);
            JSONObject posdate123 = new JSONObject();
            posdate123.put("imageX",myListSegmentation.getGeneralWidth());
            posdate123.put("imagey",myListSegmentation.getGeneralHeight());
            posdate123.put("profileItems",null);
            posdate123.put("landmarks",myListSegmentation.dataSegments());
            postdata.put("name",name);
            postdata.put("description",description);
            postdata.put("date",infoDate);
            postdata.put("information",posdate123);
        } catch(JSONException e){
            e.printStackTrace();
        }

        FileWriter fichero = null;
        PrintWriter pw = null;
        try{
            fichero = new FileWriter(file);
            pw = new PrintWriter(fichero);
            pw.print(postdata.toString());
            pw.flush();
            pw.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(null!=fichero){
                    fichero.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }


        Toast.makeText(getApplicationContext(),"Data Save Successfully",Toast.LENGTH_SHORT).show();

    }
    public void saveLandMarks(String name, String description){
        filtersWithProgressBar();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            String infoDate="";
            if(!Resource.openFile) {
                Date date = new Date();
                DateFormat hourdateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                infoDate = hourdateFormat.format(date);
            }else{
                infoDate = Resource.dateFile;
            }
            postdata.put("image", myListSegmentation.getBase64String());
            postdata.put("email",Resource.emailUserLogin);
            postdata.put("project",Resource.idCarpeta);
            JSONObject posdate123 = new JSONObject();
            posdate123.put("imageX",myListSegmentation.getGeneralWidth());
            posdate123.put("imagey",myListSegmentation.getGeneralHeight());
            posdate123.put("profileItems",null);
            posdate123.put("landmarks",myListSegmentation.dataSegments());
            postdata.put("name",name);
            postdata.put("description",description);
            postdata.put("date",infoDate);
            postdata.put("information",posdate123);
            System.out.println("************** TEST 1 ****************");
            System.out.println(posdate123.toString());
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"/landmark") /*URL ... INDEX Pc DE WILMER*/
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Wilmer ERROR :" + e);
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("**************RESPUESTA ****************");
                    System.out.println(responseData);
                    System.out.println("**************RESPUESTA ****************");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(responseData.equals("error")){
                                Toast.makeText(getApplicationContext(),"Error: The name is already",Toast.LENGTH_SHORT).show();
                            }else{
                                MemoryFigure.changeSave = true;
                                Toast.makeText(getApplicationContext(),"Save Successfully",Toast.LENGTH_SHORT).show();
                                if(Resource.openFile){
                                    System.out.println("openFile");
                                }else if(Resource.openShareFile){
                                    System.out.println("openshareFile");
                                }else{
                                    //txtsyncData.setVisibility(View.VISIBLE);
                                    //syncData.setVisibility(View.VISIBLE);
                                }
                            }
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
    }

    private void  privilegeOption(){
        if(Resource.privilegeFile.equals("edit")){
            textSave.setText("Send");
            saveData.setVisibility(View.GONE);
            sendData.setVisibility(View.VISIBLE);
        }else{
            saveData.setVisibility(View.GONE);
            textSave.setVisibility(View.GONE);
        }
    }
    public void openService(){
        filtersWithProgressBar();
        MediaType MEDIA_TYPE =
                MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        String addUrl = "";
        try {
            if(Resource.openShareFile) {
                postdata.put("email", Resource.emailSharedFrom);
                addUrl = "study/shared/selectfile";
                privilegeOption();

            }else{
                postdata.put("email", Resource.emailUserLogin);
                addUrl = "study/selectfile";
            }
            postdata.put("project",Resource.idCarpeta);
            postdata.put("file",Resource.nameFile);
            postdata.put("date",Resource.dateFile);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+addUrl) /*URL ... INDEX PX DE WILMER*/
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                e.printStackTrace();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    //System.out.println("**************RESPUESTA ****************");
                    //System.out.println(responseData);
                    try {
                        JSONObject landMarks = new JSONObject(responseData);
                        openLandMarks(landMarks);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void openLandMarks(JSONObject landMarks){
        try {
            System.out.println("********************aQUI ENTRA ******************************+");
            String image = landMarks.getString("image");
            Bitmap bitmap = myListSegmentation.decodeBase64AndSetImage(image);
            int flag = 0;
            FileOutputStream outputStream = null;
            String fileName = "photo";
            File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = null;
            try {
                imageFile = File.createTempFile(fileName,".JPEG",file);
                currentPhotoPath = imageFile.getAbsolutePath();
                outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                outputStream.flush();
                outputStream.close();
                flag = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(flag == 1){
                Mat aux = Imgcodecs.imread(currentPhotoPath);
                Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
                myListSegmentation.loadImage(imageBitmap);
                if(aux != null){
                    img = aux;
                    myFilters = new MyFilters(aux,imageBitmap);
                    updateFilters(imageBitmap,aux);
                }else{
                    System.out.println("error");
                }
            }
            myListSegmentation.addCircleSegmentation(12,12,12,-1,"123", colour);
            myListSegmentation.clearList();
            myListSegmentation.deleteMemory();         //
            JSONObject information = landMarks.getJSONObject("information");
            System.out.println("************** TEST 2 ****************");
            System.out.println(information.toString());
            JSONArray jsonArray = information.getJSONArray("landmarks");
            float imageX = Float.parseFloat(information.getString("imageX"));
            float imagey = Float.parseFloat(information.getString("imagey"));
            myListSegmentation.readDataSegments(jsonArray,imageX,imagey);

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String loadJSONFromAsset(String flName){
        String json = null;
        try{
            InputStream is = this.getAssets().open(flName);
            int size =  is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json =new String(buffer,"UTF-8");

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public void getFilterService(String image, String nameFilter,int cod){
        codHttpFilter = cod;
        filtersWithProgressBar();
        MediaType MEDIA_TYPE =
                MediaType.parse("application/json");
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(
                60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("image", image);
            postdata.put("type", nameFilter);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url(getString(R.string.url)+"filters")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Wilmer ERROR :" + e);
                switch (codHttpFilter){
                    case 1:{
                        auxOriginal = null;
                        break;
                    }
                    case 2:{
                        auxOriginal1 = null;
                        break;
                    }
                    case 3:{
                        auxOriginal2 = null;
                        break;
                    }
                    case 4:{
                        auxOriginal3 = null;
                        break;
                    }
                }
                //auxOriginal = null;
                dialog.dismiss();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    switch (codHttpFilter){
                        case 1:{
                            auxOriginal = myListSegmentation.decodeBase64AndSetImage(responseData);
                            addFilter(auxOriginal);
                            break;
                        }
                        case 2:{
                            auxOriginal1 = myListSegmentation.decodeBase64AndSetImage(responseData);
                            addFilter(auxOriginal1);
                            break;
                        }
                        case 3:{
                            auxOriginal2 = myListSegmentation.decodeBase64AndSetImage(responseData);
                            addFilter(auxOriginal2);
                            break;
                        }
                        case 4:{
                            auxOriginal3 = myListSegmentation.decodeBase64AndSetImage(responseData);
                            addFilter(auxOriginal3);
                            break;
                        }
                    }
                    dialog.dismiss();
                }
            }
        });
    }

    public void filtersWithProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.layout_loading);
        dialog = builder.create();
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 4 && resultCode == RESULT_OK){
            int flag = 0;
            imageurl = data.getData();
            FileOutputStream outputStream = null;
            String fileName = "photo";
            File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try{
                Bitmap bitmap = null;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(),imageurl));
                }else{
                    bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(),imageurl);;
                }
                File imageFile = File.createTempFile(fileName,".JPEG",file);
                currentPhotoPath = imageFile.getAbsolutePath();
                outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                outputStream.flush();
                outputStream.close();
                flag = 1;
            }catch (Exception e ){
                e.printStackTrace();
            }
            if(flag == 1){
                Mat aux = Imgcodecs.imread(currentPhotoPath);
                Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
                myListSegmentation.loadImage(imageBitmap);
                if(aux != null){
                    img = aux;
                    this.myFilters.setImage(imageBitmap);
                    this.myFilters.setImg(aux);
                    updateFilters(imageBitmap,aux);
                }else{
                    System.out.println("error");
                }
            }
        }
    }


    public void updateFilters(Bitmap original, Mat img){
        auxOriginal = null;
        auxOriginal1 = null;
        auxOriginal2 = null;
        auxOriginal3 = null;
        Bitmap scaleIconsBitmap = GroupFilters.scaleDown(original,400,true);
        Mat img_result_aux = new Mat();
        Imgproc.resize(img,img_result_aux,new Size(scaleIconsBitmap.getWidth(),scaleIconsBitmap.getHeight()));
        MyFilters myFilters = new MyFilters(img_result_aux,scaleIconsBitmap);
        //Icons with filters
        groupVariantOpenCv.setImageBitmap(GroupFilters.makeTransparent(myFilters.filterColor(5),80,"Variant"));
        groupBordersOpenCv.setImageBitmap(GroupFilters.makeTransparent(myFilters.filterCanny(),80,"Borders"));
        groupCalorOpenCv.setImageBitmap(GroupFilters.makeTransparent(myFilters.filterColor(2),80,"Calor"));
        groupColorsOpenCv.setImageBitmap(GroupFilters.makeTransparent(myFilters.filterColor(6),80,"Colors"));
        openCvHttp.setImageBitmap(GroupFilters.makeTransparent(myFilters.filterRGB(),80,"Rx-Server"));
        openCv.setImageBitmap(GroupFilters.makeText(myFilters.filterCanny(),"Canny"));
        openCv1.setImageBitmap(GroupFilters.makeText(myFilters.filerSepia(),"Sepia"));
        openCv2.setImageBitmap(GroupFilters.makeText(myFilters.filterMorph(),"Morph"));
        openCv3.setImageBitmap(GroupFilters.makeText(myFilters.filterRGB(),"Normal"));
        normalOpencv.setImageBitmap(GroupFilters.makeText(myFilters.filterRGB(),"Normal"));
        normalOpencv1.setImageBitmap(GroupFilters.makeText(myFilters.filterRGB(),"Normal"));
        normalOpencv2.setImageBitmap(GroupFilters.makeText(myFilters.filterRGB(),"Normal"));
        normalOpencv3.setImageBitmap(GroupFilters.makeText(myFilters.filterRGB(),"Normal"));
        openCv4.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(6),"Green"));
        openCv5.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(10),"Pink"));
        openCv6.setImageBitmap(GroupFilters.makeText(myFilters.filterReduceColorsGray(5),"Gray"));
        openCv7.setImageBitmap(GroupFilters.makeText(myFilters.filterReduceColors(80,15,10),"Dark"));//arreglar
        openCv8.setImageBitmap(GroupFilters.makeText(myFilters.filterPencil(),"Pencil"));
        openCv9.setImageBitmap(GroupFilters.makeText(myFilters.filterCarton(80,15,10),"Cartoon"));//arreglar
        openCv10.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(0),"Autumn"));
        openCv11.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(1),"Bone"));
        openCv12.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(2),"Jet"));
        openCv13.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(3),"Winter"));
        openCv14.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(4),"Rainbown"));
        openCv15.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(5),"Ocean"));
        openCv16.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(7),"Spring"));
        openCv17.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(8),"Cool"));
        openCv18.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(9),"Hsv"));
        openCv19.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(11),"Hot"));
        openCv20.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(12),"Parula"));
        openCv21.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(13),"Magma"));
        openCv22.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(14),"Inferno"));
        openCv23.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(15),"Plasma"));
        openCv24.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(16),"Viridis"));
        openCv25.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(17),"Cividis"));
        openCv26.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(18),"Twilight"));
        openCv27.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(19),"Shifted"));
        openCv28.setImageBitmap(GroupFilters.makeText(myFilters.filterColor(20),"Turbo"));
        getOpenCvHttp1.setImageBitmap(GroupFilters.makeText(GroupFilters.makeTransparent(myFilters.filterRGB(),90,""),"Clahe"));
        getOpenCvHttp2.setImageBitmap(GroupFilters.makeText(GroupFilters.makeTransparent(myFilters.filterRGB(),90,""),"ClaheGh"));
        getOpenCvHttp3.setImageBitmap(GroupFilters.makeText(GroupFilters.makeTransparent(myFilters.filterRGB(),90,""),"GuidedFilter"));
        getOpenCvHttp4.setImageBitmap(GroupFilters.makeText(GroupFilters.makeTransparent(myFilters.filterRGB(),90,""),"Wiener"));
    }

    /**
     * Method initial Properties Initializing Properties of Activity
     * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initialProperties(){
        //Initializing properties--
        preview = findViewById(R.id.preview);
        galery = findViewById(R.id.galery);
        openSegments = findViewById(R.id.openSegments);
        extendsImage = findViewById(R.id.extendsImage);
        backFilters = findViewById(R.id.backFilters);
        iconColors = findViewById(R.id.figuresSet);
        filterImage = findViewById(R.id.filter);
        saveData = findViewById(R.id.saveData);
        infoFigures = findViewById(R.id.infoFigures);
        eraserSegments = findViewById(R.id.eraserSegments);
        clearSegments = findViewById(R.id.clearSegments);
        menu_left = findViewById(R.id.contenedor_menu_left);
        menu_right = findViewById(R.id.contenedor_menu_right);
        //menu_left.setBackgroundColor(Color.parseColor("#80000000"));
        //menu_right.setBackgroundColor(Color.parseColor("#80000000"));
        getFigures = findViewById(R.id.getFigures);
        textSave = findViewById(R.id.textSave);
        sendData = findViewById(R.id.sendData);
        syncData = findViewById(R.id.syncData);
        txtsyncData = findViewById(R.id.txtsyncData);
        //Animation
        Animation = new MyAnimation();
        //Scrolls
        scrollLeft2 = findViewById(R.id.scrollLeft_2);
        scrollLeft3 = findViewById(R.id.scrollLeft_3);
        scrollRight1 = findViewById(R.id.scrollRight_1);
        scrollRight2 = findViewById(R.id.scrollRight_2);
        //Back and Checks
        backMenuRight = findViewById(R.id.back_menu_right_1);
        //Control
        controlMenu = findViewById(R.id.controlOption);
        control = findViewById(R.id.control);
        touchControl = findViewById(R.id.move);
        sortDown = findViewById(R.id.boot);
        sortLeft = findViewById(R.id.izquierda);
        sortRight = findViewById(R.id.derecha);
        sortUp = findViewById(R.id.upp);
        allSortDown = findViewById(R.id.downAll);
        allSortLeft = findViewById(R.id.izquierdaAll);
        allSortRight = findViewById(R.id.derechaAll);
        allSortUp = findViewById(R.id.uppAll);
        controlZ = findViewById(R.id.controlZ);
        controlY = findViewById(R.id.controlY);
        closeControl = findViewById(R.id.closeControl1);
        aristas = findViewById(R.id.arista);
        ControlMenu controlMenu = new ControlMenu(control,touchControl,sortLeft,sortRight,sortUp,sortDown);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        control.setTranslationX(metrics.widthPixels/2-((int)(getResources().getDimension(R.dimen.initial_position_control)))); //convertir a DP
        control.setTranslationY(metrics.heightPixels/2-((int)(getResources().getDimension(R.dimen.initial_position_control)))); //convertir a DP
        //Segmentation
        addPointSegment = findViewById(R.id.addPointSegment);
        pencilSegment =  findViewById(R.id.pencilSegment);
        cardView = findViewById(R.id.zoomImage_1); //CARD
        cardView.setVisibility(View.GONE);
        zoomImageLayout = findViewById(R.id.zoomLayoutImageRx_1); //Layout Zoom Image Ray-X
        zoomImageLayout.setVisibility(View.INVISIBLE);
        layoutImageRx1 = findViewById(R.id.layoutImageRx_1); //Layout Image Ray-X
        int d = (int) getResources().getDimension(R.dimen.traslate_zoom);
        myListSegmentation = new ListSegmentation(this, zoomImageLayout, cardView,layoutImageRx1,metrics,d);
        layoutImageRx1.addView(myListSegmentation);
        deleteSegments = findViewById(R.id.deleteSegments);
        changeColorSegments = findViewById(R.id.changeColorSegments);
        //Filters
        openCv = findViewById(R.id.openCV);
        openCv1 = findViewById(R.id.openCV1);
        openCv2 = findViewById(R.id.openCV2);
        openCv3 = findViewById(R.id.openCV3);
        openCv4 = findViewById(R.id.openCV4);
        openCv5 = findViewById(R.id.openCV5);
        openCv6 = findViewById(R.id.openCV6);
        openCv7 = findViewById(R.id.openCV7);
        openCv8 = findViewById(R.id.openCV8);
        openCv9 = findViewById(R.id.openCV9);
        openCv10 = findViewById(R.id.openCV10);
        openCv11 = findViewById(R.id.openCV11);
        openCv12 = findViewById(R.id.openCV12);
        openCv13 = findViewById(R.id.openCV13);
        openCv14 = findViewById(R.id.openCV14);
        openCv15 = findViewById(R.id.openCV15);
        openCv16 = findViewById(R.id.openCV16);
        openCv17 = findViewById(R.id.openCV17);
        openCv18 = findViewById(R.id.openCV18);
        openCv19 = findViewById(R.id.openCV19);
        openCv20 = findViewById(R.id.openCV20);
        openCv21 = findViewById(R.id.openCV21);
        openCv22 = findViewById(R.id.openCV22);
        openCv23 = findViewById(R.id.openCV23);
        openCv24 = findViewById(R.id.openCV24);
        openCv25 = findViewById(R.id.openCV25);
        openCv26 = findViewById(R.id.openCV26);
        openCv27 = findViewById(R.id.openCV27);
        openCv28 = findViewById(R.id.openCV28);
        groupVariantOpenCv = findViewById(R.id.openCVGroupVariant);
        groupBordersOpenCv = findViewById(R.id.openCVGroupBorders);
        groupCalorOpenCv = findViewById(R.id.openCVGroupCalor);
        groupColorsOpenCv = findViewById(R.id.openCVGroupColors);
        normalOpencv = findViewById(R.id.openCVNormal);
        normalOpencv1 = findViewById(R.id.openCVNormal01);
        normalOpencv2 = findViewById(R.id.openCVNormal02);
        normalOpencv3 = findViewById(R.id.openCVNormal03);
        openCvHttp = findViewById(R.id.openCVhttp);
        getOpenCvHttp1 = findViewById(R.id.openCVhttp1);
        getOpenCvHttp2 = findViewById(R.id.openCVhttp2);
        getOpenCvHttp3 = findViewById(R.id.openCVhttp3);
        getOpenCvHttp4 = findViewById(R.id.openCVhttp4);
        //IMAGE
        test1 = findViewById(R.id.test);
        img = null;
        flagAnimation=true;
        flagAnimationColors=true;
        flagSegmentation = false;
        flagEraserSegmentation = false;
        flagPencilSegmentation = false;
        flagPreviewSegmentation = false;
        flagVariant = false;
        flagHttp = false;
        flagBorders = false;
        flagCalor = false;
        flagColors = false;
        flagControl = false;
        flagAristas = false;
        initialImage(Resource.uriImageResource);
        //Icons Color
        listColors = new ArrayList<>();
        color1 = findViewById(R.id.color1); listColors.add(color1);
        color2 = findViewById(R.id.color2); listColors.add(color2);
        color3 = findViewById(R.id.color3); listColors.add(color3);
        color4 = findViewById(R.id.color4); listColors.add(color4);
        color5 = findViewById(R.id.color5); listColors.add(color5);
        color6 = findViewById(R.id.color6); listColors.add(color6);
        color7 = findViewById(R.id.color7); listColors.add(color7);
        color8 = findViewById(R.id.color8); listColors.add(color8);
        color9 = findViewById(R.id.color9); listColors.add(color9);
        color10 = findViewById(R.id.color10); listColors.add(color10);
        color11 = findViewById(R.id.color11); listColors.add(color11);
        color12 = findViewById(R.id.color12); listColors.add(color12);
        color13 = findViewById(R.id.color13); listColors.add(color13);
        color14 = findViewById(R.id.color14); listColors.add(color14);
        color15 = findViewById(R.id.color15); listColors.add(color15);
        color16 = findViewById(R.id.color16); listColors.add(color16);
        color17 = findViewById(R.id.color17); listColors.add(color17);
        color18 = findViewById(R.id.color18); listColors.add(color18);
        color19 = findViewById(R.id.color19); listColors.add(color19);
        color20 = findViewById(R.id.color20); listColors.add(color20);
        color21 = findViewById(R.id.color21); listColors.add(color21);
        color22 = findViewById(R.id.color22); listColors.add(color22);
        color23 = findViewById(R.id.color23); listColors.add(color23);
        color24 = findViewById(R.id.color24); listColors.add(color24);
        color25 = findViewById(R.id.color25); listColors.add(color25);
        //Add Filter Color
        for(int i = 0;i < listColors.size();i++){
            if(Util.getCollections()[i]!=null)
                listColors.get(i).setColorFilter(Color.rgb(Util.getCollections()[i][0],Util.getCollections()[i][1],Util.getCollections()[i][2]));
        }
        if(Resource.openFile){
            System.out.println("openFile");
        }else if(Resource.openShareFile){
            System.out.println("openshareFile");
        }else{
            txtsyncData.setVisibility(View.GONE);
            syncData.setVisibility(View.GONE);
        }
        //--End Initializing
    }//End Method

    private void showDialogERROR(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("FILE SOURCE ERROR !!");
        dialog.setMessage("Do you want to cle this file?");
        LayoutInflater inflater = LayoutInflater.from(this);
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LandMarkModelActivity.this.finish();
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private boolean flagInit = true;
    @Override
    public boolean onKeyDown(int KeyCod, KeyEvent event){
        if(KeyCod == event.KEYCODE_BACK){
            if(Resource.openShareFile && Resource.privilegeFile.equals("lecture")){
                return super.onKeyDown(KeyCod,event);
            }
            if(MemoryFigure.changeSave==false){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("EXIT: ");
                dialog.setMessage("There are unsaved changes, are you sure?");
                LayoutInflater inflater = LayoutInflater.from(this);
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LandMarkModelActivity.this.finish();
                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }else{
                return super.onKeyDown(KeyCod,event);
            }
            return true;
        }
        return super.onKeyDown(KeyCod,event);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initialImage(String uri){
        if(Resource.openFile||Resource.openShareFile){
            int nameImage = R.drawable.fondo_negro_x;
            try {
                img = Utils.loadResource(getApplicationContext(),nameImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MemoryFigure.changeSave = true;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            this.original = BitmapFactory.decodeResource(getResources(), nameImage, options);
            myListSegmentation.loadImage(this.original);
            openService();
            nameFileGlobal = Resource.nameFile;
            descriptionFileGlobal = Resource.descriptionFile;
        }else {
            if (uri != null) {
                Mat aux = Imgcodecs.imread(uri);
                Bitmap imageBitmap = BitmapFactory.decodeFile(uri);
                myListSegmentation.loadImage(imageBitmap);
                if (aux != null) {
                    img = aux;
                    this.original = imageBitmap;
                    updateFilters(imageBitmap, aux);
                } else {
                    System.out.println("error");
                }
                myListSegmentation.loadImage(this.original);
                myFilters = new MyFilters(this.img, this.original);
                updateFilters(this.original, img);
                MemoryFigure.changeSave = false;
            }
        }
    }

}//End Class





