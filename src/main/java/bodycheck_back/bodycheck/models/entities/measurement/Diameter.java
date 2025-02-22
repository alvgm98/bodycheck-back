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
@Table(name = "diameters")
public class Diameter {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(precision = 5, scale = 2)
   private BigDecimal biacromial;

   @Column(precision = 5, scale = 2)
   private BigDecimal biIliacCrest;

   @Column(precision = 5, scale = 2)
   private BigDecimal humeralBicondylar;

   @Column(precision = 5, scale = 2)
   private BigDecimal femoralBicondylar;

   @Column(precision = 5, scale = 2)
   private BigDecimal bistyloid;

   @Column(precision = 5, scale = 2)
   private BigDecimal bimalleolar;

   @Column(precision = 5, scale = 2)
   private BigDecimal transverseThoracic;

   @Column(precision = 5, scale = 2)
   private BigDecimal anteroposteriorThoracic;
}
