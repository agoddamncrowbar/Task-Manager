package com.example.taskorganizer;

public class reportModel {
    String Email;
    String Report;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getReport() {
        return Report;
    }

    public void setReport(String report) {
        Report = report;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    String Title;

    public reportModel(String email, String report, String title,String uploaded ) {
        this.Email = email;
        this.Report = report;
        this.Title = title;
        this.uploaded = uploaded;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    String uploaded;
    reportModel() {
    }
}
