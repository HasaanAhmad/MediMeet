import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AppointmentGUI extends JFrame {

  private JTextField dateField;
  private JTextField timeField;
  private JTextField statusField;
  private JTextField feeField;

  public AppointmentGUI() {
    // Set up the GUI components
    JLabel dateLabel = new JLabel("Date:");
    dateField = new JTextField(20);
    JLabel timeLabel = new JLabel("Time:");
    timeField = new JTextField(20);
    JLabel statusLabel = new JLabel("Status:");
    statusField = new JTextField(20);
    JLabel feeLabel = new JLabel("Fee:");
    feeField = new JTextField(20);

    JButton saveButton = new JButton("Save");
    saveButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          saveAppointment();
        }
      }
    );

    JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
    panel.add(dateLabel);
    panel.add(dateField);
    panel.add(timeLabel);
    panel.add(timeField);
    panel.add(statusLabel);
    panel.add(statusField);
    panel.add(feeLabel);
    panel.add(feeField);
    panel.add(saveButton);

    getContentPane().add(panel);

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the appointment window, not the entire application
    setSize(300, 200);
    setTitle("New Appointment");
  }

  private void saveAppointment() {
    String date = dateField.getText();
    String time = timeField.getText();
    String status = statusField.getText();
    String fee = feeField.getText();

    // Perform validation or additional processing here before saving the appointment

    // Save the appointment to the database or perform any necessary operations
    // For demonstration purposes, let's just display the appointment details
    JOptionPane.showMessageDialog(
      this,
      "Appointment Saved\nDate: " +
      date +
      "\nTime: " +
      time +
      "\nStatus: " +
      status +
      "\nFee: " +
      fee
    );

    // Clear the fields after saving
    clearFields();
  }

  private void clearFields() {
    dateField.setText("");
    timeField.setText("");
    statusField.setText("");
    feeField.setText("");
  }
}
