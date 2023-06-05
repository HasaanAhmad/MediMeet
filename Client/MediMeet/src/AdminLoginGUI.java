import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminLoginGUI extends JFrame {

  public AdminLoginGUI() {
    setTitle("Admin Login");
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
    JLabel usernameLabel = new JLabel("Username:");
    JTextField usernameField = new JTextField(15);
    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(15);
    JButton loginButton = new JButton("Login");

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
    loginButton.setBackground(buttonColor);
    loginButton.setForeground(buttonTextColor);

    // Add action listener to the login button
    loginButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Action to perform when Login button is clicked
          String username = usernameField.getText();
          String password = new String(passwordField.getPassword());
          // Add code here to validate login credentials
          // For demonstration purposes, we'll just print the input

          if (username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(
              AdminLoginGUI.this,
              "Login Successful"
            );
            openAdminDashboard();
          } else {
            JOptionPane.showMessageDialog(AdminLoginGUI.this, "Login Failed");
          }
        }
      }
    );

    // Create panel for labels and text fields
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    formPanel.add(
      usernameLabel,
      createGridBagConstraints(0, 0, 1, 1, GridBagConstraints.WEST)
    );
    formPanel.add(
      usernameField,
      createGridBagConstraints(1, 0, 1, 1, GridBagConstraints.WEST)
    );
    formPanel.add(
      passwordLabel,
      createGridBagConstraints(0, 1, 1, 1, GridBagConstraints.WEST)
    );
    formPanel.add(
      passwordField,
      createGridBagConstraints(1, 1, 1, 1, GridBagConstraints.WEST)
    );

    // Create panel for the login button
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.setBackground(Color.WHITE);
    buttonPanel.add(loginButton);

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
    int anchor
  ) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.gridwidth = gridwidth;
    gbc.gridheight = gridheight;
    gbc.anchor = anchor;
    gbc.insets = new Insets(5, 5, 5, 5);
    return gbc;
  }

  private void openAdminDashboard() {
    LoggedInAdminUI adminUI = new LoggedInAdminUI();
    adminUI.setVisible(true);
    dispose();
  }
}
