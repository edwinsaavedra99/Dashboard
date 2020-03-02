package ListFigures;

import java.util.ArrayList;

import Figures.Figure;

public class ElementMemory {
    private ArrayList<Figure> memoryList;
    private int codMemoryList;
    private int indexInList;

    ElementMemory(ArrayList<Figure> memoryList, int codMemoryList, int indexInList) {
        this.memoryList = new ArrayList<>(memoryList);
        this.codMemoryList = codMemoryList;
        this.indexInList = indexInList;
    }

    public ArrayList<Figure> getMemoryList() {
        return memoryList;
    }

    public void setMemoryList(ArrayList<Figure> memoryList) {
        this.memoryList = memoryList;
    }

    public int getCodMemoryList() {
        return codMemoryList;
    }

    public void setCodMemoryList(int codMemoryList) {
        this.codMemoryList = codMemoryList;
    }

    public int getIndexInList() {
        return indexInList;
    }

    public void setIndexInList(int indexInList) {
        this.indexInList = indexInList;
    }
}
