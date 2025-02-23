package bodycheck_back.bodycheck.exceptions.appointment;

public class UnauthorizedAppointmentAccessException extends RuntimeException {
   public UnauthorizedAppointmentAccessException() {
      super("La cita a la que intenta acceder no le pertenece!");
   }
}
