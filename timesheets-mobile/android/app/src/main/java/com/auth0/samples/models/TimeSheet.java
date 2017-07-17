package com.auth0.samples.models;

import java.util.Date;

/**
 * Created by ej on 7/9/17.
 */

public class TimeSheet {
    private String userID;
    private String projectName;
    private String date;
    private double hours;
    private int ID;

    public TimeSheet(String gUserID, String gProjectName, String gDate, double gHours, int gID) {
        this.userID = gUserID;
        this.projectName = gProjectName;
        this.date = gDate;
        this.hours = gHours;
        this.ID = gID;
    }

    public String getUserID() {
        return userID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDateString() {
        return date;
    }

    public double getHours() {
        return hours;
    }

    public int getID() {
        return ID;
    }
}
