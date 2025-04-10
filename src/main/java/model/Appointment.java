package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    public enum Status { BOOKED, CANCELLED, ATTENDED }

    private String id;
    private Treatment treatment;
    private Physiotherapist physio;
    private Patient patient;
    private LocalDateTime time;
    private Status status;

    public Appointment(Treatment treatment, Physiotherapist physio, Patient patient, LocalDateTime time) {
        this.id = UUID.randomUUID().toString();
        this.treatment = treatment;
        this.physio = physio;
        this.patient = patient;
        this.time = time;
        this.status = Status.BOOKED;
    }

    public String getId() { return id; }
    public LocalDateTime getTime() { return time; }
    public Status getStatus() { return status; }
    public Physiotherapist getPhysio() { return physio; }
    public Patient getPatient() { return patient; }
    public Treatment getTreatment() { return treatment; }

    public void setStatus(Status status) { this.status = status; }
}