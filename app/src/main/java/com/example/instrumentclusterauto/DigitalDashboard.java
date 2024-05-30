package com.example.instrumentclusterauto;

public class DigitalDashboard {
    private int speedometer;
    private int fuelLevel;
    private int engineTemp;
    private int tachometer;
    private String clock;

    public DigitalDashboard(int speedometer, int fuelLevel, int engineTemp, int tachometer, String clock) {
        this.speedometer = speedometer;
        this.fuelLevel = fuelLevel;
        this.engineTemp = engineTemp;
        this.tachometer = tachometer;
        this.clock = clock;
    }

    public int getSpeedometer() {
        return speedometer;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public int getEngineTemp() {
        return engineTemp;
    }

    public int getTachometer() {
        return tachometer;
    }

    public String getClock() {
        return clock;
    }

    public String displayInfo() {
        return "Speedometer: " + speedometer + " km/h | " +
                "Fuel Level: " + fuelLevel + "% | " +
                "Engine Temp: " + engineTemp + "°C | " +
                "RPM: " + tachometer + " | " +
                "Clock: " + clock;
    }
}