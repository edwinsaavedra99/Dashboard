package com.example.dashboard.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileProject {
    private String imageFileProject;
    private String nameFileProject;
    private String DateFileProject;
    private String timeFileProject;
    private String descriptionFileProject;
    private String dateAux;
    public FileProject(String nameFileProject, String descriptionFileProject) {
        this.nameFileProject = nameFileProject;
        this.descriptionFileProject = descriptionFileProject;
        DateTimeInitial();
    }

    public FileProject(String imageFileProject, String nameFileProject, String descriptionFileProject) {
        this.imageFileProject = imageFileProject;
        this.nameFileProject = nameFileProject;
        this.descriptionFileProject = descriptionFileProject;
        DateTimeInitial();
    }
    public FileProject(String imageFileProject, String nameFileProject, String descriptionFileProject,String dateFileProject) {
        this.imageFileProject = imageFileProject;
        this.nameFileProject = nameFileProject;
        this.descriptionFileProject = descriptionFileProject;
        this.dateAux = dateFileProject;
        char[] test = dateFileProject.toCharArray();
        try {
            this.DateFileProject = dateFileProject.substring(0,dateFileProject.indexOf("_")) + "/" + test[5] + test[6] + "/" + test[8] + test[9];
            this.timeFileProject = dateFileProject.substring(11,13)  + ":" + test[17] + test[18];
        }catch (Exception e){
            this.DateFileProject = "";
            this.timeFileProject = "";
        }
    }
    public void DateTimeInitial(){
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        this.timeFileProject = hourFormat.format(date);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.DateFileProject = dateFormat.format(date);
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss");
        String test = hourdateFormat.format(date);
        System.out.println(test);
    }

    public String getTimeFileProject() {
        return timeFileProject;
    }

    public void setTimeFileProject(String timeFileProject) {
        this.timeFileProject = timeFileProject;
    }

    public String getDateFileProject() {
        return DateFileProject;
    }

    public void setDateFileProject(String dateFileProject) {
        DateFileProject = dateFileProject;
    }

    public String getImageFileProject() {
        return imageFileProject;
    }

    public void setImageFileProject(String imageFileProject) {
        this.imageFileProject = imageFileProject;
    }

    public String getNameFileProject() {
        return nameFileProject;
    }

    public void setNameFileProject(String nameFileProject) {
        this.nameFileProject = nameFileProject;
    }

    public String getDescriptionFileProject() {
        return descriptionFileProject;
    }

    public void setDescriptionFileProject(String descriptionFileProject) {
        this.descriptionFileProject = descriptionFileProject;
    }

    public String getDateAux() {
        return dateAux;
    }

    public void setDateAux(String dateAux) {
        this.dateAux = dateAux;
    }
}
