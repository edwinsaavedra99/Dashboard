package com.example.dashboard.Models;

public class FileProject {
    private String imageFileProject;
    private String nameFileProject;
    private String DateFileProject;
    private String descriptionFileProject;

    public FileProject(String imageFileProject, String nameFileProject, String dateFileProject, String descriptionFileProject) {
        this.imageFileProject = imageFileProject;
        this.nameFileProject = nameFileProject;
        DateFileProject = dateFileProject;
        this.descriptionFileProject = descriptionFileProject;
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
}
