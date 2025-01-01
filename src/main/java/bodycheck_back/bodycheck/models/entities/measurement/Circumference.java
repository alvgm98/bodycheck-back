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

   @Column(precision = 3, scale = 1)
   private BigDecimal neck;

   @Column(precision = 3, scale = 1)
   private BigDecimal chest;

   @Column(precision = 3, scale = 1)
   private BigDecimal armRelaxed;

   @Column(precision = 3, scale = 1)
   private BigDecimal armFlexed;

   @Column(precision = 3, scale = 1)
   private BigDecimal waist;

   @Column(precision = 3, scale = 1)
   private BigDecimal hip;

   @Column(precision = 3, scale = 1)
   private BigDecimal thigh;

   @Column(precision = 3, scale = 1)
   private BigDecimal calf;

}
