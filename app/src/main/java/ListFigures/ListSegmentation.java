package ListFigures;
//Imports
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.dashboard.ControlMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Figures.*;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * This class Canvas defines a list of segments in a view
 * @author Edwin Saavedra
 * @version 3
 */
@SuppressLint("ViewConstructor")
public class ListSegmentation extends View {
    //Class Attributes
    private ArrayList<Figure> segmentation;
    private ArrayList<Figure> segmentationMin;
    private int[] color = {183, 149, 11};
    public boolean isSelectedFigure(){

        return this.figureSelected >= 0 && this.figureSelected<segmentation.size();
    }


    public ArrayList<Figure> getSegmentation() {
        return segmentation;
    }

    public int getFigureSelected() {
        return figureSelected;
    }

    public CardView getCardView() {
        return cardView;
    }
    public boolean setDescriptionFigure(String description){
        if (segmentation.size()>0 && this.figureSelected >= 0 && figureSelected<segmentation.size()) {
            segmentation.get(this.figureSelected).setDescription(description);
            return true;
        }else{
            return false;
        }
    }
    public String getDescriptionFigure(){
        if (segmentation.size()>0 && this.figureSelected >= 0 && figureSelected<segmentation.size()) {
            return segmentation.get(this.figureSelected).getDescription();
        }else{
            return "";
        }
    }

    private LinearLayout viewZoom;
    private LinearLayout content;
    private CardView cardView;
    private int figureSelected=-1;
    private boolean flagFilter = false;
    protected float touchX = 0;
    protected float touchY = 0;
    private float getPastX = 0;
    private float getPastY = 0;
    private float mPositionX;
    private float mPositionY;
    private float acceptDistance = 30;
    private float generalHeight = 0;
    private float generalWidth = 0;
    private Circle circle01 = null;
    private Circle circle02 = null;
    private int indexCircle01 = -1;
    private int indexCircle02 = -1;

    private int contador=0;
    private int alto=0,ancho= 0;

    public float getGeneralHeight() {
        return generalHeight;
    }

    public float getGeneralWidth() {
        return generalWidth;
    }

    public LinearLayout getViewZoom() {
        return viewZoom;
    }

    public float getmPositionX() {
        return mPositionX;
    }

    public float getmPositionY() {
        return mPositionY;
    }

    public float getmScaleFactor() {
        return mScaleFactor;
    }

    private float scaleZoomLayout = 3.0f;
    //Mode Touch
    private boolean touchEvent = false;
    private boolean flag = true;
    private int modeTouch = 0; //Mode 0 is Normal ... Mode 1 is zoomMode
    // ... //Mode 2 is eraser .. //Mode 3 is Point Segmentation...//Mode 4 is Pencil Segmentatio
    private boolean flagPreview = false;
    private  LinearLayout layout;
    private boolean upMode = false;
    private Drawable d;
    //PicToZoom
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ListZoomSegmentation zoomList;
    private Drawable mBoard;
    private float mBoardWidth;
    private  float mBoardHeght;
    private Bitmap mImage;
    private  int mImageWidth;
    private int mImageHeight;
    private int dimen;
    private int scaleSort = 3;
    //Control
    private  DisplayMetrics metrics;
    /**
    /**
     * Class Constructor
     * @param context The View
     * @param viewZoom : layout zoom Image RX*/
    public ListSegmentation (Context context, LinearLayout viewZoom, CardView cardView, LinearLayout content, DisplayMetrics metrics, int dimen){
        super(context);
        this.content = content;
        this.viewZoom = viewZoom;
        layout = content;
        this.dimen =dimen;
        scaleGestureDetector = new ScaleGestureDetector(context, new ListSegmentation.ScaleListener());
        this.viewZoom.setVisibility(INVISIBLE);
        this.cardView = cardView;
        this.cardView.setVisibility(GONE);
        segmentation = new ArrayList<>();
        segmentationMin = new ArrayList<>();
        zoomList = new ListZoomSegmentation(context);
        this.viewZoom.addView(zoomList);
        this.metrics = metrics;
        invalidate();
    }//Closing the class constructor
    public void loadImage(Bitmap mImage){
        invalidate();
        //zoomList.loadImage(mImage,generalHeight,generalWidth);
        zoomList.loadImage(mImage,generalHeight,generalWidth);
        this.mImage = mImage;
        mBoard = new BitmapDrawable(getResources(),mImage);
        invalidate();
        requestLayout();
//        getBase64String();
    }
    public void getFlagPreview(){
        flagPreview = !flagPreview;
        zoomList.flagPreview = flagPreview;
    }

    public ListZoomSegmentation getZoomList() {
        return zoomList;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            float m_MIN_ZOOM = 1.0f;
            float m_MAX_ZOOM = 3.0f;
            mScaleFactor = Math.max(m_MIN_ZOOM,Math.min(mScaleFactor, m_MAX_ZOOM));
            zoomList.setmScaleFactor(mScaleFactor);
            zoomList.invalidate();
            invalidate();
            return true;
        }
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector){
            return true;
        }
    }

    public ArrayList<Figure> distanceMin(ArrayList<Figure> lista1){
        ArrayList<Figure> lista = new ArrayList<Figure>(lista1);
        ArrayList<Figure> newLista = new ArrayList<Figure>();
        newLista.clear();
        float min = 10000000,aux;
        int indexMin=0;
        newLista.add(lista.get(indexMin));
        while (lista.size()>0){
            Circle temp2 = (Circle) newLista.get(newLista.size() - 1);
            for (int i = 0; i<lista.size(); i++){
                if (lista.get(i) instanceof Circle ) {
                    Circle temp = (Circle) lista.get(i);
                    aux = distancePoint_to_Point(temp2.getCenterX(),temp2.getCenterY(), temp.getCenterX(),temp.getCenterY());
                    if (aux < min) {
                        min = aux;
                        indexMin = i;
                    }
                }
            }
            if(indexMin != -1 ) {
                newLista.add(lista.get(indexMin));
                lista.remove(indexMin);
            }
            min = 10000000;
            indexMin= -1;
        }
        return newLista;
    }

    /**
     * Method addCircleSegmentation add a circle in the list of segments
     * @param _startX Define the position of the segment
     * @param _startY Define the position of the segment
     * @param _radius Define the radius od the segment*/
    public void addCircleSegmentation(float _startX, float _startY, float _radius,int index,String description, int [] color01) {
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        Paint pencil;
        pencil = new Paint();
        pencil.setAntiAlias(true);
        pencil.setARGB(250, color01[0],color01[1],color01[2]);
        pencil.setStrokeWidth(4);
        pencil.setStyle(Paint.Style.FILL);
        pencil.setPathEffect(dashPathEffect);
        Circle aux = new Circle(_startX, _startY,  _radius, pencil,color01);
        aux.setDescription(description);
        if(index == -1 ) {
            this.segmentation.add(aux);
            figureSelected = this.segmentation.size() - 1;
        }else if(index == 0) {
            this.segmentation.add(0,aux);
        }else{
            this.segmentation.add(index,aux);

        }
        invalidate();
        zoomList.addCircleSegmentation(_startX,_startY, _radius,pencil,index,description,color01);
        zoomList.invalidate();
    }
    public boolean deleteMemory(){
        MemoryFigure.deleteMemory();
        return false;
    }
    public boolean controlZ(){
        int index = MemoryFigure.controlZinMemory();
        if(index!= -1 ) {
            ElementMemory elementMemory =MemoryFigure.memoryList.get(index);
            switch (elementMemory.getCodMemoryList()) {
                case 1:{
                    segmentation.remove(elementMemory.getIndexInList());
                    zoomList.segmentation.remove(elementMemory.getIndexInList());
                    figureSelected = -1;
                    break;
                }
                case 2:{
                    Circle a = (Circle) elementMemory.getMemoryList().get(0);
                    addCircleSegmentation(a.getCenterX(), a.getCenterY(), a.getRadius(),elementMemory.getIndexInList(),a.getDescription(),a.getColour());
                    figureSelected = -1;
                    break;
                }
                case 3:{
                    for (int i = 0; i < elementMemory.getMemoryList().size(); i++) {
                        Circle a = (Circle) elementMemory.getMemoryList().get(i);
                        addCircleSegmentation(a.getCenterX(), a.getCenterY(), a.getRadius(),-1,a.getDescription(),a.getColour());
                    }
                    figureSelected = -1;
                    break;
                }
                case 4:{ //move
                    if( elementMemory.getMemoryList().size() ==2) {
                        Circle afterMove = (Circle) elementMemory.getMemoryList().get(0);
                        Circle beforeMove = (Circle) elementMemory.getMemoryList().get(1);
                        int indexElement = elementMemory.getIndexInList();
                        Circle aux = (Circle) segmentation.get(indexElement);
                        Circle aux01 = (Circle) zoomList.segmentation.get(indexElement);
                        aux.setCenterX(afterMove.getCenterX());
                        aux.setCenterY(afterMove.getCenterY());
                        aux01.setCenterX(aux.getCenterX());
                        aux01.setCenterY(aux.getCenterY());
                    }
                    figureSelected = -1;
                    break;
                }
                case 5:{ //Edge
                    if( elementMemory.getMemoryList().size() ==2) {
                        Circle firstPoint = (Circle) elementMemory.getMemoryList().get(0);
                        Circle secondPoint = (Circle) elementMemory.getMemoryList().get(1);
                        int indexFirstElement = elementMemory.getIndexInList();
                        int indexSecondElement = elementMemory.getIndexTwoList();
                        if(indexFirstElement>indexSecondElement) {
                            segmentation.remove(indexFirstElement);
                            zoomList.segmentation.remove(indexFirstElement);
                            addCircleSegmentation(secondPoint.getCenterX(),secondPoint.getCenterY(),secondPoint.getRadius(),indexSecondElement,secondPoint.getDescription(),secondPoint.getColour());
                        }else{
                            segmentation.remove(indexFirstElement+1);
                            zoomList.segmentation.remove(indexFirstElement+1);
                            addCircleSegmentation(secondPoint.getCenterX(),secondPoint.getCenterY(),secondPoint.getRadius(),indexSecondElement,secondPoint.getDescription(),secondPoint.getColour());
                        }
                    }
                    figureSelected = -1;
                    break;
                }
            }
        }
        invalidate();
        zoomList.invalidate();
        return false;
    }
    public boolean controlY() {
        int index = MemoryFigure.controlYinMemory();
        if(index != -1 ) {
            ElementMemory elementMemory = MemoryFigure.memoryListCtrlZ.get(index);
            switch (elementMemory.getCodMemoryList()) {
                case 1: {
                    Circle a = (Circle) elementMemory.getMemoryList().get(0);
                    addCircleSegmentation(a.getCenterX(), a.getCenterY(), a.getRadius(),elementMemory.getIndexInList(),a.getDescription(),a.getColour());
                    figureSelected = -1;
                    break;
                }
                case 2: {
                    segmentation.remove(elementMemory.getIndexInList());
                    zoomList.segmentation.remove(elementMemory.getIndexInList());
                    figureSelected = -1;
                    break;
                }
                case 3: {
                    segmentation.clear();
                    zoomList.segmentation.clear();
                    figureSelected = -1;
                    break;
                }
                case 4:{ //move
                    if( elementMemory.getMemoryList().size() ==2) {
                        Circle afterMove = (Circle) elementMemory.getMemoryList().get(0);
                        Circle beforeMove = (Circle) elementMemory.getMemoryList().get(1);
                        int indexElement = elementMemory.getIndexInList();
                        Circle aux = (Circle) segmentation.get(indexElement);
                        Circle aux01 = (Circle) zoomList.segmentation.get(indexElement);
                        aux.setCenterX(beforeMove.getCenterX());
                        aux.setCenterY(beforeMove.getCenterY());
                        aux01.setCenterX(aux.getCenterX());
                        aux01.setCenterY(aux.getCenterY());
                    }
                    figureSelected = -1;
                    break;
                }
                case 5:{ //edge
                    if( elementMemory.getMemoryList().size() ==2) {
                        Circle firstPoint = (Circle) elementMemory.getMemoryList().get(0);
                        Circle secondPoint = (Circle) elementMemory.getMemoryList().get(1);
                        int indexFirstElement = elementMemory.getIndexInList();
                        int indexSecondElement = elementMemory.getIndexTwoList();
                        if(indexFirstElement>indexSecondElement) {//esta mal
                            segmentation.remove(indexSecondElement);
                            zoomList.segmentation.remove(indexSecondElement);
                            addCircleSegmentation(secondPoint.getCenterX(), secondPoint.getCenterY(), secondPoint.getRadius(), indexFirstElement,secondPoint.getDescription(),secondPoint.getColour());
                        }else{
                            segmentation.remove(indexSecondElement);
                            zoomList.segmentation.remove(indexSecondElement);
                            addCircleSegmentation(secondPoint.getCenterX(),secondPoint.getCenterY(),secondPoint.getRadius(),indexFirstElement+1,secondPoint.getDescription(),secondPoint.getColour());
                        }
                    }
                    break;
                }
            }
        }
        invalidate();
        zoomList.invalidate();
        return false;
    }

    public void newEdge(){
        circle01 = null;
        indexCircle01 = -1;
        indexCircle02 = -1;
        circle02 = null;
        contador = 0;
    }
    public boolean sortMoveControl(int option){
        if(figureSelected>-1 && figureSelected<segmentation.size() && segmentation.size()>0) {
            ArrayList<Figure> listMemoryMove = new ArrayList<>();
            Circle a = (Circle) segmentation.get(figureSelected);
            Circle auxMemoryAfterMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
            Circle a1 = (Circle) zoomList.segmentation.get(zoomList.figureSelected);
            listMemoryMove.add(auxMemoryAfterMove);
            switch (option) {
                case 1: { //sortup
                    zoomList.setmPositionY(dpToPx(60) - a.getCenterY()*mScaleFactor);
                    if(a.getCenterY() - scaleSort>=0) {
                        a.setCenterY(a.getCenterY() - scaleSort);
                    }else{
                        a.setCenterY(0);
                    }
                    Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    listMemoryMove.add(auxMemoryBeforeMove);
                    a1.setCenterY(a.getCenterY());
                    invalidate();
                    viewZoom.invalidate();
                    MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                    return true;
                }
                case 2: { //sortleft
                    zoomList.setmPositionX(dpToPx(60) - a.getCenterX()*mScaleFactor);
                    if(a.getCenterX() - scaleSort >=0) {
                        a.setCenterX(a.getCenterX() - scaleSort);
                    }else{
                        a.setCenterX(0);
                    }
                    Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    listMemoryMove.add(auxMemoryBeforeMove);
                    a1.setCenterX(a.getCenterX());
                    invalidate();
                    viewZoom.invalidate();
                    MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                    return true;
                }
                case 3: { //sortRight
                    zoomList.setmPositionX(dpToPx(60) - a.getCenterX() *mScaleFactor);
                    if(a.getCenterX() + scaleSort <=generalWidth) {
                        a.setCenterX(a.getCenterX() + scaleSort);
                    }else{
                        a.setCenterX(generalWidth);
                    }
                    Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    listMemoryMove.add(auxMemoryBeforeMove);
                    a1.setCenterX(a.getCenterX());
                    invalidate();
                    viewZoom.invalidate();
                    MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                    return true;
                }
                case 4: { //sortDown
                    zoomList.setmPositionY(dpToPx(60) - a.getCenterY()*mScaleFactor );
                    if(a.getCenterY() + scaleSort <=generalHeight) {
                        a.setCenterY(a.getCenterY() + scaleSort);
                    }else{
                        a.setCenterY(generalHeight);
                    }
                    Circle aux1 = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    listMemoryMove.add(auxMemoryBeforeMove);
                    a1.setCenterY(a.getCenterY());
                    invalidate();
                    viewZoom.invalidate();
                    MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                    return true;
                }
                case 5: { //allSortUp
                    zoomList.setmPositionY((dpToPx(60)));
                    a.setCenterY(0);
                    Circle aux1 = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    listMemoryMove.add(auxMemoryBeforeMove);
                    a1.setCenterY(a.getCenterY());
                    invalidate();
                    viewZoom.invalidate();
                    MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                    return true;
                }
                case 6:{ //allSortLeft
                    zoomList.setmPositionX(dpToPx(60));
                    a.setCenterX(0);
                    Circle aux1 = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    listMemoryMove.add(auxMemoryBeforeMove);
                    a1.setCenterX(a.getCenterX());
                    invalidate();
                    viewZoom.invalidate();
                    MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                    return true;
                }
                case 7:{ //alsortRight
                    zoomList.setmPositionX(dpToPx(60) - generalWidth*mScaleFactor);
                    a.setCenterX(generalWidth);
                    Circle aux1 = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    listMemoryMove.add(auxMemoryBeforeMove);
                    a1.setCenterX(a.getCenterX());
                    invalidate();
                    viewZoom.invalidate();
                    MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                    return true;
                }
                case 8:{ //alSortDown
                    zoomList.setmPositionY(dpToPx(60) - generalHeight*mScaleFactor);
                    a.setCenterY(generalHeight);
                    Circle aux1 = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                    listMemoryMove.add(auxMemoryBeforeMove);
                    a1.setCenterY(a.getCenterY());
                    invalidate();
                    viewZoom.invalidate();
                    MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                    return true;
                }
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    /**
     * Method after : remove one segment : deleteFigure the selected figure is deleted*/
    public boolean after(){
        zoomList.after();
        if(figureSelected == segmentation.size()-1) {
            if (segmentation.size() > 0) {
                ArrayList<Figure> aux01 = new ArrayList<>();
                aux01.add(segmentation.get(figureSelected));
                MemoryFigure.addElementMemory(2,figureSelected,aux01);
                segmentation.remove(segmentation.size() - 1);
                figureSelected = segmentation.size() - 1;
                invalidate();
                return true;
            }
        }else{
            if (segmentation.size()>0 && this.figureSelected > -1 && this.figureSelected <segmentation.size() ) {
                ArrayList<Figure> aux01 = new ArrayList<>();
                aux01.add(segmentation.get(figureSelected));
                MemoryFigure.addElementMemory(2,figureSelected,aux01);
                segmentation.remove(this.figureSelected);
                figureSelected = -1;
                invalidate(); //Redraw
                return true;
            }else{
                System.out.println("Error de indexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            }

        }
        return false;
    }

    /**
     * Method clearList deleted the list
     * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void clearList(){
        //        if(aux ==null)
        if(!touchEvent) {
            ArrayList<Figure> aux = new ArrayList<>(segmentation);
            MemoryFigure.addElementMemory(3,0,aux);
            zoomList.clearList();
            zoomList.invalidate();
            segmentation.clear();
            invalidate();
        }
    }
    /**
     * Method changeColour of the list of segments
     * */
    public boolean changeColour(int [] colour) {
        zoomList.changeColour(colour);
        if(figureSelected == segmentation.size()-1 || figureSelected == 0) { //si el seleccionado es el ultimo -- cambiar el color a partir del ultimo
            if(segmentation.size()==0){
                return false;
            }else {
                Circle temp = (Circle) segmentation.get(figureSelected);
                temp.getPaint().setARGB(255, colour[0], colour[1], colour[2]);
                temp.setColour(colour);
                color = colour;
                invalidate(); //Redraw
                return true;
            }
        }else{
            if (segmentation.size() >0 && this.figureSelected != -1 && figureSelected<segmentation.size()) {
                segmentation.get(this.figureSelected).setColour(colour);
                segmentation.get(this.figureSelected).getPaint().setARGB(255,colour[0],colour[1],colour[2]);
                invalidate(); //Redraw
                return true;
            }else {
                return false;
            }
        }

    }//End Method

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
     * Method onDraw draw the segment
     * @param canvas area of draw*/
    protected void onDraw(Canvas canvas) {
        int altoCa = getBottom();
        int anchoCa = getRight();
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
        zoomList.loadImage(mImage,generalHeight,generalWidth);
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
        for(int i=0;i<segmentation.size();i++){
            if (segmentation.get(i) instanceof Circle) {
                Circle temp = (Circle) segmentation.get(i);
                if(modeTouch == 4 && i== figureSelected){
                    temp.getPaint().setStyle(Paint.Style.STROKE);
                    temp.getPaint().setStrokeWidth(6);
                }else{
                    temp.getPaint().setStyle(Paint.Style.FILL);
                }
                canvas.drawCircle(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getPaint());
                if(i == figureSelected){
                    canvas.drawCircle(temp.getCenterX(),temp.getCenterY(),acceptDistance,Util.Circle(temp.getColour()));
                }
                if(modeTouch == 5 ){
                    if( indexCircle02!=indexCircle01  && (i ==indexCircle01 || i == indexCircle02 ))
                    canvas.drawCircle(temp.getCenterX(),temp.getCenterY(),acceptDistance,Util.CircleTransparent(temp.getColour()));
                }
            }
        }
        if(modeTouch ==2 && upMode){
            float[] intervals = new float[]{0.0f, 0.0f};
            float phase = 0;
            DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
            Paint pencil;
            pencil = new Paint();
            pencil.setAntiAlias(true);
            pencil.setARGB(250, 255,250,240);
            pencil.setStrokeWidth(4);
            pencil.setStyle(Paint.Style.STROKE);
            pencil.setPathEffect(dashPathEffect);
            canvas.drawCircle((touchX-mPositionX)/mScaleFactor,(touchY-mPositionY)/mScaleFactor,acceptDistance/2,pencil);
            canvas.drawCircle((touchX-mPositionX)/mScaleFactor,(touchY-mPositionY)/mScaleFactor,acceptDistance*2,pencil);
        }
        if(flagPreview && !segmentation.isEmpty()){
            segmentationMin = segmentation;
            for(int i=0;i< segmentationMin.size();i++){
                if ( segmentationMin.get(i) instanceof Circle) {
                    Circle temp = (Circle)  segmentationMin.get(i);
                    if(i+1!= segmentationMin.size()) {
                        Circle temp02 = (Circle)  segmentationMin.get(i+1);
                        canvas.drawLine( temp.getCenterX(),  temp.getCenterY(), temp02.getCenterX(),  temp02.getCenterY(), Util.Circle(temp.getColour()));
                    }

                }
            }
        }
        canvas.restore();
    }

    @SuppressLint("WrongThread")
    public String getBase64String(){ //1 - formato STRING -- 2 - formato ARRAYLIST
        ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
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

    public void changeModeTouch( int mode){
        modeTouch = mode;
        zoomList.modeTouch = mode; //recordar que vamos a cambiar el modo touch
        zoomList.invalidate();
    }
    public int getModeTouch(){
        return modeTouch;
    }
    /**
     * Method toString show list of figures content
     * @return content of the list*/
    public JSONArray dataSegments() throws JSONException {
        JSONArray jsonArray =new JSONArray();
        /*StringBuilder listJson = new StringBuilder("[\n");
        for(int i=0;i<this.segmentation.size();i++) {
            Circle aux = (Circle) segmentation.get(i);
            listJson.append(aux.getJSON(i)).append("\n");
            if (i < this.segmentation.size() - 1)
                listJson.append(",");
        }*/
        //jsonArray.
        for(int i = 0 ;i<this.segmentation.size();i++){
            Circle aux = (Circle) segmentation.get(i);
            jsonArray.put(aux.getJSON(i));
        }
        return jsonArray;
    }//End Method

    public void readDataSegments(JSONArray jsonArray,float width, float height) throws JSONException {
        figureSelected = -1;
        segmentation.clear();
        zoomList.segmentation.clear();
        for (int i = 0; i< jsonArray.length(); i++){
            JSONObject aux = jsonArray.getJSONObject(i);
            int[] colour = {Integer.parseInt(aux.getString("r")), Integer.parseInt(aux.getString("g")),Integer.parseInt(aux.getString("b"))};
            float x = Float.parseFloat(aux.getString("x"))*generalWidth/width;
            float y = Float.parseFloat(aux.getString("y"))*generalHeight/height;
            addCircleSegmentation(x,y,9,-1,(aux.getString("comment")),colour);
        }
        invalidate();
        zoomList.invalidate();
    }

    public String toString(){

        String a =  "\"imageX\":"+generalWidth+","+
                "\"imagey\":"+generalHeight+"," +
                "\"profileItems\":"+null+","+
                "\"landmarksNumber\":"+0+","+
                //"\"landmarks\":"+dataSegments()+","+
                "\"landmarksCreated\":"+segmentation.size()+","+
                "\"profileName\":"+"Edwin"+","+
                "\"imageName\":"+"image_rx_10.jpg";
        //System.out.println(a);
        return a;
    }
    //public JSONObject
    /*

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
     * Method checkCircle check the dimensions of the figure Circle in its container
     * @param temp Circle figure */
    public boolean checkCircle(Circle temp){
        return temp.getCenterX()+temp.getRadius() < generalWidth &&
                temp.getCenterX()-temp.getRadius()>0 &&
                temp.getCenterY()+temp.getRadius()<generalHeight &&
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

    public int dpToPx(int dp){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp*(displayMetrics.xdpi/DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int pxToDp(int px){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px/(displayMetrics.xdpi/DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void moveZoom(float touchX, float touchY){ //ESTO DEBE TESTEARSE CON UN DISPOSITIVO DE DIFERENTE DIMENSION
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        boolean flagMoveZoom = false;
        if(touchX>= dpToPx(45) && touchX<=dpToPx(165+40) && touchY >= dpToPx(8) && touchY <= dpToPx(128+35)){
            flagMoveZoom = true;
        }
        //int d = (int) getContext().getResources().getDimension(getContext().R.)
        //debe trasladarse segun las dimensiones de la pantalla 120 - 120 45
        if (flagMoveZoom) {
            this.cardView.setTranslationX(displayMetrics.widthPixels);
            this.cardView.setTranslationX(this.cardView.getTranslationX()-dimen);
        }else
            this.cardView.setTranslationX(0);
    }
    public void add(float getX,float getY){ //AQUI HAY ERROR
        ArrayList<Figure> aux01 = new ArrayList<>();
        float[] intervals = new float[]{0.0f, 0.0f};
        float phase = 0;
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
        Paint pencil;
        pencil = new Paint();
        pencil.setAntiAlias(true);
        pencil.setARGB(250, color[0],color[1],color[2]);
        pencil.setStrokeWidth(4);
        pencil.setStyle(Paint.Style.FILL);
        pencil.setPathEffect(dashPathEffect);
        Circle aux = new Circle((getX - mPositionX) / mScaleFactor,(getY - mPositionY) / mScaleFactor,9,pencil,color);
        aux01.add(aux);
        MemoryFigure.addElementMemory(1,figureSelected,aux01);
    }
    /**
     * Method onTouchEvent
     * @param event Events of touch*/
    private int mActivePointerId = INVALID_POINTER_ID;
    private ArrayList<Figure> listMemoryMove  = new ArrayList<>();;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        touchEvent = true;
        invalidate();
        zoomList.invalidate();
        touchX = event.getX();
        touchY = event.getY();
        upMode = true;
        float getX = event.getX();
        float getY = event.getY();
        final int acct = event.getActionMasked();
        //Mode Zoom
        if(modeTouch == 1) {
            scaleGestureDetector.onTouchEvent(event);
        }
        //zoomList.acceptDistance = acceptDistance*this.viewZoom.getWidth()/this.getWidth();
        zoomList.touchX = (event.getX()-mPositionX)/mScaleFactor;
        zoomList.touchY = (event.getY()-mPositionY)/mScaleFactor;
        zoomList.setmPositionX(dpToPx(60)-event.getX()+mPositionX);
        zoomList.setmPositionY(dpToPx(60)-event.getY()+mPositionY);
        this.viewZoom.invalidate();
        invalidate();
        switch (acct) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = event.getActionIndex();
                getX = event.getX(pointerIndex);
                getY = event.getY(pointerIndex);
                mActivePointerId = event.getPointerId(0);
                touchX = event.getX(pointerIndex);
                touchY = event.getY(pointerIndex);
                zoomList.touchX = (event.getX()-mPositionX)/mScaleFactor;
                zoomList.touchY = (event.getY()-mPositionY)/mScaleFactor;
                this.figureSelected = -1;
                zoomList.figureSelected = -1;
                zoomList.setTouchEvent(true);
                zoomList.invalidate();
                invalidate();
                getPastX = getX;
                getPastY = getY;
                if(modeTouch == 0 || modeTouch ==1) { //Touch segments
                    if (!segmentation.isEmpty()) {
                        Circle aux = (Circle) segmentation.get(segmentation.size() - 1);
                        float centerX = aux.getCenterX()*mScaleFactor+mPositionX - event.getX(pointerIndex);
                        float centerY = aux.getCenterY()*mScaleFactor+mPositionY - event.getY(pointerIndex);
                        if (Math.sqrt(centerX * centerX + centerY * centerY) <= 40*mScaleFactor) {
                            this.viewZoom.setVisibility(View.VISIBLE);
                            this.cardView.setVisibility(VISIBLE);
                        }
                        for (int i = 0; i < segmentation.size(); i++) {
                            if (segmentation.get(i) instanceof Circle) {
                                Circle temp = (Circle) segmentation.get(i);
                                //distance with the origin
                                float distance = distancePoint_to_Point(getX, getY, temp.getCenterX()*mScaleFactor+mPositionX, temp.getCenterY()*mScaleFactor+mPositionY);
                                if (distance <= acceptDistance*mScaleFactor) { //Move
                                    zoomList.figureSelected = i;
                                    this.figureSelected = i;
                                    //intento 1 de controlz en movimiento
                                    Circle auxMemoryAfterMove = new Circle(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getPaint(), temp.getColour());
                                    listMemoryMove.add(auxMemoryAfterMove);
                                    System.out.println("listmemory : "+listMemoryMove.size());
                                }
                            }
                        }
                    }
                }else if(modeTouch == 2){ //touch eraser
                    this.viewZoom.setVisibility(View.VISIBLE);
                    this.cardView.setVisibility(VISIBLE);
                    zoomList.invalidate();
                }else if(modeTouch == 3){//point independient Segmentation
                    addCircleSegmentation((getX - mPositionX) / mScaleFactor, (getY - mPositionY) / mScaleFactor, 9,-1,"",color); //9 is radius acceptable
                    add(getX,getY);
                    this.viewZoom.setVisibility(View.VISIBLE);
                    this.cardView.setVisibility(VISIBLE);
                    zoomList.invalidate();
                }else if(modeTouch == 4){ //pencil Segment
                    for (int i = 0; i < segmentation.size(); i++) {
                        if (segmentation.get(i) instanceof Circle) {
                            Circle temp = (Circle) segmentation.get(i);
                            //distance with the origin
                            float distance = distancePoint_to_Point(getX, getY, temp.getCenterX()*mScaleFactor+mPositionX, temp.getCenterY()*mScaleFactor+mPositionY);
                            if (distance <= acceptDistance*mScaleFactor) { //Move
                                zoomList.figureSelected = i;
                                this.figureSelected = i;
                            }
                        }
                    }
                }else if(modeTouch == 5){ //editEdge --- aun esta en testing
                    for (int i = 0; i < segmentation.size(); i++) {
                        if (segmentation.get(i) instanceof Circle) {
                            Circle temp = (Circle) segmentation.get(i);
                            //distance with the origin
                            float distance = distancePoint_to_Point(getX, getY, temp.getCenterX()*mScaleFactor+mPositionX, temp.getCenterY()*mScaleFactor+mPositionY);
                            if (distance <= acceptDistance*mScaleFactor) { //Move
                                zoomList.figureSelected = i;
                                this.figureSelected = i;
                                if(contador != 1 || indexCircle01 != i) {
                                    contador++;
                                }
                                if(contador == 1) {
                                    circle01 = (Circle) segmentation.get(i);
                                    indexCircle01 = i;
                                }
                                else if(contador == 2) {
                                    circle02 = (Circle) segmentation.get(i);
                                    indexCircle02 = i;
                                    //break;
                                }
                            }
                        }
                    }
                    if(circle01!=null && circle02 !=null&&indexCircle01!=-1&& indexCircle02!=-1 &&indexCircle01 !=indexCircle02 ){
                        ArrayList<Figure> dataMemoryEdge = new ArrayList<>();
                        dataMemoryEdge.add(segmentation.get(indexCircle01));
                        dataMemoryEdge.add(segmentation.get(indexCircle02));
                        MemoryFigure.addElementMemory(5,indexCircle01,indexCircle02,dataMemoryEdge);
                        if(indexCircle02<indexCircle01) {
                            segmentation.remove(indexCircle02);
                            zoomList.segmentation.remove(indexCircle02);
                            addCircleSegmentation(circle02.getCenterX(), circle02.getCenterY(), circle02.getRadius(), indexCircle01,circle02.getDescription(),circle02.getColour());
                        }else{
                            segmentation.remove(indexCircle02);
                            zoomList.segmentation.remove(indexCircle02);
                            addCircleSegmentation(circle02.getCenterX(), circle02.getCenterY(), circle02.getRadius(), indexCircle01+1,circle02.getDescription(),circle02.getColour());
                        }
                        newEdge();
                        Toast toast;
                        toast = Toast.makeText(getContext(),"\tSuccessful Change,\n Select Two New Points",Toast.LENGTH_LONG);
                        toast.show();
                        figureSelected = -1;
                    }else{
                        Toast toast;
                        toast = Toast.makeText(getContext(),"Select Points, Please",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    invalidate();
                }
            break;
        }
        case MotionEvent.ACTION_MOVE: {
                //System.out.println("touch --> "+event.getX()+" : "+event.getY());
                final int pointerIndex = event.findPointerIndex(mActivePointerId);
                getX = event.getX(pointerIndex);
                getY = event.getY(pointerIndex);
                moveZoom(getX,getY);
                touchX = event.getX(pointerIndex);
                touchY = event.getY(pointerIndex);
                zoomList.touchX = (event.getX()-mPositionX)/mScaleFactor;
                zoomList.touchY = (event.getY()-mPositionY)/mScaleFactor;
                if(modeTouch == 0 || modeTouch == 1 || modeTouch == 4 || modeTouch == 5) { //Touch segments
                    if(modeTouch == 4 && segmentation.isEmpty()){
                        addCircleSegmentation((getX - mPositionX) / mScaleFactor, (getY - mPositionY) / mScaleFactor, 9,-1,"",color);  //9 is radius acceptable
                        add(getX,getY);
                    }
                    if (figureSelected >-1 && modeTouch == 4) {
                        if (!segmentation.isEmpty()) {
                            boolean drawS = false;
                            Circle aux = (Circle) segmentation.get(figureSelected);
                            color = aux.getColour();
                            float centerX = aux.getCenterX() * mScaleFactor + mPositionX - event.getX(pointerIndex);
                            float centerY = aux.getCenterY() * mScaleFactor + mPositionY - event.getY(pointerIndex);
                            //60 is distance between  segments
                            if (Math.sqrt(centerX * centerX + centerY * centerY) > (acceptDistance *2) * mScaleFactor
                                   ) {
                                drawS = true;
                                this.viewZoom.setVisibility(View.VISIBLE);
                                this.cardView.setVisibility(VISIBLE);
                            }
                            if (drawS) {
                                if(figureSelected == segmentation.size()-1) {
                                    addCircleSegmentation((getX - mPositionX) / mScaleFactor,
                                            (getY - mPositionY) / mScaleFactor, 9, -1,"",color); //9 is radius acceptable
                                }else if(figureSelected != 0){
                                    addCircleSegmentation((getX - mPositionX) / mScaleFactor,
                                            (getY - mPositionY) / mScaleFactor, 9, figureSelected,"",color);
                                }else if (figureSelected == 0){
                                    addCircleSegmentation((getX - mPositionX) / mScaleFactor,
                                            (getY - mPositionY) / mScaleFactor, 9, 0,"",color);
                                }
                                add(getX,getY);
                            }
                        } else {
                            addCircleSegmentation((getX - mPositionX) / mScaleFactor, (getY - mPositionY) / mScaleFactor, 9,-1,"",color);  //9 is radius acceptable
                            System.out.println("ESTA SECCION DE CODIGO SE ESTA  EJECUTANDO");
                            add(getX,getY);
                        }
                    }else if (this.figureSelected > -1 && !segmentation.isEmpty() && modeTouch !=5) {
                        if( !scaleGestureDetector.isInProgress()) {
                            if (figureSelected<segmentation.size() && segmentation.get(figureSelected) instanceof Circle) {
                                Circle temp = (Circle) segmentation.get(figureSelected);
                                Circle temp_z = (Circle) zoomList.segmentation.get(figureSelected);
                                    if ((getX - mPositionX) / mScaleFactor <= generalWidth &&
                                            (getX - mPositionX) / mScaleFactor >= 0) {
                                        temp.setCenterX((getX - mPositionX) / mScaleFactor);
                                        temp_z.setCenterX(temp.getCenterX());

                                    }
                                    if ((getY - mPositionY) / mScaleFactor <= generalHeight
                                            && (getY - mPositionY) / mScaleFactor >= 0) {
                                        temp.setCenterY((getY - mPositionY) / mScaleFactor);
                                        temp_z.setCenterY(temp.getCenterY());
                                    }
                                invalidate();
                            }
                        }
                    }else{
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
                }else if(modeTouch == 2){ //touch eraser
                    for (int i = 0; i < segmentation.size(); i++) {
                        if (segmentation.get(i) instanceof Circle){
                            Circle temp = (Circle) segmentation.get(i);
                            //distance with the origin
                            float distance = distancePoint_to_Point(getX, getY, temp.getCenterX()*mScaleFactor+mPositionX, temp.getCenterY()*mScaleFactor+mPositionY);
                            if(distance<=temp.getRadius()){
                                ArrayList<Figure> aux01 = new ArrayList<>();
                                aux01.add(segmentation.get(i));
                                MemoryFigure.addElementMemory(2,i,aux01);
                                segmentation.remove(i);
                                zoomList.segmentation.remove(i);
                                figureSelected = segmentation.size()-1;
                                zoomList.figureSelected = zoomList.segmentation.size()-1;
                                zoomList.invalidate();
                            }
                        }
                    }
                }
                this.viewZoom.setVisibility(View.VISIBLE);
                this.cardView.setVisibility(VISIBLE);
                invalidate();
                zoomList.invalidate();
            break;
        }
            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                touchEvent = false;
                zoomList.setTouchEvent(false);
                upMode = false;
                invalidate();
                zoomList.invalidate();
                if(figureSelected<=-1) {
                    this.viewZoom.setVisibility(View.INVISIBLE);
                    this.cardView.setVisibility(GONE);
                }else{
                    if (figureSelected < segmentation.size()){
                        Circle a = (Circle) segmentation.get(figureSelected);
                        Circle auxMemoryBeforeMove = new Circle(a.getCenterX(),a.getCenterY(),a.getRadius(),a.getPaint(),a.getColour());
                        if(listMemoryMove.size() == 1 ){
                            listMemoryMove.add(auxMemoryBeforeMove);
                            MemoryFigure.addElementMemory(4,figureSelected,listMemoryMove);
                        }
                        System.out.println("test de move"+listMemoryMove.size());
                    }
                }
                listMemoryMove.clear();
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = event.getActionIndex();
                final int pointerId = event.getPointerId(pointerIndex);
                for (int i = 0; i < segmentation.size(); i++) {
                    if (segmentation.get(i) instanceof Circle) {
                        Circle temp = (Circle) segmentation.get(i);
                        if (!checkCircle(temp))
                            adaptCircle(temp);
                    }
                }
                if (layout.getScaleX() <= 1f && layout.getScaleY() <= 1f) {
                    layout.animate().translationX(0).translationY(0);
                }
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    getPastX = event.getX(newPointerIndex);
                    getPastY = event.getY(newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }//End Method
}//Close Class