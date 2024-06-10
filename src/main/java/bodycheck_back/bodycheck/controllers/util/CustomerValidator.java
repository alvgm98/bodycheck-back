package bodycheck_back.bodycheck.controllers.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import bodycheck_back.bodycheck.auth.services.AuthService;
import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.models.entities.User;
import bodycheck_back.bodycheck.services.CustomerService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerValidator {

   private final CustomerService customerService;
   private final AuthService authService;

   /**
    * Validates if a Customer exists and if it belongs to the User making the
    * request.
    * <p>
    * This method checks if the Customer with the given ID exists in the database.
    * If it does,
    * it then verifies if the Customer is associated with the currently
    * authenticated User.
    * </p>
    * 
    * @param customerId the ID of the Customer to validate
    * @return a ResponseEntity with:
    *         <ul>
    *         <li>status 404 (Not Found) if the Customer does not exist</li>
    *         <li>status 403 (Forbidden) if the Customer does not belong to the
    *         authenticated User</li>
    *         <li>status 200 (OK) with the Customer in the body if validation is
    *         successful</li>
    *         </ul>
    */
   public ResponseEntity<?> validateCustomerOwnership(Long customerId) {
      Optional<Customer> o = customerService.findById(customerId);
      if (!o.isPresent()) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      Customer c = o.get();
      User user = authService.getUserFromToken();

      if (!c.getUser().getId().equals(user.getId())) {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

      return ResponseEntity.ok(customerService.convertToDto(c));
   }

}
