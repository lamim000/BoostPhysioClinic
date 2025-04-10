package main;

import model.*;
import service.BookingManager;
import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class Main {
    private static BookingManager manager = new BookingManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        preloadData();
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> managePatient();
                case "2" -> bookAppointment();
                case "3" -> cancelAppointment();
                case "4" -> attendAppointment();
                case "5" -> manager.printReport();
                case "6" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

private static void preloadData() {
    // Treatments
    Treatment neuralMobilisation = new Treatment("Neural mobilisation", "General", 60);
    Treatment acupuncture = new Treatment("Acupuncture", "Physiotherapy", 50);
    Treatment massage = new Treatment("Massage", "Rehabilitation", 45);
    Treatment mobilisationOfSpine = new Treatment("Mobilisation of the spine and joints", "Osteopathy", 55);
    Treatment poolRehabilitation = new Treatment("Pool rehabilitation", "Rehabilitation", 60);
    Treatment sportsInjuryRecovery = new Treatment("Sports injury recovery", "Rehabilitation", 50);
    Treatment postOpRecovery = new Treatment("Post-op recovery", "Rehabilitation", 40);

    // OSTEOPATHY
    Physiotherapist osteo1 = new Physiotherapist("O01", "Dr. Amelia Clarke", "London", "07700111222", List.of("Osteopathy", "Spine Mobilisation", "Cranial Osteopathy"));
    osteo1.addTreatment(neuralMobilisation);  // Adding specific treatments
    osteo1.addTreatment(acupuncture);
    osteo1.addAvailability(DayOfWeek.SUNDAY, LocalTime.of(17, 0));
    osteo1.addAvailability(DayOfWeek.TUESDAY, LocalTime.of(14, 0));
    osteo1.setConsultationFee(15.0);

    Physiotherapist osteo2 = new Physiotherapist("O02", "Dr. Oliver Smith", "Manchester", "07700333444", List.of("Osteopathy", "Muscle Energy Technique", "Spine Mobilisation"));
    osteo2.addTreatment(massage);
    osteo2.addTreatment(mobilisationOfSpine);
    osteo2.addAvailability(DayOfWeek.MONDAY, LocalTime.of(10, 0));
    osteo2.addAvailability(DayOfWeek.TUESDAY, LocalTime.of(14, 0));
    osteo2.setConsultationFee(18.0);

    // REHABILITATION
    Physiotherapist rehab1 = new Physiotherapist("R01", "Dr. Emily Davies", "Birmingham", "07700555666", List.of("Rehabilitation", "Pool Rehabilitation", "Neuro Rehabilitation"));
    rehab1.addTreatment(poolRehabilitation);
    rehab1.addTreatment(sportsInjuryRecovery);
    rehab1.addTreatment(postOpRecovery);
    rehab1.addAvailability(DayOfWeek.WEDNESDAY, LocalTime.of(11, 0));
    rehab1.addAvailability(DayOfWeek.SATURDAY, LocalTime.of(13, 0));
    rehab1.setConsultationFee(20.0);

    Physiotherapist rehab2 = new Physiotherapist("R02", "Dr. Jack Wilson", "Leeds", "07700777888", List.of("Rehabilitation", "Massage Therapy"));
    rehab2.addTreatment(massage);
    rehab2.addTreatment(poolRehabilitation);
    rehab2.addAvailability(DayOfWeek.TUESDAY, LocalTime.of(9, 30));
    rehab2.addAvailability(DayOfWeek.FRIDAY, LocalTime.of(14, 30));
    rehab2.setConsultationFee(17.5);

    // PHYSIOTHERAPY
    Physiotherapist physio1 = new Physiotherapist("P01", "Dr. Sophie Brown", "Bristol", "07700999000", List.of("Physiotherapy", "Acupuncture", "Electrotherapy"));
    physio1.addTreatment(acupuncture);  // Adding treatments
    physio1.addTreatment(massage);
    physio1.addAvailability(DayOfWeek.SUNDAY, LocalTime.of(17, 0));
    physio1.addAvailability(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0));
    physio1.setConsultationFee(12.5);

    Physiotherapist physio2 = new Physiotherapist("P02", "Dr. Harry Taylor", "Liverpool", "07700222444", List.of("Physiotherapy", "Joint Mobilisation", "Muscle Energy Technique"));
    physio2.addTreatment(sportsInjuryRecovery);
    physio2.addTreatment(mobilisationOfSpine);
    physio2.addAvailability(DayOfWeek.MONDAY, LocalTime.of(11, 0));
    physio2.addAvailability(DayOfWeek.FRIDAY, LocalTime.of(16, 30));
    physio2.setConsultationFee(14.0);

    // Add doctors to system
    manager.addPhysio(osteo1);
    manager.addPhysio(osteo2);
    manager.addPhysio(rehab1);
    manager.addPhysio(rehab2);
    manager.addPhysio(physio1);
    manager.addPhysio(physio2);

    // Preloaded UK-style Patients
    manager.addPatient(new Patient("P01", "James Walker", "London", "07900000111"));
    manager.addPatient(new Patient("P02", "Chloe Harris", "Manchester", "07900000222"));
    manager.addPatient(new Patient("P03", "George Evans", "Bristol", "07900000333"));
    manager.addPatient(new Patient("P04", "Isla Thompson", "Leeds", "07900000444"));
}



    private static void printMenu() {
        System.out.println("\n=== Boost Physio Clinic Booking System ===");
        System.out.println("1. Add/Remove patient");
        System.out.println("2. Book appointment");
        System.out.println("3. Cancel appointment");
        System.out.println("4. Attend appointment");
        System.out.println("5. Print report");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    // Manage Add/Remove Patient
    private static void managePatient() {
        System.out.println("Choose an option:");
        System.out.println("1. Add new patient");
        System.out.println("2. Remove patient");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                addPatient();
                break;

            case 2:
                removePatient();
                break;

            default:
                System.out.println("❌ Invalid choice.");
                break;
        }
    }

    // Add Patient
    private static void addPatient() {
        int nextIdNumber = manager.getAllPatients().size() + 1;
        String id = String.format("P%02d", nextIdNumber);  // e.g., P01, P02, etc.

        System.out.println("Patient ID: " + id);
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        Patient patient = new Patient(id, name, address, phone);
        manager.addPatient(patient);
        System.out.println("✅ Patient added with ID: " + id);
    }

    // Remove Patient
    private static void removePatient() {
        System.out.print("Enter Patient ID to remove: ");
        String removePatientId = scanner.nextLine();

        // Check if patient exists
        Patient patientToRemove = manager.getPatientById(removePatientId);
        if (patientToRemove != null) {
            manager.removePatient(removePatientId);
            System.out.println("✅ Patient removed: " + patientToRemove.getName());
        } else {
            System.out.println("❌ Patient with ID " + removePatientId + " not found.");
        }
    }

private static void bookAppointment() {
    // Show all patients
    System.out.println("Available Patients:");
    for (Patient p : manager.getAllPatients()) {
        System.out.println("- " + p.getId() + ": " + p.getName());
    }

    System.out.print("Enter Patient ID: ");
    String pid = scanner.nextLine();
    Patient patient = manager.getPatientById(pid);

    if (patient == null) {
        System.out.println("❌ Patient not found.");
        return;
    }

    // Choose doctor list option
    System.out.println("\nChoose doctor list view option:");
    System.out.println("1. View doctor list by expertise");
    System.out.println("2. View doctor list by name");
    System.out.print("Enter your choice (1 or 2): ");
    int listChoice = Integer.parseInt(scanner.nextLine());

    List<Physiotherapist> doctorList = new ArrayList<>();  // Initialize here

    if (listChoice == 1) {
        // Show doctors grouped by expertise
        System.out.println("\nAvailable Doctors by Expertise:");
        Set<String> allExpertise = new HashSet<>();
        for (Physiotherapist p : manager.getAllPhysios()) {
            allExpertise.addAll(p.getExpertise());
        }

        int count = 1;
        for (String category : allExpertise) {
            System.out.println("\n[" + category + "]");

            for (Physiotherapist p : manager.getAllPhysios()) {
                if (p.getExpertise().contains(category)) {
                    System.out.println(count + ". " + p.getName() + " (" + category + ") - Available: " +
                            p.getAvailability().stream().map(Object::toString).collect(Collectors.joining(", ")) +
                            ", Consultation Fee: " + p.getConsultationFee() + " Pounds");
                    doctorList.add(p);
                    count++;
                }
            }
        }
    } else if (listChoice == 2) {
        // Show doctors sorted by name
        System.out.println("\nAvailable Doctors by Name:");
        List<Physiotherapist> sortedDoctors = manager.getAllPhysios().stream()
                .sorted(Comparator.comparing(Physiotherapist::getName))
                .collect(Collectors.toList());

        int count = 1;
        for (Physiotherapist p : sortedDoctors) {
            System.out.println(count + ". " + p.getName() + " - Available: " +
                    p.getAvailability().stream().map(Object::toString).collect(Collectors.joining(", ")) +
                    ", Consultation Fee: " + p.getConsultationFee() + " Pounds");
            doctorList.add(p);
            count++;
        }
    } else {
        System.out.println("❌ Invalid selection.");
        return;
    }

    // Select doctor
    System.out.print("\nEnter doctor number to continue: ");
    int docChoice = Integer.parseInt(scanner.nextLine()) - 1;
    if (docChoice < 0 || docChoice >= doctorList.size()) {
        System.out.println("❌ Invalid doctor selection.");
        return;
    }

    Physiotherapist selectedDoctor = doctorList.get(docChoice);

    // Define the list of valid treatments
    List<String> validTreatments = Arrays.asList(
            "Neural mobilisation", 
            "Acupuncture", 
            "Massage", 
            "Mobilisation of the spine and joints", 
            "Pool rehabilitation", 
            "Sports injury recovery", 
            "Post-op recovery"
    );

    // Show available treatments for the selected doctor
    System.out.println("\nAvailable Treatments for " + selectedDoctor.getName() + ":");
    List<Treatment> treatments = selectedDoctor.getTreatments().stream()
            .filter(treatment -> validTreatments.contains(treatment.getName()))
            .collect(Collectors.toList());

    if (treatments.isEmpty()) {
        System.out.println("No valid treatments available for this doctor.");
        return;
    }

    for (int i = 0; i < treatments.size(); i++) {
        Treatment treatment = treatments.get(i);
        System.out.println((i + 1) + ". " + treatment.getName() + " (" + treatment.getDuration() + " mins)");
    }

    // Select treatment
    System.out.print("\nEnter treatment number to select: ");
    int treatmentChoice = Integer.parseInt(scanner.nextLine()) - 1;
    if (treatmentChoice < 0 || treatmentChoice >= treatments.size()) {
        System.out.println("❌ Invalid treatment selection.");
        return;
    }

    Treatment selectedTreatment = treatments.get(treatmentChoice);

    // Show next available date-time slots for the next 4 weeks
    System.out.println("\nNext Available Dates From 4 Weeks:");
    List<LocalDateTime> upcoming = new ArrayList<>();
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusWeeks(4);  // Next 4 weeks

    // Generate available slots for the next 4 weeks
    while (!startDate.isAfter(endDate)) {
        for (AvailabilitySlot slot : selectedDoctor.getAvailability()) {
            if (slot.getDay() == startDate.getDayOfWeek()) {
                LocalDateTime fullDateTime = LocalDateTime.of(startDate, slot.getTime());

                // Check if the slot is already booked
                boolean isBooked = manager.getAllAppointments().stream().anyMatch(a ->
                        a.getPhysio().getId().equals(selectedDoctor.getId()) &&
                        a.getTime().withSecond(0).withNano(0).equals(fullDateTime.withSecond(0).withNano(0)) &&
                        a.getStatus() == Appointment.Status.BOOKED
                );

                // If booked, display "Booked", otherwise display the time slot as available
                if (isBooked) {
                    System.out.println(fullDateTime + " -> Booked");
                } else {
                    upcoming.add(fullDateTime);
                }
            }
        }
        startDate = startDate.plusDays(1);
    }

    // Display upcoming available slots
    for (int i = 0; i < upcoming.size(); i++) {
        LocalDateTime dt = upcoming.get(i);
        System.out.println((i + 1) + ". " + dt.getDayOfWeek() + " " + dt.toLocalTime() + " (" + dt.toLocalDate() + ")");
    }

    // Select date
    System.out.print("\nSelect a date by number to view details: ");
    int selectedDateIndex = Integer.parseInt(scanner.nextLine()) - 1;
    if (selectedDateIndex >= 0 && selectedDateIndex < upcoming.size()) {
        LocalDateTime chosenDate = upcoming.get(selectedDateIndex);
        System.out.println("\nAppointment for " + selectedDoctor.getName() + " on " +
                chosenDate.getDayOfWeek() + ", " + chosenDate.toLocalDate() + " at " + chosenDate.toLocalTime());
        System.out.println("Consultation fee: " + selectedDoctor.getConsultationFee() + " Pounds");
        System.out.println("1. Confirm Appointment \n2. Cancel");
        int confirm = Integer.parseInt(scanner.nextLine());
        if (confirm == 1) {
            Appointment confirmedAppointment = new Appointment(selectedTreatment, selectedDoctor, patient, chosenDate);
            confirmedAppointment.setId("A" + String.format("%03d", new Random().nextInt(1000)));  // Generate unique appointment ID
            if (manager.bookAppointment(confirmedAppointment)) {
                System.out.println("✅ Appointment confirmed! ID: " + confirmedAppointment.getId());
            } else {
                System.out.println("❌ Could not confirm. You have another appointment at this time.");
            }
        } else {
            System.out.println("❌ Appointment not confirmed.");
        }
    } else {
        System.out.println("❌ Invalid date selection.");
    }
}


private static void cancelAppointment() {
    // Show all booked appointments
    System.out.println("\n=== List of All Booked Appointments ===");
    List<Appointment> bookedAppointments = manager.getAllAppointments().stream()
            .filter(a -> a.getStatus() == Appointment.Status.BOOKED)
            .collect(Collectors.toList());

    if (bookedAppointments.isEmpty()) {
        System.out.println("❌ No appointments found.");
        return;
    }

    // Display all booked appointments with ID, patient name, doctor name, and time
    int count = 1;
    for (Appointment appointment : bookedAppointments) {
        Patient patient = appointment.getPatient();
        Physiotherapist doctor = appointment.getPhysio();
        LocalDateTime time = appointment.getTime();
        System.out.println(count + ". Appointment ID: " + appointment.getId());
        System.out.println("   Patient: " + patient.getName() + " (" + patient.getId() + ")");
        System.out.println("   Doctor: " + doctor.getName() + " (" + doctor.getId() + ")");
        System.out.println("   Time: " + time.toLocalDate() + " at " + time.toLocalTime());
        System.out.println("   Consultation Fee: £" + doctor.getConsultationFee());
        System.out.println("-------------------------------");
        count++;
    }

    // Prompt user to select an appointment by number
    System.out.print("\nSelect the appointment number to cancel: ");
    int appointmentChoice = Integer.parseInt(scanner.nextLine()) - 1;

    if (appointmentChoice >= 0 && appointmentChoice < bookedAppointments.size()) {
        Appointment selectedAppointment = bookedAppointments.get(appointmentChoice);

        // Cancel the selected appointment by checking its ID
        String appointmentId = selectedAppointment.getId();
        if (appointmentId != null && manager.cancelAppointment(appointmentId)) {
            System.out.println("✅ Appointment cancelled successfully!");
        } else {
            System.out.println("❌ Could not cancel the appointment. Appointment ID is null or not found.");
        }
    } else {
        System.out.println("❌ Invalid appointment selection.");
    }
}



    private static void attendAppointment() {
        System.out.print("Enter appointment ID to mark as attended: ");
        String id = scanner.nextLine();
        if (manager.attendAppointment(id)) {
            System.out.println("Appointment attended.");
        } else {
            System.out.println("Could not mark appointment.");
        }
    }
}
