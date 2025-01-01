package bodycheck_back.bodycheck.exceptions.customer;

public class UnauthorizedCustomerAccessException extends RuntimeException {
   public UnauthorizedCustomerAccessException() {
      super("El Cliente al que intenta acceder no le pertenece!");
   }
}
