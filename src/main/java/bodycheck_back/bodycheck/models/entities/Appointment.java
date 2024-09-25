package bodycheck_back.bodycheck.models.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "customer_id")
   private Customer customer;

   @Size(max = 120)
   @Column(length = 120)
   private String customerName;

   @Size(max = 15)
   @Column(length = 15)
   private String customerPhone;

   @NotNull
   @Column(nullable = false)
   private LocalDate date;

   @NotNull
   @Column(nullable = false)
   private LocalDateTime startTime;

   @NotNull
   @Column(nullable = false)
   private LocalDateTime endTime;

   @NotBlank
   @Size(max = 20)
   @Column(nullable = false, length = 20)
   private String reason;

   @Column(columnDefinition = "TEXT")
   private String observations;
}
