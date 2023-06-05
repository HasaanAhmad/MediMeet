import java.util.ArrayList;
import java.util.Date;
import javax.print.Doc;

import java.io.*;
import java.util.*;

public class Appointment extends Storable {

  private static final long serialVersionUID = 8682333983606994514L;

  private int appointmentID;
  private int doctorID;
  private String doctorName;
  private String patientName;
  private int patientID;
  private boolean attended;
  private Prescription prescription;
  private Date appointmentDate;
  private int appointmentFee;

  public Appointment(
    int appointmentID,
    int doctorID,
    int patientID,
    Date appointmentDate,
    int appointmentFee
  ) {
    this.appointmentID = appointmentID;
    this.doctorID = doctorID;
    this.patientID = patientID;
    this.appointmentDate = appointmentDate;
    this.appointmentFee = appointmentFee;
    this.attended = false;
    this.prescription = null;
  }

  public Appointment(
    int appointmentID,
    String doctorName,
    String patientName,
    Date appointmentDate,
    int appointmentFee
  ) {
    this.appointmentID = appointmentID;
    this.doctorName = doctorName;
    this.patientName = patientName;
    this.appointmentDate = appointmentDate;
    this.appointmentFee = appointmentFee;
  }

  // getters and Setters

  public int getAppointmentID() {
    return appointmentID;
  }

  public void setAppointmentID(int appointmentID) {
    this.appointmentID = appointmentID;
  }

  public int getDoctorID() {
    return doctorID;
  }

  public void setDoctorID(int doctorID) {
    this.doctorID = doctorID;
  }

  public int getPatientID() {
    return patientID;
  }

  public void setPatientID(int patientID) {
    this.patientID = patientID;
  }

  public Prescription getPrescription() {
    return prescription;
  }

  public void setPrescription(Prescription prescription) {
    this.prescription = prescription;
  }

  public boolean getAttended() {
    return attended;
  }

  public void setAttended(boolean attended) {
    this.attended = attended;
  }

  public Date getAppointmentDate() {
    return appointmentDate;
  }

  public void setAppointmentDate(Date appointmentDate) {
    this.appointmentDate = appointmentDate;
  }

  public int getAppointmentFee() {
    return appointmentFee;
  }

  public void setAppointmentFee(int appointmentFee) {
    this.appointmentFee = appointmentFee;
  }

  // Static method that takes patientID and returns Patient object of that patient
  public static Patient getPatient(int patientID) {
    return (Patient) Storable.get(Patient.class, patientID);
  }

  // Static method that takes doctorID and returns Doctor object of that doctor
  public static Doctor getDoctor(int doctorID) {
    return (Doctor) Storable.get(Doctor.class, doctorID);
  }

  @Override
  public String toString() {
    return "Appointment to Mr/Ms. " +
      patientName +
      " by Dr. " +
      doctorName +
      " on " +
      appointmentDate +
      " for " +
      appointmentFee +
      " L.E.";
  }

  public String getDoctorName() {
    return doctorName;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public static Appointment getAppointmentByNames(
    String doctorName,
    String patientName
  ) {
    ArrayList<Storable> appointments = Storable.all(Appointment.class);
    for (Storable appointment : appointments) {
      Appointment tmp = (Appointment) appointment;
      if (
        tmp.getDoctorName().equals(doctorName) &&
        tmp.getPatientName().equals(patientName)
      ) {
        return tmp;
      }
    }
    return null;
  }



  // Print all data related to this appointment to a text file including perscription,
  // everything in perfect format so that it can be printed and given to the patient:

  void printAllInformationToTextFile(){
    String fileName = "Appointment" + this.getAppointmentID() + ".txt";
    String data = "Appointment ID: " + this.getAppointmentID() + "\n" +
                  "Doctor Name: " + this.getDoctorName() + "\n" +
                  "Patient Name: " + this.getPatientName() + "\n" +
                  "Appointment Date: " + this.getAppointmentDate() + "\n" +
                  "Appointment Fee: " + this.getAppointmentFee() + "\n" +
                  "Prescription: " + this.getPrescription().getInstruction() + "\n";
    try {
      FileWriter myWriter = new FileWriter(fileName);
      myWriter.write(data);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred while eriting appointment and perscription to file.");
      e.printStackTrace();
    }
  }


}
