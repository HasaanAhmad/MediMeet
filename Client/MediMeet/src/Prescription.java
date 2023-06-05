import java.sql.Date;

public class Prescription extends Storable {
  private static final long serialVersionUID = -7756106466251150463L;

  private String instruction;
  private int prescriptionId;
  private Appointment relatedAppointment;

  public Prescription(String instruction, Appointment relatedAppointment) {
    this.instruction = instruction;
    this.prescriptionId = (int) (Math.random() * 1000000) +
        (int) (Math.random() * 1000) -
        (int) (Math.random() * 1000);
    this.relatedAppointment = relatedAppointment;
  }

  public Prescription() {
    this.instruction = "";
    this.prescriptionId = (int) (Math.random() * 1000000) +
        (int) (Math.random() * 1000) -
        (int) (Math.random() * 1000);
    this.relatedAppointment = null;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public int getPrescriptionId() {
    return prescriptionId;
  }

  public Appointment getRelatedAppointment() {
    return relatedAppointment;
  }

  public void setRelatedAppointment(Appointment relatedAppointment) {
    this.relatedAppointment = relatedAppointment;
  }

  @Override
  public String toString() {
    return "Perscription to Mr/Ms. " + relatedAppointment.getPatientName() +
    " by Dr. " + relatedAppointment.getDoctorName() + " Dated: " +
    new Date(System.currentTimeMillis()).toString() + " is: " + instruction;

  }
}
