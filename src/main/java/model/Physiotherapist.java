package model;

import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class Physiotherapist {
    private String id;
    private String name;
    private String address;
    private String phone;
    private List<String> expertise;
    private List<Treatment> treatments;
    private List<AvailabilitySlot> availability;
    private double consultationFee;

    public Physiotherapist(String id, String name, String address, String phone, List<String> expertise) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.expertise = expertise;
        this.treatments = new ArrayList<>();
        this.availability = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public List<AvailabilitySlot> getAvailability() {
        return availability;
    }

    public void addAvailability(DayOfWeek day, LocalTime time) {
        availability.add(new AvailabilitySlot(day, time));
    }

    public void setConsultationFee(double fee) {
        this.consultationFee = fee;
    }

    public double getConsultationFee() {
        return consultationFee;
    }
}
