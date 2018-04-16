package com.smartpullup.smartpullup;

import java.text.DateFormat;

/**
 * Created by Jorren on 16/04/2018.
 */

public class Exercise {

    private String Date;
    private double MaxSpeed;
    private double AvgSpeed;
    private double TotalTime;
    private int TotalPullups;

    public Exercise() {
    }

    public Exercise(double maxSpeed, double avgSpeed, double totalTime, int totalPullups) {
        Date = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(System.currentTimeMillis());
        MaxSpeed = maxSpeed;
        AvgSpeed = avgSpeed;
        TotalTime = totalTime;
        TotalPullups = totalPullups;
    }

    public String getDate() {
        return Date;
    }

    public double getMaxSpeed() {
        return MaxSpeed;
    }

    public double getAvgSpeed() {
        return AvgSpeed;
    }

    public double getTotalTime() {
        return TotalTime;
    }

    public int getTotalPullups() {
        return TotalPullups;
    }
}
