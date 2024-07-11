package bodycheck_back.bodycheck.models.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
   private Long id;
   private CustomerDTO customer;
   private LocalDateTime dateTime;
   private Integer duration;
   private String reason;
   private String observations;
}
