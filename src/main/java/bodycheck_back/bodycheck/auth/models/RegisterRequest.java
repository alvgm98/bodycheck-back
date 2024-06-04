package bodycheck_back.bodycheck.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
   private String username; // Email
   private String password;
   private String firstName;
   private String lastName;
   private String phone;
}
