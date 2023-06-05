import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Doctor extends User {

  private static final long serialVersionUID = 6403115235281749848L;

  private int doctorID;
  private String specialization;
  private ArrayList<Appointment> currentAppointments; // Open appointments which this doctor is currently part of

  public Doctor(
    String name,
    String email,
    String password,
    String phone,
    String address,
    int doctorID,
    String specialization,
    ArrayList<Appointment> currentAppointments
  ) {
    super(name, email, password, phone, address);
    this.doctorID = doctorID;
    this.specialization = specialization;
    this.currentAppointments = currentAppointments;
  }

  // Register a new doctor
  public static void register(
    String name,
    String email,
    String password,
    String phone,
    String address,
    int doctorID,
    String specialization
  ) {
    ArrayList<Storable> doctors = Storable.all(Doctor.class);
    if (validID(doctorID)) {
      System.out.println("Doctor ID already exists");
      return;
    } else {
      Doctor newDoctor = new Doctor(
        name,
        email,
        password,
        phone,
        address,
        doctorID,
        specialization,
        null
      );
      newDoctor.save();
      System.out.println("Doctor registered successfully");
    }
  }

  public static boolean validID(int id) {
    // Check if doctorID is unique
    ArrayList<Storable> doctors = Storable.all(Doctor.class);
    for (Storable doctor : doctors) {
      Doctor tmp = (Doctor) doctor;
      if (tmp.getDoctorID() == id) {
        return true;
      }
    }
    return false;
  }

  public static Doctor login(int doctorID, String password) {
    ArrayList<Storable> doctors = Storable.all(Doctor.class);
    for (Storable doctor : doctors) {
      Doctor tmp = (Doctor) doctor;
      if (tmp.getDoctorID() == doctorID && tmp.getPassword().equals(password)) {
        return tmp;
      }
    }
    return null;
  }

  // Doctor can view all the appointments he is part of and attend patient and
  // assign him with prescription
  public static Prescription attendPatient(
    Appointment appointment,
    String description
  ) {
    // generateing random id
    Random rand = new Random();
    int id = rand.nextInt(1000);
    // making object of perscription
    Prescription prescription = new Prescription(description, appointment);
    // adding prescription to appointment
    appointment.setPrescription(prescription);
    return prescription;
  }

  // Getter Setters
  public int getDoctorID() {
    return doctorID;
  }

  public void setDoctorID(int doctorID) {
    this.doctorID = doctorID;
  }

  public static Doctor getDoctorByName(String name) {
    ArrayList<Storable> doctors = Storable.all(Doctor.class);
    for (Storable doctor : doctors) {
      Doctor tmp = (Doctor) doctor;
      if (tmp.getName().equals(name)) {
        return tmp;
      }
    }
    return null;
  }

  public String getSpecialization() {
    return specialization;
  }

  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }

  public ArrayList<Appointment> getCurrentAppointments() {
    return currentAppointments;
  }

  public void setCurrentAppointments(
    ArrayList<Appointment> currentAppointments
  ) {
    this.currentAppointments = currentAppointments;
  }

  @Override
  public String toString() {
    return "Dr. " + this.getName();
  }

  public static boolean isDoctorIDTaken(int doctorID2) {
    ArrayList<Storable> doctors = Storable.all(Doctor.class);
    for (Storable doctor : doctors) {
      Doctor tmp = (Doctor) doctor;
      if (tmp.getDoctorID() == doctorID2) {
        return true;
      }
    }
    return false;
  }
}
