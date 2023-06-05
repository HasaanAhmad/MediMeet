import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.*;

public class SignUpGUIDoc extends JFrame implements ActionListener {

  private JTextField nameField;
  private JTextField emailField;
  private JPasswordField passwordField;
  private JTextField phoneField;
  private JTextField addressField;
  private JTextField doctorIDField;
  private JTextField specializationField;
  private JButton signupButton;
  private JButton loginButton;

  public SignUpGUIDoc() {
    super("Doctor Signup");
    // Create components
    nameField = new JTextField(20);
    emailField = new JTextField(20);
    passwordField = new JPasswordField(20);
    phoneField = new JTextField(20);
    addressField = new JTextField(20);
    doctorIDField = new JTextField(20);
    specializationField = new JTextField(20);
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
    addLabel("Email:", constraints, 0, 1);
    addTextField(emailField, constraints, 1, 1);
    addLabel("Password:", constraints, 0, 2);
    addPasswordField(passwordField, constraints, 1, 2);
    addLabel("Phone:", constraints, 0, 3);
    addTextField(phoneField, constraints, 1, 3);
    addLabel("Address:", constraints, 0, 4);
    addTextField(addressField, constraints, 1, 4);
    addLabel("Doctor ID:", constraints, 0, 5);
    addTextField(doctorIDField, constraints, 1, 5);
    addLabel("Specialization:", constraints, 0, 6);
    addTextField(specializationField, constraints, 1, 6);
    addButton(signupButton, constraints, 0, 7);
    addButton(loginButton, constraints, 1, 7);

    // Set frame properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);
    setVisible(true);

    // Add event listeners
    signupButton.addActionListener(this);

    loginButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(
            SignUpGUIDoc.this,
            "Redirecting to Login Screen"
          );
          openLoginScreen();
        }
      }
    );
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String name = nameField.getText();
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());
    String phone = phoneField.getText();
    String address = addressField.getText();
    int doctorID;
    String specialization = specializationField.getText();
    ArrayList<Appointment> currentAppointments = new ArrayList<>();

    // Validation for name field
    if (name.isEmpty() || !Pattern.matches("^[a-zA-Z\\s]+$", name)) {
      JOptionPane.showMessageDialog(
        SignUpGUIDoc.this,
        "Invalid name format",
        "Input Error",
        JOptionPane.ERROR_MESSAGE
      );
      nameField.setText("");
      return;
    }

    // Validation for email field
    if (!Pattern.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", email)) {
      JOptionPane.showMessageDialog(
        SignUpGUIDoc.this,
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
        SignUpGUIDoc.this,
        "Password should be at least 4 characters long",
        "Input Error",
        JOptionPane.ERROR_MESSAGE
      );
      passwordField.setText("");
      return;
    }

    // Validation for phone field
    if (!Pattern.matches("^[0-9]{10,}$", phone)) {
      JOptionPane.showMessageDialog(
        SignUpGUIDoc.this,
        "Phone should be an integer with at least 10 digits",
        "Input Error",
        JOptionPane.ERROR_MESSAGE
      );
      phoneField.setText("");
      return;
    }

    // Validation for doctor ID field
    try {
      doctorID = Integer.parseInt(doctorIDField.getText());
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(
        SignUpGUIDoc.this,
        "Doctor ID should be an integer",
        "Input Error",
        JOptionPane.ERROR_MESSAGE
      );
      doctorIDField.setText("");
      return;
    }

    // Perform doctor registration here using the validated data
    if (Doctor.isDoctorIDTaken(doctorID)) {
      JOptionPane.showMessageDialog(
        SignUpGUIDoc.this,
        "Doctor ID is already taken",
        "Input Error",
        JOptionPane.ERROR_MESSAGE
      );
      doctorIDField.setText("");
      return;
    } else {
      JOptionPane.showMessageDialog(
        SignUpGUIDoc.this,
        "Doctor data saved successfully"
      );
      openLoginScreen();
    }
    Doctor.register(
      name,
      email,
      password,
      phone,
      address,
      doctorID,
      specialization
    );
  }

  void openLoginScreen() {
    DoctorLoginGUI loginScreen = new DoctorLoginGUI();
    loginScreen.setVisible(true);
    SignUpGUIDoc.this.setVisible(false);
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
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          // Set look and feel to system default
          try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch (Exception e) {
            e.printStackTrace();
          }

          new SignUpGUIDoc();
        }
      }
    );
  }
}
