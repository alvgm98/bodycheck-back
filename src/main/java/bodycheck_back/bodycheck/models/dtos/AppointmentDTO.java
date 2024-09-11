package bodycheck_back.bodycheck.models.dtos;

import java.time.LocalDate;
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
   private String customerName;
   private String customerPhone;
   private LocalDate date;
   private LocalDateTime startTime;
   private LocalDateTime endTime;
   private String reason;
   private String observations;
}
