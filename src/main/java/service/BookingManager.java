package service;

import model.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BookingManager {
    private List<Patient> patients = new ArrayList<>();
    private List<Physiotherapist> physios = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

    public void addPatient(Patient p) {
        if (patients.stream().anyMatch(x -> x.getId().equals(p.getId()))) return;
        patients.add(p);
    }

    public void removePatient(String id) {
        patients.removeIf(p -> p.getId().equals(id));
    }

    public void addPhysio(Physiotherapist p) {
        physios.add(p);
    }

    public List<Appointment> findAppointmentsByExpertise(String expertise) {
        return appointments.stream()
            .filter(a -> a.getTreatment().getExpertise().equalsIgnoreCase(expertise) && a.getStatus() == Appointment.Status.BOOKED)
            .collect(Collectors.toList());
    }

    public List<Appointment> findAppointmentsByPhysioName(String name) {
        return appointments.stream()
            .filter(a -> a.getPhysio().getName().equalsIgnoreCase(name) && a.getStatus() == Appointment.Status.BOOKED)
            .collect(Collectors.toList());
    }

    public boolean bookAppointment(Appointment appointment) {
        boolean conflict = appointments.stream().anyMatch(existing ->
            existing.getTime().equals(appointment.getTime()) &&
            (existing.getPatient().getId().equals(appointment.getPatient().getId()) ||
             existing.getPhysio().getId().equals(appointment.getPhysio().getId())) &&
            existing.getStatus() == Appointment.Status.BOOKED
        );

        if (!conflict) {
            appointments.add(appointment);
            appointment.getPatient().addAppointment(appointment);
            return true;
        }
        return false;
    }

    public boolean cancelAppointment(String id) {
        for (Appointment a : appointments) {
            if (a.getId().equals(id)) {
                a.setStatus(Appointment.Status.CANCELLED);
                a.getPatient().removeAppointment(a);
                return true;
            }
        }
        return false;
    }

    public boolean attendAppointment(String id) {
        for (Appointment a : appointments) {
            if (a.getId().equals(id) && a.getStatus() == Appointment.Status.BOOKED) {
                a.setStatus(Appointment.Status.ATTENDED);
                return true;
            }
        }
        return false;
    }
    public List<Physiotherapist> getAllPhysios() {
    return physios;
}


public void printReport() {
    System.out.println("=== 🩺 Boost Physio Clinic Full Report ===");

    // 1. Show ALL doctor information
    System.out.println("\n👨‍⚕️ All Registered Doctors:");
    for (Physiotherapist doc : physios) {
        System.out.println("\nDoctor: " + doc.getName() + " (" + String.join(", ", doc.getExpertise()) + ")");
        System.out.println("ID: " + doc.getId());
        System.out.println("Address: " + doc.getAddress());
        System.out.println("Phone: " + doc.getPhone());
        System.out.printf("Consultation Fee: £%.2f\n", doc.getConsultationFee());
        System.out.print("Available Slots: ");
        if (doc.getAvailability().isEmpty()) {
            System.out.println("None");
        } else {
            System.out.println(doc.getAvailability().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
        }
    }

    // 2. Show ALL patient info + any appointments
    System.out.println("\n🧑‍🤝‍🧑 All Patients & Their Appointments:");

    if (patients.isEmpty()) {
        System.out.println("No patients registered.");
        return;
    }

    for (Patient patient : patients) {
        System.out.println("\nPatient: " + patient.getName() + " (ID: " + patient.getId() + ")");

        List<Appointment> patientAppointments = appointments.stream()
                .filter(a -> a.getPatient().getId().equals(patient.getId()))
                .collect(Collectors.toList());

        if (patientAppointments.isEmpty()) {
            System.out.println("Appointments: None");
        } else {
            System.out.println("Appointments:");
            for (Appointment appt : patientAppointments) {
                System.out.printf("- %s with %s on %s at %s | Fee: £%.2f\n",
                        appt.getTreatment().getName(),
                        appt.getPhysio().getName(),
                        appt.getTime().toLocalDate(),
                        appt.getTime().toLocalTime(),
                        appt.getPhysio().getConsultationFee());
            }
        }
    }
}


    public List<Patient> getAllPatients() {
    return patients;
}

    public Patient getPatientById(String id) {
        for(Patient p : patients) {
            if (p.getId().equals(id)){
                return p;
            }
        }
        return null;
       }
}