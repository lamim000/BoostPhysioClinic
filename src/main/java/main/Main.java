package main;
import model.*;
import service.BookingManager;
import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class Main {
    private static BookingManager manager = new BookingManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        preloadData();
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addPatient();
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
    // OSTEOPATHY
    Physiotherapist osteo1 = new Physiotherapist("O01", "Dr. Amelia Clarke", "London", "07700111222", List.of("Osteopathy"));
    osteo1.addTreatment(new Treatment("Spine Mobilisation", "Osteopathy", 60));
    osteo1.addTreatment(new Treatment("Cranial Osteopathy", "Osteopathy", 45));
    osteo1.addAvailability(DayOfWeek.SUNDAY, LocalTime.of(17, 0));
    osteo1.addAvailability(DayOfWeek.TUESDAY, LocalTime.of(14, 0));
    osteo1.setConsultationFee(15.0);

    Physiotherapist osteo2 = new Physiotherapist("O02", "Dr. Oliver Smith", "Manchester", "07700333444", List.of("Osteopathy"));
    osteo2.addTreatment(new Treatment("Muscle Energy Technique", "Osteopathy", 50));
    osteo2.addAvailability(DayOfWeek.MONDAY, LocalTime.of(10, 0));
    osteo2.addAvailability(DayOfWeek.THURSDAY, LocalTime.of(16, 0));
    osteo2.setConsultationFee(18.0);

    // REHABILITATION
    Physiotherapist rehab1 = new Physiotherapist("R01", "Dr. Emily Davies", "Birmingham", "07700555666", List.of("Rehabilitation"));
    rehab1.addTreatment(new Treatment("Pool Rehabilitation", "Rehabilitation", 60));
    rehab1.addTreatment(new Treatment("Post-op Recovery", "Rehabilitation", 40));
    rehab1.addAvailability(DayOfWeek.WEDNESDAY, LocalTime.of(11, 0));
    rehab1.addAvailability(DayOfWeek.SATURDAY, LocalTime.of(13, 0));
    rehab1.setConsultationFee(20.0);

    Physiotherapist rehab2 = new Physiotherapist("R02", "Dr. Jack Wilson", "Leeds", "07700777888", List.of("Rehabilitation"));
    rehab2.addTreatment(new Treatment("Massage Therapy", "Rehabilitation", 45));
    rehab2.addAvailability(DayOfWeek.TUESDAY, LocalTime.of(9, 30));
    rehab2.addAvailability(DayOfWeek.FRIDAY, LocalTime.of(14, 30));
    rehab2.setConsultationFee(17.5);

    // PHYSIOTHERAPY
    Physiotherapist physio1 = new Physiotherapist("P01", "Dr. Sophie Brown", "Bristol", "07700999000", List.of("Physiotherapy"));
    physio1.addTreatment(new Treatment("Acupuncture", "Physiotherapy", 50));
    physio1.addTreatment(new Treatment("Electrotherapy", "Physiotherapy", 30));
    physio1.addAvailability(DayOfWeek.SUNDAY, LocalTime.of(17, 0));
    physio1.addAvailability(DayOfWeek.WEDNESDAY, LocalTime.of(15, 0));
    physio1.setConsultationFee(12.5);

    Physiotherapist physio2 = new Physiotherapist("P02", "Dr. Harry Taylor", "Liverpool", "07700222444", List.of("Physiotherapy"));
    physio2.addTreatment(new Treatment("Joint Mobilisation", "Physiotherapy", 40));
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
        System.out.println("1. Add new patient");
        System.out.println("2. Book appointment");
        System.out.println("3. Cancel appointment");
        System.out.println("4. Attend appointment");
        System.out.println("5. Print report");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addPatient() {
    int nextIdNumber = manager.getAllPatients().size() + 1;
    String id = String.format("P%02d", nextIdNumber);  // e.g., P05, P06

    System.out.println("Patient ID: " + id);
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Address: ");
    String address = scanner.nextLine();
    System.out.print("Enter Phone: ");
    String phone = scanner.nextLine();

    Patient patient = new Patient(id, name, address, phone);
    manager.addPatient(patient);
    System.out.println("Patient added with ID: " + id);
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

    // Show all doctors grouped by expertise
    System.out.println("\nAvailable Doctors:");
    List<Physiotherapist> doctorList = new ArrayList<>();
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
                        ", Consultation Fee: " + p.getConsultationFee()+ " Pounds");
                doctorList.add(p);
                count++;
            }
        }
    }

    // Select doctor
    System.out.print("\nEnter doctor number to continue: ");
    int docChoice = Integer.parseInt(scanner.nextLine()) - 1;
    if (docChoice < 0 || docChoice >= doctorList.size()) {
        System.out.println("❌ Invalid doctor selection.");
        return;
    }

    Physiotherapist selectedDoctor = doctorList.get(docChoice);

    // Show next 5 available date-time slots
    System.out.println("\nNext 5 Available Dates:");
    List<LocalDateTime> upcoming = new ArrayList<>();
    LocalDateTime base = LocalDateTime.now();
    while (upcoming.size() < 5) {
        for (AvailabilitySlot slot : selectedDoctor.getAvailability()) {
            LocalDateTime next = slot.getNextOccurrence();
            if (next.isAfter(base)) {
                upcoming.add(next);
            }
            if (upcoming.size() == 5) break;
        }
        base = base.plusDays(1);
    }

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
        System.out.println("Consultation fee: " + selectedDoctor.getConsultationFee()+" Pounds");
        System.out.println("1. Confirm Appointment \n2. Cancel");
        int confirm = Integer.parseInt(scanner.nextLine());
        if (confirm == 1) {
            Treatment treatment = selectedDoctor.getTreatments().get(0); // default treatment
            Appointment confirmedAppointment = new Appointment(treatment, selectedDoctor, patient, chosenDate);
            if (manager.bookAppointment(confirmedAppointment)) {
                System.out.println("✅ Appointment confirmed! ID: " + confirmedAppointment.getId());
            } else {
                System.out.println("❌ Could not confirm. Possible conflict.");
            }
        } else {
            System.out.println("❌ Appointment not confirmed.");
        }
    } else {
        System.out.println("❌ Invalid date selection.");
    }
}



    private static void cancelAppointment() {
        System.out.print("Enter appointment ID to cancel: ");
        String id = scanner.nextLine();
        if (manager.cancelAppointment(id)) {
            System.out.println("Appointment cancelled.");
        } else {
            System.out.println("Appointment not found.");
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