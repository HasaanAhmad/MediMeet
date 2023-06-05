import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupGUI extends JFrame {

  private JTextField nameField;
  private JTextField patientField;
  private JTextField emailField;
  private JPasswordField passwordField;
  private JTextField phoneField;
  private JTextField addressField;
  private JButton signupButton;
  private JButton loginButton;

  public SignupGUI() {
    super("Signup Page");
    // Create components
    nameField = new JTextField(20);
    patientField = new JTextField(20);
    emailField = new JTextField(20);
    passwordField = new JPasswordField(20);
    phoneField = new JTextField(20);
    addressField = new JTextField(20);
    signupButton = new JButton("Signup");
    loginButton = new JButton("Login");

    // Set layout manager
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.WEST;
    constraints.insets = new Insets(10, 10, 10, 10);

    // Add components to the frame
    addLabel("Name:", constraints, 0, 0);
    addTextField(nameField, constraints, 1, 0);
    addLabel("Patient ID:", constraints, 0, 1);
    addTextField(patientField, constraints, 1, 1);
    addLabel("Email:", constraints, 0, 2);
    addTextField(emailField, constraints, 1, 2);
    addLabel("Password:", constraints, 0, 3);
    addPasswordField(passwordField, constraints, 1, 3);
    addLabel("Phone:", constraints, 0, 4);
    addTextField(phoneField, constraints, 1, 4);
    addLabel("Address:", constraints, 0, 5);
    addTextField(addressField, constraints, 1, 5);
    addButton(signupButton, constraints, 0, 6);
    addButton(loginButton, constraints, 1, 6);

    // Set frame properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);
    setVisible(true);

    // Add event listeners
    signupButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // Make object of patient and save it
          // Redirect to login page
          // Make current window invisible
          String name = nameField.getText();
          String email = emailField.getText();
          int patientID;
          String password = new String(passwordField.getPassword());
          String phone = phoneField.getText();
          String address = addressField.getText();
          ArrayList<Appointment> appointments = new ArrayList<Appointment>();

          // Validation for name field
          if (name.isEmpty() || !name.matches("^[a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(
              SignupGUI.this,
              "Invalid name format",
              "Input Error",
              JOptionPane.ERROR_MESSAGE
            );
            nameField.setText("");
            return;
          }

          // Validation for patient ID field
          try {
            patientID = Integer.parseInt(patientField.getText());
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
              SignupGUI.this,
              "Patient ID should be an integer",
              "Input Error",
              JOptionPane.ERROR_MESSAGE
            );
            patientField.setText("");
            return;
          }

          // Validation for email field
          if (
            !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
          ) {
            JOptionPane.showMessageDialog(
              SignupGUI.this,
              "Email should be in the correct format",
              "Input Error",
              JOptionPane.ERROR_MESSAGE
            );
            emailField.setText("");
            return;
          }

          // Validation for password field
          if (password.length() < 4) {
            JOptionPane.showMessageDialog(
              SignupGUI.this,
              "Password should be at least 4 characters long",
              "Input Error",
              JOptionPane.ERROR_MESSAGE
            );
            passwordField.setText("");
            return;
          }

          // Validation for phone field
          if (!phone.matches("^[0-9]{10,}$")) {
            JOptionPane.showMessageDialog(
              SignupGUI.this,
              "Phone should be an integer with at least 10 digits",
              "Input Error",
              JOptionPane.ERROR_MESSAGE
            );
            phoneField.setText("");
            return;
          }
          // Check patientID from database if it already exists or not
          if (Patient.getPatient(patientID) != null) {
            JOptionPane.showMessageDialog(
              SignupGUI.this,
              "Patient ID already exists",
              "Input Error",
              JOptionPane.ERROR_MESSAGE
            );
            patientField.setText("");
            return;
          } else {
            Patient patient = new Patient(
              name,
              email,
              password,
              phone,
              address,
              patientID,
              appointments
            );
            patient.save();
            JOptionPane.showMessageDialog(null, "Patient created successfully");
            PatientLoginGUI patientLoginGUI = new PatientLoginGUI();
            setVisible(false);
          }
        }
      }
    );

    loginButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // Redirect to login page
          // Make current window invisible
          PatientLoginGUI patientLoginGUI = new PatientLoginGUI();
          setVisible(false);
        }
      }
    );
  }

  private void addLabel(
    String text,
    GridBagConstraints constraints,
    int gridx,
    int gridy
  ) {
    constraints.gridx = gridx;
    constraints.gridy = gridy;
    JLabel label = new JLabel(text);
    add(label, constraints);
  }

  private void addTextField(
    JTextField textField,
    GridBagConstraints constraints,
    int gridx,
    int gridy
  ) {
    constraints.gridx = gridx;
    constraints.gridy = gridy;
    textField.setFont(textField.getFont().deriveFont(14f));
    add(textField, constraints);
  }

  private void addPasswordField(
    JPasswordField passwordField,
    GridBagConstraints constraints,
    int gridx,
    int gridy
  ) {
    constraints.gridx = gridx;
    constraints.gridy = gridy;
    passwordField.setFont(passwordField.getFont().deriveFont(14f));
    add(passwordField, constraints);
  }

  private void addButton(
    JButton button,
    GridBagConstraints constraints,
    int gridx,
    int gridy
  ) {
    constraints.gridx = gridx;
    constraints.gridy = gridy;
    add(button, constraints);
  }

  public static void main(String[] args) {
    SignupGUI signupGUI = new SignupGUI();
  }
}
