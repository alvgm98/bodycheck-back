package bodycheck_back.bodycheck.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bodycheck_back.bodycheck.controllers.util.CustomerValidator;
import bodycheck_back.bodycheck.models.dtos.MeasurementDTO;
import bodycheck_back.bodycheck.models.entities.measurement.Measurement;
import bodycheck_back.bodycheck.services.MeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/measurements")
@RequiredArgsConstructor
public class MeasurementController {

   private final MeasurementService measurementService;
   private final CustomerValidator customerValidator;

   @GetMapping("/{customerId}")
   public ResponseEntity<List<MeasurementDTO>> getMeasurements(@PathVariable Long customerId) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      customerValidator.validateCustomerOwnership(customerId);

      // Devuelve la Lista de Mediciones del Customer.
      return ResponseEntity.ok(measurementService.getList(customerId));
   }

   @PostMapping()
   public ResponseEntity<MeasurementDTO> createMeasurement(@RequestBody @Valid Measurement measurement) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      customerValidator.validateCustomerOwnership(measurement.getCustomer().getId());

      // Crea una nueva Medición y la devuelve.
      return ResponseEntity.ok(measurementService.create(measurement));
   }

   @PutMapping("/{measurementId}")
   public ResponseEntity<MeasurementDTO> updateMeasurement(@PathVariable Long measurementId, @RequestBody @Valid Measurement measurement) {
      // Comprueba que el Customer existe y pertenece al User de la petición.
      customerValidator.validateCustomerOwnership(measurement.getCustomer().getId());

      // Actualiza la medición y la devuelve.
      return ResponseEntity.ok(measurementService.update(measurementId, measurement));
   }

}
