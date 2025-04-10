package model;

public class Treatment {
    private String name;
    private String expertise;
    private int duration;

    public Treatment(String name, String expertise, int duration) {
        this.name = name;
        this.expertise = expertise;
        this.duration = duration;
    }

    public String getName() { return name; }
    public String getExpertise() { return expertise; }
    public int getDuration() { return duration; }
}