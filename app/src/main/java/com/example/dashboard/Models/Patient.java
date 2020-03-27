package com.example.dashboard.Models;

import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject getJsonPatient(){
        JSONObject patient = new JSONObject();
        try {
            patient.put("name",this.name);
            patient.put("residency",this.residencia);
            patient.put("age",this.age);
            patient.put("description",this.description);
            patient.put("dni",this.cod);
            if (sex)  //true - masculino
                patient.put("gender",0);
            else  //false - femenino
                patient.put("gender",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return patient;
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
