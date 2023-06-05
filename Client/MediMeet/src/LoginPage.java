import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginPage extends JFrame {

  private JButton doctorLoginButton;
  private JButton patientLoginButton;
  private JButton adminLoginButton;

  public LoginPage() {
    setTitle("Login Page");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Center the frame on the screen
    setLayout(new BorderLayout());

    // Create a panel for the buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridBagLayout());
    buttonPanel.setBackground(new Color(220, 220, 220)); // Greyish background color

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    // Create buttons with updated styling
    Font buttonFont = new Font("Arial", Font.BOLD, 20);
    Color buttonColor = new Color(38, 166, 154);
    Color buttonTextColor = new Color(34, 58, 92);

    doctorLoginButton = new JButton("Doctor Login");
    patientLoginButton = new JButton("Patient Login");
    adminLoginButton = new JButton("Admin Login");

    // Apply consistent colors
    doctorLoginButton.setFont(buttonFont);
    doctorLoginButton.setBackground(buttonColor);
    doctorLoginButton.setForeground(buttonTextColor);
    doctorLoginButton.setFocusPainted(false);

    patientLoginButton.setFont(buttonFont);
    patientLoginButton.setBackground(buttonColor);
    patientLoginButton.setForeground(buttonTextColor);
    patientLoginButton.setFocusPainted(false);

    adminLoginButton.setFont(buttonFont);
    adminLoginButton.setBackground(buttonColor);
    adminLoginButton.setForeground(buttonTextColor);
    adminLoginButton.setFocusPainted(false);

    doctorLoginButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                LoginPage.this,
                "Redirecting to Doctor Login");
            openDoctorGUI();
          }
        });

    patientLoginButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                LoginPage.this,
                "Redirecting to Patient Login");
            openPatientGUI();
          }
        });

    adminLoginButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                LoginPage.this,
                "Redirecting to Admin Login");
            openAdminGUI();
          }
        });

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    buttonPanel.add(adminLoginButton, gbc);

    gbc.gridy = 1;
    buttonPanel.add(doctorLoginButton, gbc);

    gbc.gridy = 2;
    buttonPanel.add(patientLoginButton, gbc);

    // Create a panel for the logo and button panel
    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(new Color(220, 220, 220)); // Greyish background color

    // Create a logo label with updated styling
    JLabel logoLabel = new JLabel("MEDIMEET");
    logoLabel.setFont(new Font("Helvetica", Font.BOLD, 48));
    logoLabel.setForeground(buttonColor); // Use the button color for consistency
    logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    logoLabel.setVerticalAlignment(SwingConstants.CENTER);
    contentPanel.add(logoLabel, BorderLayout.NORTH);
    contentPanel.add(buttonPanel, BorderLayout.CENTER);

    getContentPane().add(contentPanel);

    setVisible(true);
  }

  void openPatientGUI() {
    PatientLoginGUI patientLoginGUI = new PatientLoginGUI();
    patientLoginGUI.setVisible(true);
    LoginPage.this.setVisible(false);
  }

  void openDoctorGUI() {
    DoctorLoginGUI doctorLoginGUI = new DoctorLoginGUI();
    doctorLoginGUI.setVisible(true);
    LoginPage.this.setVisible(false);
  }

  void openAdminGUI() {
    AdminLoginGUI adminLoginGUI = new AdminLoginGUI();
    adminLoginGUI.setVisible(true);
    LoginPage.this.setVisible(false);
  }

  public static void main(String[] args) {
    Storable.fromDisk();
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            // Set look and feel to system default
            try {
              UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
              e.printStackTrace();
            }

            new LoginPage();
          }
        });
  }
}
