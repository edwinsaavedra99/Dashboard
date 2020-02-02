package com.example.dashboard;
//Imports
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.Random;
import ListFigures.ListFigure;
import ListFigures.ListSegmentation;
import ListFigures.Util;

/**
 * This class define the Main Activity Image Ray-X editing space
 * @author Edwin Saavedra
 * @version 3
 */
public class MainActivity extends AppCompatActivity {
    public static int TIME_ANIMATION = 100;
    public static float SCALE_ANIMATION = 1.1f;
    //Class Attributes--
    /*/Load and Save data
    private String nameFile;
    private String nameBinder;
    private File file;*/
    //Insert Figures
    private ImageView creatorCircles;
    private ImageView creatorRectangles;
    private ImageView creatorLines;
    private ImageView creatorEllipses;
    private ImageView creatorPoints;
    //Edit and delete Figures
    private ImageView deleteFigures;
    private ImageView changeColor;
    private int[] colour = {183, 149, 11};
    //Zoom Image
    private ImageView extendsImage;
    //Filters
    private ImageView filterImage;
    //Change Layout
    private ImageView segmentation;
    //Layout for Image RX
    private LinearLayout layoutImageRx;
    private LinearLayout layoutImageRx1;
    //View
    private ListFigure myListFigures;
    private ListSegmentation myListSegmentation;
    //Animation
    private MyAnimation Animation;
    //Scroll
    private ScrollView scrollLeft1;
    private ScrollView scrollLeft2;
    private ScrollView scrollRight1;
    private ScrollView scrollRight2;
    //Back and Checks
    private ImageView backMenuLeft; //back menu left
    private ImageView backMenuRight; //back menu right
    //Segments
    private ImageView deleteSegments;
    private ImageView changeColorSegments;
    private LinearLayout zoomImageLayout;
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
    private Bitmap original; //img original format Bitmap
    private Mat img;
    //--End Attributes of class
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ORIENTATION FALSE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Add Layout Activity XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Load OpenCV
        OpenCVLoader.initDebug();
        //Initializing Properties
        initialProperties();
        //Back menu left
        backMenuLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(backMenuLeft,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                segmentation.setColorFilter(Color.rgb(255,255,255));
                scrollLeft2.setVisibility(View.GONE);
                scrollLeft1.setVisibility(View.VISIBLE);
                layoutImageRx1.setVisibility(View.GONE);
                layoutImageRx.setVisibility(View.VISIBLE);
            }
        });
        //Change of layout to "Segmentation"
        segmentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(segmentation,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                segmentation.setColorFilter(Color.rgb(255,127,80));
                layoutImageRx.setVisibility(View.GONE);
                layoutImageRx1.setVisibility(View.VISIBLE);
                scrollLeft1.setVisibility(View.GONE);
                scrollLeft2.setVisibility(View.VISIBLE);
            }
        });
        //back menu right
        backMenuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(backMenuRight,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                filterImage.setColorFilter(Color.rgb(255,255,255));
                scrollRight2.setVisibility(View.GONE);
                scrollRight1.setVisibility(View.VISIBLE);
            }
        });
        //Add Filter Image
        filterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(filterImage,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                filterImage.setColorFilter(Color.rgb(255,127,80));
                scrollRight1.setVisibility(View.GONE);
                scrollRight2.setVisibility(View.VISIBLE);
            }
        });
        //Zoom Image RX
        extendsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(extendsImage,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                if(myListFigures.getModeTouch()==0) {
                    extendsImage.setColorFilter(Color.rgb(255, 127, 80)); //orange
                }else{
                    extendsImage.setColorFilter(Color.rgb(255, 255, 255)); //white
                }
                myListFigures.changeModeTouch();
            }
        });
        //Add Point
        creatorPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorPoints,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addPoint(100,100);
            }
        });
        //Add Circle
        creatorCircles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorCircles,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addCircle(100,100,100);
            }
        });
        //Add Rectangle
        creatorRectangles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorRectangles,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addRectangle(0,100,400,300);
            }
        });
        //Add Line
        creatorLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorLines,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addLine(0,100,400,300);
            }
        });
        //Add Ellipse
        creatorEllipses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(creatorEllipses,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListFigures.addEllipse(0,100,400,300);
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
        //Change Colour of select figure
        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(changeColor,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                Toast toast;
                Random r = new Random();
                int result = r.nextInt(10);
                colour = Util.getCollections()[result];
                if( myListFigures.changeColour(colour))
                    toast = Toast.makeText(getApplicationContext(),"Change Successfully",Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(getApplicationContext(),"Isn't Selected Figure",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        //Delete the after segment
        deleteSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(deleteSegments,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListSegmentation.after();
            }
        });
        //change color all segments
        changeColorSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(changeColorSegments,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                Toast toast;
                Random r = new Random();
                int result = r.nextInt(10);
                colour = Util.getCollections()[result];
                if( myListSegmentation.changeColour(colour))
                    toast = Toast.makeText(getApplicationContext(),"Change Successfully",Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(getApplicationContext(),"Isn't Selected Figure",Toast.LENGTH_SHORT);
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
            Drawable d = new BitmapDrawable(getResources(), myFilters.filterCanny());
            layoutImageRx.setBackground(d);
            }
        });
        //Filter RGB is image original
        openCv3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                Drawable d = new BitmapDrawable(getResources(),myFilters.filterRGB());
                layoutImageRx.setBackground(d);
            }
        });
        //Filter morph
        openCv2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Drawable d = new BitmapDrawable(getResources(), myFilters.filterMorph());
                layoutImageRx.setBackground(d);
            }
        });
        //Filter SEPIA
        openCv1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Drawable d = new BitmapDrawable(getResources(),myFilters.filerSepia());
                layoutImageRx.setBackground(d);
            }
        });
        //filter summer
        openCv4.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
             Drawable d = new BitmapDrawable(getResources(),myFilters.filterSummer());
             layoutImageRx.setBackground(d);
           }
                     });
        //filter pink
        openCv5.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
            Drawable d = new BitmapDrawable(getResources(),myFilters.filterPink());
            layoutImageRx.setBackground(d);
        }
                  });
        //filter reduce colors gray
        openCv6.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Drawable d = new BitmapDrawable(getResources(),myFilters.filterReduceColorsGray(5));
                layoutImageRx.setBackground(d);
            }
        });
        //filters reduce Colors
        openCv7.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Drawable d = new BitmapDrawable(getResources(),myFilters.filterReduceColors(80,15,10));
                layoutImageRx.setBackground(d);
            }
        });
        //filter Pencil
        openCv8.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Drawable d = new BitmapDrawable(getResources(),myFilters.filterPencil());
                layoutImageRx.setBackground(d);
            }
        });
        //filter Carton
        openCv9.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Drawable d = new BitmapDrawable(getResources(),myFilters.filterCarton(80,15,10));
                layoutImageRx.setBackground(d);
            }
        });
        //filter Carton
        openCv10.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int result = r.nextInt(20);
                Drawable d = new BitmapDrawable(getResources(),myFilters.filterColor(result));
                layoutImageRx.setBackground(d);
            }
        });
    }
    /**
     * Method initial Properties Initializing Properties of Activity
     * */
    private void initialProperties(){
        //Initializing properties--
        extendsImage = findViewById(R.id.extendsImage);
        filterImage = findViewById(R.id.filter);
        creatorCircles = findViewById(R.id.addCircle);
        creatorCircles.setColorFilter(Color.rgb(255,255,255));
        creatorRectangles = findViewById(R.id.addRectangle);
        creatorLines = findViewById(R.id.addLine);
        creatorEllipses = findViewById(R.id.addEllipse);
        creatorPoints = findViewById(R.id.addPoint);
        deleteFigures = findViewById(R.id.deleteFigures);
        changeColor = findViewById(R.id.changeColor);
        segmentation = findViewById(R.id.segmentation);
        //LinearLayout menu_left = findViewById(R.id.menu_left);
        //LinearLayout menu_right = findViewById(R.id.menu_right);
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
        scrollRight1 = findViewById(R.id.scrollRight_1);
        scrollRight2 = findViewById(R.id.scrollRight_2);
        //Back and Checks
        backMenuLeft = findViewById(R.id.back_menu_left_1);
        backMenuRight = findViewById(R.id.back_menu_right_1);
        //Segmentation
        CardView cardView = findViewById(R.id.zoomImage_1); //CARD
        cardView.setVisibility(View.GONE);
        zoomImageLayout = findViewById(R.id.zoomLayoutImageRx_1); //Layout Zoom Image Ray-X
        zoomImageLayout.setVisibility(View.INVISIBLE);
        myListSegmentation = new ListSegmentation(this, zoomImageLayout, cardView);
        layoutImageRx1 = findViewById(R.id.layoutImageRx_1); //Layout Image Ray-X
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
        //IMAGE
        img = null;
        try{
            int d1 = R.drawable.rx_image_1;
            img = Utils.loadResource(getApplicationContext(),d1);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            this.original = BitmapFactory.decodeResource(getResources(), R.drawable.rx_image_1, options);
        }catch (IOException e) {
            e.printStackTrace();
        }
        myFilters = new MyFilters(this.img,this.original);
        //--End Initializing
    }//End Method
    /*
    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                System.out.println("test Time");
                //menu_left.setVisibility(View.INVISIBLE);
                //menu_right.setVisibility(View.INVISIBLE);
            }
        }, milisegundos);
    }*/
}//End Class
