package bodycheck_back.bodycheck.exceptions;

public class AppointmentConflictException extends RuntimeException {
   public AppointmentConflictException() {
      super("There is already an appointment scheduled during this time.");
   }
}
