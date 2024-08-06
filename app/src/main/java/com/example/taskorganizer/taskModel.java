package com.example.taskorganizer;

public class taskModel {

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    String uploaded;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    String details;
    public taskModel(String task, String uploaded, String supervisor, String details) {
        this.task = task;
        this.supervisor = supervisor;
        this.uploaded = uploaded;
        this.details = details;

    }
    taskModel(){
    }
    String task;

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    String supervisor;
}
