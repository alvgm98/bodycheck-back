package bodycheck_back.bodycheck.models.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import bodycheck_back.bodycheck.models.enums.Role;
import bodycheck_back.bodycheck.models.enums.Situation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotBlank
   @Email
   @Size(max = 100)
   @Column(unique = true, nullable = false, length = 100)
   private String username;

   @NotBlank
   @Column(nullable = false)
   private String password;

   @NotBlank
   @Size(max = 40)
   @Column(nullable = false, length = 40)
   private String firstName;

   @NotBlank
   @Size(max = 40)
   @Column(nullable = false, length = 40)
   private String lastName;

   private String phone;

   @Enumerated(EnumType.STRING)
   private Situation situation;

   @Enumerated(EnumType.STRING)
   private Role role;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority(role.name()));
   }
}
