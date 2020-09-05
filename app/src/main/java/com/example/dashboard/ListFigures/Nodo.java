package com.example.dashboard.ListFigures;

import java.util.ArrayList;
import java.util.List;

public class Nodo <T>{
    private T dato;
    private List<Nodo<T>> hijos;
    private Nodo<T> padre;
    public Nodo(T dato) {
        this.dato = dato;
        this.hijos = new ArrayList<>();
    }
    public Nodo(Nodo<T> nodo) {
        this.dato = (T) nodo.getDato();
        hijos = new ArrayList<>();
    }
    public void agregarHijo(Nodo<T> hijo) {
        hijo.setPadre(this);
        hijos.add(hijo);
    }
    public void agregarHijoEn(int posicion, Nodo<T> hijo) {
        hijo.setPadre(this);
        this.hijos.add(posicion, hijo);
    }
    public void setHijos(List<Nodo<T>> hijos) {
        for (Nodo<T> hijo : hijos)
            hijo.setPadre(this);
        this.hijos = hijos;
    }
    public void eliminarHijos() {
        this.hijos.clear();
    }
    public Nodo<T> eliminarHijoEn(int posicion) {
        return hijos.remove(posicion);
    }
    public void eliminarHijo(Nodo<T> hijoABorrar)   {
        List <Nodo<T>> list = getHijos();
        list.remove(hijoABorrar);
    }
    public T getDato() {
        return this.dato;
    }
    public Nodo<T> getPadre() {
        return this.padre;
    }
    public void setPadre(Nodo<T> padre) {
        this.padre = padre;
    }
    public List<Nodo<T>> getHijos() {
        return this.hijos;
    }
    public Nodo<T> getHijoEn(int posicion) {
        return hijos.get(posicion);
    }
    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (obj instanceof Nodo) {
            if (((Nodo<?>) obj).getDato().equals(this.dato))
                return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return this.dato.toString();
    }
}