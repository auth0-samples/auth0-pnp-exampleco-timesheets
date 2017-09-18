package com.auth0.samples.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ej on 7/9/17.
 */

public class TimeSheet {
    private String userId;
    private String projectName;
    private String date;
    private double hours;
    private boolean status;
    private String id;

    public TimeSheet(String userId, String projectName, String date, double hours, boolean status, String id) {
        this.userId = userId;
        this.projectName = projectName;
        this.date = date;
        this.hours = hours;
        this.status = status;
        this.id = id;
    }

    public String getUserID() {
        return userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDate() {
        return date;
    }

    public double getHours() {
        return hours;
    }

    public String getId() {
        return id;
    }

    public boolean isApproved() { return status; }
}
