package com.example.dashboard.ListFigures;

import java.util.ArrayList;

import com.example.dashboard.Figures.Figure;

public class ElementMemory {
    private ArrayList<Figure> memoryList;
    private int codMemoryList;
    private int indexInList;
    private int indexTwoList;
    ElementMemory(ArrayList<Figure> memoryList, int codMemoryList, int indexInList) {
        this.memoryList = new ArrayList<>(memoryList);
        this.codMemoryList = codMemoryList;
        this.indexInList = indexInList;
    }
    ElementMemory(ArrayList<Figure> memoryList, int codMemoryList,  int indexInList, int indexTwoList) {
        this.memoryList = new ArrayList<>(memoryList);
        this.codMemoryList = codMemoryList;
        this.indexInList = indexInList;
        this.indexTwoList = indexTwoList;
    }

    public int getIndexTwoList() {
        return indexTwoList;
    }

    public void setIndexTwoList(int indexTwoList) {
        this.indexTwoList = indexTwoList;
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
