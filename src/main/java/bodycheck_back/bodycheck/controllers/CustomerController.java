package bodycheck_back.bodycheck.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bodycheck_back.bodycheck.controllers.util.CustomerValidator;
import bodycheck_back.bodycheck.models.dtos.AppointmentDTO;
import bodycheck_back.bodycheck.models.dtos.CustomerDTO;
import bodycheck_back.bodycheck.models.dtos.CustomerDetailedDTO;
import bodycheck_back.bodycheck.models.dtos.MeasurementDTO;
import bodycheck_back.bodycheck.models.entities.Customer;
import bodycheck_back.bodycheck.services.AppointmentService;
import bodycheck_back.bodycheck.services.CustomerService;
import bodycheck_back.bodycheck.services.MeasurementService;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
   private final MeasurementService measurementService;
   private final AppointmentService appointmentService;

   private final CustomerValidator customerValidator;

   @GetMapping()
   public ResponseEntity<List<CustomerDTO>> getCustomers() {
      return ResponseEntity.ok(customerService.findAllByUser());
   }

   @GetMapping("/{id}")
   public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
      // Obtenemos el CustomerDTO comprobando que existe y pertenece al User de la petición.
      CustomerDTO customerDTO = customerValidator.validateCustomerOwnership(id);

      // Devuelve el Customer como DTO.
      return ResponseEntity.ok(customerDTO);
   }

   @GetMapping("/detailed/{id}")
   public ResponseEntity<CustomerDetailedDTO> getCustomerDetailed(@PathVariable Long id) {
      // Obtenemos el CustomerDTO comprobando que existe y pertenece al User de la petición.
      CustomerDTO customerDTO = customerValidator.validateCustomerOwnership(id);

      // Obtenemos sus datos de Mediciones y Citas.
      List<MeasurementDTO> measurements = measurementService.getList(customerDTO.getId());
      List<AppointmentDTO> appointments = appointmentService.findAllByCustomer(customerDTO.getId());

      // Lo transformamos en CustomerDetailedDTO.
      CustomerDetailedDTO customerDetailedDTO = customerService.getCustomerDetailed(customerDTO, measurements, appointments);
      // Devuelve el Customer con DTO.
      return ResponseEntity.ok(customerDetailedDTO);
   }

   @PostMapping()
   public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
      // Se crea el Customer y se devuelve como DTO.
      return ResponseEntity.ok(customerService.create(customer));
   }

   @PutMapping("/{id}")
   public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      customerValidator.validateCustomerOwnership(id);

      // Se actualiza el Customer y se devuelve como DTO.
      return ResponseEntity.ok(customerService.update(id, customer));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      customerValidator.validateCustomerOwnership(id);

      // Se elimina el Customer.
      customerService.delete(id);
      return ResponseEntity.noContent().build();
   }

}
