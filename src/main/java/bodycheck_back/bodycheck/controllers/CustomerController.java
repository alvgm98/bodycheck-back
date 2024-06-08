package bodycheck_back.bodycheck.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bodycheck_back.bodycheck.auth.services.AuthService;
import bodycheck_back.bodycheck.models.dtos.CustomerDTO;
import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.models.entities.User;
import bodycheck_back.bodycheck.services.CustomerService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

   private final CustomerService customerService;
   private final AuthService authService;

   @GetMapping()
   public ResponseEntity<List<CustomerDTO>> getCustomers() {
      return ResponseEntity.status(HttpStatus.OK).body(customerService.findAllByUser(getCurrentUser()));
   }

   @GetMapping("/{id}")
   public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
      Optional<Customer> o = customerService.findById(id);
      // El Customer no existe.
      if (!o.isPresent()) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      Customer c = o.get();
      User user = getCurrentUser();

      // El Customer no le pertenece.
      if (!c.getUser().getId().equals(user.getId())) {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

      // Funcionó correctamente.
      return ResponseEntity.status(HttpStatus.OK).body(customerService.convertToDto(c));
   }

   @PostMapping()
   public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody Customer customer) {
      customer.setUser(getCurrentUser());
      return ResponseEntity.status(HttpStatus.OK).body(customerService.save(customer));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
      Optional<Customer> o = customerService.findById(id);
      // El Customer no existe.
      if (!o.isPresent()) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      Customer c = o.get();
      User user = getCurrentUser();

      // El Customer no le pertenece.
      if (!c.getUser().getId().equals(user.getId())) {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

      // Funcionó correctamente.
      customer.setId(id);
      customer.setUser(user);
      return ResponseEntity.status(HttpStatus.OK).body(customerService.save(customer));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
      Optional<Customer> o = customerService.findById(id);
      // El Customer no existe.
      if (!o.isPresent()) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      Customer c = o.get();
      User user = getCurrentUser();

      // El Customer no le pertenece.
      if (!c.getUser().getId().equals(user.getId())) {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

      // Funcionó correctamente.
      customerService.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }

   private User getCurrentUser() {
      return authService.getUserFromToken();
   }

}
