package bodycheck_back.bodycheck.auth.models;

import bodycheck_back.bodycheck.models.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
   private String token;
   private UserDTO user;
}
