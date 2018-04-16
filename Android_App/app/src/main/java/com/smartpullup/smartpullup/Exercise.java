package com.smartpullup.smartpullup;

/**
 * Created by Jorren on 16/04/2018.
 */

public class Exercise {

    private double MaxSpeed;
    private double AvgSpeed;
    private double TotalTime;
    private int TotalPullups;

    public Exercise() {
    }

    public Exercise(double maxSpeed, double avgSpeed, double totalTime, int totalPullups) {
        MaxSpeed = maxSpeed;
        AvgSpeed = avgSpeed;
        TotalTime = totalTime;
        TotalPullups = totalPullups;
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
