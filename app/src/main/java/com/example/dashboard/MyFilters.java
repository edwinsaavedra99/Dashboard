package com.example.dashboard;

import android.graphics.Bitmap;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.LUT;
import static org.opencv.core.CvType.CV_8UC1;

class MyFilters {
    private Mat img;
    private Bitmap image;
    MyFilters(Mat img, Bitmap image){
        this.img = img;
        this.image = image;
    }
    Bitmap filterCanny(){
        Mat img_result = img.clone();
        Imgproc.cvtColor(img_result,img_result,Imgproc.COLOR_RGB2BGRA);
        Imgproc.Canny(img,img_result,80,90);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(),img_result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result,img_bitmap);
        return img_bitmap;
    }
    Bitmap filterRGB(){
        Mat img_result = img.clone();
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(),img_result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result,img_bitmap);
        return  img_bitmap;
    }
    Bitmap filterMorph(){
        Mat img_result = img.clone();
        Imgproc.cvtColor(img_result,img_result,Imgproc.COLOR_RGB2BGRA);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(3,3));
        Imgproc.morphologyEx(img,img_result,Imgproc.MORPH_GRADIENT,kernel);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(),img_result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result,img_bitmap);
        return img_bitmap;
    }
    Bitmap filerSepia(){
        Mat  mSepiaKernel;
        mSepiaKernel = new Mat(4, 4, CvType.CV_32F);
        mSepiaKernel.put(0, 0, /* R */0.189f, 0.769f, 0.393f, 0f);
        mSepiaKernel.put(1, 0, /* G */0.168f, 0.686f, 0.349f, 0f);
        mSepiaKernel.put(2, 0, /* B */0.131f, 0.534f, 0.272f, 0f);
        mSepiaKernel.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 1f);
        Mat img_result = img.clone();
        Imgproc.cvtColor(img_result, img_result, Imgproc.COLOR_BGR2RGBA);
        Core.transform(img_result, img_result, mSepiaKernel);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        return img_bitmap;
    }
    Bitmap filterColor(int colorMap){
        Mat img_result = img.clone();
        Imgproc.applyColorMap(img_result,img_result,colorMap);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        return img_bitmap;
    }
    Bitmap filterSummer(){
        Mat img_result = img.clone();
        Imgproc.applyColorMap(img_result,img_result,Imgproc.COLORMAP_SUMMER);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        return img_bitmap;
    }

    Bitmap filterPink(){
        Mat img_result = img.clone();
        Imgproc.applyColorMap(img_result,img_result,Imgproc.COLORMAP_PINK);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        return img_bitmap;
    }
    Bitmap filterReduceColorsGray(int numColors){
        Mat img_result = img.clone();
        Imgproc.cvtColor(img_result,img_result,Imgproc.COLOR_BGR2GRAY);
        img_result = reduceColorsGray(img_result, numColors);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        return img_bitmap;
    }
    Bitmap filterReduceColors(int numColorRed, int numColorGreen , int numColorBlue){
        Mat img_result_aux = new Mat();
        Bitmap aux = image;
        Utils.bitmapToMat(aux, img_result_aux);
        Mat img_result = reduceColors(img_result_aux, numColorRed, numColorGreen, numColorBlue);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        return img_bitmap;
    }
    Bitmap filterPencil(){
        Mat img_result = img.clone();
        Imgproc.cvtColor(img_result,img_result,Imgproc.COLOR_BGR2GRAY);
        Imgproc.adaptiveThreshold(img_result,img_result,255, Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,9,2);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        return img_bitmap;
    }
    Bitmap filterCarton(int numColorRed, int numColorGreen , int numColorBlue){
        Mat img1 = new Mat();
        Bitmap aux = image;
        Utils.bitmapToMat(aux, img1);
        Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGRA2BGR);
        Mat img_result = cartoon(img1, numColorRed, numColorGreen, numColorBlue);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        return img_bitmap;
    }

    private Mat reduceColorsGray(Mat img, int numColors) {
        Mat LUT = createLUT(numColors);
        assert LUT != null;
        LUT(img, LUT, img);
        return img;
    }
    private Mat createLUT(int numColors) {
        if (numColors < 0 || numColors > 256)
            return null;
        Mat lookupTable = Mat.zeros(new Size(1, 256), CV_8UC1);
        int startIdx = 0;
        for (int x = 0; x < 256; x += 256.0 / numColors) {
            lookupTable.put(x, 0, x);
            for (int y = startIdx; y < x; y++) {
                if (lookupTable.get(y, 0)[0] == 0)
                    lookupTable.put(y, 0, lookupTable.get(x, 0));
            }
            startIdx = x;
        }
        return lookupTable;
    }
    private Mat reduceColors(Mat img, int numRed, int numGreen, int numBlue) {
        Mat redLUT = createLUT(numRed);
        Mat greenLUT = createLUT(numGreen);
        Mat blueLUT = createLUT(numBlue);
        List<Mat> BGR = new ArrayList<>(3);
        Core.split(img, BGR);
        assert blueLUT != null;
        LUT(BGR.get(0), blueLUT, BGR.get(0));
        assert greenLUT != null;
        LUT(BGR.get(1), greenLUT, BGR.get(1));
        assert redLUT != null;
        LUT(BGR.get(2), redLUT, BGR.get(2));
        Core.merge(BGR, img);
        return img;
    }
    private Mat cartoon(Mat img, int numRed, int numGreen, int numBlue) {
        Mat reducedColorImage = reduceColors(img, numRed, numGreen, numBlue);
        Mat result = new Mat();
        Imgproc.cvtColor(img, result, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(result, result, 15);
        Imgproc.adaptiveThreshold(result, result, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 2);
        Imgproc.cvtColor(result, result, Imgproc.COLOR_GRAY2BGR);
        Core.bitwise_and(reducedColorImage, result, result);
        return result;
    }
}
