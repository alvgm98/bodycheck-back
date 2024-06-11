package bodycheck_back.bodycheck.models.entities.measurement;

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

   private Integer triceps;
   private Integer biceps;
   private Integer subscapular;
   private Integer suprailiac;
   private Integer iliacCrest;
   private Integer abdominal;
   private Integer thigh;
   private Integer calf;
}
