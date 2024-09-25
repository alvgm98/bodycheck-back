package bodycheck_back.bodycheck.exceptions;

public class AppointmentConflictException extends RuntimeException {
   public AppointmentConflictException() {
      super("Ya hay una cita programada en este horario.");
   }
}
