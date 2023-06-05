import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AppointmentMenu extends JFrame implements ActionListener {

  private JPanel buttonPanel;
  private JPanel mainPanel;
  private CardLayout cardLayout;
  private AppointmentMenu previousMenu;

  private Color primaryColor = new Color(38, 166, 154);
  private Color backgroundColor = new Color(212, 224, 253);
  private Color buttonTextColor = new Color(34, 58, 92);

  public AppointmentMenu(Patient p, AppointmentMenu previousMenu) {
    this.previousMenu = previousMenu;
    setTitle("Appointment Menu");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Center the frame on the screen
    setLayout(new BorderLayout());

    // Create the logo panel in the top left corner
    JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    logoPanel.setBackground(primaryColor);
    JLabel logoLabel = new JLabel("MEDIMEET");
    logoLabel.setFont(new Font("MONOSPACED", Font.BOLD, 26));
    JLabel welcomeLabel = new JLabel("Welcome, " + p.getName() + "!");
    welcomeLabel.setFont(new Font("MONOSPACED", Font.BOLD, 26));
    logoLabel.setForeground(Color.WHITE);
    logoPanel.add(logoLabel);
    logoPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 20));
    // Create the button panel on the left side
    buttonPanel = new JPanel();
    buttonPanel.setBackground(primaryColor);
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JButton makeAppointmentButton = createStyledButton("Make Appointment");
    JButton viewAppointmentsButton = createStyledButton("View Appointments");
    JButton removeAppointmentsButton = createStyledButton(
      "Remove Appointments"
    );
    JButton logoutButton = createStyledButton("Logout");
    logoutButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(AppointmentMenu.this, "Logging out...");
          redirectToLogin();
        }

        private void redirectToLogin() {
          LoginPage loginPage = new LoginPage();
          loginPage.setVisible(true);
          AppointmentMenu.this.dispose();
        }
      }
    );

    makeAppointmentButton.addActionListener(this);
    viewAppointmentsButton.addActionListener(this);
    removeAppointmentsButton.addActionListener(this);
    logoutButton.addActionListener(this);

    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(makeAppointmentButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPanel.add(viewAppointmentsButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPanel.add(removeAppointmentsButton);
    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(logoutButton);
    buttonPanel.add(Box.createVerticalGlue());

    // Create the main panel in the middle using CardLayout
    mainPanel = new JPanel();
    cardLayout = new CardLayout();
    mainPanel.setLayout(cardLayout);

    JPanel makeAppointmentPane = createMakeAppointmentPane(p);
    JPanel viewAppointmentsPane = createViewAppointmentsPane(p);
    JPanel removeAppointmentsPane = createRemoveAppointmentsPane(p);

    mainPanel.add(makeAppointmentPane, "makeAppointment");
    mainPanel.add(viewAppointmentsPane, "viewAppointments");
    mainPanel.add(removeAppointmentsPane, "removeAppointments");

    // Add the logo panel, button panel, and main panel to the frame
    add(logoPanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.WEST);
    add(mainPanel, BorderLayout.CENTER);

    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (previousMenu != null) {
      previousMenu.dispose();
    }

    // Switch panes based on the button clicked
    switch (command) {
      case "Make Appointment":
        cardLayout.show(mainPanel, "makeAppointment");
        break;
      case "View Appointments":
        cardLayout.show(mainPanel, "viewAppointments");
        break;
      case "Remove Appointments":
        cardLayout.show(mainPanel, "removeAppointments");
        break;
      default:
        break;
    }
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 15));
    button.setBackground(primaryColor);
    button.setForeground(buttonTextColor);
    button.setFocusPainted(false);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setMaximumSize(new Dimension(200, 50));
    button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    return button;
  }

  private JPanel createStyledPane(String text) {
    JPanel pane = new JPanel();
    pane.setBackground(backgroundColor);
    pane.setLayout(new BorderLayout());
    pane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setFont(new Font("Arial", Font.BOLD, 24));
    pane.add(label, BorderLayout.NORTH);

    return pane;
  }

  private JPanel createMakeAppointmentPane(Patient p) {
    Storable.fromDisk();
    JPanel pane = createStyledPane("Welcome " + p.getName() + "!");
    pane.setForeground(buttonTextColor);
    pane.setBackground(backgroundColor);

    // Dummy data for doctors
    ArrayList<Storable> doctorInstances = Storable.all(Doctor.class);
    Doctor[] doctors = new Doctor[doctorInstances.size()];
    int tmpI = 0;
    for (Storable d : doctorInstances) {
      doctors[tmpI] = (Doctor) d;
      tmpI++;
    }
    // Dummy data for dates and times
    String[] dates = { "June 6, 2023", "June 7, 2023", "June 8, 2023" };
    String[] times = { "10:00 AM", "2:00 PM", "4:00 PM" };

    // Create nested panels for organization
    JPanel contentPanel = new JPanel(new GridBagLayout());
    contentPanel.setBackground(backgroundColor);
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Doctor selection panel
    JLabel doctorLabel = new JLabel("Select Doctor:");
    JComboBox<Doctor> doctorComboBox = new JComboBox<>(doctors);
    JPanel doctorPanel = new JPanel(new BorderLayout());
    doctorPanel.setBackground(backgroundColor);
    doctorPanel.add(doctorLabel, BorderLayout.NORTH);
    doctorPanel.add(doctorComboBox, BorderLayout.CENTER);
    contentPanel.add(doctorPanel, gbc);

    gbc.gridy++;

    // Date selection panel
    JLabel dateLabel = new JLabel("Select Date:");
    JComboBox<String> dateComboBox = new JComboBox<>(dates);
    JPanel datePanel = new JPanel(new BorderLayout());
    datePanel.setBackground(backgroundColor);
    datePanel.add(dateLabel, BorderLayout.NORTH);
    datePanel.add(dateComboBox, BorderLayout.CENTER);
    contentPanel.add(datePanel, gbc);

    gbc.gridy++;

    // Time selection panel
    JLabel timeLabel = new JLabel("Select Time:");
    JComboBox<String> timeComboBox = new JComboBox<>(times);
    JPanel timePanel = new JPanel(new BorderLayout());
    timePanel.setBackground(backgroundColor);
    timePanel.add(timeLabel, BorderLayout.NORTH);
    timePanel.add(timeComboBox, BorderLayout.CENTER);
    contentPanel.add(timePanel, gbc);

    gbc.gridy++;

    // Confirm appointment button
    JButton confirmButton = new JButton("Confirm Appointment");
    confirmButton.setFont(new Font("Arial", Font.PLAIN, 12));
    confirmButton.setBackground(primaryColor);
    confirmButton.setForeground(buttonTextColor);
    confirmButton.setFocusPainted(false);
    confirmButton.setPreferredSize(new Dimension(150, 30));
    confirmButton.setMaximumSize(confirmButton.getPreferredSize());

    confirmButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
          int AppointmentId = generateUniqueID();
          int doctorId = selectedDoctor.getDoctorID();
          String doctorName = selectedDoctor.getName();
          String patientName = p.getName();
          Date date = new Date();
          int fee = 1000;

          // Create new appointment
          Appointment appointment = new Appointment(
            AppointmentId,
            doctorName,
            patientName,
            date,
            fee
          );
          appointment.setPatientID(p.getPatientID());
          appointment.setDoctorID(doctorId);
          System.out.println(appointment.toString());
          appointment.save();
          JOptionPane.showMessageDialog(
            null,
            "Appointment created successfully!"
          );
          dispose();
          AppointmentMenu appointmentsPane = new AppointmentMenu(
            p,
            AppointmentMenu.this
          );
          appointmentsPane.setVisible(true);
        }
      }
    );
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setBackground(backgroundColor);
    buttonPanel.add(confirmButton);
    contentPanel.add(buttonPanel, gbc);

    // Add the content panel to the main pane
    pane.add(contentPanel, BorderLayout.CENTER);

    return pane;
  }

  private int generateUniqueID() {
    int id = 0;
    ArrayList<Storable> appointments = Storable.all(Appointment.class);
    for (Storable a : appointments) {
      Appointment appointment = (Appointment) a;
      if (appointment.getAppointmentID() > id) {
        id = appointment.getAppointmentID();
      }
    }
    return id + 1;
  }

  // Returns an array of appointments for the given patient
  ArrayList<Appointment> getAppointmentsByPatient(Patient p) {
    ArrayList<Storable> appointments = Storable.all(Appointment.class);
    ArrayList<Appointment> patientAppointments = new ArrayList<Appointment>();
    for (Storable a : appointments) {
      System.out.println("debug loop appointment per patient called");
      Appointment appointment = (Appointment) a;
      if (appointment.getPatientID() == p.getPatientID()) {
        System.out.println("appointment belongs to user hehe");
        patientAppointments.add(appointment); // currently return all appointments for all patients
      }
    }
    return patientAppointments;
  }

  private JPanel createViewAppointmentsPane(Patient p) {
    Storable.fromDisk();
    JPanel pane = createStyledPane("View Appointments Pane");

    // Dummy data for appointments
    ArrayList<Appointment> appointments = getAppointmentsByPatient(p);

    // Table data
    String[] columnNames = { "ID", "Doctor", "Status", "Prescription" };
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

    for (Appointment appointment : appointments) {
      Object[] rowData = new Object[4];
      rowData[0] = appointment.getAppointmentID();
      rowData[1] = appointment.getDoctorName();
      rowData[2] = appointment.getAttended() ? "Attended" : "Pending";
      Prescription prescription = appointment.getPrescription();
      rowData[3] =
        prescription == null ? "None" : prescription.getInstruction();

      // Check if the appointment already exists in the table
      int existingRow = findExistingAppointmentRow(
        tableModel,
        appointment.getAppointmentID()
      );

      if (existingRow != -1) {
        // Update the existing row
        for (int i = 0; i < rowData.length; i++) {
          tableModel.setValueAt(rowData[i], existingRow, i);
        }
      } else {
        // Add a new row to the table
        tableModel.addRow(rowData);
      }
    }

    // Create the table
    JTable appointmentsTable = new JTable(tableModel) {
      @Override
      public Component prepareRenderer(
        TableCellRenderer renderer,
        int row,
        int column
      ) {
        Component component = super.prepareRenderer(renderer, row, column);

        // Check if the column is the "Status" column
        if (column == 2) {
          String status = (String) getValueAt(row, column);
          Color cellColor = status.equals("Attended") ? Color.GREEN : Color.RED;
          component.setBackground(cellColor);
        } else {
          component.setBackground(getBackground());
        }

        return component;
      }
    };

    // Set custom table cell renderer to display escape characters
    appointmentsTable.setDefaultRenderer(
      Object.class,
      new EscapeCharacterRenderer()
    );

    // Add the table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(appointmentsTable);

    // Add the scroll pane to the pane
    pane.add(scrollPane, BorderLayout.CENTER);

    return pane;
  }

  private int findExistingAppointmentRow(
    DefaultTableModel tableModel,
    int appointmentID
  ) {
    int rowCount = tableModel.getRowCount();
    for (int i = 0; i < rowCount; i++) {
      int existingAppointmentID = (int) tableModel.getValueAt(i, 0);
      if (existingAppointmentID == appointmentID) {
        return i;
      }
    }
    return -1;
  }

  // Custom table cell renderer to display escape characters
  private class EscapeCharacterRenderer extends DefaultTableCellRenderer {

    @Override
    public void setValue(Object value) {
      if (value != null) {
        setText(
          value
            .toString()
            .replace("\n", "<br>")
            .replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
        );
      } else {
        setText("");
      }
    }
  }

  private JPanel createRemoveAppointmentsPane(Patient p) {
    JPanel pane = createStyledPane("Remove Appointments Pane");

    // Dummy data for appointments
    ArrayList<Appointment> appointments = getAppointmentsByPatient(p);

    // Appointments list with remove buttons
    JPanel appointmentsPanel = new JPanel();
    appointmentsPanel.setBackground(backgroundColor);
    appointmentsPanel.setLayout(new GridBagLayout());
    appointmentsPanel.setBorder(
      BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ); // Add padding to the panel
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(10, 0, 0, 0); // Add top padding for the first row

    Set<Integer> addedAppointmentIDs = new HashSet<>(); // Set to store added appointment IDs

    for (Appointment appointment : appointments) {
      int appointmentID = appointment.getAppointmentID();

      // Skip duplicate appointments
      if (addedAppointmentIDs.contains(appointmentID)) {
        continue;
      }

      JButton removeButton = new JButton("Remove");
      removeButton.addActionListener(this);
      removeButton.setFont(new Font("Arial", Font.PLAIN, 12));
      removeButton.setBackground(primaryColor);
      removeButton.setForeground(Color.black);
      removeButton.setFocusPainted(false);
      removeButton.setPreferredSize(new Dimension(100, 30));
      removeButton.setMaximumSize(removeButton.getPreferredSize());

      removeButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            appointment.delete(); // Delete appointment from Storable
            JOptionPane.showMessageDialog(
              null,
              "Appointment removed successfully!"
            );
            // dispose current pane
            // Refresh the appointments pane

            dispose();

            AppointmentMenu appointmentsPane = new AppointmentMenu(
              p,
              AppointmentMenu.this
            );
            appointmentsPane.setVisible(true);
          }
        }
      );

      JLabel appointmentLabel = new JLabel(
        "<html><body style='width: 200px'>" +
        appointment.toString() +
        "</body></html>"
      );
      appointmentLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 12));

      // Create a nested panel with FlowLayout to hold the label and button
      JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
      innerPanel.setBackground(backgroundColor);
      innerPanel.add(appointmentLabel);
      innerPanel.add(removeButton);

      JPanel appointmentPanel = new JPanel(new BorderLayout());
      appointmentPanel.setBackground(backgroundColor);
      appointmentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding to the appointment panel
      appointmentPanel.add(innerPanel, BorderLayout.CENTER);
      appointmentsPanel.add(appointmentPanel, gbc);
      gbc.gridy++; // Increment the grid row
      gbc.insets = new Insets(0, 0, 0, 0); // Reset the top padding for subsequent rows

      addedAppointmentIDs.add(appointmentID); // Add the appointment ID to the set of added IDs
      // Add the appointment ID to the set of added IDs
    }

    // Add appointments list to the pane
    pane.add(appointmentsPanel, BorderLayout.CENTER);

    return pane;
  }
}
