package model;
import java.util.*;
import model.Appointment;

public class Patient {
    private String id, name, address, phone;
    private List<Appointment> appointments = new ArrayList<>();

    public Patient(String id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Appointment> getAppointments() { return appointments; }

    public void addAppointment(Appointment a) { appointments.add(a); }
    public void removeAppointment(Appointment a) { appointments.remove(a); }
}