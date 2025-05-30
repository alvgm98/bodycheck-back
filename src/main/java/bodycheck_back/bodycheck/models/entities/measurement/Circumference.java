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
@Table(name = "circumferences")
public class Circumference {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(precision = 5, scale = 2)
   private BigDecimal neck;

   @Column(precision = 5, scale = 2)
   private BigDecimal chest;

   @Column(precision = 5, scale = 2)
   private BigDecimal armRelaxed;

   @Column(precision = 5, scale = 2)
   private BigDecimal armFlexed;

   @Column(precision = 5, scale = 2)
   private BigDecimal waist;

   @Column(precision = 5, scale = 2)
   private BigDecimal hip;

   @Column(precision = 5, scale = 2)
   private BigDecimal thigh;

   @Column(precision = 5, scale = 2)
   private BigDecimal calf;
}
