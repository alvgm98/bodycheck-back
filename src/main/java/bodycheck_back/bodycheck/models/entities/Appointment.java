package bodycheck_back.bodycheck.models.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "appointments")
public class Appointment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @NotNull
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "customer_id", nullable = false)
   private Customer customer;

   @NotNull
   @Column(nullable = false)
   private LocalDate date;

   @NotNull
   @Column(nullable = false)
   private LocalTime time;

   @NotNull
   @Column(nullable = false)
   private Integer duration;

   @NotBlank
   @Size(max = 20)
   @Column(nullable = false, length = 20)
   private String reason;

   @Size(max = 1000)
   @Column(length = 1000)
   private String observations;
}
