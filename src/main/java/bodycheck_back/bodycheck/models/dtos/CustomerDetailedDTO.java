package bodycheck_back.bodycheck.models.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailedDTO {
   private Long id;
   private String firstName;
   private String lastName;
   private String email;
   private String phone;
   private LocalDate birthdate;
   private Short height;
   private String gender;
   private String ethnicity;
   private String target;
   private String observations;
   private List<MeasurementDTO> measurements;
   private List<AppointmentDTO> appointments;
}
