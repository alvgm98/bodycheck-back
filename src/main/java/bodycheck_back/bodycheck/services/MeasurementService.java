package bodycheck_back.bodycheck.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bodycheck_back.bodycheck.models.dtos.MeasurementDTO;
import bodycheck_back.bodycheck.models.entities.measurement.Measurement;
import bodycheck_back.bodycheck.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeasurementService {

   private final MeasurementRepository measurementRepository;

   public List<MeasurementDTO> getList(Long customerId) {
      List<Measurement> measurements = measurementRepository.findAllByCustomerId(customerId);

      return measurements.stream().map(this::convertToDto).collect(Collectors.toList());
   }

   @Transactional
   public MeasurementDTO create(Measurement measurement) {
      return convertToDto(measurementRepository.save(measurement));
   }

   @Transactional
   public MeasurementDTO update(Long measurementId, Measurement measurement) {
      measurement.setId(measurementId);
      return convertToDto(measurementRepository.save(measurement));
   }

   public MeasurementDTO convertToDto(Measurement measurement) {
      return MeasurementDTO.builder()
            .id(measurement.getId())
            .date(measurement.getDate())
            .weight(measurement.getWeight())
            .circumference(measurement.getCircumference())
            .skinfold(measurement.getSkinfold())
            .diameter(measurement.getDiameter())
            .observations(measurement.getObservations())
            .build();
   }
}
