package com.example.dashboard;
//Imports
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
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
    private ImageView load;
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
    private CardView cardView;
    private LinearLayout zoomImageLayout;
    //Filters
    private ImageView openCv;
    private ImageView openCv1;
    private ImageView openCv2;
    private ImageView openCv3;
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
                myListFigures.testLayout();
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
        deleteSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation.animationScale(deleteSegments,TIME_ANIMATION,SCALE_ANIMATION,SCALE_ANIMATION);
                myListSegmentation.after();
            }
        });
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
        zoomImageLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast toast;
                toast = Toast.makeText(getApplicationContext(),"Touch",Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        });
        //filtre canny
        openCv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Mat img = null;
                try{
                    int d1 = R.drawable.rx_image_1;
                    img = Utils.loadResource(getApplicationContext(),d1);

                }catch (IOException e){
                    e.printStackTrace();
                }
                Imgproc.cvtColor(img,img,Imgproc.COLOR_RGB2BGRA);
                Mat img_result = img.clone();
                Imgproc.Canny(img,img_result,80,90);
                Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(),img_result.rows(),Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(img_result,img_bitmap);
                //result esta en img_bitamp
                Drawable d = new BitmapDrawable(getResources(),img_bitmap);
                layoutImageRx.setBackground(d);

            }
        });



//Filter normal
                openCv3.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        Mat img = null;
                        try{
                            int d1 = R.drawable.rx_image_1;
                            img = Utils.loadResource(getApplicationContext(),d1);

                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        //Imgproc.cvtColor(img,img,Imgproc.COLOR_RGB2BGRA);
                        Mat img_result = img.clone();
                        //Imgproc.Canny(img,img_result,80,90);
                        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(),img_result.rows(),Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(img_result,img_bitmap);
                        //result esta en img_bitamp
                        Drawable d = new BitmapDrawable(getResources(),img_bitmap);
                        layoutImageRx.setBackground(d);

                    }
                });



                //Filter
                openCv2.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        Mat img = null;
                        try{
                            int d1 = R.drawable.rx_image_1;
                            img = Utils.loadResource(getApplicationContext(),d1);

                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        Imgproc.cvtColor(img,img,Imgproc.COLOR_RGB2BGRA);
                        Mat img_result = img.clone();
                        //Imgproc.GaussianBlur(img,img_result,new Size(13,7),8);
                        //Imgproc.Canny(img,img_result,80,90);
                        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(3,3));
                        //Imgproc.erode(img,img_result,kernel);
                        Imgproc.morphologyEx(img,img_result,Imgproc.MORPH_GRADIENT,kernel);
                        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(),img_result.rows(),Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(img_result,img_bitmap);
                        //result esta en img_bitamp
                        Drawable d = new BitmapDrawable(getResources(),img_bitmap);
                        layoutImageRx.setBackground(d);

                    }
                });



//Filter SEPIA
                openCv1.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        // Fill sepia kernel
                        Mat  mSepiaKernel;
                        mSepiaKernel = new Mat(4, 4, CvType.CV_32F);
                        mSepiaKernel.put(0, 0, /* R */0.189f, 0.769f, 0.393f, 0f);
                        mSepiaKernel.put(1, 0, /* G */0.168f, 0.686f, 0.349f, 0f);
                        mSepiaKernel.put(2, 0, /* B */0.131f, 0.534f, 0.272f, 0f);
                        mSepiaKernel.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 1f);
                        Mat img = null;
                        try{
                            int d1 = R.drawable.rx_image_1;
                            img = Utils.loadResource(getApplicationContext(),d1);

                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2RGBA);
                        Mat img_result = img.clone();
                        //Mat sepiaMat = new Mat(img_result.size(), img_result.type());
                        Core.transform(img, img_result, mSepiaKernel);
                        //new SepiaFilter().apply(img_result, sepiaMat);
                        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(img_result, img_bitmap);
                        //result esta en img_bitamp
                        Drawable d = new BitmapDrawable(getResources(),img_bitmap);
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
        load = findViewById(R.id.load);
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
        cardView = findViewById(R.id.zoomImage_1); //CARD
        cardView.setVisibility(View.GONE);
        zoomImageLayout = findViewById(R.id.zoomLayoutImageRx_1); //Layout Zoom Image Ray-X
        zoomImageLayout.setVisibility(View.INVISIBLE);
        myListSegmentation = new ListSegmentation(this, zoomImageLayout,cardView);
        layoutImageRx1 = findViewById(R.id.layoutImageRx_1); //Layout Image Ray-X
        layoutImageRx1.addView(myListSegmentation);
        deleteSegments = findViewById(R.id.deleteSegments);
        changeColorSegments = findViewById(R.id.changeColorSegments);
        //Filters
        openCv = findViewById(R.id.openCV);
        openCv1 = findViewById(R.id.openCV1);
        openCv2 = findViewById(R.id.openCV2);
        openCv3 = findViewById(R.id.openCV3);
        //--End Initializing
    }//End Method
    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                System.out.println("test Time");
                //menu_left.setVisibility(View.INVISIBLE);
                //menu_right.setVisibility(View.INVISIBLE);
            }
        }, milisegundos);
    }
}//End Class
