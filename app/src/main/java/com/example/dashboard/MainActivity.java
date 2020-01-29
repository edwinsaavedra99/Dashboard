package com.example.dashboard;
//Imports
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Random;
import ListFigures.ListFigure;
import ListFigures.Util;
/**
 * This class define the Main Activity Image Ray-X editing space
 * @author Edwin Saavedra
 * @version 3
 */
public class MainActivity extends AppCompatActivity {
    public static int MILISEGUNDOS_ESPERA = 5000;
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
    //Edit and delete Figures
    private ImageView deleteFigures;
    private ImageView changeColor;
    private ImageView menu;
    private int[] colour = {183, 149, 11};
    //Zoom Image
    private ImageView extendsImage;
    //Change Activity
    private ImageView segmentation;
    //Layout for Image RX
    private LinearLayout layoutImageRx;
    private LinearLayout menu_left;
    private LinearLayout menu_right;
    private ConstraintLayout frame;
    //View
    private ListFigure myListFigures;



    //--End Attributes of class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ORIENTATION FALSE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Add Layout Activity XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing Properties
        initialProperties();
        //Change of activity to "SegmentationActivity"
        segmentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SegmentationActivity.class);
                startActivity(i);
            }
        });
        //Zoom Image RX
        extendsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutImageRx.setPivotX(myListFigures.getXTouch());
                layoutImageRx.setPivotY(myListFigures.getYTouch());
                layoutImageRx.animate().scaleX(3).scaleY(3);
            }
        });
        //Add Circle
        creatorCircles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListFigures.addCircle(100,100,100);
            }
        });
        //Add Rectangle
        creatorRectangles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListFigures.addRectangle(0,100,400,300);
            }
        });
        //Add Line
        creatorLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListFigures.addLine(0,100,400,300);
            }
        });
        //Add Ellipse
        creatorEllipses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListFigures.addEllipse(0,100,400,300);
            }
        });
        //Delete Select Figure
        deleteFigures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

       /* frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_left.setVisibility(View.VISIBLE);
                menu_right.setVisibility(View.VISIBLE);
                esperarYCerrar(MILISEGUNDOS_ESPERA);
            }
        });*/
    }
    /**
     * Method initial Properties Initializing Properties of Activity
     * */
    private void initialProperties(){
        //Profile Image Circle--
        Drawable originalDrawable = getResources().getDrawable(R.drawable.programador);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        if (originalBitmap.getWidth() > originalBitmap.getHeight())
            originalBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getHeight(), originalBitmap.getHeight());
        else if (originalBitmap.getWidth() < originalBitmap.getHeight())
            originalBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getWidth());
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(),originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        ImageView imageView = findViewById(R.id.perfil);
        imageView.setImageDrawable(roundedDrawable);
        //--End Profile Image Circle
        //Initializing properties--
        extendsImage = findViewById(R.id.extendsImage);
        menu = findViewById(R.id.menu);
        creatorCircles = findViewById(R.id.addCircle);
        creatorRectangles = findViewById(R.id.addRectangle);
        creatorLines = findViewById(R.id.addLine);
        creatorEllipses = findViewById(R.id.addEllipse);
        deleteFigures = findViewById(R.id.deleteFigures);
        changeColor = findViewById(R.id.changeColor);
        segmentation = findViewById(R.id.segmentation);
        menu_left = findViewById(R.id.menu_left);
        menu_right = findViewById(R.id.menu_right);
        frame = findViewById(R.id.frame);
        //NOTA NO PUEDO OCULTAR ELEMENTOS DE UN GRID LAYOUT ***********************
        //menu_left.setVisibility(View.INVISIBLE);
        //menu_right.setVisibility(View.INVISIBLE);
        myListFigures = new ListFigure(this,menu_left,menu_right);
        layoutImageRx = findViewById(R.id.layoutImageRx);
        layoutImageRx.addView(myListFigures);
        //--End Initializing
    }//End Method
    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                System.out.println("test Time");
                menu_left.setVisibility(View.INVISIBLE);
                menu_right.setVisibility(View.INVISIBLE);
            }
        }, milisegundos);
    }
}//End Class