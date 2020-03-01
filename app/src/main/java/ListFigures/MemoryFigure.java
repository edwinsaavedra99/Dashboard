package ListFigures;

import java.util.ArrayList;

import Figures.Figure;

public class MemoryFigure {
    static ArrayList<Figure> memoryList = new ArrayList<Figure>();
    static ArrayList<Integer> codMemoryList = new ArrayList<Integer>();
    static ArrayList<Integer> indexInList = new ArrayList<Integer>();
    static int lenghtMemory = 5;
    static int indexControlZ = -1;
    static void addElementMemory(int cod, int index, Figure figure){
        memoryList.add(figure);
        codMemoryList.add(cod);
        indexInList.add(index);
        indexControlZ++;
        if(memoryList.size() > lenghtMemory){
            indexControlZ--;
            memoryList.remove(0);
            codMemoryList.remove(0);
            indexInList.remove(0);
        }
    }
    static int controlZinMemory(){
        if(indexControlZ -1 >=0)
            indexControlZ--;
        int aux = codMemoryList.size()-1;
        if(aux <= -1){
            return -1;
        }else {
            return codMemoryList.get(aux);
        }
    }
    static int controlYinMemory(){
        if(indexControlZ +1 < lenghtMemory){
            indexControlZ++;
        }
        return codMemoryList.get(indexControlZ);
    }

}
