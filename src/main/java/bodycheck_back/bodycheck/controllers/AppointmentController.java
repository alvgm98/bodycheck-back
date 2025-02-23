package bodycheck_back.bodycheck.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bodycheck_back.bodycheck.controllers.util.CustomerValidator;
import bodycheck_back.bodycheck.models.dtos.AppointmentDTO;
import bodycheck_back.bodycheck.models.entities.Appointment;
import bodycheck_back.bodycheck.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
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
      customerValidator.validateCustomerOwnership(customerId);

      // Devolvemos todas las citas del Cliente.
      return ResponseEntity.ok(appointmentService.findAllByCustomer(customerId));
   }

   @PostMapping()
   public ResponseEntity<?> createAppointment(@RequestBody @Valid Appointment appointment) {
      return ResponseEntity.ok(appointmentService.create(appointment));
   }

   @PutMapping("/{id}")
   public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody @Valid Appointment appointment) {
      // Compruebo que el usuario de la cita le pertenezca, si lo hay
      if (appointment.getCustomer() != null) {
         customerValidator.validateCustomerOwnership(appointment.getCustomer().getId());
      }

      return ResponseEntity.ok(appointmentService.update(id, appointment));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
      appointmentService.delete(id);
      return ResponseEntity.noContent().build();
   }
}
