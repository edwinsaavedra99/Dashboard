package com.example.dashboard.ListFigures;
//Imports
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import com.example.dashboard.Figures.*;
import com.example.dashboard.Resources.Resource;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * This class Canvas defines a list of figures in a view
 * @author Edwin Saavedra
 * @version 3
 */
@SuppressLint("ViewConstructor")
public class ListFigure  extends View {
    //Class Attributes
    private int figureSelected;
    private ArrayList<Figure> myFigures;
    private Paint pencil;
    private int[] figureColour = {183, 149, 11};
    private float getPastX = 0;
    private float getPastY = 0;
    private DisplayMetrics metrics;
    private float touchX = 0;
    private int alto=0,ancho= 0;
    private float touchY = 0;
    private float mPositionX;
    private float mPositionY;
    // acceptDistance is the acceptable distance to interact with the figure
    private float acceptDistance = 30;
    private float generalHeight = 0;
    private float generalWidth = 0;
    int mode=0; //Mode 1 is Resize... mode 2 is Move
    private  LinearLayout layout;
    //Mode Touch
    private int modeTouch = 0; //Mode 0 is Normal ... Mode 1 es zoomMode
    //PicToZoom
    private ScaleGestureDetector scaleGestureDetector;
    private Drawable mBoard;
    private float mBoardWidth;
    private  float mBoardHeght;
    private float mScaleFactor = 1.f;
    private Bitmap mImage;
    private  int mImageWidth;
    private int mImageHeight;
    /**
     * Class Constructor
     * @param context The View*/
    public ListFigure(Context context,LinearLayout _layout,DisplayMetrics metrics){
        super(context);
        myFigures = new ArrayList<>();
        layout = _layout;
        this.metrics = metrics;
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        figureSelected = -1;
        initialStyleFigure();
        invalidate();
    }//Closing the class constructor
    public void loadImage(Bitmap mImage){
        invalidate();
        mBoard = new BitmapDrawable(getResources(),mImage);
        this.mImage = mImage;
        invalidate();
    }
    /**
     * Method changeModeTouch : change modeTouch if is 0 change to 1 else change to 0
     * */
    public void changeModeTouch(){
        if(modeTouch == 0){
            modeTouch =1;
        }else{
            modeTouch = 0;
        }
    }

    public void readDataIndicators(JSONArray jsonArray,float width, float height) throws JSONException {
        int altoCa = metrics.heightPixels;
        int anchoCa = metrics.widthPixels;
        float medioCa = (float) altoCa/anchoCa;
        int altoIm = mBoard.getIntrinsicHeight();
        int anchoIm = mBoard.getIntrinsicWidth();
        float medioIm = (float)altoIm/anchoIm;
        if(medioCa<medioIm){
            ancho = anchoCa;
            alto =(int)(medioIm*ancho);
        }else{
            alto = altoCa;
            ancho = (int) (alto/medioIm);
        }
        generalWidth = ancho;
        generalHeight = alto;
        System.out.println("information 1 : generalWidth - "+generalWidth+", generalHeight - "+generalHeight);
        System.out.println("information 2 : generalWidth - "+width+", generalHeight - "+height);
        figureSelected = -1;
        myFigures.clear();
        invalidate();
        //requestLayout();
        for (int i = 0; i< jsonArray.length(); i++){
            JSONObject aux = jsonArray.getJSONObject(i);
            int type = Integer.parseInt(aux.getString("type"));
            int[] colour = {Integer.parseInt(aux.getString("r")), Integer.parseInt(aux.getString("g")),Integer.parseInt(aux.getString("b"))};
            switch (type){
                case 1:{ //circle
                    float x = Float.parseFloat(aux.getString("x"))*generalWidth/width;
                    float y = Float.parseFloat(aux.getString("y"))*generalHeight/height;
                    float radius = Float.parseFloat(aux.getString("radius"))*generalWidth/width;
                    addCircle(x,y,radius);
                    break;
                }
                case 2:{
                    float left = Float.parseFloat(aux.getString("left"))*generalWidth/width;
                    float top = Float.parseFloat(aux.getString("top"))*generalHeight/height;
                    float right = Float.parseFloat(aux.getString("right"))*generalWidth/width;
                    float bottom = Float.parseFloat(aux.getString("bottom"))*generalHeight/height;
                    addEllipse(left,top,right,bottom);
                    break;
                }
                case 3:{
                    float left = Float.parseFloat(aux.getString("left"))*generalWidth/width;
                    float top = Float.parseFloat(aux.getString("top"))*generalHeight/height;
                    float right = Float.parseFloat(aux.getString("right"))*generalWidth/width;
                    float bottom = Float.parseFloat(aux.getString("bottom"))*generalHeight/height;
                    addRectangle(left,top,right,bottom);
                    break;
                }
                case 4:{
                    float startX = Float.parseFloat(aux.getString("startX"))*generalWidth/width;
                    float startY = Float.parseFloat(aux.getString("startY"))*generalHeight/height;
                    float stopX = Float.parseFloat(aux.getString("stopX"))*generalWidth/width;
                    float stopY = Float.parseFloat(aux.getString("stopY"))*generalHeight/height;
                    addLine(startX,startY,stopX,stopY);
                    break;
                }
                case 5:{
                    float x = Float.parseFloat(aux.getString("x"))*generalWidth/width;
                    float y = Float.parseFloat(aux.getString("y"))*generalHeight/height;
                    addPoint(x,y);
                    break;
                }
            }
        }
        invalidate();
    }

    public JSONArray dataFigures() throws JSONException {
        JSONArray jsonArray =new JSONArray();
        for(int i = 0 ;i<this.myFigures.size();i++){
            if(myFigures.get(i) instanceof Circle){
                Circle aux = (Circle) myFigures.get(i);
                jsonArray.put(aux.getJSONFigure(i));
            }else if(myFigures.get(i) instanceof Rectangle){
                Rectangle rectangle = (Rectangle) myFigures.get(i);
                jsonArray.put(rectangle.getJSONFigure(i));
            }else if(myFigures.get(i) instanceof Ellipse){
                Ellipse ellipse = (Ellipse) myFigures.get(i);
                jsonArray.put(ellipse.getJSONFigure(i));
            }else if(myFigures.get(i) instanceof  Line){
                Line line = (Line) myFigures.get(i);
                jsonArray.put(line.getJSONFigure(i));
            }else if(myFigures.get(i) instanceof Point){
                Point point = (Point) myFigures.get(i);
                jsonArray.put(point.getJSONFigure(i));
            }

        }
        return jsonArray;
    }//End Method
    public ArrayList<Figure> getMyFigures() {
        return myFigures;
    }

    public void setMyFigures(ArrayList<Figure> myFigures) {
        this.myFigures = myFigures;
    }

    public float getGeneralHeight() {
        return generalHeight;
    }

    public float getGeneralWidth() {
        return generalWidth;
    }

    /**
     * Method getModeTouch
     * @return modeTouch
     * */
    public int getModeTouch(){
        return modeTouch;
    }
    /**
     * Class ScaleListener is custom setting of SimpleOnScaleGestureListener
     ** */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            float m_MIN_ZOOM = 1.0f;
            float m_MAX_ZOOM = 3.0f;
            mScaleFactor = Math.max(m_MIN_ZOOM,Math.min(mScaleFactor, m_MAX_ZOOM));
            invalidate();
            return true;
        }
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector){
            return true;
        }
    }
    /**
     * Method changeOrderList : change a element in the list of figures
     * if and only if the element is not the index entered as a parameter and getX and getY are inside
     * @param getX x of point
     * @param getY y of point
     * @param index
     * * */
    public void changeOrderList(float getX, float getY , int index){
        for(int i = 0; i<myFigures.size();i++){
            if(i!=index){
                if(myFigures.get(i) instanceof  Point) {
                    Point temp = (Point) myFigures.get(i);
                    float distance = distancePoint_to_Point(getX, getY, temp.getCenterX(), temp.getCenterY());
                    if(distance <= acceptDistance*2){
                        myFigures.remove(i);
                        myFigures.add(0,temp);
                    }
                }else if (myFigures.get(i) instanceof Circle) {
                    Circle temp = (Circle) myFigures.get(i);
                    float distance = distancePoint_to_Point(getX, getY, temp.getCenterX(), temp.getCenterY());
                    if (distance <= temp.getRadius()) {
                        myFigures.remove(i);
                        myFigures.add(0,temp);
                    }
                } else if (myFigures.get(i) instanceof Rectangle) {
                    Rectangle temp = (Rectangle) myFigures.get(i);
                    if (getX <= temp.getRight() && getX >= temp.getLeft() && getY <= temp.getBottom() && getY >= temp.getTop()) {
                        myFigures.remove(i);
                        myFigures.add(0,temp);
                    }
                } else if (myFigures.get(i) instanceof Line) {
                    Line temp = (Line) myFigures.get(i);
                    float distanceFirstPoint = distancePoint_to_Point(getX,getY,temp.getStartX(),temp.getStartY());
                    float distanceSecondPoint = distancePoint_to_Point(getX,getY,temp.getStopX(),temp.getStopY());
                    if (temp.distancePoint_to_Line(getX, getY) <= acceptDistance
                            &&distanceFirstPoint>acceptDistance && distanceSecondPoint>acceptDistance) {
                        myFigures.remove(i);
                        myFigures.add(0,temp);
                    }
                } else if (myFigures.get(i) instanceof Ellipse) {
                    Ellipse temp = (Ellipse) myFigures.get(i);
                    if(temp.pointEquationX1(getY) >= getX && temp.pointEquationX2(getY) <= getX
                            && temp.pointEquationY1(getX) >= getY && temp.pointEquationY2(getX) <= getY){
                        myFigures.remove(i);
                        myFigures.add(0,temp);
                    }
                }
            }
        }
    }
    /**
     * Method initialStyleFigure define the parameters initials of the pencil Paint
     */
    public void initialStyleFigure(){
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        pencil = new Paint();
        pencil.setAntiAlias(true);
        pencil.setARGB(250, figureColour[0],figureColour[1],figureColour[2]);
        pencil.setStrokeWidth(4);
        pencil.setStyle(Paint.Style.STROKE);
        pencil.setPathEffect(dashPathEffect);
    }//End Method
    /**
     * Method addPoint add a point in the list of figures
     * @param _centerX Define the position of the point
     * @param _centerY Define the position of the point*/
    public void addPoint(float _centerX, float _centerY) {
        Resource.changeSaveFigures = false;
        Point aux = new Point(_centerX, _centerY, pencil,figureColour);
        this.myFigures.add(aux);
        figureSelected = myFigures.size()-1;
        invalidate();
    }//End Method
    /**
     * Method addCircle add a circle in the list of figures
     * @param _startX Define the position of the circle
     * @param _startY Define the position of the circle
     * @param _radius Define the radius od the circle*/
    public void addCircle(float _startX, float _startY, float _radius) {
        Resource.changeSaveFigures = false;
        Circle aux = new Circle(_startX, _startY, _radius, pencil,figureColour);
        this.myFigures.add(aux);
        figureSelected = myFigures.size()-1;
        invalidate();
    }//End Method
    /**
     * Method addRectangle add a rectangle in the list of figures
     * @param _left Define the position of the rectangle from the left
     * @param _top Define the position of the rectangle from above
     * @param _right Define the position of the rectangle from the right
     * @param _bottom Define the position of the rectangle from the below*/
    public void addRectangle(float _left, float _top, float _right, float _bottom) {
        Resource.changeSaveFigures = false;
        Rectangle aux = new Rectangle(_left, _top, _right, _bottom, pencil,figureColour);
        this.myFigures.add(aux);
        figureSelected = myFigures.size()-1;
        invalidate();
    }//End Method
    /**
     * Method addEllipse add a ellipse in the list of figures
     * @param _left Define the position of the ellipse from the left
     * @param _top Define the position of the ellipse from above
     * @param _right Define the position of the ellipse from the right
     * @param _bottom Define the position of the ellipse from the below*/
    public void addEllipse(float _left, float _top, float _right, float _bottom) {
        Resource.changeSaveFigures = false;
        Ellipse aux = new Ellipse(_left, _top, _right, _bottom, pencil,figureColour);
        this.myFigures.add(aux);
        figureSelected = myFigures.size()-1;
        invalidate();
    }//End Method
    /**
     * Method addLine add a line in the list of figures
     * @param _startX Define the start Coordinate X
     * @param _startY Define the start Coordinate Y
     * @param _stopX Define the stop Coordinate X
     * @param _stopY Define the stop Coordinate Y*/
    public void addLine(float _startX , float _startY, float _stopX, float _stopY) {
        Resource.changeSaveFigures = false;
        Line aux = new Line(_startX, _startY, _stopX, _stopY, pencil,figureColour);
        this.myFigures.add(aux);
        figureSelected = myFigures.size()-1;
        invalidate();
    }//End Method
    /**
     * Method deleteFigure the selected figure is deleted
     * */
    public boolean deleteFigure() {
        if (myFigures.size()>0 && this.figureSelected != -1 ) {
            myFigures.remove(this.figureSelected);
            figureSelected = -1;
            invalidate(); //Redraw
            Resource.changeSaveFigures = false;
            return true;
        }else{
            return false;
        }
    }//End Method

    @SuppressLint("WrongThread")
    public String getBase64String(){ //1 - formato STRING -- 2 - formato ARRAYLIST
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        mImage.compress(Bitmap.CompressFormat.JPEG,40,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
//        System.out.println(Base64.encodeToString(bytes,Base64.DEFAULT));
        return Base64.encodeToString(bytes,Base64.DEFAULT);

    }
    public Bitmap decodeBase64AndSetImage(String complete){
        String image = complete.substring(complete.indexOf(",")+1);
        InputStream stream = new ByteArrayInputStream(Base64.decode(image.getBytes(),Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return bitmap;
    }

    public boolean isSelectedFigure(){
        return this.figureSelected != -1;
    }
    public boolean setDescriptionFigure(String description){
        if (myFigures.size()>0 && this.figureSelected != -1 ) {
            myFigures.get(this.figureSelected).setDescription(description);
            Resource.changeSaveFigures = false;
            return true;
        }else{
            return false;
        }
    }
    public String getDescriptionFigure(){
        if (myFigures.size()>0 && this.figureSelected != -1 ) {
            return myFigures.get(this.figureSelected).getDescription();
        }else{
            return "";
        }
    }
    /**
     * Method changeColour the selected figure changes color
     * */
    public boolean changeColour(int [] colour) {
        if (myFigures.size() >0 && this.figureSelected != -1) {
            myFigures.get(this.figureSelected).setColour(colour);
            myFigures.get(this.figureSelected).getPaint().setARGB(255,colour[0],colour[1],colour[2]);
            invalidate(); //Redraw
            Resource.changeSaveFigures = false;
            return true;
        }else {
            return false;
        }
    }//End Method
    /**
     * Method toString show list of figures content
     * @return content of the list*/
    public String toString(){
        StringBuilder listJson = new StringBuilder("[\n");
        for(int i=0;i<this.myFigures.size();i++) {
            listJson.append(myFigures.get(i).toString()).append("\n");
            if (i < this.myFigures.size() - 1)
                listJson.append(",");
        }
        return listJson+"]";
    }//End Method
    /*
     Method clearList deleted the list
     public void clearList(){
     this.myFigures.clear();
     }
    */
    /*Method toUpdate reDraw the content - is invalidate method
     public void toUpdate() {
     invalidate();
    }
    */
    /**
     * Method distancePoint to Point define tha distance between two points
     * @param pointX X Coordinate of the first point
     * @param pointX1 X Coordinate of the second point
     * @param pointY Y Coordinate of the first point
     * @param pointY1 Y Coordinate of the second point
     * @return distance*/
    public float distancePoint_to_Point(float pointX, float pointY, float pointX1, float pointY1){
        float valX = pointX - pointX1;
        float valY = pointY - pointY1;
        double distance = Math.sqrt(valX * valX + valY * valY);
        return (float) distance;
    }//Closing the Method



    /**
     * Method onDraw draw the figure geometric
     * @param canvas area of draw*/
    @SuppressLint("CanvasSize")
    protected void onDraw(Canvas canvas) {
        //int altoCa = getBottom();
        //int anchoCa = getRight();
        int altoCa = metrics.heightPixels;
        int anchoCa = metrics.widthPixels;
        float medioCa = (float) altoCa/anchoCa;
            int altoIm = mBoard.getIntrinsicHeight();
            int anchoIm = mBoard.getIntrinsicWidth();
            float medioIm = (float)altoIm/anchoIm;
            if(medioCa<medioIm){
                ancho = anchoCa;
                alto =(int)(medioIm*ancho);
            }else{
                alto = altoCa;
                ancho = (int) (alto/medioIm);
            }
        generalWidth = ancho;
        generalHeight = alto;
        if(mBoard !=null){
            canvas.save();
            mBoard.setBounds(0,0,ancho,alto);
            mImageHeight = alto;
            mImageWidth = ancho;
            if(mPositionX*-1<0){
                mPositionX = 0;
            }else if((mPositionX*-1)>mImageWidth*mScaleFactor-getWidth()){
                mPositionX = (mImageWidth * mScaleFactor-getWidth())*-1;
            }
            if(mPositionY*-1<0){
                mPositionY = 0;
            }else if((mPositionY*-1)>mImageHeight*mScaleFactor-getHeight()){
                mPositionY = (mImageHeight * mScaleFactor-getHeight())*-1;
            }
            if((mImageHeight*mScaleFactor<getHeight())){
                mPositionY = 0;
            }
            canvas.translate(mPositionX,mPositionY);
            canvas.scale(mScaleFactor,mScaleFactor);
            mBoard.draw(canvas);
        }
        for (int i = 0; i < myFigures.size(); i++) {
            if(myFigures.get(i) instanceof  Point) {
                Point temp = (Point) myFigures.get(i);
                canvas.drawPoint(temp.getCenterX(),temp.getCenterY(),Util.Circle(temp.getColour()));
                canvas.drawCircle(temp.getCenterX(),temp.getCenterY(),acceptDistance*2,Util.Circle(temp.getColour()));
                canvas.drawCircle(temp.getCenterX(), temp.getCenterY(), 9,Util.CircleSmall(temp.getColour()));
                if(i == figureSelected){
                    canvas.drawCircle(temp.getCenterX(),temp.getCenterY(),acceptDistance,Util.CircleTransparent(temp.getColour()));
                }
            }else if (myFigures.get(i) instanceof Circle) {
                Circle temp = (Circle) myFigures.get(i);
                canvas.drawCircle(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), Util.Circle(temp.getColour()));
                canvas.drawCircle(temp.getCenterX()+temp.getRadius(), temp.getCenterY(), acceptDistance/3, Util.CircleSmall(temp.getColour()));
                if(i == figureSelected){
                    canvas.drawCircle(temp.getCenterX()+temp.getRadius(), temp.getCenterY(), acceptDistance, Util.CircleTransparent(temp.getColour()));
                }
            } else if (myFigures.get(i) instanceof Rectangle) {
                Rectangle temp = (Rectangle) myFigures.get(i);
                canvas.drawRect(temp.getLeft(), temp.getTop(), temp.getRight(), temp.getBottom(), Util.Circle(temp.getColour()));
                canvas.drawCircle(temp.getLeft(), temp.getTop(), acceptDistance/3,  Util.CircleSmall(temp.getColour()));
                canvas.drawCircle(temp.getRight(), temp.getBottom(), acceptDistance/3,  Util.CircleSmall(temp.getColour()));
                if(i == figureSelected){
                    canvas.drawCircle(temp.getLeft(), temp.getTop(), acceptDistance, Util.CircleTransparent(temp.getColour()));
                    canvas.drawCircle(temp.getRight(), temp.getBottom(), acceptDistance, Util.CircleTransparent(temp.getColour()));
                }
            }else if (myFigures.get(i) instanceof Line) {
                Line temp = (Line) myFigures.get(i);
                canvas.drawLine(temp.getStartX(), temp.getStartY(), temp.getStopX(), temp.getStopY(), Util.Circle(temp.getColour()));
                canvas.drawCircle(temp.getStartX(), temp.getStartY(), acceptDistance/3,  Util.CircleSmall(temp.getColour()));
                canvas.drawCircle(temp.getStopX(), temp.getStopY(), acceptDistance/3,  Util.CircleSmall(temp.getColour()));
                if(i == figureSelected){
                    canvas.drawCircle(temp.getStartX(), temp.getStartY(), acceptDistance, Util.CircleTransparent(temp.getColour()));
                    canvas.drawCircle(temp.getStopX(), temp.getStopY(), acceptDistance, Util.CircleTransparent(temp.getColour()));
                }
            } else if (myFigures.get(i) instanceof Ellipse) {
                Ellipse temp = (Ellipse) myFigures.get(i);
                @SuppressLint("DrawAllocation") RectF rectAux = new RectF(temp.getLeft(),temp.getTop(),temp.getRight(),temp.getBottom());
                canvas.drawOval(rectAux,Util.Circle(temp.getColour()));
                canvas.drawCircle((temp.getLeft()+temp.getRight())/2, temp.getTop(), acceptDistance/3, Util.CircleSmall(temp.getColour()));
                canvas.drawCircle(temp.getRight(), (temp.getBottom()+temp.getTop())/2, acceptDistance/3, Util.CircleSmall(temp.getColour()));
                canvas.drawCircle((temp.getLeft()+temp.getRight())/2, temp.getBottom(), acceptDistance/3, Util.CircleSmall(temp.getColour()));
                canvas.drawCircle(temp.getLeft(), (temp.getBottom()+temp.getTop())/2, acceptDistance/3, Util.CircleSmall(temp.getColour()));
                if(i == figureSelected){
                    canvas.drawCircle((temp.getLeft()+temp.getRight())/2, temp.getTop(),  acceptDistance, Util.CircleTransparent(temp.getColour()));
                    canvas.drawCircle(temp.getRight(), (temp.getBottom()+temp.getTop())/2, acceptDistance, Util.CircleTransparent(temp.getColour()));
                    canvas.drawCircle((temp.getLeft()+temp.getRight())/2, temp.getBottom(),  acceptDistance, Util.CircleTransparent(temp.getColour()));
                    canvas.drawCircle(temp.getLeft(), (temp.getBottom()+temp.getTop())/2, acceptDistance, Util.CircleTransparent(temp.getColour()));
                }
            } else {
                System.out.println("No Tips");
            }
        }
        canvas.restore();

    }//End Method
    /**
     * Method check check the dimensions of the figure Point in its container
     * @param x of point
     * @param y of point */
    public boolean check(float x,float y){
        return x< generalWidth &&
                x>0 &&
                y<generalHeight &&
                y>0;
    }//End Method
    /**
     * Method checkPoint check the dimensions of the figure Point in its container
     * @param temp Point figure */
    public boolean checkPoint(Point temp){
        return temp.getCenterX()< generalWidth &&
                temp.getCenterX()>0 &&
                temp.getCenterY()<generalHeight &&
                temp.getCenterY()>0;
    }//End Method
    /**
     * Method adaptPoint change and adapt the dimensions of the figure Point in its container
     * @param temp Point figure */
    public void adaptPoint(Point temp){
        if(temp.getCenterX() >= generalWidth ){
                temp.setCenterX(generalWidth  - 1);
        }else if(temp.getCenterX()<=0){
            temp.setCenterX(1);
        }
        if(temp.getCenterY()>=generalHeight){
            temp.setCenterY(generalHeight - 1);
        }else if(temp.getCenterY()<=0){
            temp.setCenterY(1);
        }
    }//End Method
    /**
     * Method checkCircle check the dimensions of the figure Circle in its container
     * @param temp Circle figure */
    public boolean checkCircle(Circle temp){
        return temp.getCenterX()+temp.getRadius() < generalWidth &&
                temp.getCenterX()-temp.getRadius()>0 &&
                temp.getCenterY()+temp.getRadius()< generalHeight &&
                temp.getCenterY()-temp.getRadius()>0;
    }//End Method
    /**
     * Method adaptCircle change and adapt the dimensions of the figure Circle in its container
     * @param temp Circle figure */
    public void adaptCircle(Circle temp){
        if(temp.getCenterX()+temp.getRadius() >= generalWidth ){
            if(generalWidth-temp.getRadius()-1-temp.getRadius()<=0){
                temp.setRadius(temp.getRadius()-1);
            }else {
                temp.setCenterX(generalWidth - temp.getRadius() - 1);
            }
        }else if(temp.getCenterX()-temp.getRadius()<=0){
            temp.setCenterX(temp.getRadius()+1);
        }
        if(temp.getCenterY()+temp.getRadius()>=generalHeight){
            if(generalHeight-temp.getRadius()-1-temp.getRadius()<=0){
                temp.setRadius(temp.getRadius()-1);
            }else {
                temp.setCenterY(generalHeight - temp.getRadius() - 1);
            }
        }else if(temp.getCenterY()-temp.getRadius()<=0){
            temp.setCenterY(temp.getRadius()+1);
        }
    }//End Method
    /**
     * Method checkEllipse check the dimensions of the figure Ellipse in its container
     * @param temp Ellipse figure */
    public boolean checkEllipse(Ellipse temp){
        return temp.getLeft() >= 0 && temp.getRight() <= generalWidth && temp.getBottom() <= generalHeight && temp.getTop() >= 0;
    }//End Method
    /**
     * Method adaptEllipse change and adapt the dimensions of the figure Ellipse in its container
     * @param  temp Rectangle figure */
    public void adaptEllipse(Ellipse temp){
        //Dimensions of the Ellipse
        float widthRectangle = Math.abs(temp.getRight() - temp.getLeft());
        float heightRectangle = Math.abs(temp.getBottom() - temp.getTop());
        if(temp.getLeft()<0){
            temp.setLeft(1);
            if(widthRectangle+1>generalWidth){
                temp.setRight(generalWidth-2);
            }else {
                temp.setRight(widthRectangle + 1);
            }
        }else if(temp.getRight()>generalWidth){
            temp.setRight(generalWidth-1);
            temp.setLeft(generalWidth-widthRectangle-1);
        }else if(temp.getTop()<0){
            temp.setTop(1);
            if(heightRectangle+1>generalHeight){
                temp.setBottom(generalHeight-2);
            }else{
                temp.setBottom(heightRectangle+1);
            }
        }else if(temp.getBottom()>generalHeight){
            temp.setTop(generalHeight-heightRectangle-1);
            temp.setBottom(generalHeight-1);
        }

    }//End Method
    /**
     * Method checkRectangle check the dimensions of the figure Rectangle in its container
     * @param aux Rectangle figure */
    public boolean checkRectangle(Rectangle aux){
        return aux.getLeft()>=0 && aux.getRight()<=generalWidth && aux.getBottom()<=generalHeight&& aux.getTop()>=0;
    }//End Method
    /**
     * Method adaptRectangle change and adapt the dimensions of the figure Rectangle in its container
     * @param  aux Rectangle figure */
    public void adaptRectangle(Rectangle aux){
        //Dimensions of the Rectangle
        float widthRectangle = (aux.getRight() - aux.getLeft());
        float heightRectangle = (aux.getBottom() - aux.getTop());
        if(aux.getLeft()<0){
            aux.setLeft(1);
            if(widthRectangle+1>generalWidth){
                aux.setRight(generalWidth-2);
            }else {
                aux.setRight(widthRectangle + 1);
            }
        }else if(aux.getRight()>generalWidth){
            aux.setRight(generalWidth-1);
            aux.setLeft(generalWidth-widthRectangle-1);
        }else if(aux.getTop()<0){
            aux.setTop(1);
            if(heightRectangle+1>generalHeight){
                aux.setBottom(generalHeight-2);
            }else{
                aux.setBottom(heightRectangle+1);
            }
        }else if(aux.getBottom()>generalHeight){
            aux.setTop(generalHeight-heightRectangle-1);
            aux.setBottom(generalHeight-1);
        }
    }//End Method
    /**
     * Method checkLine check the dimensions of the figure line in its container
     * @param temp Line figure */
    public boolean checkLine(Line temp){
        return temp.getStartX()>=0 && temp.getStopX()<=generalWidth &&
                temp.getStartY()>=0 && temp.getStopY()<=generalHeight
                && temp.getStopX()>=0 && temp.getStartX()<=generalWidth
                &&  temp.getStopY()>=0 && temp.getStartY()<=generalHeight;
    }//End Method
    /**
     * Method adaptLine change and adapt the dimensions of the figure line in its container
     * @param  temp Line figure */
    public void adaptLine(Line temp){
        //Dimensions of the Line
        float prolongWidth = Math.abs(temp.getStopX()-temp.getStartX());
        float prolongHeight = Math.abs(temp.getStopY()-temp.getStartY());
        if(temp.getStartX()<0) {
            temp.setStartX(1);
            if (prolongWidth + 1 > generalWidth) {
                temp.setStopX(generalWidth - 2);
            } else {
                temp.setStopX(prolongWidth + 1);
            }
        }else if(temp.getStopX()<0) {
            temp.setStopX(1);
            if (prolongWidth + 1 > generalWidth) {
                temp.setStartX(generalWidth - 2);
            } else {
                temp.setStartX(prolongWidth + 1);
            }
        }else if( temp.getStopX()>generalWidth) {
            temp.setStopX(generalWidth - 1);
            temp.setStartX(generalWidth - prolongWidth - 1);
        }else if( temp.getStartX()>generalWidth) {
            temp.setStartX(generalWidth - 1);
            temp.setStopX(generalWidth - prolongWidth - 1);
        }else if(temp.getStartY()<0) {
            temp.setStartY(1);
            if (prolongHeight + 1 > generalHeight) {
                temp.setStopY(generalHeight - 2);
            } else {
                temp.setStopY(prolongHeight + 1);
            }
        }else if(temp.getStopY()<0) {
            temp.setStopY(1);
            if (prolongHeight + 1 > generalHeight) {
                temp.setStartY(generalHeight - 2);
            } else {
                temp.setStartY(prolongHeight + 1);
            }
        }else if(temp.getStopY()>generalHeight) {
            temp.setStartY(generalHeight - prolongHeight - 1);
            temp.setStopY(generalHeight - 1);
        }else if(temp.getStartY()>generalHeight) {
            temp.setStopY(generalHeight - prolongHeight - 1);
            temp.setStartY(generalHeight - 1);
        }
    }//End Method
    public void checkFigures(){
        for (int i = 0; i < myFigures.size(); i++) {
            if (myFigures.get(i) instanceof Circle){
                Circle temp = (Circle) myFigures.get(i);
                if(!checkCircle(temp))
                    adaptCircle(temp);
            }else if (myFigures.get(i) instanceof Rectangle){
                Rectangle temp = (Rectangle) myFigures.get(i);
                if(!checkRectangle(temp))
                    adaptRectangle(temp);
            }else if (myFigures.get(i) instanceof Point){
                Point temp = (Point) myFigures.get(i);
                if(!checkPoint(temp))
                    adaptPoint(temp);
            }else if (myFigures.get(i) instanceof Line){
                Line temp = (Line) myFigures.get(i);
                if(!checkLine(temp))
                    adaptLine(temp);
            }else if (myFigures.get(i) instanceof Ellipse){
                Ellipse temp = (Ellipse) myFigures.get(i);
                if(!checkEllipse(temp))
                    adaptEllipse(temp);
            }
        }
    }
    public float getXTouch(){
        return this.touchX;
    }
    public  float getYTouch(){
        return this.touchY;
    }
    private int mActivePointerId = INVALID_POINTER_ID;
    /**
     * Method onTouchEvent
     * @param event Events of touch*/
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        float getX = event.getX();
        float getY = event.getY();
        final int acct =   event.getActionMasked();
       //Mode Zoom
        if(modeTouch == 1) {
            scaleGestureDetector.onTouchEvent(event);
        }
        //Mode com.example.dashboard.Figures
        switch (acct) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = event.getActionIndex();
                getX = event.getX(pointerIndex);
                getY = event.getY(pointerIndex);
                this.figureSelected = -1;
                invalidate();
                getPastX = getX;
                getPastY = getY;
                mActivePointerId = event.getPointerId(0);
                for (int i = 0; i < myFigures.size(); i++) {
                    if (myFigures.get(i) instanceof Point) {
                        Point temp = (Point) myFigures.get(i);
                        //distance with the origin(centerX,centerY)
                        float distance = distancePoint_to_Point(getX, getY, temp.getCenterX()*mScaleFactor+mPositionX, temp.getCenterY()*mScaleFactor+mPositionY);
                        if (distance <= acceptDistance * 2 *mScaleFactor) { //Move
                            mode = 2;
                            this.figureSelected = i;
                            changeOrderList((getX-mPositionX)/mScaleFactor, (getY-mPositionY)/mScaleFactor, i);
                        }
                    } else if (myFigures.get(i) instanceof Circle) {
                        //float acceptDistanceCircle = acceptDistance;
                        Circle temp = (Circle) myFigures.get(i);
                        //distance with the origin
                        float distance = distancePoint_to_Point(getX, getY, temp.getCenterX()*mScaleFactor+mPositionX, temp.getCenterY()*mScaleFactor+mPositionY);
                        //distance with the point pivot
                        float distance_ = distancePoint_to_Point(getX, getY, temp.getCenterX()*mScaleFactor+mPositionX + temp.getRadius()*mScaleFactor, temp.getCenterY()*mScaleFactor+mPositionY);
                        if (distance <= temp.getRadius()*mScaleFactor) { //Move
                            mode = 2;
                            this.figureSelected = i;
                            changeOrderList((getX-mPositionX)/mScaleFactor, (getY-mPositionY)/mScaleFactor, i);
                        }
                        if (distance_ <= acceptDistance*mScaleFactor) { //Resize
                            mode = 1;
                            this.figureSelected = i;
                        }
                    } else if (myFigures.get(i) instanceof Rectangle) {
                        Rectangle temp = (Rectangle) myFigures.get(i);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX, getY, temp.getLeft()*mScaleFactor+mPositionX, temp.getTop()*mScaleFactor+mPositionY);
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX, getY, temp.getRight()*mScaleFactor+mPositionX, temp.getBottom()*mScaleFactor+mPositionY);
                        if (getX <= temp.getRight()*mScaleFactor+mPositionX && getX >= temp.getLeft()*mScaleFactor+mPositionX&& getY <= temp.getBottom()*mScaleFactor+mPositionY && getY >= temp.getTop()*mScaleFactor+mPositionY) {
                            //Move
                            mode = 2;
                            this.figureSelected = i;
                            changeOrderList((getX-mPositionX)/mScaleFactor, (getY-mPositionY)/mScaleFactor, i);
                        }
                        if (distanceFirstPoint <= acceptDistance*mScaleFactor || distanceSecondPoint <= acceptDistance*mScaleFactor) {
                            //Resize
                            mode = 1;
                            this.figureSelected = i;
                        }
                    } else if (myFigures.get(i) instanceof Line) {
                        Line temp = (Line) myFigures.get(i);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX, getY, temp.getStartX()*mScaleFactor+mPositionX, temp.getStartY()*mScaleFactor+mPositionY);
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX, getY, temp.getStopX()*mScaleFactor+mPositionX, temp.getStopY()*mScaleFactor+mPositionY);
                        if (temp.distancePoint_to_Line((getX-mPositionX)/mScaleFactor, (getY-mPositionY)/mScaleFactor) <= acceptDistance*mScaleFactor) {
                            //Move
                            mode = 2;
                            this.figureSelected = i;
                            changeOrderList((getX-mPositionX)/mScaleFactor, (getY-mPositionY)/mScaleFactor, i);
                        }
                        if (distanceFirstPoint <= acceptDistance*mScaleFactor || distanceSecondPoint <= acceptDistance*mScaleFactor) {
                            //Resize
                            mode = 1;
                            this.figureSelected = i;
                        }
                    } else if (myFigures.get(i) instanceof Ellipse) {
                        Ellipse temp = (Ellipse) myFigures.get(i);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX, getY, temp.getLeft()*mScaleFactor+mPositionX, (temp.getBottom()*mScaleFactor+mPositionY )/ 2 + (temp.getTop()*mScaleFactor+mPositionY) / 2);
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX, getY, temp.getRight()*mScaleFactor+mPositionX, (temp.getBottom()*mScaleFactor+mPositionY) / 2 + (temp.getTop()*mScaleFactor+mPositionY) / 2);
                        //distance a the thirst point of interaction
                        float distanceThirstPoint = distancePoint_to_Point(getX, getY, (temp.getRight()*mScaleFactor+mPositionX )/ 2 + (temp.getLeft()*mScaleFactor+mPositionX) / 2, temp.getTop()*mScaleFactor+mPositionY);
                        //distance a the forty point of interaction
                        float distanceFortyPoint = distancePoint_to_Point(getX, getY, (temp.getRight()*mScaleFactor+mPositionX) / 2 + (temp.getLeft() *mScaleFactor+mPositionX)/ 2, temp.getBottom()*mScaleFactor+mPositionY);
                        if (temp.pointEquationX1((getY-mPositionY)/mScaleFactor) >= (getX-mPositionX)/mScaleFactor &&
                                temp.pointEquationX2((getY-mPositionY)/mScaleFactor) <= (getX-mPositionX)/mScaleFactor &&
                                temp.pointEquationY1((getX-mPositionX)/mScaleFactor) >= (getY-mPositionY)/mScaleFactor &&
                                temp.pointEquationY2((getX-mPositionX)/mScaleFactor )<= (getY-mPositionY)/mScaleFactor) {
                            //Move
                            mode = 2;
                            this.figureSelected = i;
                            changeOrderList((getX-mPositionX)/mScaleFactor, (getY-mPositionY)/mScaleFactor, i);
                        }
                        if (distanceFirstPoint <= acceptDistance*mScaleFactor || distanceSecondPoint <= acceptDistance*mScaleFactor || distanceThirstPoint <= acceptDistance*mScaleFactor || distanceFortyPoint <= acceptDistance*mScaleFactor) {
                            //Resize
                            mode = 1;
                            this.figureSelected = i;
                        }
                    } else {
                        //Type of Mode (is not selection)
                        mode = 0;
                        invalidate();
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = event.findPointerIndex(mActivePointerId);
                getX = event.getX(pointerIndex);
                getY = event.getY(pointerIndex);
                if (this.figureSelected > -1 && !scaleGestureDetector.isInProgress() && Resource.privilegeFile.equals("edit")) {
                    if (myFigures.get(figureSelected) instanceof Point) {
                        Point temp = (Point) myFigures.get(figureSelected);
                        //checkPoint check dimensions of the point
                        if (checkPoint(temp)) {
                            if (mode == 1) { //ReSize
                                System.out.println("Error");
                            }
                            if (mode == 2) { //Move
                                Resource.changeSaveFigures = false;
                                temp.setCenterX((temp.getCenterX() - (getPastX - (getX))));
                                temp.setCenterY((temp.getCenterY() - (getPastY - (getY))));
                                invalidate();
                            }
                        } else {
                            adaptPoint(temp);
                        }
                    } else if (myFigures.get(figureSelected) instanceof Circle) {
                        Circle temp = (Circle) myFigures.get(figureSelected);
                        //checkCircle check dimensions of the circle
                        if (checkCircle(temp)) {
                            if (mode == 1) { //ReSize
                                // 80 is the radius acceptable
                                if ((temp.getRadius()- (getPastX - getX)) >= 80 && check(getX, getY)) {
                                    temp.setRadius(temp.getRadius() + (getX - getPastX));
                                    invalidate();
                                }
                            }
                            if (mode == 2) { //Move
                                Resource.changeSaveFigures = false;
                                //changeOrderList(getX,getY,figureSelected);
                                temp.setCenterX(temp.getCenterX() - (getPastX - getX));
                                temp.setCenterY(temp.getCenterY() - (getPastY - getY));
                                invalidate();
                            }
                        } else {
                            adaptCircle(temp);
                        }
                    } else if (myFigures.get(figureSelected) instanceof Rectangle) {
                        Rectangle aux = (Rectangle) myFigures.get(figureSelected);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX, getY, aux.getLeft()*mScaleFactor+mPositionX, aux.getTop()*mScaleFactor+mPositionY);
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX, getY, aux.getRight()*mScaleFactor+mPositionX, aux.getBottom()*mScaleFactor+mPositionY);
                        //checkRectangle check dimensions of the Rectangle
                        if (checkRectangle(aux)) {
                            if (mode == 1 && check(getX, getY)) { //Resize
                                if (distanceFirstPoint <= (acceptDistance+50)*mScaleFactor) {
                                    //100x100 is Dimension of Rectangle limit
                                    if (aux.getRight()- aux.getLeft()> 100 && aux.getBottom() - aux.getTop()> 100) {
                                        aux.setLeft((getX-mPositionX)/mScaleFactor);
                                        aux.setTop((getY-mPositionY)/mScaleFactor);
                                        invalidate();
                                    } else {
                                        //100+1 for no limit dimension
                                        if (aux.getRight() - aux.getLeft() <= 100) {
                                            aux.setLeft(aux.getRight() - 101);
                                            invalidate();
                                        } else if (aux.getBottom() - aux.getTop() <= 100) {
                                            aux.setTop(aux.getBottom() - 101);
                                            invalidate();
                                        }
                                    }
                                }
                                else if (distanceSecondPoint <= (acceptDistance+50)*mScaleFactor) {
                                    //100x100 is Dimension of Rectangle limit
                                    if (aux.getRight() - aux.getLeft() > 100 && aux.getBottom() - aux.getTop()> 100) {
                                        aux.setRight((getX-mPositionX)/mScaleFactor);
                                        aux.setBottom((getY-mPositionY)/mScaleFactor);
                                        invalidate();
                                    } else {
                                        //100+1 for no limit dimension
                                        if (aux.getRight() - aux.getLeft() <= 100) {
                                            aux.setRight(aux.getLeft() + 101);
                                            invalidate();
                                        } else if (aux.getBottom() - aux.getTop() <= 100) {
                                            aux.setBottom(aux.getTop() + 101);
                                            invalidate();
                                        }
                                    }
                                }
                            }
                            if (mode == 2) { //Move
                                Resource.changeSaveFigures = false;
                                aux.setRight(getX + aux.getRight() - getPastX);
                                aux.setBottom(getY + aux.getBottom() - getPastY);
                                aux.setLeft(getX - (getPastX - aux.getLeft()));
                                aux.setTop(getY - (getPastY - aux.getTop()));
                                invalidate();
                            }
                        } else {
                            //adaptRectangle Setter dimensions
                            adaptRectangle(aux);
                            invalidate();
                        }
                    } else if (myFigures.get(figureSelected) instanceof Line) {
                        Line temp = (Line) myFigures.get(figureSelected);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX, getY, temp.getStartX()*mScaleFactor+mPositionX, temp.getStartY()*mScaleFactor+mPositionY);
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX, getY, temp.getStopX()*mScaleFactor+mPositionX, temp.getStopY()*mScaleFactor+mPositionY);
                        //distance a the line
                        //float distanceToLine = temp.distancePoint_to_Line(getX,getY);
                        if (checkLine(temp)) {
                            if (mode == 1 && check(getX, getY)) { //ReSize
                                if (distanceFirstPoint <= (acceptDistance+50)*mScaleFactor) {
                                    //50 is distance between points start and stop
                                    if (distancePoint_to_Point(temp.getStartX(), temp.getStartY(), temp.getStopX(), temp.getStopY()) <= 50) {
                                        if (temp.getStartX() < temp.getStopX() || temp.getStartY() < temp.getStopY()) {
                                            temp.setStartX(temp.getStartX() - 1);
                                            temp.setStartY(temp.getStartY() - 1);
                                            temp.setStopX(temp.getStopX() + 1);
                                            temp.setStopY(temp.getStopY() + 1);
                                            invalidate();
                                        } else {
                                            temp.setStartX(temp.getStartX() + 1);
                                            temp.setStartY(temp.getStartY() + 1);
                                            temp.setStopX(temp.getStopX() - 1);
                                            temp.setStopY(temp.getStopY() - 1);
                                            invalidate();
                                        }
                                    } else {
                                        temp.setStartX((getX-mPositionX)/mScaleFactor);
                                        temp.setStartY((getY-mPositionY)/mScaleFactor);
                                        invalidate();
                                    }
                                }
                                if (distanceSecondPoint <= (acceptDistance+50)*mScaleFactor) {
                                    temp.setStopX((getX-mPositionX)/mScaleFactor);
                                    temp.setStopY((getY-mPositionY)/mScaleFactor);
                                    invalidate();
                                }
                            }
                            if (mode == 2) { //Move
                                Resource.changeSaveFigures = false;
                                temp.setStopX(getX + temp.getStopX() - getPastX);
                                temp.setStopY(getY + temp.getStopY() - getPastY);
                                temp.setStartX(getX - (getPastX - temp.getStartX()));
                                temp.setStartY(getY - (getPastY - temp.getStartY()));
                                invalidate();
                            }
                        } else {
                            adaptLine(temp);
                        }
                    } else if (myFigures.get(figureSelected) instanceof Ellipse) {
                        Ellipse temp = (Ellipse) myFigures.get(figureSelected);
                        temp.updateParameters(temp);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX, getY, temp.getLeft()*mScaleFactor+mPositionX, (temp.getBottom()*mScaleFactor+mPositionY )/ 2 + (temp.getTop()*mScaleFactor+mPositionY) / 2);
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX, getY, temp.getRight()*mScaleFactor+mPositionX, (temp.getBottom()*mScaleFactor+mPositionY) / 2 + (temp.getTop()*mScaleFactor+mPositionY) / 2);
                        //distance a the thirst point of interaction
                        float distanceThirstPoint = distancePoint_to_Point(getX, getY, (temp.getRight()*mScaleFactor+mPositionX )/ 2 + (temp.getLeft()*mScaleFactor+mPositionX) / 2, temp.getTop()*mScaleFactor+mPositionY);
                        //distance a the forty point of interaction
                        float distanceFortyPoint = distancePoint_to_Point(getX, getY, (temp.getRight()*mScaleFactor+mPositionX) / 2 + (temp.getLeft() *mScaleFactor+mPositionX)/ 2, temp.getBottom()*mScaleFactor+mPositionY);
                        //Dimensions of the Ellipse
                        float widthEllipse = Math.abs(temp.getRight() - temp.getLeft());
                        float heightEllipse = Math.abs(temp.getBottom() - temp.getTop());
                        if (checkEllipse(temp)) {
                            if (mode == 1 && check(getX, getY)) { //Resize
                                if (distanceFirstPoint <= (acceptDistance+50)*mScaleFactor) {
                                    if (widthEllipse > 100) {
                                        temp.setLeft((getX-mPositionX)/mScaleFactor);
                                        invalidate();
                                    } else {
                                        temp.setLeft(temp.getRight() - 101);
                                        invalidate();
                                    }
                                    temp.updateParameters(temp);
                                } else if (distanceSecondPoint <= (acceptDistance+50)*mScaleFactor) {
                                    if (widthEllipse > 100) {
                                        temp.setRight((getX-mPositionX)/mScaleFactor);
                                        invalidate();
                                    } else {
                                        temp.setRight(temp.getLeft() + 101);
                                        invalidate();
                                    }
                                    temp.updateParameters(temp);
                                } else if (distanceThirstPoint <= (acceptDistance+50)*mScaleFactor) {
                                    if (heightEllipse > 100) {
                                        temp.setTop((getY-mPositionY)/mScaleFactor);
                                        invalidate();
                                    } else {
                                        temp.setTop(temp.getBottom() - 101);
                                        invalidate();
                                    }
                                    temp.updateParameters(temp);
                                } else if (distanceFortyPoint <= (acceptDistance+50)*mScaleFactor) {
                                    if (heightEllipse > 100) {
                                        temp.setBottom((getY-mPositionY)/mScaleFactor);
                                        invalidate();
                                    } else {
                                        temp.setBottom(temp.getTop() + 101);
                                        invalidate();
                                    }
                                    temp.updateParameters(temp);
                                }
                            }
                            if (mode == 2) { //Move
                                Resource.changeSaveFigures = false;
                                temp.setRight(getX + temp.getRight() - getPastX);
                                temp.updateParameters(temp);
                                temp.setBottom(getY + temp.getBottom() - getPastY);
                                temp.updateParameters(temp);
                                temp.setLeft(getX - (getPastX - temp.getLeft()));
                                temp.updateParameters(temp);
                                temp.setTop(getY - (getPastY - temp.getTop()));
                                temp.updateParameters(temp);
                                invalidate();
                            }
                        } else {
                            adaptEllipse(temp);
                        }
                    } else {
                        System.out.println("No Tips");
                    }
                } else {
                    if(!scaleGestureDetector.isInProgress()) {
                        final float distanceX = getX - getPastX;
                        final float distanceY = getY - getPastY;
                        mPositionX += distanceX;
                        mPositionY += distanceY;
                        invalidate(); //reDraw
                    }
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
                if (layout.getScaleX() <= 1f && layout.getScaleY() <= 1f) {
                    layout.animate().translationX(0).translationY(0);
                }
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    getPastX = event.getX(newPointerIndex);
                    getPastY = event.getY( newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        checkFigures();
        return true;
    }//End Method
}