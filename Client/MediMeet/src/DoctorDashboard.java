import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class DoctorDashboard extends JFrame {

  private JLabel showAppointments;
  private JButton prescribeMedication;
  private JButton logout;
  private JList<Appointment> appointmentList;

  private Appointment selectedAppointment = null;

  public DoctorDashboard(Doctor d) {
    setTitle("Doctor Dashboard");
    setSize(800, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    // Create the top panel for the title
    JPanel topPanel = new JPanel();
    topPanel.setBackground(Colors.PRIMARY_COLOR);
    topPanel.setPreferredSize(new Dimension(getWidth(), 70));
    topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    // Create the title label
    JLabel titleLabel = new JLabel("Welcome, " + d.getName() + "!");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setForeground(Color.WHITE);
    topPanel.add(titleLabel);

    JLabel baseDescription = new JLabel("Select an appointment and prescribe medication!");
    baseDescription.setFont(new Font("Arial", Font.ITALIC, 18));
    baseDescription.setForeground(Color.WHITE);
    topPanel.add(baseDescription);

    // Add the top panel to the frame
    add(topPanel, BorderLayout.NORTH);

    // Create the left panel for the buttons
    JPanel leftPanel = new JPanel();
    leftPanel.setBackground(Colors.PRIMARY_COLOR);
    leftPanel.setPreferredSize(new Dimension(180, getHeight()));
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    int padding = 10; // Padding value

    // Create the showAppointments label
    showAppointments = new JLabel("View Appointments");
    showAppointments.setAlignmentX(Component.CENTER_ALIGNMENT);
    showAppointments.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
    showAppointments.setForeground(Color.WHITE);
    leftPanel.add(showAppointments);

    // Add spacing between buttons
    leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // Create the prescribeMedication button
    prescribeMedication = new JButton("Prescribe Medication");
    prescribeMedication.setAlignmentX(Component.CENTER_ALIGNMENT);
    prescribeMedication.addActionListener(this::prescribeMedication);
    prescribeMedication.setBackground(Colors.BACKGROUND_COLOR);
    prescribeMedication.setForeground(Colors.BUTTON_TEXT_COLOR);
    prescribeMedication.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
    prescribeMedication.setMaximumSize(new Dimension(160, 40)); // Set maximum size for button
    leftPanel.add(prescribeMedication);

    // Add spacing between buttons
    leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // Create the logout button
    logout = new JButton("Logout");
    logout.setAlignmentX(Component.CENTER_ALIGNMENT);
    logout.addActionListener(this::logout);
    logout.setBackground(new Color(255, 99, 71));
    logout.setMaximumSize(new Dimension(160, 40)); // Set maximum size for button
    leftPanel.add(logout);

    // Add the left panel to the frame
    add(leftPanel, BorderLayout.WEST);

    ArrayList<Appointment> appointments = getAppointmentsByDoctorID(d.getDoctorID());
    DefaultListModel<Appointment> appointmentListModel = new DefaultListModel<>();

    for (Appointment a : appointments) {
      appointmentListModel.addElement(a);
    }

    // Create the central area for appointment list
    appointmentList = new JList<>(appointmentListModel);
    JScrollPane scrollPane = new JScrollPane(appointmentList);

    // Add the central area to the frame
    add(scrollPane, BorderLayout.CENTER);

    // Set the frame visible
    setVisible(true);
  }

  private void prescribeMedication(ActionEvent e) {
    Appointment selectedAppointment = appointmentList.getSelectedValue();
    if (selectedAppointment != null) {
      this.selectedAppointment = selectedAppointment;
      JTextArea textArea = new JTextArea(4, 20);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);

      int option = JOptionPane.showOptionDialog(
          this,
          new JScrollPane(textArea),
          "Perscribe to Mr/Ms. " + selectedAppointment.getPatientName() + ": ",
          JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE,
          null,
          null,
          null);

      if (option == JOptionPane.OK_OPTION) {
        String prescriptionText = textArea.getText();
        System.out.println("Prescription: " + prescriptionText);
        Prescription prescription = new Prescription(prescriptionText, selectedAppointment);
        selectedAppointment.setPrescription(prescription);
        selectedAppointment.setAttended(true);
        selectedAppointment.save();
        selectedAppointment.printAllInformationToTextFile();
        System.out.println("Prescription saved successfully.");
      }
    }
  }

  private void logout(ActionEvent e) {
    JOptionPane.showMessageDialog(this, "Logging out...");
    redirectToLogin();
  }

  private void redirectToLogin() {
    LoginPage loginPage = new LoginPage();
    loginPage.setVisible(true);
    dispose();
  }

  // Get all appointments for this doctor
  ArrayList<Appointment> getAppointmentsByDoctorID(int id) {
    ArrayList<Appointment> appointments = new ArrayList<>();
    ArrayList<Storable> allAppointments = Storable.all(Appointment.class);
    for (Storable appointment : allAppointments) {
      Appointment tmp = (Appointment) appointment;
      System.out.println(tmp.getDoctorID() + " is doctor ID.");
      if (tmp.getDoctorID() == id) {
        appointments.add(tmp);
      }
    }
    return appointments;
  }

  public static void main(String[] args) {
    Doctor d = new Doctor("Dr. John Doe", "hasaanahmad10023@gmail.com", "123", "123", "fdsfsd", 122, "Heart",
        new ArrayList<>(0));
    DoctorDashboard doctorDashboard = new DoctorDashboard(d);
    doctorDashboard.setVisible(true);

  }
}