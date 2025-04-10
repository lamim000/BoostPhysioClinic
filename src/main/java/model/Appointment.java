package model;

import java.time.LocalDateTime;

public class Appointment {

    // Enum to represent the status of the appointment
    public enum Status {
        BOOKED,       // Appointment is booked
        CANCELLED,    // Appointment is cancelled
        ATTENDED      // Appointment is attended
    }

    private String id;  // Unique ID for the appointment
    private Physiotherapist physio;  // The physiotherapist for this appointment
    private Patient patient;  // The patient for this appointment
    private LocalDateTime time;  // Date and time of the appointment
    private Status status;  // Status of the appointment (BOOKED, CANCELLED, ATTENDED)
    private Treatment treatment;  // The treatment assigned for this appointment

    // Constructor to initialize the appointment
    public Appointment(Treatment treatment, Physiotherapist physio, Patient patient, LocalDateTime time) {
        this.treatment = treatment;
        this.physio = physio;
        this.patient = patient;
        this.time = time;
        this.status = Status.BOOKED;  // By default, the appointment is booked
    }

    // Getters and Setters for all the fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Physiotherapist getPhysio() {
        return physio;
    }

    public void setPhysio(Physiotherapist physio) {
        this.physio = physio;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    // To display a formatted string for the appointment
    @Override
    public String toString() {
        return "Appointment ID: " + id + ", Patient: " + patient.getName() + ", Doctor: " + physio.getName() +
               ", Date: " + time.toLocalDate() + " at " + time.toLocalTime() + ", Status: " + status;
    }
}
