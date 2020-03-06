package com.example.dashboard;
//Imports
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Figures.Circle;
import ListFigures.ListFigure;
import ListFigures.ListSegmentation;
import ListFigures.Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * This class define the Main Activity Image Ray-X editing space
 * @author Edwin Saavedra
 * @version 3
 */
public class MainActivity extends AppCompatActivity {
    public static int TIME_ANIMATION = 100;
    public static float SCALE_ANIMATION = 1.1f;
    //Class Attributes--
    //Load and Save data

    //Insert Figures
    private ImageView saveData;
    private ImageView saveFigures;
    private ImageView creatorCircles;
    private ImageView creatorRectangles;
    private ImageView creatorLines;
    private ImageView creatorEllipses;
    private ImageView creatorPoints;
    //Edit and delete Figures
    private ImageView deleteFigures;
    private ImageView changeColor;
    private ImageView infoFigures;
    private int[] colour = {183, 149, 11};
    //Zoom Image
    private ImageView extendsImage;
    //Filters
    private ImageView filterImage;
    private Drawable d;
    //Change Layout
    private ImageView segmentation;
    private ImageView addPointSegment;
    private ImageView pencilSegment;
    //Layout for Image RX
    private LinearLayout layoutImageRx;
    private LinearLayout layoutImageRx1;
    //View
    private ListFigure myListFigures;
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
    private ScrollView scrollLeft1;
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
    private ImageView openCvHttp;
    private ImageView groupVariantOpenCv;
    private ImageView groupBordersOpenCv;
    private ImageView groupCalorOpenCv;
    private ImageView groupColorsOpenCv;
    private ImageView normalOpencv;
    private ImageView normalOpencv1;
    private ImageView normalOpencv2;
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
    private int scaleSort;
    //img original format Bitmap
    private Bitmap original;
    private Bitmap auxOriginal = null;
    private Mat img;
    private int nameImage;
    private boolean longClick = false;
    private int mActivePointerId = INVALID_POINTER_ID;
    private LinearLayout rootLayout;
    //--End Attributes of class
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ORIENTATION FALSE
        setTheme(R.style.AppTheme);
        //Add Layout Activity XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        //Load OpenCV
        OpenCVLoader.initDebug();
        //Initializing Properties
        //
        initialProperties();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       infoFigures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListFigures.isSelectedFigure())
                    showInfoDialog();
                else{
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
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
                    Animation.animationScale(aristas,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                    aristas.setColorFilter(Color.rgb(255, 127, 80));
                    myListSegmentation.changeModeTouch(5);
                    flagAristas = true;
                }else{
                    aristas.setColorFilter(Color.rgb(255, 255, 255));
                    flagAristas = false;
                }
                myListSegmentation.newEdge();
            }
        });
        controlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        allSortUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myListSegmentation.allSortUp()){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(),"Please selected figure ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        allSortLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListSegmentation.getFigureSelected()>-1) {
                    Circle a = (Circle) myListSegmentation.getSegmentation().get(myListSegmentation.getFigureSelected());
                    Circle a1 = (Circle) myListSegmentation.getZoomList().getSegmentation().get(myListSegmentation.getZoomList().getFigureSelected());
                    myListSegmentation.getViewZoom().setPivotX(myListSegmentation.getViewZoom().getWidth() / myListSegmentation.getGeneralWidth());
                    a.setCenterX(0);
                    a1.setCenterX(a.getCenterX() * myListSegmentation.getViewZoom().getWidth() / myListSegmentation.getGeneralWidth());
                    myListSegmentation.invalidate();
                    myListSegmentation.getZoomList().invalidate();
                }
            }
        });
        allSortRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListSegmentation.getFigureSelected()>-1) {
                    Circle a = (Circle) myListSegmentation.getSegmentation().get(myListSegmentation.getFigureSelected());
                    Circle a1 = (Circle) myListSegmentation.getZoomList().getSegmentation().get(myListSegmentation.getZoomList().getFigureSelected());
                    myListSegmentation.getViewZoom().setPivotX((myListSegmentation.getGeneralWidth()) * myListSegmentation.getViewZoom().getWidth() / myListSegmentation.getGeneralWidth());
                    a.setCenterX(myListSegmentation.getGeneralWidth());
                    a1.setCenterX(a.getCenterX() * myListSegmentation.getViewZoom().getWidth() / myListSegmentation.getGeneralWidth());
                    myListSegmentation.invalidate();
                    myListSegmentation.getZoomList().invalidate();
                }
            }
        });
        allSortDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListSegmentation.getFigureSelected()>-1) {
                    Circle a = (Circle) myListSegmentation.getSegmentation().get(myListSegmentation.getFigureSelected());
                    Circle a1 = (Circle) myListSegmentation.getZoomList().getSegmentation().get(myListSegmentation.getZoomList().getFigureSelected());
                    myListSegmentation.getViewZoom().setPivotY(( myListSegmentation.getGeneralHeight())  * myListSegmentation.getViewZoom().getHeight() /  myListSegmentation.getGeneralHeight());
                    a.setCenterY( myListSegmentation.getGeneralHeight());
                    a1.setCenterY(a.getCenterY() * myListSegmentation.getViewZoom().getHeight() /  myListSegmentation.getGeneralHeight());
                    myListSegmentation.invalidate();
                    myListSegmentation.getZoomList().invalidate();
                }
            }
        });
        sortUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(myListSegmentation.getFigureSelected()>-1){
                Circle a = (Circle) myListSegmentation.getSegmentation().get(myListSegmentation.getFigureSelected());
                Circle a1 = (Circle) myListSegmentation.getZoomList().getSegmentation().get(myListSegmentation.getZoomList().getFigureSelected());
                myListSegmentation.getViewZoom().setPivotY((a.getCenterY())*myListSegmentation.getViewZoom().getHeight()/ myListSegmentation.getGeneralHeight());
                if(a.getCenterY() - scaleSort>=0) {
                    a.setCenterY(a.getCenterY() - scaleSort);
                }else{
                    a.setCenterY(0);
                }
                a1.setCenterY(a.getCenterY() * myListSegmentation.getViewZoom().getHeight() /  myListSegmentation.getGeneralHeight());
                myListSegmentation.invalidate();
                myListSegmentation.getZoomList().invalidate();
            }
            }
        });
        sortDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListSegmentation.getFigureSelected()>-1){
                    Circle a = (Circle) myListSegmentation.getSegmentation().get(myListSegmentation.getFigureSelected());
                    Circle a1 = (Circle) myListSegmentation.getZoomList().getSegmentation().get(myListSegmentation.getZoomList().getFigureSelected());
                    myListSegmentation.getViewZoom().setPivotY((a.getCenterY())*myListSegmentation.getViewZoom().getHeight()/ myListSegmentation.getGeneralHeight());
                    if(a.getCenterY() + scaleSort<= myListSegmentation.getGeneralHeight() ) {
                        a.setCenterY(a.getCenterY() + scaleSort);
                    }else{
                        a.setCenterY(myListSegmentation.getHeight());
                    }
                    a1.setCenterY(a.getCenterY() * myListSegmentation.getViewZoom().getHeight() /  myListSegmentation.getGeneralHeight());
                    myListSegmentation.invalidate();
                    myListSegmentation.getZoomList().invalidate();
                }
            }
        });
        sortRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListSegmentation.getFigureSelected()>-1){
                    Circle a = (Circle) myListSegmentation.getSegmentation().get(myListSegmentation.getFigureSelected());
                    Circle a1 = (Circle) myListSegmentation.getZoomList().getSegmentation().get(myListSegmentation.getZoomList().getFigureSelected());
                    myListSegmentation.getViewZoom().setPivotX((a.getCenterX())*myListSegmentation.getViewZoom().getWidth()/myListSegmentation.getGeneralWidth());
                    if(a.getCenterX() + scaleSort<=myListSegmentation.getGeneralWidth()) {
                        a.setCenterX(a.getCenterX() + scaleSort);
                    }else{
                        a.setCenterX(myListSegmentation.getGeneralWidth());
                    }
                    a1.setCenterX(a.getCenterX() * myListSegmentation.getViewZoom().getWidth() / myListSegmentation.getGeneralWidth());
                    myListSegmentation.invalidate();
                    myListSegmentation.getZoomList().invalidate();
                }
            }
        });
        sortLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListSegmentation.getFigureSelected()>-1){
                    Circle a = (Circle) myListSegmentation.getSegmentation().get(myListSegmentation.getFigureSelected());
                    Circle a1 = (Circle) myListSegmentation.getZoomList().getSegmentation().get(myListSegmentation.getZoomList().getFigureSelected());
                    myListSegmentation.getViewZoom().setPivotX((a.getCenterX())*myListSegmentation.getViewZoom().getWidth()/myListSegmentation.getGeneralWidth());
                    if(a.getCenterX() - scaleSort-a.getRadius()>=0) {
                        a.setCenterX(a.getCenterX() - scaleSort);
                    }else{
                        a.setCenterX(0);
                    }
                    a1.setCenterX(a.getCenterX() * myListSegmentation.getViewZoom().getWidth() /myListSegmentation.getGeneralWidth());
                    myListSegmentation.invalidate();
                    myListSegmentation.getZoomList().invalidate();
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


        saveFigures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(deleteFigures,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                Toast toast;
                if(myListFigures.deleteFigure())
                    toast = Toast.makeText(getApplicationContext(),"Data Save Successfully",Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(getApplicationContext(),"Data not Save",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(deleteFigures,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                Toast toast;
                //myListSegmentation ... ESTAS AQUI !!
                /*if(myListFigures.toString())
                    toast = Toast.makeText(getApplicationContext(),"Data Save Successfully",Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(getApplicationContext(),"Data not Save",Toast.LENGTH_SHORT);
                toast.show();*/
                System.out.println(myListSegmentation.toString());

                //myListSegmentation.toString();
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagPreviewSegmentation) {
                    Animation.animationScale(preview, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                    preview.setColorFilter(Color.rgb(255, 127, 80));
                    //myListSegmentation.changeModeTouch(4);
                    myListSegmentation.getFlagPreview();
                    myListSegmentation.invalidate();
                    flagPreviewSegmentation = true;
                }else{
                    preview.setColorFilter(Color.rgb(255, 255, 255));
                    //myListSegmentation.changeModeTouch(0);
                    myListSegmentation.getFlagPreview();
                    myListSegmentation.invalidate();
                    flagPreviewSegmentation = false;
                }

            }
        });
        //Change of layout to "Segmentation"
        segmentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagSegmentation) {
                    if(myListSegmentation.getModeTouch()==1)
                        extendsImage.setColorFilter(Color.rgb(255, 127, 80)); //orange
                    else
                        extendsImage.setColorFilter(Color.rgb(255, 255, 255)); //white
                    Animation.animationScale(segmentation, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                    segmentation.setColorFilter(Color.rgb(255, 127, 80));
                    if(flagControl)
                        control.setVisibility(View.VISIBLE);
                    else
                        control.setVisibility(View.GONE);
                    layoutImageRx.setVisibility(View.GONE);
                    layoutImageRx1.setVisibility(View.VISIBLE);
                    scrollLeft1.setVisibility(View.GONE);

                    if(flagAnimationColors) //menu de figuritas esta activo
                        scrollLeft2.setVisibility(View.VISIBLE);
                    else
                        scrollLeft2.setVisibility(View.GONE);
                    flagSegmentation = true;
                }else{
                    if(myListFigures.getModeTouch()==1)
                        extendsImage.setColorFilter(Color.rgb(255, 127, 80)); //orange
                    else
                        extendsImage.setColorFilter(Color.rgb(255, 255, 255)); //white
                    segmentation.setColorFilter(Color.rgb(255,255,255));
                    scrollLeft2.setVisibility(View.GONE);
                    if(flagAnimationColors)
                        scrollLeft1.setVisibility(View.VISIBLE);
                    else
                        scrollLeft1.setVisibility(View.GONE);
                    layoutImageRx1.setVisibility(View.GONE);
                    control.setVisibility(View.GONE);
                    myListSegmentation.getCardView().setVisibility(View.GONE);
                    layoutImageRx.setVisibility(View.VISIBLE);
                    flagSegmentation = false;
                }
            }
        });
        clearSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(clearSegments,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListSegmentation.clearList();
                myListSegmentation.getCardView().setVisibility(View.GONE);
            }
        });
        eraserSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagEraserSegmentation) {
                    Animation.animationScale(eraserSegments, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                    eraserSegments.setColorFilter(Color.rgb(255, 127, 80));
                    addPointSegment.setColorFilter(Color.rgb(255, 255, 255));
                    extendsImage.setColorFilter(Color.rgb(255, 255, 255));
                    pencilSegment.setColorFilter(Color.rgb(255, 255, 255));
                    myListSegmentation.changeModeTouch(2);
                    flagEraserSegmentation = true;
                }else{
                    eraserSegments.setColorFilter(Color.rgb(255, 255, 255));
                    myListSegmentation.changeModeTouch(0);
                    flagEraserSegmentation = false;
                }
            }
        });
        addPointSegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int modeTouch = myListSegmentation.getModeTouch();
                if(!flagPointSegmentation) {
                    Animation.animationScale(addPointSegment, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                    eraserSegments.setColorFilter(Color.rgb(255, 255, 255));
                    pencilSegment.setColorFilter(Color.rgb(255, 255, 255));
                    extendsImage.setColorFilter(Color.rgb(255, 255, 255));
                    addPointSegment.setColorFilter(Color.rgb(255, 127, 80));
                    myListSegmentation.changeModeTouch(3);
                    flagPointSegmentation = true;
                }else{
                    addPointSegment.setColorFilter(Color.rgb(255, 255, 255));
                        myListSegmentation.changeModeTouch(0);
                    flagPointSegmentation = false;
                }
            }
        });

        pencilSegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int modeTouch = myListSegmentation.getModeTouch();
                if(!flagPencilSegmentation) {
                    Animation.animationScale(pencilSegment, TIME_ANIMATION, SCALE_ANIMATION, SCALE_ANIMATION);
                    eraserSegments.setColorFilter(Color.rgb(255, 255, 255));
                    addPointSegment.setColorFilter(Color.rgb(255, 255, 255));
                    extendsImage.setColorFilter(Color.rgb(255, 255, 255));
                    pencilSegment.setColorFilter(Color.rgb(255, 127, 80));
                    myListSegmentation.changeModeTouch(4);
                    flagPencilSegmentation = true;
                }else{
                    pencilSegment.setColorFilter(Color.rgb(255, 255, 255));
                        myListSegmentation.changeModeTouch(0);
                    flagPencilSegmentation = false;
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
                if(!flagSegmentation) {
                    if(myListFigures.getModeTouch()==0 ) {
                        extendsImage.setColorFilter(Color.rgb(255, 127, 80)); //orange
                    }else{
                        extendsImage.setColorFilter(Color.rgb(255, 255, 255)); //white
                    }
                    myListFigures.changeModeTouch();
                }else {

                    if(myListSegmentation.getModeTouch()==0 || myListSegmentation.getModeTouch()==2
                            || myListSegmentation.getModeTouch()==3 || myListSegmentation.getModeTouch()==4) {
                        eraserSegments.setColorFilter(Color.rgb(255, 255, 255)); //white
                        pencilSegment.setColorFilter(Color.rgb(255, 255, 255)); //white
                        addPointSegment.setColorFilter(Color.rgb(255, 255, 255)); //white
                        extendsImage.setColorFilter(Color.rgb(255, 127, 80)); //orange
                        myListSegmentation.changeModeTouch(1);
                    }else{
                        extendsImage.setColorFilter(Color.rgb(255, 255, 255)); //white
                        myListSegmentation.changeModeTouch(0);
                    }
                }
            }
        });
        //Add Point
        creatorPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorPoints,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addPoint(layoutImageRx.getWidth()/2,layoutImageRx.getHeight()/2);
            }
        });
        //Add Circle
        creatorCircles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorCircles,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addCircle(layoutImageRx.getWidth()/2,layoutImageRx.getHeight()/2,100);
            }
        });
        //Add Rectangle
        creatorRectangles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorRectangles,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addRectangle(layoutImageRx.getWidth()/2-200,layoutImageRx.getHeight()/2-150,layoutImageRx.getWidth()/2+200,layoutImageRx.getHeight()/2+150);
            }
        });
        //Add Line
        creatorLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorLines,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addLine(layoutImageRx.getWidth()/2-200,layoutImageRx.getHeight()/2-150,layoutImageRx.getWidth()/2+200,layoutImageRx.getHeight()/2+150);
            }
        });
        //Add Ellipse
        creatorEllipses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorEllipses,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addEllipse(layoutImageRx.getWidth()/2-200,layoutImageRx.getHeight()/2-150,layoutImageRx.getWidth()/2+200,layoutImageRx.getHeight()/2+150);
            }
        });
        //Delete Select Figure
        deleteFigures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(deleteFigures,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                Toast toast;
                if(myListFigures.deleteFigure())
                    toast = Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(getApplicationContext(),"Isn't Selected Figure",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        //Menu of Change Colour of select figure
        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(changeColor,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
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
                            scrollLeft1.setVisibility(View.GONE);
                            scrollLeft3.setVisibility(View.VISIBLE);
                            getFigures.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onAnimationRepeat(android.view.animation.Animation animation) {
                        }
                    });
                flagAnimationColors=false;
                    scrollLeft1.startAnimation(animation1);

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
                        if(flagSegmentation){
                            scrollLeft2.setVisibility(View.VISIBLE);
                            getFigures.setVisibility(View.GONE);
                            Animation animation2 = new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
                            animation2.setDuration(550);
                            scrollLeft2.startAnimation(animation2);
                        }else {
                            scrollLeft1.setVisibility(View.VISIBLE);
                            getFigures.setVisibility(View.GONE);
                            Animation animation2 = new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
                            animation2.setDuration(550);
                            scrollLeft1.startAnimation(animation2);
                        }

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
        //detectPruebas

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
        //Delete the after segment
        deleteSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        addFilter(myFilters.filterSummer());
           }
                     });
        //filter pink
        openCv5.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    addFilter(myFilters.filterPink());
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
        openCvHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFilter(auxOriginal);
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
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addFilter(Bitmap d){

        Drawable drawable = new BitmapDrawable(getResources(),d);
        if(!flagSegmentation)
            myListFigures.loadImage(d);
        else {
            myListSegmentation.loadImage(d);
            zoomImageLayout.setBackground(drawable);
        }
    }
    @SuppressLint("ShowToast")
    private void changeColor(int _color){
        Toast toast=Toast.makeText(getApplicationContext(),"Isn't Selected Figure",Toast.LENGTH_SHORT);
        if(flagSegmentation){
            if( myListSegmentation.changeColour(Util.getCollections()[_color]))
                toast = Toast.makeText(getApplicationContext(),"Change Successfully in Segments",Toast.LENGTH_SHORT);
        }else{
            if( myListFigures.changeColour(Util.getCollections()[_color]))
                toast = Toast.makeText(getApplicationContext(),"Change Successfully in Figure",Toast.LENGTH_SHORT);
        }
        toast.show();
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
    }

    public Bitmap makeTransparent(Bitmap src,int value,String description){
        int width = src.getWidth();
        int height =  src.getHeight();
        Bitmap transBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0,0,0,0);
        final Paint paint = new Paint();
        paint.setAlpha(value);
        final Paint pencil = new Paint();
        Typeface tipoFuente = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL);
        pencil.setTypeface(tipoFuente);
        pencil.setTextSize(30);
        pencil.setTextAlign(Paint.Align.CENTER);
        pencil.setColor(Color.WHITE);
        canvas.drawBitmap(src,0,0,paint);
        canvas.drawText(description,src.getWidth()/2,src.getHeight()/2,pencil);
        return  transBitmap;
    }

    public Bitmap makeText(Bitmap src,String description){
        int width = src.getWidth();
        int height =  src.getHeight();
        Bitmap textBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(textBitmap);
        canvas.drawARGB(0,0,0,0);

        final Paint paint = new Paint();
        final Paint pencil2 = new Paint();

        Typeface tipoFuente = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL);
        pencil2.setTypeface(tipoFuente);
        pencil2.setTextSize(30);

        Rect areRect = new Rect(0,height-60,width,height);
        pencil2.setColor(Color.BLACK);

        canvas.drawBitmap(src,0,0,paint);
        canvas.drawRect(areRect,pencil2);

        RectF bounds = new RectF(areRect);
        bounds.right = pencil2.measureText(description,0,description.length());
        bounds.bottom = pencil2.descent()-pencil2.ascent();
        bounds.left += (areRect.width()-bounds.right)/2.0f;
        bounds.top += (areRect.height()-bounds.bottom)/2.0f;
        pencil2.setColor(Color.WHITE);
        canvas.drawText(description,bounds.left,bounds.top-pencil2.ascent(),pencil2);
        return  textBitmap;
    }
    private void showInfoDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("INFO FIGURE: ");
        dialog.setMessage("Please description figure");
        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_info_figure,null);
        final TextInputEditText editDescription = login_layout.findViewById(R.id.txt_description);
        editDescription.setText(myListFigures.getDescriptionFigure());
        dialog.setView(login_layout);
        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast;
                if(myListFigures.setDescriptionFigure(editDescription.getText().toString()))
                    toast = Toast.makeText(getApplicationContext(),"Save Successfully",Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(getApplicationContext(),"No Save ",Toast.LENGTH_SHORT);
                toast.show();
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

    public static Bitmap scaleDown(Bitmap realImage,float maxImageSize , boolean filter){
        float ratio = Math.min((float) maxImageSize/realImage.getWidth(),(float) maxImageSize/realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());
        Bitmap newImage = Bitmap.createScaledBitmap(realImage,width,height,filter);
        return newImage;
    }

    public void enviar(){

        MediaType MEDIA_TYPE =
                MediaType.parse("application/json");
        String image="";
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60,
                TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).writeTimeout(

                        60,TimeUnit.SECONDS).build();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("image", myListSegmentation.getBase64String());
            postdata.put("type", "clahe");
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                postdata.toString());
        final Request request = new Request.Builder()
                .url("http://192.168.12.97:5000/filters") /*URL ... INDEX PX DE WILMER*/
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Wilmer ERROR :" + e);
                auxOriginal = myFilters.filterRGB();
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();
                    System.out.println("**************RESPUESTA ****************");
                    System.out.println(responseData);
                    System.out.println("**************RESPUESTA ****************");
                    auxOriginal = myListSegmentation.decodeBase64AndSetImage(responseData);
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String json="";
                            auxOriginal = myListSegmentation.decodeBase64AndSetImage(responseData);
                            System.out.println("RECIBIDO CON EXITO");
                        }
                    });*/
                }
            }
        });
    }
    /**
     * Method initial Properties Initializing Properties of Activity
     * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initialProperties(){
        //Initializing properties--
        preview = findViewById(R.id.preview);
        extendsImage = findViewById(R.id.extendsImage);
        backFilters = findViewById(R.id.backFilters);
        iconColors = findViewById(R.id.figuresSet);
        filterImage = findViewById(R.id.filter);
        saveData = findViewById(R.id.saveData);
        saveFigures = findViewById(R.id.saveFigures);
        creatorCircles = findViewById(R.id.addCircle);
        String  sectionColor = "#C3ACAC";
        creatorCircles.setColorFilter(Color.parseColor(sectionColor));
        creatorRectangles = findViewById(R.id.addRectangle);
        creatorRectangles.setColorFilter(Color.parseColor(sectionColor));
        creatorLines = findViewById(R.id.addLine);
        creatorLines.setColorFilter(Color.parseColor(sectionColor));
        creatorEllipses = findViewById(R.id.addEllipse);
        creatorEllipses.setColorFilter(Color.parseColor(sectionColor));
        creatorPoints = findViewById(R.id.addPoint);
        creatorPoints.setColorFilter(Color.parseColor(sectionColor));
        infoFigures = findViewById(R.id.infoFigures);
        deleteFigures = findViewById(R.id.deleteFigures);
        changeColor = findViewById(R.id.changeColor);
        segmentation = findViewById(R.id.segmentation);
        eraserSegments = findViewById(R.id.eraserSegments);
        clearSegments = findViewById(R.id.clearSegments);
        menu_left = findViewById(R.id.contenedor_menu_left);
        menu_right = findViewById(R.id.contenedor_menu_right);
        //contenedor_description.setBackgroundColor(Color.parseColor("#80000000"));
        menu_left.setBackgroundColor(Color.parseColor("#80000000"));
        menu_right.setBackgroundColor(Color.parseColor("#80000000"));
        getFigures = findViewById(R.id.getFigures);
        //ImageView load = findViewById(R.id.load);
        //ConstraintLayout frame = findViewById(R.id.frame);
        layoutImageRx = findViewById(R.id.layoutImageRx);
        myListFigures = new ListFigure(this,layoutImageRx);
        layoutImageRx.addView(myListFigures);
        //Animation
        Animation = new MyAnimation();
        //Scrolls
        scrollLeft1 = findViewById(R.id.scrollLeft_1);
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
        control.setTranslationX(metrics.widthPixels/2-((int)(getResources().getDimension(R.dimen.initial_position_control)))*2); //convertir a DP
        control.setTranslationY(metrics.heightPixels/2-((int)(getResources().getDimension(R.dimen.initial_position_control)))*2); //convertir a DP
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
        openCvHttp = findViewById(R.id.openCVhttp);
        //IMAGE
        test1 = findViewById(R.id.test);
        img = null;
        nameImage = -1;
        flagAnimation=true;
        flagAnimationColors=true;
        flagSegmentation = false;
        flagEraserSegmentation = false;
        flagPencilSegmentation = false;
        flagPreviewSegmentation = false;
        flagVariant = false;
        flagBorders = false;
        flagCalor = false;
        flagColors = false;
        flagControl = false;
        flagAristas = false;
        try{
            nameImage = R.drawable.rx_image_femur;
            img = Utils.loadResource(getApplicationContext(),nameImage);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            this.original = BitmapFactory.decodeResource(getResources(), nameImage, options);
        }catch (IOException e) {
            System.out.println("Insert image");
            e.printStackTrace();
        }
        myListFigures.loadImage(this.original);
        myListSegmentation.loadImage(this.original);
        myFilters = new MyFilters(this.img,this.original);
        zoomImageLayout.setBackground(new BitmapDrawable(getResources(),myFilters.filterRGB()));
        //Resize Image Icon Filter
        int d1 = (int) getResources().getDimension(R.dimen.icon_filter);
        Bitmap scaleIconsBitmap = scaleDown(this.original,d1,true);
        Mat img_result_aux = new Mat();
        Imgproc.resize(img,img_result_aux,new Size(d1,d1));
        MyFilters myFilters = new MyFilters(img_result_aux,scaleIconsBitmap);
        //Icons with filter
        groupVariantOpenCv.setImageBitmap(makeTransparent(myFilters.filterRGB(),80,"Variant"));
        groupBordersOpenCv.setImageBitmap(makeTransparent(myFilters.filterCanny(),80,"Borders"));
        groupCalorOpenCv.setImageBitmap(makeTransparent(myFilters.filterColor(2),80,"Calor"));
        groupColorsOpenCv.setImageBitmap(makeTransparent(myFilters.filterSummer(),80,"Colors"));
        openCv.setImageBitmap(makeText(myFilters.filterCanny(),"Canny"));
        openCv1.setImageBitmap(makeText(myFilters.filerSepia(),"Sepia"));
        openCv2.setImageBitmap(makeText(myFilters.filterMorph(),"Morph"));
        openCv3.setImageBitmap(makeText(myFilters.filterRGB(),"Normal"));
        normalOpencv.setImageBitmap(makeText(myFilters.filterRGB(),"Normal"));
        normalOpencv1.setImageBitmap(makeText(myFilters.filterRGB(),"Normal"));
        normalOpencv2.setImageBitmap(makeText(myFilters.filterRGB(),"Normal"));
        openCv4.setImageBitmap(makeText(myFilters.filterSummer(),"Green"));
        openCv5.setImageBitmap(makeText(myFilters.filterPink(),"Pink"));
        openCv6.setImageBitmap(makeText(myFilters.filterReduceColorsGray(5),"Gray"));
        openCv7.setImageBitmap(makeText(myFilters.cropBitmap(myFilters.filterReduceColors(80,15,10)),"Dark"));//arreglar
        openCv8.setImageBitmap(makeText(myFilters.filterPencil(),"Pencil"));
        openCv9.setImageBitmap(makeText(myFilters.cropBitmap(myFilters.filterCarton(80,15,10)),"Cartoon"));//arreglar
        openCv10.setImageBitmap(makeText(myFilters.filterColor(0),"Autumn"));
        openCv11.setImageBitmap(makeText(myFilters.filterColor(1),"Bone"));
        openCv12.setImageBitmap(makeText(myFilters.filterColor(2),"Jet"));
        openCv13.setImageBitmap(makeText(myFilters.filterColor(3),"Winter"));
        openCv14.setImageBitmap(makeText(myFilters.filterColor(4),"Rainbown"));
        openCv15.setImageBitmap(makeText(myFilters.filterColor(5),"Ocean"));
        openCv16.setImageBitmap(makeText(myFilters.filterColor(7),"Spring"));
        openCv17.setImageBitmap(makeText(myFilters.filterColor(8),"Cool"));
        openCv18.setImageBitmap(makeText(myFilters.filterColor(9),"Hsv"));
        openCv19.setImageBitmap(makeText(myFilters.filterColor(11),"Hot"));
        openCv20.setImageBitmap(makeText(myFilters.filterColor(12),"Parula"));
        openCv21.setImageBitmap(makeText(myFilters.filterColor(13),"Magma"));
        openCv22.setImageBitmap(makeText(myFilters.filterColor(14),"Inferno"));
        openCv23.setImageBitmap(makeText(myFilters.filterColor(15),"Plasma"));
        openCv24.setImageBitmap(makeText(myFilters.filterColor(16),"Viridis"));
        openCv25.setImageBitmap(makeText(myFilters.filterColor(17),"Cividis"));
        openCv26.setImageBitmap(makeText(myFilters.filterColor(18),"Twilight"));
        openCv27.setImageBitmap(makeText(myFilters.filterColor(19),"Shifted"));
        openCv28.setImageBitmap(makeText(myFilters.filterColor(20),"Turbo"));
        //enviar();
        //openCvHttp.setImageBitmap(makeText(auxOriginal,"CLAHE"));
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
        scaleSort = 3;

        //--End Initializing
    }//End Method
}//End Class




