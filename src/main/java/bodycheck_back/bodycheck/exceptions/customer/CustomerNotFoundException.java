package bodycheck_back.bodycheck.exceptions.customer;

public class CustomerNotFoundException extends RuntimeException {
   public CustomerNotFoundException() {
      super("El Cliente al que intenta acceder no existe!");
   }
}
