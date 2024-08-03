package bodycheck_back.bodycheck.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bodycheck_back.bodycheck.controllers.util.CustomerValidator;
import bodycheck_back.bodycheck.models.dtos.AppointmentDTO;
import bodycheck_back.bodycheck.models.entities.Appointment;
import bodycheck_back.bodycheck.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AppointmentController {

   private final AppointmentService appointmentService;
   private final CustomerValidator customerValidator;

   @GetMapping("/date/{date}")
   public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDate(@PathVariable String date) {
      return ResponseEntity.ok(appointmentService.findAllByDate(LocalDate.parse(date)));
   }

   @GetMapping("/customer/{customerId}")
   public ResponseEntity<List<AppointmentDTO>> getAppointmentsByCustomer(@PathVariable Long customerId) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      ResponseEntity<?> validationReponse = customerValidator.validateCustomerOwnership(customerId);
      if (!validationReponse.getStatusCode().is2xxSuccessful()) {
         return ResponseEntity.status(validationReponse.getStatusCode()).build();
      }

      return ResponseEntity.ok(appointmentService.findAllByCustomer(customerId));
   }

   @PostMapping()
   public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
      return ResponseEntity.ok(appointmentService.create(appointment));
   }  
}
