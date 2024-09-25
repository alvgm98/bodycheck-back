package bodycheck_back.bodycheck.exceptions;

public class AppointmentCustomerExpectedException extends RuntimeException {
   public AppointmentCustomerExpectedException() {
      super("Es necesario vincular la cita a un cliente o a un nombre y teléfono.");
   }
}
