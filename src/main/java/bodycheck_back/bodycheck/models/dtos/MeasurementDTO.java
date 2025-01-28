package bodycheck_back.bodycheck.models.dtos;

import java.time.LocalDate;

import bodycheck_back.bodycheck.models.entities.measurement.Circumference;
import bodycheck_back.bodycheck.models.entities.measurement.Skinfold;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {
   private Long id;
   private LocalDate date;
   private Integer weight;
   private Circumference circumference;
   private Skinfold skinfold;
   private String observations;
}
