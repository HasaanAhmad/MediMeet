import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PatientLoginGUI extends JFrame {

  public PatientLoginGUI() {
    Storable.fromDisk();
    setTitle("Login Page");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    int frameWidth = 800;
    int frameHeight = 600;
    int xPos = (screenWidth - frameWidth) / 2;
    int yPos = (screenHeight - frameHeight) / 2;
    setLocation(xPos, yPos);

    // Create a panel to hold the login components
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new EmptyBorder(50, 50, 50, 50));
    panel.setBackground(Color.WHITE);

    // Create labels, text fields, and buttons
    JLabel usernameLabel = new JLabel("Patient ID:");
    JTextField usernameField = new JTextField(15);
    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(15);
    JButton loginButton = new JButton("Login");
    JButton signUpButton = new JButton("Sign Up");

    // Set modern style for components
    Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    Font textFieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
    Color buttonColor = new Color(58, 175, 169);
    Color buttonTextColor = Color.WHITE;

    usernameLabel.setFont(labelFont);
    passwordLabel.setFont(labelFont);
    usernameField.setFont(textFieldFont);
    passwordField.setFont(textFieldFont);
    loginButton.setFont(labelFont);
    signUpButton.setFont(labelFont);
    loginButton.setBackground(buttonColor);
    signUpButton.setBackground(buttonColor);
    loginButton.setForeground(buttonTextColor);
    signUpButton.setForeground(buttonTextColor);

    // Add action listeners to the buttons
    loginButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            // Action to perform when Login button is clicked
            int patientID = Integer.parseInt((usernameField.getText()));
            String password = passwordField.getText();
            Patient p = Patient.login(patientID, password);
            openPatientGUI(p);
          }

          private void openPatientGUI(Patient p) {
            if (p != null) {
              AppointmentMenu patientGUI = new AppointmentMenu(p, null);
              patientGUI.setVisible(true);
              PatientLoginGUI.this.setVisible(false);
            } else {
              JOptionPane.showMessageDialog(null, "Invalid Credentials");
            }
          }
        });

    signUpButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            // Action to perform when Sign Up button is clicked
            System.out.println("Sign Up button clicked");
            // Add code here to handle sign-up functionality
            openPatientSignUpGUI();
          }

          private void openPatientSignUpGUI() {
            SignupGUI patientGUI = new SignupGUI();
            patientGUI.setVisible(true);
            PatientLoginGUI.this.setVisible(false);
          }
        });

    // Create panel for labels and text fields
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    formPanel.add(
        usernameLabel,
        createGridBagConstraints(0, 0, 1, 1, GridBagConstraints.WEST));
    formPanel.add(
        usernameField,
        createGridBagConstraints(1, 0, 1, 1, GridBagConstraints.WEST));
    formPanel.add(
        passwordLabel,
        createGridBagConstraints(0, 1, 1, 1, GridBagConstraints.WEST));
    formPanel.add(
        passwordField,
        createGridBagConstraints(1, 1, 1, 1, GridBagConstraints.WEST));

    // Create panel for buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.setBackground(Color.WHITE);
    buttonPanel.add(loginButton);
    buttonPanel.add(signUpButton);

    // Add the components to the main panel
    panel.add(formPanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    // Set the panel as the content pane of the frame
    setContentPane(panel);

    // Set the size and make the frame visible
    setSize(frameWidth, frameHeight);
    setVisible(true);
  }

  private static GridBagConstraints createGridBagConstraints(
      int gridx,
      int gridy,
      int gridwidth,
      int gridheight,
      int anchor) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.gridwidth = gridwidth;
    gbc.gridheight = gridheight;
    gbc.anchor = anchor;
    gbc.insets = new Insets(5, 5, 5, 5);
    return gbc;
  }
}
