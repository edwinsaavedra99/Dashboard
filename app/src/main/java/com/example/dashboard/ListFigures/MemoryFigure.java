package com.example.dashboard.ListFigures;
import java.util.ArrayList;

import com.example.dashboard.Figures.Figure;

public class MemoryFigure {
    static ArrayList<ElementMemory> memoryList = new ArrayList<>();
    static ArrayList<ElementMemory> memoryListCtrlZ = new ArrayList<>();
    static ArrayList<ElementMemory> memoryListCtrlZwithCtrlY = new ArrayList<>();
    static int indexControlZ = -1;
    static int indexControlY = -1;
    static int cont=-1;
    public static boolean changeSave = true;
    static void addElementMemory(int cod, int index, ArrayList<Figure> figure){
        indexControlZ++;
        if (memoryList.size() > indexControlZ) {
            memoryList.subList(indexControlZ, memoryList.size()).clear();
        }
        ElementMemory elementMemory = new ElementMemory(figure,cod,index);
        memoryList.add(elementMemory);
        memoryListCtrlZ.clear();
        memoryListCtrlZwithCtrlY.clear();
        indexControlY = -1;
        cont = -1;
        changeSave = false;
    }

    static void addElementMemory(int cod, int index, int indexTwo, ArrayList<Figure> figure){
        indexControlZ++;
        if (memoryList.size() > indexControlZ) {
            memoryList.subList(indexControlZ, memoryList.size()).clear();
        }
        ElementMemory elementMemory = new ElementMemory(figure,cod,index,indexTwo);
        memoryList.add(elementMemory);
        memoryListCtrlZ.clear();
        memoryListCtrlZwithCtrlY.clear();
        indexControlY = -1;
        cont = -1;
        changeSave = false;
    }

    static int controlZinMemory(){
        int aux = indexControlZ;
        if(aux!=-1) {
            indexControlY++;
            if(memoryListCtrlZ.size() > indexControlY){
                memoryListCtrlZ.subList(indexControlY,memoryListCtrlZ.size()).clear();
            }
            memoryListCtrlZ.add(memoryList.get(aux));
        }
        if(aux >=0) {
            changeSave = false;
            indexControlZ--;
        }
        return aux;
    }
    static int controlZwithControlY(){
        int aux = cont;
        if(cont >= 0 ) {
            cont--;
        }
        return aux;
    }
    static int controlYinMemory(){
        int aux = indexControlY;
        if(aux!=-1){
            indexControlZ++;
            if (memoryList.size() > indexControlZ) {
                memoryList.subList(indexControlZ, memoryList.size()).clear();
            }
            memoryList.add(memoryListCtrlZ.get(indexControlY));
        }
        if(aux >=0) {
            changeSave = false;
            indexControlY--;
        }
        return aux;
    }
    static void deleteMemory(){
        memoryList.clear();
        memoryListCtrlZ.clear();
        indexControlZ=-1;
        indexControlY=-1;
        cont=-1;
    }
}
