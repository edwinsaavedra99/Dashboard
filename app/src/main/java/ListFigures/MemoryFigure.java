package ListFigures;
import java.util.ArrayList;

import Figures.Figure;

class MemoryFigure {
    static ArrayList<ElementMemory> memoryList = new ArrayList<>();
    static ArrayList<ElementMemory> memoryListCtrlZ = new ArrayList<>();
    static int indexControlZ = -1;
    static int indexControlY = -1;
    static void addElementMemory(int cod, int index, ArrayList<Figure> figure){
        indexControlZ++;
        if (memoryList.size() > indexControlZ) {
            memoryList.subList(indexControlZ, memoryList.size()).clear();
        }
        ElementMemory elementMemory = new ElementMemory(figure,cod,index);
        memoryList.add(elementMemory);
        memoryListCtrlZ.clear();
    }
    static int controlZinMemory(){
        int aux = indexControlZ;
        if(aux!=-1) {
            indexControlY++;
            memoryListCtrlZ.add(memoryList.get(aux));
        }
        if(aux >=0) {
            indexControlZ--;
        }
        return aux;
    }
    static void controlYinMemory(){

    }
    static void deleteMemory(){
        memoryList.clear();
        memoryListCtrlZ.clear();
        indexControlZ=-1;
        indexControlY=-1;
    }
}
