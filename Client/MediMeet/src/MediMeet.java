import java.util.Date;

public class MediMeet {

  public static void main(String[] args) {
    System.out.println("Hello WOrd");
    Prescription p = new Prescription(
      "Take 2 tablets daily",
      new Appointment(
        1,
        "Dr. John",
        "Mr. Smith",
        new Date(System.currentTimeMillis()),
        1000
      )
    );
    System.out.print(p.toString());
  }
}
