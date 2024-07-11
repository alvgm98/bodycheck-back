package bodycheck_back.bodycheck.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bodycheck_back.bodycheck.controllers.util.CustomerValidator;
import bodycheck_back.bodycheck.models.dtos.CustomerDTO;
import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.services.CustomerService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class CustomerController {

   private final CustomerService customerService;
   private final CustomerValidator customerValidator;

   @GetMapping()
   public ResponseEntity<List<CustomerDTO>> getCustomers() {
      return ResponseEntity.ok(customerService.findAllByUser());
   }

   @GetMapping("/{id}")
   public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      ResponseEntity<?> validationReponse = customerValidator.validateCustomerOwnership(id);
      if (!validationReponse.getStatusCode().is2xxSuccessful()) {
         return ResponseEntity.status(validationReponse.getStatusCode()).build();
      }

      // Devuelve el Customer con DTO.
      return ResponseEntity.ok((CustomerDTO) validationReponse.getBody());
   }

   @PostMapping()
   public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
      // Se crea el Customer y se devuelve como DTO.
      return ResponseEntity.ok(customerService.create(customer));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      ResponseEntity<?> validationReponse = customerValidator.validateCustomerOwnership(id);
      if (!validationReponse.getStatusCode().is2xxSuccessful()) {
         return ResponseEntity.status(validationReponse.getStatusCode()).build();
      }

      // Se actualiza el Customer y se devuelve como DTO.
      return ResponseEntity.ok(customerService.update(id, customer));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      ResponseEntity<?> validationReponse = customerValidator.validateCustomerOwnership(id);
      if (!validationReponse.getStatusCode().is2xxSuccessful()) {
         return ResponseEntity.status(validationReponse.getStatusCode()).build();
      }

      // Se elimina el Customer.
      customerService.delete(id);
      return ResponseEntity.noContent().build();
   }

}
