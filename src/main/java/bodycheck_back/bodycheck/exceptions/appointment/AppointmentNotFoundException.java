package bodycheck_back.bodycheck.exceptions.appointment;

public class AppointmentNotFoundException extends RuntimeException {
   public AppointmentNotFoundException() {
      super("La cita a la que intenta acceder no existe!");
   }
}
