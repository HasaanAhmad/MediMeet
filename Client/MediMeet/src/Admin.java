import java.util.ArrayList;

public class Admin extends User {

  private int adminID;
  private String adminPassword;

  public Admin(String name, String email, String password, String phone, String address, int adminID,
      String adminPassword) {
    super(name, email, password, phone, address);
    this.adminID = adminID;
    this.adminPassword = adminPassword;
  }

  // Register a new admin
  public static Admin register(String name, String email, String password, String phone, String address, int adminID,
      String adminPassword) {
    ArrayList<Storable> admins = Storable.all(Admin.class);
    if (validID(adminID)) {
      System.out.println("Admin ID already exists");
      return null;
    } else {
      Admin newAdmin = new Admin(name, email, password, phone, address, adminID, adminPassword);
      newAdmin.save();
      System.out.println("Admin registered successfully");
      return newAdmin;
    }
  }

  static boolean validID(int id) {
    // Check if adminID is unique
    ArrayList<Storable> admins = Storable.all(Admin.class);
    for (Storable admin : admins) {
      Admin tmp = (Admin) admin;
      if (tmp.getAdminID() == id) {
        return true;
      }
    }
    return false;
  }

  public static Admin login(int adminID, String password) {
    ArrayList<Storable> admins = Storable.all(Admin.class);
    for (Storable admin : admins) {
      Admin tmp = (Admin) admin;
      if (tmp.getAdminID() == adminID && tmp.getPassword().equals(password)) {
        return tmp;
      }
    }
    return null;
  }

  // Method That returns list of all Doctors
  public static ArrayList<Doctor> getAllDoctors() {
    ArrayList<Storable> doctors = Storable.all(Doctor.class);
    ArrayList<Doctor> allDoctors = new ArrayList<Doctor>();
    for (Storable doctor : doctors) {
      allDoctors.add((Doctor) doctor);
    }
    return allDoctors;
  }

  // Method that returns list of all Patients
  public static ArrayList<Patient> getAllPatients() {
    ArrayList<Storable> patients = Storable.all(Patient.class);
    ArrayList<Patient> allPatients = new ArrayList<Patient>();
    for (Storable patient : patients) {
      allPatients.add((Patient) patient);
    }
    return allPatients;
  }

  // method that returns all appointments

  // getters and Setters

  public int getAdminID() {
    return adminID;
  }

  public void setAdminID(int adminID) {
    this.adminID = adminID;
  }

  public String getAdminPassword() {
    return adminPassword;
  }

  public void setAdminPassword(String adminPassword) {
    this.adminPassword = adminPassword;
  }

  // Method to get all appointments
  public static ArrayList<Appointment> getAllAppointments() {
    ArrayList<Storable> appointments = Storable.all(Appointment.class);
    ArrayList<Appointment> allAppointments = new ArrayList<Appointment>();
    for (Storable appointment : appointments) {
      allAppointments.add((Appointment) appointment);
    }
    return allAppointments;
  }

  public static String getDoctorName(int doctorID) {
    ArrayList<Storable> doctors = Storable.all(Doctor.class);
    for (Storable doctor : doctors) {
      Doctor tmp = (Doctor) doctor;
      if (tmp.getDoctorID() == doctorID) {
        return tmp.getName();
      }
    }
    return null;
  }

  public static String getPatientName(int patientID) {
    ArrayList<Storable> patients = Storable.all(Patient.class);
    for (Storable patient : patients) {
      Patient tmp = (Patient) patient;
      if (tmp.getPatientID() == patientID) {
        return tmp.getName();
      }
    }
    return null;
  }

  public static boolean removeDoctor(Doctor d) {
    ArrayList<Storable> doctors = Storable.all(Doctor.class);
    for (Storable doctor : doctors) {
      Doctor tmp = (Doctor) doctor;
      if (tmp.getDoctorID() == d.getDoctorID()) {
        tmp.delete();
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Storable.fromDisk();
    System.out.println("Doctors");
    ArrayList<Doctor> doctors = Admin.getAllDoctors();
    for (Doctor doctor : doctors) {
      System.out.println(doctor.getName());
    }
    System.out.println("Patients");
    ArrayList<Patient> patients = Admin.getAllPatients();
    for (Patient patient : patients) {
      System.out.println(patient.getName());
    }
    System.out.println("Appointments");
    ArrayList<Appointment> appointments = Admin.getAllAppointments();
    // Print name of doctor and patient and date of appointment
    for (Appointment appointment : appointments) {

    }
  }

}
