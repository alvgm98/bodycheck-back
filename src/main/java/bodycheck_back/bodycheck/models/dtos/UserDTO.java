package bodycheck_back.bodycheck.models.dtos;

import bodycheck_back.bodycheck.models.enums.Situation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
   private Long id;
   private String username;
   private String firstName;
   private String lastName;
   private String phone;
   private Situation situation;
}
