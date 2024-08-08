package bodycheck_back.bodycheck.models.entities;

import java.time.LocalDate;

import bodycheck_back.bodycheck.models.enums.Ethnicity;
import bodycheck_back.bodycheck.models.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
@Table(name = "customers")
public class Customer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @NotBlank
   @Size(max = 40)
   @Column(nullable = false, length = 40)
   private String firstName;

   @NotBlank
   @Size(max = 80)
   @Column(nullable = false, length = 80)
   private String lastName;

   @Email
   @Size(max = 100)
   @Column(length = 100)
   private String email;

   @Size(max = 15)
   @Column(length = 15)
   private String phone;

   @NotNull
   @Past
   @Column(nullable = false)
   private LocalDate birthdate;

   @NotNull
   @Max(250)
   @Column(nullable = false)
   private Short height;

   @NotNull
   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private Gender gender;

   @NotNull
   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private Ethnicity ethnicity;

   @NotBlank
   @Size(max = 50)
   @Column(length = 50)
   private String target;

   @Column(columnDefinition = "TEXT")
   private String observations;
}
