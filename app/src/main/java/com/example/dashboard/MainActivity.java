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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ListFigures.ListFigure;
import ListFigures.ListSegmentation;
import ListFigures.Util;

import static org.opencv.core.Core.LUT;
import static org.opencv.core.CvType.CV_8UC1;

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
    private ImageView openCv4;
    private ImageView openCv5;
    private ImageView openCv6;
    private ImageView openCv7;
    private ImageView openCv8;
    private ImageView openCv9;
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
                openCv4.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        Mat img = null;
                        try{
                            int d1 = R.drawable.rx_image_1;
                            img = Utils.loadResource(getApplicationContext(),d1);

                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        Imgproc.applyColorMap(img,img,Imgproc.COLORMAP_SUMMER);
                        Mat img_result = img.clone();
                        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(img_result, img_bitmap);
                        //result esta en img_bitamp
                        Drawable d = new BitmapDrawable(getResources(),img_bitmap);
                        layoutImageRx.setBackground(d);
                    }
                });
            openCv5.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    Mat img = null;
                    try{
                        int d1 = R.drawable.rx_image_1;
                        img = Utils.loadResource(getApplicationContext(),d1);

                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    Imgproc.applyColorMap(img,img,Imgproc.COLORMAP_PINK);
                    Mat img_result = img.clone();
                    Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(img_result, img_bitmap);
                    //result esta en img_bitamp
                    Drawable d = new BitmapDrawable(getResources(),img_bitmap);
                    layoutImageRx.setBackground(d);
                }
            });
        openCv6.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Mat img = null;
                try{
                    int d1 = R.drawable.rx_image_1;
                    img = Utils.loadResource(getApplicationContext(),d1);

                }catch (IOException e) {
                    e.printStackTrace();
                }
                Imgproc.cvtColor(img,img,Imgproc.COLOR_BGR2GRAY);
                Mat img_result = img.clone();
                img_result = reduceColorsGray(img_result, 5);
                Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(img_result, img_bitmap);
                //result esta en img_bitamp
                Drawable d = new BitmapDrawable(getResources(),img_bitmap);
                layoutImageRx.setBackground(d);
            }
        });
        openCv7.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Mat img = null;
                try{
                    int d1 = R.drawable.rx_image_1;
                    img = Utils.loadResource(getApplicationContext(),d1);

                }catch (IOException e) {
                    e.printStackTrace();
                }
                Imgproc.cvtColor(img,img,Imgproc.COLOR_BGR2GRAY);
                Mat img_result = img.clone();
                /**/
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false; // Leaving it to true enlarges the decoded image size.
                Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.rx_image_1, options);
                Mat img1 = new Mat();
                Utils.bitmapToMat(original, img1);
                img_result = reduceColors(img1, 80, 15, 10);
                /**/
                //img_result = reduceColors(img_result, 5,5,5);
                Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(img_result, img_bitmap);
                //result esta en img_bitamp
                Drawable d = new BitmapDrawable(getResources(),img_bitmap);
                layoutImageRx.setBackground(d);
            }
        });
        openCv8.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Mat img = null;
                try{
                    int d1 = R.drawable.rx_image_1;
                    img = Utils.loadResource(getApplicationContext(),d1);

                }catch (IOException e) {
                    e.printStackTrace();
                }
                Imgproc.cvtColor(img,img,Imgproc.COLOR_BGR2GRAY);
                Mat img_result = img.clone();
                Imgproc.adaptiveThreshold(img_result,img_result,255,
                        Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,9,2);
                Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(img_result, img_bitmap);
                //result esta en img_bitamp
                Drawable d = new BitmapDrawable(getResources(),img_bitmap);
                layoutImageRx.setBackground(d);
            }
        });
        openCv9.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Mat img = null;
                try{
                    int d1 = R.drawable.rx_image_1;
                    img = Utils.loadResource(getApplicationContext(),d1);

                }catch (IOException e) {
                    e.printStackTrace();
                }
                Imgproc.cvtColor(img,img,Imgproc.COLOR_BGR2GRAY);
                Mat img_result = img.clone();
                //
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false; // Leaving it to true enlarges the decoded image size.
                Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.rx_image_1, options);
                Mat img1 = new Mat();
                Utils.bitmapToMat(original, img1);
                Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGRA2BGR);
                img_result = cartoon(img1, 80, 15, 10);
                //img_result = cartoon(img_result, 80, 15, 10);
                //
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
        openCv4 = findViewById(R.id.openCV4);
        openCv5 = findViewById(R.id.openCV5);
        openCv6 = findViewById(R.id.openCV6);
        openCv7 = findViewById(R.id.openCV7);
        openCv8 = findViewById(R.id.openCV8);
        openCv9 = findViewById(R.id.openCV9);
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
    public Mat createLUT(int numColors) {
        // When numColors=1 the LUT will only have 1 color which is black.
        if (numColors < 0 || numColors > 256) {
            System.out.println("Invalid Number of Colors. It must be between 0 and 256 inclusive.");
            return null;
        }

        Mat lookupTable = Mat.zeros(new Size(1, 256), CV_8UC1);

        int startIdx = 0;
        for (int x = 0; x < 256; x += 256.0 / numColors) {
            lookupTable.put(x, 0, x);

            for (int y = startIdx; y < x; y++) {
                if (lookupTable.get(y, 0)[0] == 0) {
                    lookupTable.put(y, 0, lookupTable.get(x, 0));
                }
            }
            startIdx = x;
        }
        return lookupTable;
    }
    public Mat reduceColorsGray(Mat img, int numColors) {
        Mat LUT = createLUT(numColors);
        LUT(img, LUT, img);
        return img;
    }
    public Mat reduceColors(Mat img, int numRed, int numGreen, int numBlue) {
        Mat redLUT = createLUT(numRed);
        Mat greenLUT = createLUT(numGreen);
        Mat blueLUT = createLUT(numBlue);

        List<Mat> BGR = new ArrayList<>(3);
        //BGR.add(blueLUT);
        //BGR.add(greenLUT);
        //BGR.add(redLUT);
        Core.split(img, BGR);

        LUT(BGR.get(0), blueLUT, BGR.get(0));
        LUT(BGR.get(1), greenLUT, BGR.get(1));
        LUT(BGR.get(2), redLUT, BGR.get(2));

        Core.merge(BGR, img);

        return img;
    }
    public Mat cartoon(Mat img, int numRed, int numGreen, int numBlue) {
        Mat reducedColorImage = reduceColors(img, numRed, numGreen, numBlue);

        Mat result = new Mat();
        Imgproc.cvtColor(img, result, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(result, result, 15);

        Imgproc.adaptiveThreshold(result, result, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 2);

        Imgproc.cvtColor(result, result, Imgproc.COLOR_GRAY2BGR);

        Core.bitwise_and(reducedColorImage, result, result);

        return result;
    }

}//End Class
