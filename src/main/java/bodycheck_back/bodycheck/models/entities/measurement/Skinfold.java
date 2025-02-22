package bodycheck_back.bodycheck.models.entities.measurement;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skinfolds")
public class Skinfold {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(precision = 5, scale = 2)
   private BigDecimal triceps;

   @Column(precision = 5, scale = 2)
   private BigDecimal biceps;

   @Column(precision = 5, scale = 2)
   private BigDecimal subscapular;

   @Column(precision = 5, scale = 2)
   private BigDecimal suprailiac;

   @Column(precision = 5, scale = 2)
   private BigDecimal iliacCrest;

   @Column(precision = 5, scale = 2)
   private BigDecimal abdominal;

   @Column(precision = 5, scale = 2)
   private BigDecimal thigh;

   @Column(precision = 5, scale = 2)
   private BigDecimal calf;
}
