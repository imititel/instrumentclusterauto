package com.example.instrumentclusterauto;

public class DigitalDashboard {
    private int speedometer;
    private int fuelLevel;
    private int engineTemp;
    private int rpm;
    private String clock;

    public DigitalDashboard(int speedometer, int fuelLevel, int engineTemp, int rpm, String clock) {
        this.speedometer = speedometer;
        this.fuelLevel = fuelLevel;
        this.engineTemp = engineTemp;
        this.rpm = rpm;
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

    public int getRpm() {
        return rpm;
    }

    public String getClock() {
        return clock;
    }

    public String displayInfo() {
        return "Speed: " + speedometer + " km/h, Fuel Level: " + fuelLevel + "%, Temp: " + engineTemp + "°C, RPM: " + rpm + ", Time: " + clock;
    }
}
