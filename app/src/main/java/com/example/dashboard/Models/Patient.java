package com.example.dashboard.Models;

public class Patient {

    private String name;
    private String residencia;
    private int age;
    private String description;
    private int cod;
    private boolean sex;

    public Patient(String name, String residencia, int age, String description, int cod, boolean sex) {
        this.name = name;
        this.residencia = residencia;
        this.age = age;
        this.description = description;
        this.cod = cod;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
