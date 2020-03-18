package com.example.dashboard.Models;

public class Project {
    private String nameProject;
    private String description;

    public Project(String nameProject, String description) {
        this.nameProject = nameProject;
        this.description = description;
    }

    public Project(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
