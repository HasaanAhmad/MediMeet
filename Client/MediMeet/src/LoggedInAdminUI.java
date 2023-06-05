import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.print.Doc;
import javax.swing.*;

public class LoggedInAdminUI extends JFrame {

  private JList<String> list;
  private DefaultListModel<String> listModel;
  private JLabel welcomeLabel;
  private JTextArea appointmentTextArea;

  public LoggedInAdminUI() {
    setTitle("Logged In Admin");
    setSize(800, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create side bar buttons
    JButton viewDoctorsButton = createSidebarButton("View All Doctors");
    JButton viewPatientsButton = createSidebarButton("View All Patients");
    JButton viewAppointmentsButton = createSidebarButton("View All Appointments");
    JButton deleteDoctorButton = createSidebarButton("Delete Doctor");
    JButton deletePatientButton = createSidebarButton("Delete Patient");
    JButton deleteAppointmentButton = createSidebarButton("Delete Appointment");
    JButton logoutButton = createSidebarButton("Logout");
    logoutButton.setBackground(new Color(255, 99, 71));

    // Apply the desired colors
    viewDoctorsButton.setBackground(Colors.PRIMARY_COLOR);
    viewPatientsButton.setBackground(Colors.PRIMARY_COLOR);
    viewAppointmentsButton.setBackground(Colors.PRIMARY_COLOR);
    deleteDoctorButton.setBackground(Colors.PRIMARY_COLOR);
    deletePatientButton.setBackground(Colors.PRIMARY_COLOR);
    deleteAppointmentButton.setBackground(Colors.PRIMARY_COLOR);

    // Create main content panel
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.setBackground(Colors.BACKGROUND_COLOR);

    // Create header panel
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(Colors.BACKGROUND_COLOR);
    welcomeLabel = new JLabel("Welcome, Admin!");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
    headerPanel.add(welcomeLabel);
    contentPanel.add(headerPanel, BorderLayout.NORTH);

    // Create appointment panel
    appointmentTextArea = new JTextArea();
    appointmentTextArea.setBackground(Colors.BACKGROUND_COLOR);
    JScrollPane appointmentScrollPane = new JScrollPane(appointmentTextArea);
    contentPanel.add(appointmentScrollPane, BorderLayout.CENTER);

    // Create list for displaying elements
    listModel = new DefaultListModel<>();
    list = new JList<>(listModel);
    list.setBackground(Colors.BACKGROUND_COLOR);
    JScrollPane scrollPane = new JScrollPane(list);
    contentPanel.add(scrollPane, BorderLayout.SOUTH);

    // Add sidebar buttons to the side panel
    JPanel sidePanel = new JPanel();
    sidePanel.setLayout(new GridLayout(7, 1));
    sidePanel.setBackground(Colors.BACKGROUND_COLOR);
    sidePanel.add(viewDoctorsButton);
    sidePanel.add(viewPatientsButton);
    sidePanel.add(viewAppointmentsButton);
    sidePanel.add(deleteDoctorButton);
    sidePanel.add(deletePatientButton);
    sidePanel.add(deleteAppointmentButton);
    sidePanel.add(logoutButton);

    // Add panels to the main frame
    add(sidePanel, BorderLayout.WEST);
    add(contentPanel, BorderLayout.CENTER);

    // Attach action listeners to sidebar buttons
    viewDoctorsButton.addActionListener(this::viewDoctors);
    viewPatientsButton.addActionListener(this::viewPatients);
    viewAppointmentsButton.addActionListener(this::viewAppointments);
    deleteDoctorButton.addActionListener(this::deleteDoctor);
    deletePatientButton.addActionListener(this::deletePatient);
    deleteAppointmentButton.addActionListener(this::deleteAppointment);
    logoutButton.addActionListener(this::logout);
  }

  private JButton createSidebarButton(String buttonText) {
    JButton button = new JButton(buttonText);
    button.setPreferredSize(new Dimension(150, 30));
    return button;
  }

  private void viewDoctors(ActionEvent e) {
    Storable.fromDisk();
    // Clear the list
    listModel.clear();

    // Add dummy doctors to the list
    // All doctors from Storage

    ArrayList<Doctor> doctors = Admin.getAllDoctors();
    for (Doctor doctor : doctors) {
      listModel.addElement(doctor.getName());
    }
  }

  private void viewPatients(ActionEvent e) {
    // Clear the list
    listModel.clear();

    // Add dummy patients to the list
    // All patients from Storage
    ArrayList<Patient> patients = Admin.getAllPatients();
    for (Patient patient : patients) {
      listModel.addElement(patient.getName());
    }
  }

  private void viewAppointments(ActionEvent e) {
    // Clear the list
    listModel.clear();

    // Clear the appointment details
    appointmentTextArea.setText("");

    // All appointments from Storage
    ArrayList<Appointment> appointments = Admin.getAllAppointments();
    for (Appointment appointment : appointments) {
      listModel.addElement(
          appointment.getDoctorName() + " - " + appointment.getPatientName());
    }
    // Showing all details
    appointmentTextArea.append("Doctor Name - Patient Name - Date - Time\n");
    for (Appointment appointment : appointments) {
      appointmentTextArea.append(
          appointment.getDoctorName() +
              " - " +
              appointment.getPatientName() +
              " - " +
              appointment.getAppointmentDate() +
              " - " +
              "\n");
    }
  }

  private void deleteDoctor(ActionEvent e) {
    // Get the selected element from the list
    String selectedDoctor = list.getSelectedValue();

    // Perform the delete operation
    if (selectedDoctor != null) {
      // Delete the selected doctor from the system/database
      listModel.removeElement(selectedDoctor);
    } else {
      JOptionPane.showMessageDialog(
          this,
          "Please select a doctor to delete.",
          "Delete Doctor",
          JOptionPane.WARNING_MESSAGE);
    }
    Doctor d = Doctor.getDoctorByName(selectedDoctor);
    d.delete();
    Storable.toDisk();
    // Dialouge box to confirm deletion
    // Delete the doctor from the system/database
    JOptionPane.showMessageDialog(
        this,
        "Doctor deleted successfully. Restart App to see the changes",
        "Delete Doctor",
        JOptionPane.INFORMATION_MESSAGE);

    // Restarting Admin Dashboard
    dispose();
    new LoggedInAdminUI().setVisible(true);
  }

  private void deletePatient(ActionEvent e) {
    // Get the selected element from the list
    String selectedPatient = list.getSelectedValue();

    // Perform the delete operation
    if (selectedPatient != null) {
      // Delete the selected patient from the system/database
      listModel.removeElement(selectedPatient);
    } else {
      JOptionPane.showMessageDialog(
          this,
          "Please select a patient to delete.",
          "Delete Patient",
          JOptionPane.WARNING_MESSAGE);
    }
    Patient p = Patient.getPatientByName(selectedPatient);
    p.delete();
    Storable.toDisk();
    // Dialouge box to confirm deletion
    // Delete the patient from the system/database
    JOptionPane.showMessageDialog(
        this,
        "Patient deleted successfully. Restart App to see the changes",
        "Delete Patient",
        JOptionPane.INFORMATION_MESSAGE);

    // Restarting Admin Dashboard
    dispose();
    new LoggedInAdminUI().setVisible(true);
  }

  private void deleteAppointment(ActionEvent e) {
    // Get the selected element from the list
    // Doctor name and Patient name
    String selectedAppointment = list.getSelectedValue();
    String[] names = selectedAppointment.split(" - ");
    String selectedDoctor = names[0];
    String selectedPatient = names[1];

    // Delete the selected appointment from the system/database
    listModel.removeElement(selectedAppointment);

    Appointment a = Appointment.getAppointmentByNames(
        selectedDoctor,
        selectedPatient);
    a.delete();
    Storable.toDisk();
    // Dialouge box to confirm deletion
    JOptionPane.showMessageDialog(
        this,
        "Appointment deleted successfully. Restart App to see the changes",
        "Delete Appointment",
        JOptionPane.INFORMATION_MESSAGE);
    // Restarting Admin Dashboard
    dispose();
    new LoggedInAdminUI().setVisible(true);
  }

  private void logout(ActionEvent e) {
    // Go back to the login screen
    LoginPage loginUI = new LoginPage();
    loginUI.setVisible(true);
    dispose();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      LoggedInAdminUI adminUI = new LoggedInAdminUI();
      adminUI.setVisible(true);
    });
  }
}
