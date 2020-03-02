package ListFigures;

import java.util.ArrayList;

import Figures.Figure;

public class MemoryFigure {
    static ArrayList<ArrayList<Figure>> memoryList = new ArrayList<ArrayList<Figure>>();
    static ArrayList<Integer> codMemoryList = new ArrayList<Integer>();
    static ArrayList<Integer> indexInList = new ArrayList<Integer>();
    static int lenghtMemory = 30;
    static int indexControlZ = -1;
    static int indexControlY = -1;

    static void addElementMemory(int cod, int index, ArrayList<Figure> figure){
        //if(memoryList.size() > lenghtMemory){
//            memoryList.remove(0);
  //          codMemoryList.remove(0);
    //        indexInList.remove(0);
            indexControlZ++;
  //      }
//        }else {
        memoryList.add(indexControlZ, figure);
        codMemoryList.add(indexControlZ, cod);
        indexInList.add(indexControlZ, index);
    }
    static void controlZinMemory(){
        if(indexControlZ >=0) {
            indexControlZ--;
        }
    }
    static void controlYinMemory(){
        if(indexControlZ +1 < memoryList.size()){
            indexControlZ++;
        }
    }

}
