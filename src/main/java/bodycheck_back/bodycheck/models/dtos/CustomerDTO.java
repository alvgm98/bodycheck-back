package bodycheck_back.bodycheck.models.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
   private Long id;
   private String firstName;
   private String lastName;
   private String email;
   private String phone;
   private LocalDate birthdate;
   private String gender;
   private Short height;
   private String observations;
}
