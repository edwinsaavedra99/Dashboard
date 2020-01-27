package com.example.dashboard;
//Imports
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Random;
import ListFigures.ListSegmentation;
import ListFigures.Util;
/**
 * This class define Segmentation Activity add small segments in Image rX
 * @author Edwin Saavedra
 * @version 3
 */
public class SegmentationActivity extends AppCompatActivity {
    //Class Attributes--
    private ListSegmentation myListSegmentation;
    private ImageView deleteSegments;
    private ImageView changeColorSegments;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ORIENTATION FALSE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Add Layout Activity XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segmentation);
        //Initializing Properties
        initialProperties();
        //delete the last Segment
        deleteSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListSegmentation.after();
            }
        });
        //change Colour segments
        changeColorSegments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                Random r = new Random();
                int result = r.nextInt(10);
                int [] colour = Util.getCollections()[result];
                if( myListSegmentation.changeColour(colour))
                    toast = Toast.makeText(getApplicationContext(),"Change Successfully",Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(getApplicationContext(),"Isn't Selected Figure",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
    /**
     * Method initial Properties Initializing Properties of Activity
     * */
    private void initialProperties(){
        deleteSegments = findViewById(R.id.deletesSegment); //Delete the last segment
        changeColorSegments = findViewById(R.id.changeColorSegment); //Change Colour segments
        LinearLayout zoomImageLayout = findViewById(R.id.zoomLayoutImageRx); //Layout Zoom Image Ray-X
        zoomImageLayout.setVisibility(View.INVISIBLE);
        myListSegmentation = new ListSegmentation(this, zoomImageLayout);
        LinearLayout layoutImageRxSegmentation = findViewById(R.id.layoutImageRxSegmentation); //Layout Image Ray-X
        layoutImageRxSegmentation.addView(myListSegmentation);
    }
}