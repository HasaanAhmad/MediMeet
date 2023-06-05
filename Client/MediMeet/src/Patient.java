import java.util.ArrayList;
import java.util.Date;

public class Patient extends User {
  public static final long serialVersionUID = 6149778175131693507L;

  private int patientID;
  private ArrayList<Appointment> appointments;

  public Patient(String name, String email, String password, String phone, String address, int patientID,
      ArrayList<Appointment> appointments) {
    super(name, email, password, phone, address);
    this.patientID = patientID;
    this.appointments = appointments;
  }

  // Register a new patient
  public static Patient register(String name, String email, String password, String phone, String address,
      int patientID, Prescription prescription, int doctorID, ArrayList<Appointment> appointments) {
    ArrayList<Storable> patients = Storable.all(Patient.class);
    if (validID(patientID)) {
      System.out.println("Patient ID already exists");
      return null;
    } else {
      Patient newPatient = new Patient(name, email, password, phone, address, patientID,
          appointments);
      newPatient.save();
      System.out.println("Patient registered successfully");
      return newPatient;
    }
  }

  static boolean validID(int id) {
    // Check if patientID is unique
    ArrayList<Storable> patients = Storable.all(Patient.class);
    for (Storable patient : patients) {
      Patient tmp = (Patient) patient;
      if (tmp.getPatientID() == id) {
        return true;
      }
    }
    return false;
  }

  public static Patient login(int patientID, String password) {
    ArrayList<Storable> patients = Storable.all(Patient.class);
    for (Storable patient : patients) {
      Patient tmp = (Patient) patient;
      if (tmp.getPatientID() == patientID && tmp.getPassword().equals(password)) {
        return tmp;
      }
    }
    return null;
  }
  // Patient should be able to book an appointment. Appointment for the patient
  // should be stored in patient's appointment array. and in the respective
  // "currentAppointments" array of the doctor under specific doctor id.

  public void bookAppointment(Doctor doctor, Date date, int fee) {
    // Create a new appointment
    // random appointment id
    int appointmentID = (int) (Math.random() * 1000000);
    Appointment newAppointment = new Appointment(appointmentID, this.patientID, doctor.getDoctorID(), date, fee);
    newAppointment.save();
    // Add appointment to patient's appointment array
    this.appointments.add(newAppointment);
    // Add appointment to doctor's currentAppointments array
    doctor.getCurrentAppointments().add(newAppointment);
    // Update doctor's currentAppointments array in database
    doctor.save();
  }

  // Method should return the patient object with id
  public static Patient getPatient(int id) {
    ArrayList<Storable> patients = Storable.all(Patient.class);
    for (Storable patient : patients) {
      Patient tmp = (Patient) patient;
      if (tmp.getPatientID() == id) {
        return tmp;
      }
    }
    return null;
  }

  // getters and Setters
  public int getPatientID() {
    return patientID;
  }

  public static Patient getPatientByName(String patientName) {
    ArrayList<Storable> patients = Storable.all(Patient.class);
    for (Storable patient : patients) {
      Patient tmp = (Patient) patient;
      if (tmp.getName().equals(patientName)) {
        return tmp;
      }
    }
    return null;
  }

  public void setPatientID(int patientID) {
    this.patientID = patientID;
  }

  public ArrayList<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(ArrayList<Appointment> appointments) {
    this.appointments = appointments;
  }

  @Override
  public String toString() {
    return "Mr/Ms. " + this.getName();
  }

}
