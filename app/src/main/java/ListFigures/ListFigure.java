package ListFigures;
//Imports
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import Figures.*;
/**
 * This class Canvas defines a list of figures in a view
 * @author Edwin Saavedra
 * @version 3
 */
public class ListFigure  extends View {
    //Class Attributes
    private int figureSelected;
    private ArrayList<Figure> myFigures;
    private Paint pencil;
    private float globalX = 0;
    private float globalY = 0;
    private int[] figureColour = {183, 149, 11};
    private float getPastX = 0;
    private float getPastY = 0;
    // acceptDistance is the acceptable distance to interact with the figure
    private float acceptDistance = 30;
    private float generalHeight = 0;
    private float generalWidth = 0;
    private LinearLayout menuLeft;
    private LinearLayout menuRight;
    int mode=0; //Mode 1 is Resize... mode 2 is Move
    /**
     * Class Constructor
     * @param context The View*/
    public ListFigure(Context context, LinearLayout menuLeft, LinearLayout menuRight){
        super(context);
        myFigures = new ArrayList<>();
        this.menuLeft = menuLeft;
        this.menuRight = menuRight;
        this.menuLeft.setVisibility(INVISIBLE);
        this.menuRight.setVisibility(INVISIBLE);
        initialStyleFigure();
        invalidate();
    }//Closing the class constructor
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
     * Method addCircle add a circle in the list of figures
     * @param _startX Define the position of the circle
     * @param _startY Define the position of the circle
     * @param _radius Define the radius od the circle*/
    public void addCircle(float _startX, float _startY, float _radius) {
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
            return true;
        }else{
            return false;
        }
    }//End Method
    /**
     * Method changeColour the selected figure changes color
     * */
    public boolean changeColour(int [] colour) {
        if (myFigures.size() >0 && this.figureSelected != -1) {
            myFigures.get(this.figureSelected).setColour(colour);
            myFigures.get(this.figureSelected).getPaint().setARGB(255,colour[0],colour[1],colour[2]);
            invalidate(); //Redraw
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
    /**
     * Method clearList deleted the list
     * */
    public void clearList(){
        this.myFigures.clear();
    }//End Method
    /**
     * Method toUpdate reDraw the content - is invalidate method
     * */
    public void toUpdate() {
        invalidate();
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
     * Method onDraw draw the figure geometric
     * @param canvas area of draw*/
    protected void onDraw(Canvas canvas) {
        generalWidth = canvas.getWidth();
        generalHeight = canvas.getHeight();
        for (int i = 0; i < myFigures.size(); i++) {
            if (myFigures.get(i) instanceof Circle) {
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

    public float getXTouch(){
        return this.globalX;
    }
    public  float getYTouch(){
        return this.globalY;
    }
    /**
     * Method onTouchEvent
     * @param event Events of touch*/
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float getX = event.getX();
        float getY = event.getY();
        this.globalX = getX;
        this.globalY = getY;
        this.menuLeft.setVisibility(VISIBLE);
        this.menuRight.setVisibility(VISIBLE);
        int acct = event.getAction();
        if (acct == MotionEvent.ACTION_DOWN ) {
                this.figureSelected = -1;
                invalidate();
                getPastX = getX;
                getPastY = getY;
                for (int i = 0; i < myFigures.size(); i++) {
                    if (myFigures.get(i) instanceof Circle) {
                        Circle temp = (Circle) myFigures.get(i);
                        float distance = distancePoint_to_Point(getX, getY, temp.getCenterX(), temp.getCenterY());
                        float distance_ = distancePoint_to_Point(getX,getY,temp.getCenterX()+temp.getRadius(),temp.getCenterY());
                        if (distance <= temp.getRadius()) { //Move
                            mode=2;
                            this.figureSelected = i;
                            invalidate();
                        }else if(distance_ <= acceptDistance){ //Resize
                            mode=1;
                            this.figureSelected = i;
                            invalidate();
                        }
                    } else if (myFigures.get(i) instanceof Rectangle) {
                        Rectangle temp = (Rectangle) myFigures.get(i);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX, getY, temp.getLeft(), temp.getTop());
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX, getY, temp.getRight(), temp.getBottom());
                        if (getX <= temp.getRight() && getX >= temp.getLeft() && getY <= temp.getBottom() && getY >= temp.getTop()) {
                            //Move
                            mode=2;
                            this.figureSelected = i;
                            invalidate();
                        } else if(distanceFirstPoint<=acceptDistance||distanceSecondPoint<=acceptDistance){
                            //Resize
                            mode=1;
                            this.figureSelected = i;
                            invalidate();
                        }
                    } else if (myFigures.get(i) instanceof Line) {
                        Line temp = (Line) myFigures.get(i);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX,getY,temp.getStartX(),temp.getStartY());
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX,getY,temp.getStopX(),temp.getStopY());
                        if (temp.distancePoint_to_Line(getX, getY) <= acceptDistance
                        &&distanceFirstPoint>acceptDistance && distanceSecondPoint>acceptDistance) {
                            //Move
                            mode=2;
                            this.figureSelected = i;
                            invalidate();
                        }else if(distanceFirstPoint<=acceptDistance || distanceSecondPoint <=acceptDistance){
                            //Resize
                            mode=1;
                            this.figureSelected = i;
                            invalidate();
                        }
                    } else if (myFigures.get(i) instanceof Ellipse) {
                        Ellipse temp = (Ellipse) myFigures.get(i);
                        //distance a the first point of interaction
                        float distanceFirstPoint = distancePoint_to_Point(getX, getY, temp.getLeft(), temp.getBottom()/2 + temp.getTop()/2);
                        //distance a the second point of interaction
                        float distanceSecondPoint = distancePoint_to_Point(getX, getY, temp.getRight(), temp.getBottom()/2 + temp.getTop()/2);
                        //distance a the thirst point of interaction
                        float distanceThirstPoint = distancePoint_to_Point(getX, getY, temp.getRight()/2 + temp.getLeft()/2, temp.getTop());
                        //distance a the forty point of interaction
                        float distanceFortyPoint = distancePoint_to_Point(getX, getY, temp.getRight()/2 + temp.getLeft()/2, temp.getBottom());
                        if(distanceFirstPoint <= acceptDistance || distanceSecondPoint <= acceptDistance ||
                                distanceThirstPoint <= acceptDistance || distanceFortyPoint <= acceptDistance){
                            mode=1;
                            this.figureSelected = i;
                            invalidate();
                        }else if(temp.pointEquationX1(getY) >= getX && temp.pointEquationX2(getY) <= getX
                                && temp.pointEquationY1(getX) >= getY && temp.pointEquationY2(getX) <= getY){
                            mode=2;
                            this.figureSelected = i;
                            invalidate();
                        }
                    }else{
                        //Type of Mode (is not selection)
                        mode=0;
                        invalidate();

                    }
                }
        }
        if (acct == MotionEvent.ACTION_MOVE) {
            if (this.figureSelected > -1) {
                if (myFigures.get(figureSelected) instanceof Circle) {
                    Circle temp = (Circle) myFigures.get(figureSelected);
                    //distance with the point pivot
                    //float distance = distancePoint_to_Point(getX,getY,(temp.getCenterX()+temp.getRadius()),temp.getCenterY());
                    //distance with the origin
                    //float distanceOrigin = distancePoint_to_Point(getX,getY,temp.getCenterX(),temp.getCenterY());
                   //checkCircle check dimensions of the circle
                    if(checkCircle(temp)){
                        if (mode==1) { //ReSize
                            // 80 is the radius acceptable
                            if ((temp.getRadius()-(getPastX-getX)) >=80)
                                temp.setRadius(temp.getRadius() + (getX - getPastX));
                        }
                        if(mode==2){ //Move
                            temp.setCenterX(temp.getCenterX() - (getPastX - getX));
                            temp.setCenterY(temp.getCenterY() - (getPastY - getY));

                        }
                    }else{
                        adaptCircle(temp);
                    }
                } else if (myFigures.get(figureSelected) instanceof Rectangle) {
                    Rectangle aux = (Rectangle) myFigures.get(figureSelected);
                    //distance a the first point of interaction
                    float distanceFirstPoint = distancePoint_to_Point(getX,getY,aux.getLeft(),aux.getTop());
                    //distance a the second point of interaction
                    float distanceSecondPoint = distancePoint_to_Point(getX,getY,aux.getRight(),aux.getBottom());
                    //checkRectangle check dimensions of the Rectangle
                    if(checkRectangle(aux)){
                        if(mode==1){ //Resize
                            if (distanceFirstPoint <= acceptDistance) {
                                //100x100 is Dimension of Rectangle limit
                                if(aux.getRight()-aux.getLeft()>100 && aux.getBottom()-aux.getTop()>100){
                                    aux.setLeft(getX);
                                    aux.setTop(getY);
                                }else{
                                    //100+1 for no limit dimension
                                    if(aux.getRight()-aux.getLeft()<=100){
                                        aux.setLeft(aux.getRight()-101);
                                    }else if(aux.getBottom()-aux.getTop()<=100){
                                        aux.setTop(aux.getBottom()-101);
                                    }
                                }
                            }
                            if(distanceSecondPoint<=acceptDistance) {
                                //100x100 is Dimension of Rectangle limit
                                if(aux.getRight()-aux.getLeft()>100 && aux.getBottom()-aux.getTop()>100){
                                    aux.setRight(getX);
                                    aux.setBottom(getY);
                                }else{
                                    //100+1 for no limit dimension
                                    if(aux.getRight()-aux.getLeft()<=100){
                                        aux.setRight(aux.getLeft()+101);
                                    }else if(aux.getBottom()-aux.getTop()<=100){
                                        aux.setBottom(aux.getTop()+101);
                                    }
                                }
                            }
                        }
                        if(mode==2) { //Move
                            aux.setRight(getX + aux.getRight()-getPastX);
                            aux.setBottom(getY + aux.getBottom()-getPastY);
                            aux.setLeft(getX - (getPastX-aux.getLeft()));
                            aux.setTop(getY - (getPastY-aux.getTop()));
                        }
                    }else{
                        //adaptRectangle Setter dimensions
                        adaptRectangle(aux);
                    }
                } else if (myFigures.get(figureSelected) instanceof Line) {
                    Line temp = (Line) myFigures.get(figureSelected);
                    //distance a the first point of interaction
                    float distanceFirstPoint = distancePoint_to_Point(getX,getY,temp.getStartX(),temp.getStartY());
                    //distance a the second point of interaction
                    float distanceSecondPoint = distancePoint_to_Point(getX,getY,temp.getStopX(),temp.getStopY());
                    //distance a the line
                    //float distanceToLine = temp.distancePoint_to_Line(getX,getY);
                    if(checkLine(temp)) {
                        if(mode == 1){ //ReSize
                            if (distanceFirstPoint <= acceptDistance) {
                                //50 is distance between points start and stop
                                if(distancePoint_to_Point(temp.getStartX(),temp.getStartY(),temp.getStopX(),temp.getStopY())<=50){
                                    if(temp.getStartX()<temp.getStopX()||temp.getStartY()<temp.getStopY()) {
                                        temp.setStartX(temp.getStartX() - 1);
                                        temp.setStartY(temp.getStartY() - 1);
                                        temp.setStopX(temp.getStopX() + 1);
                                        temp.setStopY(temp.getStopY() + 1);
                                    }else {
                                        temp.setStartX(temp.getStartX() + 1);
                                        temp.setStartY(temp.getStartY() + 1);
                                        temp.setStopX(temp.getStopX() - 1);
                                        temp.setStopY(temp.getStopY() - 1);
                                    }
                                }else{
                                    temp.setStartX(getX);
                                    temp.setStartY(getY);
                                }
                            }
                            if (distanceSecondPoint <= acceptDistance) {
                                temp.setStopX(getX);
                                temp.setStopY(getY);
                            }
                        }
                         if(mode==2) { //Move
                            temp.setStopX(getX + temp.getStopX() - getPastX);
                            temp.setStopY(getY + temp.getStopY() - getPastY);
                            temp.setStartX(getX - (getPastX - temp.getStartX()));
                            temp.setStartY(getY - (getPastY - temp.getStartY()));
                        }
                    }else{
                        adaptLine(temp);
                    }
                } else if (myFigures.get(figureSelected) instanceof Ellipse) {
                    Ellipse temp = (Ellipse) myFigures.get(figureSelected);
                    //distance a the first point of interaction
                    temp.updateParameters(temp);
                    float distanceFirstPoint = distancePoint_to_Point(getX, getY, temp.getLeft(), temp.getBottom() / 2 + temp.getTop() / 2);
                    //distance a the second point of interaction
                    float distanceSecondPoint = distancePoint_to_Point(getX, getY, temp.getRight(), temp.getBottom() / 2 + temp.getTop() / 2);
                    //distance a the third point of interaction
                    float distanceThirdPoint = distancePoint_to_Point(getX, getY, temp.getRight() / 2 + temp.getLeft() / 2, temp.getTop());
                    //distance a the forty point of interaction
                    float distanceFortyPoint = distancePoint_to_Point(getX, getY, temp.getRight() / 2 + temp.getLeft() / 2, temp.getBottom());
                    //Dimensions of the Ellipse
                    float widthEllipse= Math.abs(temp.getRight() - temp.getLeft());
                    float heightEllipse = Math.abs(temp.getBottom() - temp.getTop());
                    if (checkEllipse(temp)) {
                        if(mode==1) { //Resize
                            if (distanceFirstPoint <= acceptDistance) {
                                if (widthEllipse > 100) {
                                    temp.setLeft(getX);
                                } else {
                                    temp.setLeft(temp.getRight() - 101);
                                }
                                temp.updateParameters(temp);
                            } else if (distanceSecondPoint <= acceptDistance) {
                                if (widthEllipse > 100) {
                                    temp.setRight(getX);
                                } else {
                                    temp.setRight(temp.getLeft() + 101);
                                }
                                temp.updateParameters(temp);
                            } else if (distanceThirdPoint <= acceptDistance) {
                                if (heightEllipse > 100) {
                                    temp.setTop(getY);
                                } else {
                                    temp.setTop(temp.getBottom() - 101);
                                }
                                temp.updateParameters(temp);
                            } else if (distanceFortyPoint <= acceptDistance) {
                                if (heightEllipse > 100) {
                                    temp.setBottom(getY);
                                } else {
                                    temp.setBottom(temp.getTop() + 101);
                                }
                                temp.updateParameters(temp);
                            }
                        }
                        if(mode==2){ //Move
                            temp.setRight(getX + temp.getRight() - getPastX);
                            temp.updateParameters(temp);
                            temp.setBottom(getY + temp.getBottom() - getPastY);
                            temp.updateParameters(temp);
                            temp.setLeft(getX - (getPastX - temp.getLeft()));
                            temp.updateParameters(temp);
                            temp.setTop(getY - (getPastY - temp.getTop()));
                            temp.updateParameters(temp);
                        }
                    }else{
                        adaptEllipse(temp);
                    }
                } else {
                    System.out.println("No Tips");
                }
            }
            getPastX = getX;
            getPastY = getY;
            invalidate(); //reDraw
        }
        //if(acct == MotionEvent.ACTION_UP ){

            /*Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    menuLeft.setVisibility(INVISIBLE);
                    menuRight.setVisibility(INVISIBLE);
                }
            },10000);
            System.out.println("data test");*/
        //}
        return true;
    }//End Method

}