package bodycheck_back.bodycheck.controllers.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import bodycheck_back.bodycheck.auth.services.AuthService;
import bodycheck_back.bodycheck.exceptions.customer.CustomerNotFoundException;
import bodycheck_back.bodycheck.exceptions.customer.UnauthorizedCustomerAccessException;
import bodycheck_back.bodycheck.models.dtos.CustomerDTO;
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
    * Valida si un Cliente existe y si pertenece al Usuario que realiza la solicitud.
    * <p>
    * Este método verifica si el Cliente con el ID proporcionado existe en la base de datos.
    * Si existe, valida si el Cliente está asociado con el Usuario actualmente autenticado.
    * Si alguna validación falla, se lanza una excepción adecuada.
    * </p>
    * 
    * @param customerId el ID del Cliente que se desea validar
    * @return El cliente validado en formato CustomerDTO.
    * @throws CustomerNotFoundException           - Si el Cliente no existe.
    * @throws UnauthorizedCustomerAccessException - Si el Cliente no pertenece al Usuario autenticado.
    */
   public CustomerDTO validateCustomerOwnership(Long customerId) {
      Optional<Customer> o = customerService.findById(customerId);
      // Comprobamos la existencia del Customer
      if (!o.isPresent()) {
         throw new CustomerNotFoundException();
      }

      Customer c = o.get();
      User user = authService.getUserFromToken();
      // Comprobamos que el Customer pertenezca al User
      if (!c.getUser().getId().equals(user.getId())) {
         throw new UnauthorizedCustomerAccessException();
      }

      // Devolvemos el CustomerDTO
      return customerService.convertToDto(c);
   }

}
